import { Component, OnInit, OnDestroy } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ReactiveFormsModule, FormControl } from "@angular/forms";
import { lastValueFrom, Subject } from "rxjs";
import { takeUntil, debounceTime } from "rxjs/operators";
import { MatTableModule } from "@angular/material/table";
import { MatButtonModule } from "@angular/material/button";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatSelectModule } from "@angular/material/select";
import { MatCardModule } from "@angular/material/card";
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";
import { MatIconModule } from "@angular/material/icon";
import { MatAutocompleteModule } from "@angular/material/autocomplete";
import { Reservation, Box } from "$core/types";
import { ReservationService } from "$core/services/reservation.service";
import { BoxService } from "$core/services/box.service";
import { SearchService } from "$core/services/search/search.service";
import { LoadingService } from "$core/services/loading/loading.service";

@Component({
	selector: "app-reservations",
	standalone: true,
	imports: [
		CommonModule,
		ReactiveFormsModule,
		MatTableModule,
		MatButtonModule,
		MatFormFieldModule,
		MatInputModule,
		MatSelectModule,
		MatCardModule,
		MatProgressSpinnerModule,
		MatIconModule,
		MatAutocompleteModule,
	],
	templateUrl: "./reservations.component.html",
})
export class ReservationsComponent implements OnInit, OnDestroy {
	reservations: Reservation[] = [];
	allReservations: Reservation[] = [];
	boxes: Box[] = [];
	loading$ = this.loadingService.getLoadingState("reservations");

	displayedColumns: string[] = [
		"username",
		"boxName",
		"reservation",
		"actions",
	];

	searchControl = this.searchService.createSearchControl();
	boxControl = new FormControl();
	searchValue = "";
	filteredBoxes: Box[] = [];

	private destroy$ = new Subject<void>();

	constructor(
		private reservationService: ReservationService,
		private boxService: BoxService,
		private searchService: SearchService,
		private loadingService: LoadingService
	) {}

	ngOnInit() {
		this.loadBoxes();
		this.loadReservations();
		this.setupSearch();
	}

	ngOnDestroy() {
		this.destroy$.next();
		this.destroy$.complete();
	}

	private setupSearch() {
		this.searchControl.valueChanges
			.pipe(debounceTime(300), takeUntil(this.destroy$))
			.subscribe(() => this.filterReservations());

		this.boxControl.valueChanges
			.pipe(takeUntil(this.destroy$))
			.subscribe(() => this.filterReservations());
	}

	loadBoxes() {
		this.loadingService
			.wrapObservable(
				"boxes",
				this.boxService.getAllBoxes(),
				"Failed to load boxes"
			)
			.pipe(takeUntil(this.destroy$))
			.subscribe({
				next: (data: Box[]) => {
					this.boxes = data;
					this.filteredBoxes = [...this.boxes];
				},
			});
	}

	loadReservations() {
		this.loadingService
			.wrapObservable(
				"reservations",
				this.reservationService.getAllReservations(),
				"Failed to load reservations"
			)
			.pipe(takeUntil(this.destroy$))
			.subscribe({
				next: (data: Reservation[]) => {
					this.allReservations = data;
					this.filterReservations();
				},
			});
	}

	filterReservations() {
		let filtered = [...this.allReservations];

		if (this.searchControl.value) {
			filtered = this.searchService.filterByTerm(
				filtered,
				this.searchControl.value,
				(reservation, term) =>
					reservation.username.toLowerCase().includes(term) ||
					reservation.boxName.toLowerCase().includes(term)
			);
		}

		if (this.boxControl.value) {
			filtered = filtered.filter(r => r.boxId === this.boxControl.value.id);
		}

		this.reservations = filtered;
	}

	displayBox(box: Box): string {
		return box ? box.name : "";
	}

	onInputChange(event: Event) {
		this.searchValue = (event.target as HTMLInputElement).value;
		this.onBoxSearchChange();
	}

	onBoxSearchChange() {
		this.filteredBoxes = this.searchService.filterByProperty(
			this.boxes,
			"name",
			this.searchValue
		);
	}

	deleteReservation(reservation: Reservation) {
		if (confirm("Are you sure you want to delete this reservation?")) {
			this.loadingService
				.executeWithLoading(
					"delete-reservation",
					() =>
						lastValueFrom(
							this.reservationService.deleteReservation(
								reservation.userId,
								reservation.boxId
							)
						),
					"Failed to delete reservation"
				)
				.then(() => {
					this.loadReservations();
				});
		}
	}
}
