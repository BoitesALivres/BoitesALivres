import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ApiService } from "./api.service";
import { Reservation } from "../types";

@Injectable({
	providedIn: "root",
})
export class ReservationService {
	constructor(private api: ApiService) {}

	getAllReservations(): Observable<Reservation[]> {
		return this.api.get<Reservation[]>("/reservations");
	}

	createReservation(reservation: {
		userId: number;
		boxId: number;
		reservation: number;
	}): Observable<Reservation> {
		return this.api.post<Reservation>("/reservations", reservation);
	}

	deleteReservation(userId: number, boxId: number): Observable<void> {
		return this.api.delete<void>(`/reservations/${userId}/${boxId}`);
	}
}
