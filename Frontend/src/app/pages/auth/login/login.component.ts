import { Component } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ReactiveFormsModule, FormBuilder, FormGroup } from "@angular/forms";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatButtonModule } from "@angular/material/button";
import { MatCardModule } from "@angular/material/card";
import { MatIconModule } from "@angular/material/icon";
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";
import { RouterModule, Router, ActivatedRoute } from "@angular/router";
import { ApiService } from "$core/services/api.service";
import { UserStore } from "$core/services/user-store.service";
import { ToastService } from "$core/services/toast.service";
import { LoginValidationService } from "$core/validation/login-validation.service";
import { switchMap, finalize } from "rxjs/operators";

@Component({
	selector: "app-login",
	standalone: true,
	imports: [
		CommonModule,
		ReactiveFormsModule,
		MatFormFieldModule,
		MatInputModule,
		MatButtonModule,
		MatCardModule,
		MatIconModule,
		MatProgressSpinnerModule,
		RouterModule,
	],
	templateUrl: "./login.component.html",
})
export class LoginComponent {
	form: FormGroup;
	submitting = false;

	constructor(
		private fb: FormBuilder,
		private api: ApiService,
		private userStore: UserStore,
		private toast: ToastService,
		private router: Router,
		private route: ActivatedRoute,
		private validationService: LoginValidationService
	) {
		this.form = this.fb.group({
			email: [""],
			password: [""],
		});
	}

	login() {
		const validation = this.validationService.validate(this.form.value);

		if (!validation.success) {
			Object.entries(validation.errors).forEach(([key, msg]) => {
				const control = this.form.get(key);
				control?.setErrors({ zodError: msg });
				control?.markAsTouched();
			});
			return;
		}

		this.submitting = true;
		const credentials = validation.data;

		this.api
			.post<any>("/auth/login", credentials)
			.pipe(
				switchMap(() => this.api.get<any>("/auth/me")),
				finalize(() => (this.submitting = false))
			)
			.subscribe({
				next: user => {
					this.userStore.setUser(user);
					this.toast.success("You have logged in successfully.");
					const returnUrl =
						this.route.snapshot.queryParamMap.get("returnUrl") || "/";
					this.router.navigateByUrl(returnUrl);
				},
				error: err => {
					const msg =
						err.error?.message || "An error occurred during login.";
					this.toast.error(msg);
				},
			});
	}

	getErrorMessage(field: string): string {
		return this.form.get(field)?.getError("zodError") || "";
	}
}
