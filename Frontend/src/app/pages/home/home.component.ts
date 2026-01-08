import {
	Component,
	OnInit,
	AfterViewInit,
	OnDestroy,
	ChangeDetectorRef,
	signal,
} from "@angular/core";
import { CommonModule } from "@angular/common";
import { RouterModule } from "@angular/router";
import { FormsModule } from "@angular/forms";
import { Subject } from "rxjs";
import { debounceTime } from "rxjs/operators";
import { MatCardModule } from "@angular/material/card";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatIconModule } from "@angular/material/icon";
import { MatButtonModule } from "@angular/material/button";
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";
import { UserStore } from "$core/services/user-store.service";
import { ToastService } from "$core/services/toast.service";
import { BoxService } from "$core/services/box.service";
import { ReservationService } from "$core/services/reservation.service";
import * as L from "leaflet";
import { Box, Reservation } from "$core/types";

@Component({
	selector: "app-home",
	standalone: true,
	imports: [
		CommonModule,
		RouterModule,
		FormsModule,
		MatCardModule,
		MatFormFieldModule,
		MatInputModule,
		MatIconModule,
		MatButtonModule,
		MatProgressSpinnerModule,
	],
	templateUrl: "./home.component.html",
	styleUrls: ["./home.component.css"],
})
export class HomeComponent implements OnInit, AfterViewInit, OnDestroy {
	boxes = signal<Omit<Box, "coordinateId">[]>([]);
	reservations: Reservation[] = [];
	loading = true;
	selectedBox: Omit<Box, "coordinateId"> | null = null;
	searchTerm = "";

	get filteredBoxes(): Omit<Box, "coordinateId">[] {
		const boxes = this.boxes();
		const term = this.searchTerm.toLowerCase();
		if (!term) {
			return boxes;
		}
		return boxes.filter(box => {
			const name = box.name?.toLowerCase() || "";
			const description = box.description?.toLowerCase() || "";
			return name.includes(term) || description.includes(term);
		});
	}

	private map: L.Map | undefined;
	private markers: L.Layer[] = [];
	private boxMarkers: Map<number, L.CircleMarker> = new Map();
	private searchSubject = new Subject<string>();

	onSearchChange(value: string) {
		this.searchTerm = value;
		this.searchSubject.next(value);
	}

	constructor(
		public userStore: UserStore,
		private toast: ToastService,
		private cdr: ChangeDetectorRef,
		private boxService: BoxService,
		private reservationService: ReservationService
	) { }

	ngOnInit() {
		this.loadData();
		this.searchSubject.pipe(debounceTime(300)).subscribe(() => {
			this.updateMarkers();
			this.cdr.detectChanges();
		});
	}

	loadData() {
		this.loading = true;
		this.boxService.getAllBoxes().subscribe({
			next: boxes => {
				this.boxes.set(boxes);
				this.loading = false;
				this.updateMarkers();
			},
			error: () => {
				this.toast.error("Failed to load boxes");
				this.loading = false;
			},
		});

		this.userStore.user$.subscribe(user => {
			if (user) {
				this.reservationService.getAllReservations().subscribe({
					next: reservations => {
						this.reservations = reservations.filter(r => r.userId === user.id);
						this.updateMarkers();
					},
					error: () => {
						this.toast.error("Failed to load reservations");
					},
				});
			}
		});
	}

	ngAfterViewInit() {
		this.initMap();
	}

	ngOnDestroy() {
		if (this.map) {
			this.map.remove();
		}
		this.searchSubject.complete();
	}

	selectBox(box: Omit<Box, "coordinateId">) {
		this.selectedBox = box;
		if (this.map) {
			this.map.setView([box.latitude, box.longitude], 15);
		}
		const marker = this.boxMarkers.get(box.id);
		if (marker) {
			marker.openPopup();
			this.boxes.set([box, ...this.boxes().filter(b => b.id !== box.id)]);
		}
	}

	hasReservation(boxId: number): boolean {
		return this.reservations.some(r => r.boxId === boxId);
	}

	makeReservation(box: Omit<Box, "coordinateId">, event: Event) {
		event.stopPropagation();
		this.userStore.user$.subscribe(user => {
			if (user) {
				if (this.hasReservation(box.id)) {
					this.reservationService.deleteReservation(user.id, box.id).subscribe({
						next: () => {
							this.toast.success("Reservation cancelled successfully");
							this.loadData();
						},
						error: () => {
							this.toast.error("Failed to cancel reservation");
						},
					});
				} else {
					this.reservationService
						.createReservation({
							userId: user.id,
							boxId: box.id,
							reservation: 1,
						})
						.subscribe({
							next: () => {
								this.toast.success("Reservation made successfully");
								this.loadData();
							},
							error: () => {
								this.toast.error("Failed to make reservation");
							},
						});
				}
			}
		});
	}

	private initMap() {
		this.map = L.map("map", {
			zoomControl: false,
			attributionControl: false
		}).setView([46.6, 1.89], 6);

		L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png").addTo(
			this.map
		);
	}

	private updateMarkers() {
		if (!this.map) return;

		this.markers.forEach(marker => this.map!.removeLayer(marker));
		this.markers = [];
		this.boxMarkers.clear();

		const boxesToShow = this.filteredBoxes;
		boxesToShow.forEach(box => {
			const isReserved = this.hasReservation(box.id);
			const color = isReserved ? "orange" : "blue";
			const marker = L.circleMarker([box.latitude, box.longitude], {
				color: color,
				fillColor: color,
				fillOpacity: 0.8,
				radius: 8,
			}).addTo(this.map!);

			const popupContent = box.description
				? `<b>${box.name}</b><br>${box.description}`
				: `<b>${box.name}</b>`;
			marker.bindPopup(popupContent);
			marker.on("click", () => this.selectBox(box));
			this.markers.push(marker);
			this.boxMarkers.set(box.id, marker);
		});

		if (this.markers.length > 0) {
			const group = L.featureGroup(this.markers);
			this.map!.fitBounds(group.getBounds());
		}
	}
}
