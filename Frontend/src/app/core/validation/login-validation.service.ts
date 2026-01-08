import { ValidationResult } from "$core/types";
import { Injectable } from "@angular/core";
import { z } from "zod";

const loginSchema = z.object({
	email: z.string().email("Invalid email format"),
	password: z.string().min(1, "Password is required"),
});

type LoginData = z.infer<typeof loginSchema>;

@Injectable({
	providedIn: "root",
})
export class LoginValidationService {
	validate(data: unknown): ValidationResult<LoginData> {
		const result = loginSchema.safeParse(data);

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
