import { Injectable } from "@angular/core";
import { ValidationResult } from "../types";
import { z } from "zod";

const registerSchema = z
	.object({
		username: z.string().min(1, "Username is required").trim(),
		first_name: z.string().min(1, "First name is required").trim(),
		last_name: z.string().min(1, "Last name is required").trim(),
		email: z.email("Invalid email format"),
		password: z
			.string()
			.min(8, "Password must contain at least 8 characters")
			.regex(
				/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,}$/,
				"Password must contain at least 8 characters, 1 uppercase, 1 lowercase, 1 digit and 1 special character."
			),
		password_confirmation: z.string().min(1, "Confirmation is required"),
	})
	.refine(data => data.password === data.password_confirmation, {
		message: "Passwords do not match",
		path: ["password_confirmation"],
	});

type RegisterData = z.infer<typeof registerSchema>;

@Injectable({
	providedIn: "root",
})
export class RegisterValidationService {
	validate(data: unknown): ValidationResult<RegisterData> {
		const result = registerSchema.safeParse(data);

		if (result.success) {
			return { success: true, data: result.data };
		}

		const errors: { [key: string]: string } = {};
		result.error.issues.forEach(issue => {
			const field = String(issue.path[issue.path.length - 1]);
			errors[field] = issue.message;
		});

		return { success: false, errors };
	}
}
