import { Component, Inject } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ReactiveFormsModule, FormBuilder, FormGroup } from "@angular/forms";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatButtonModule } from "@angular/material/button";
import {
	MatDialogModule,
	MatDialogRef,
	MAT_DIALOG_DATA,
} from "@angular/material/dialog";
import { ToastService } from "$core/services/toast.service";
import { Box } from "$core/types";
import { BoxService } from "$core/services/box.service";
import { BoxCreationValidationService } from "$core/validation/boxCreation-validation.service";
import { BoxUpdateValidationService } from "$core/validation/boxUpdate-validation.service";

@Component({
	selector: "app-box-dialog",
	standalone: true,
	imports: [
		CommonModule,
		ReactiveFormsModule,
		MatFormFieldModule,
		MatInputModule,
		MatButtonModule,
		MatDialogModule,
	],
	templateUrl: "./box-dialog.component.html"
})
export class BoxDialogComponent {
	form: FormGroup;
	isEdit: boolean;

	constructor(
		@Inject(MAT_DIALOG_DATA) public data: Box | null,
		private dialogRef: MatDialogRef<BoxDialogComponent>,
		private fb: FormBuilder,
		private toast: ToastService,
		private boxService: BoxService,
		private creationVal: BoxCreationValidationService,
		private updateVal: BoxUpdateValidationService
	) {
		this.isEdit = !!data;

		this.form = this.fb.group({
			id: [data?.id ?? 0],
			name: [data?.name ?? ""],
			description: [data?.description ?? ""],
			quantity: [data?.quantity ?? 0],
			latitude: [data?.latitude ?? null],
			longitude: [data?.longitude ?? null],
			coordinateId: [data?.coordinateId ?? null],
		});
	}

	save() {
		const raw = this.form.getRawValue();

		const validation = this.isEdit
			? this.updateVal.validate(raw)
			: this.creationVal.validate(raw);

		if (!validation.success) {
			Object.entries(validation.errors).forEach(([key, msg]) => {
				const control = this.form.get(key);
				control?.setErrors({ zodError: msg });
				control?.markAsTouched();
			});
			return;
		}

		const request$ = this.isEdit
			? this.boxService.updateBox(this.data!.id, validation.data)
			: this.boxService.createBox(validation.data);

		request$.subscribe({
			next: () => {
				this.toast.success(this.isEdit ? "Box updated" : "Box created");
				this.dialogRef.close(true);
			},
			error: () => this.toast.error("An error occurred"),
		});
	}

	getErrorMessage(field: string): string {
		return this.form.get(field)?.getError("zodError") || "";
	}

	cancel() {
		this.dialogRef.close();
	}
}
