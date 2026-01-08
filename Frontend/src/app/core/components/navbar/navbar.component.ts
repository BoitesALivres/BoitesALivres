import { Component } from "@angular/core";
import { CommonModule } from "@angular/common";
import { MatToolbarModule } from "@angular/material/toolbar";
import { MatButtonModule } from "@angular/material/button";
import { MatIconModule } from "@angular/material/icon";
import { RouterModule } from "@angular/router";
import { UserStore } from "$core/services/user-store.service";
import { ApiService } from "$core/services/api.service";
import { ToastService } from "$core/services/toast.service";
import { Router } from "@angular/router";

@Component({
	selector: "app-navbar",
	standalone: true,
	imports: [
		CommonModule,
		MatToolbarModule,
		MatButtonModule,
		MatIconModule,
		RouterModule,
	],
	templateUrl: "./navbar.component.html",
})
export class NavbarComponent {
	constructor(
		public userStore: UserStore,
		private api: ApiService,
		private toast: ToastService,
		private router: Router
	) {}

	logout() {
		this.api.post<void>("/auth/logout", {}).subscribe({
			next: () => {
				this.userStore.clear();
				this.toast.success("Logged out");
				this.router.navigate(["/auth/login"]).then(() => {});
			},
			error: () => {
				this.toast.error("Logout failed");
				this.userStore.clear();
				this.router.navigate(["/auth/login"]).then(() => {});
			},
		});
	}
}
