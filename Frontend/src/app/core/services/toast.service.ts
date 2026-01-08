import { Injectable } from "@angular/core";
import { MatSnackBar } from "@angular/material/snack-bar";

@Injectable({ providedIn: "root" })
export class ToastService {
	constructor(private snackbar: MatSnackBar) {}

	success(message: string, duration = 4000) {
		this.snackbar.open(message, "OK", {
			duration,
			panelClass: ["mat-snack-bar-success"],
		});
	}

	error(message: string, duration = 6000) {
		this.snackbar.open(message, "OK", {
			duration,
			panelClass: ["mat-snack-bar-error"],
		});
	}

	info(message: string, duration = 4000) {
		this.snackbar.open(message, undefined, {
			duration,
			panelClass: ["mat-snack-bar-info"],
		});
	}
}
