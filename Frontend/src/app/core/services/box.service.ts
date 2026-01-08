import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ApiService } from "./api.service";
import { Box } from "../types";

@Injectable({
	providedIn: "root",
})
export class BoxService {
	constructor(private api: ApiService) {}

	getAllBoxes(): Observable<Box[]> {
		return this.api.get<Box[]>("/boxes");
	}

	createBox(box: Omit<Box, "id" | "coordinateId">): Observable<Box> {
		return this.api.post<Box>("/boxes", box);
	}

	updateBox(id: number, box: Partial<Box>): Observable<Box> {
		return this.api.put<Box>(`/boxes/${id}`, box);
	}

	deleteBox(id: number): Observable<void> {
		return this.api.delete<void>(`/boxes/${id}`);
	}
}
