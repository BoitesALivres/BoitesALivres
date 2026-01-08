import { Injectable } from "@angular/core";
import { Box, ValidationResult } from "../types";
import { z } from "zod";

const boxSchema = z.object({
	id: z.number("ID must be a number").min(0, "ID must be non-negative"),

	name: z
		.string()
		.min(1, "Name is required")
		.min(3, "Name must be at least 3 characters")
		.max(100, "Name cannot exceed 100 characters"),

	description: z
		.string()
		.max(500, "Description cannot exceed 500 characters")
		.optional()
		.nullable(),

	quantity: z.coerce
		.number()
		.int("Quantity must be an integer")
		.min(0, "Quantity cannot be negative")
		.max(1000, "Quantity cannot exceed 1000"),

	latitude: z.coerce
		.number()
		.min(-90, "Latitude must be between -90 and 90")
		.max(90, "Latitude must be between -90 and 90"),

	longitude: z.coerce
		.number()
		.min(-180, "Longitude must be between -180 and 180")
		.max(180, "Longitude must be between -180 and 180"),
});

@Injectable({
	providedIn: "root",
})
export class BoxUpdateValidationService {
	validate(
		data: Omit<Box, "coordinateId">
	): ValidationResult<Omit<Box, "coordinateId">> {
		const result = boxSchema.safeParse(data);

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
