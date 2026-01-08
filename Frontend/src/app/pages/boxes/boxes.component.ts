import { Component, OnInit, OnDestroy } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormControl, ReactiveFormsModule } from "@angular/forms";
import { lastValueFrom, Subject } from "rxjs";
import { debounceTime, takeUntil } from "rxjs/operators";
import { MatTableModule } from "@angular/material/table";
import { MatButtonModule } from "@angular/material/button";
import { MatIconModule } from "@angular/material/icon";
import { MatDialogModule, MatDialog } from "@angular/material/dialog";
import { MatCardModule } from "@angular/material/card";
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { Box } from "$core/types";
import { BoxService } from "$core/services/box.service";
import { SearchService } from "$core/services/search/search.service";
import { LoadingService } from "$core/services/loading/loading.service";
import { BoxDialogComponent } from "./box-dialog/box-dialog.component";

@Component({
	selector: "app-boxes",
	standalone: true,
	imports: [
		CommonModule,
		ReactiveFormsModule,
		MatTableModule,
		MatButtonModule,
		MatIconModule,
		MatDialogModule,
		MatCardModule,
		MatProgressSpinnerModule,
		MatFormFieldModule,
		MatInputModule,
	],
	templateUrl: "./boxes.component.html",
})
export class BoxesComponent implements OnInit, OnDestroy {
	displayedColumns: string[] = [
		"id",
		"name",
		"description",
		"latitude",
		"longitude",
		"actions",
	];
	allBoxes: Box[] = [];
	boxes: Box[] = [];
	loading$ = this.loadingService.getLoadingState("boxes");

	searchControl = this.searchService.createSearchControl();
	boxControl = new FormControl();

	private destroy$ = new Subject<void>();

	constructor(
		private dialog: MatDialog,
		private boxService: BoxService,
		private searchService: SearchService,
		private loadingService: LoadingService
	) {}

	ngOnInit() {
		this.loadBoxes();
		this.setupSearch();
	}

	ngOnDestroy() {
		this.destroy$.next();
		this.destroy$.complete();
	}

	private setupSearch() {
		this.searchControl.valueChanges
			.pipe(debounceTime(300), takeUntil(this.destroy$))
			.subscribe(() => this.filterBoxes());

		this.boxControl.valueChanges
			.pipe(takeUntil(this.destroy$))
			.subscribe(() => this.filterBoxes());
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
					this.allBoxes = data;
					this.filterBoxes();
				},
			});
	}

	filterBoxes() {
		let filtered = [...this.allBoxes];

		if (this.searchControl.value) {
			filtered = this.searchService.filterByTerm(
				filtered,
				this.searchControl.value,
				(box, term) => box.name.toLowerCase().includes(term)
			);
		}

		if (this.boxControl.value) {
			filtered = filtered.filter(r => r.id === this.boxControl.value.id);
		}

		this.boxes = filtered;
	}

	openDialog(box?: Box) {
		const dialogRef = this.dialog.open(BoxDialogComponent, {
			width: "500px",
			data: box || null,
		});

		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				this.loadBoxes();
			}
		});
	}

	deleteBox(box: Box) {
		if (confirm("Are you sure you want to delete this box?")) {
			this.loadingService
				.executeWithLoading(
					"delete-box",
					() => lastValueFrom(this.boxService.deleteBox(box.id)),
					"Failed to delete box"
				)
				.then(() => {
					this.loadBoxes();
				});
		}
	}
}
