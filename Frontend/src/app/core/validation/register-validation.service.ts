import { Injectable } from "@angular/core";
import { ValidationResult } from "../types";
import { z } from "zod";

const registerSchema = z
	.object({
		username: z.string().min(1, "Le nom d'utilisateur est requis").trim(),
		first_name: z.string().min(1, "Le prénom est requis").trim(),
		last_name: z.string().min(1, "Le nom est requis").trim(),
		email: z.email("Format email invalide"),
		password: z
			.string()
			.min(8, "Le mot de passe doit contenir au moins 8 caractères")
			.regex(
				/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,}$/,
				"Le mot de passe doit contenir min. 1 majuscule, 1 minuscule, 1 chiffre et 1 caractère spécial."
			),
		password_confirmation: z.string().min(1, "La confirmation est requise"),
	})
	.refine(data => data.password === data.password_confirmation, {
		message: "Les mots de passe ne correspondent pas",
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
