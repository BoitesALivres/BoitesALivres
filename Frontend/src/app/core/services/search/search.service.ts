import { Injectable } from "@angular/core";
import { FormControl } from "@angular/forms";

@Injectable({
	providedIn: "root",
})
export class SearchService {
	createSearchControl(): FormControl {
		return new FormControl("");
	}

	filterByTerm<T>(
		items: T[],
		searchTerm: string,
		filterFn?: (item: T, term: string) => boolean
	): T[] {
		if (!searchTerm || searchTerm.trim() === "") {
			return items;
		}

		const term = searchTerm.toLowerCase().trim();

		if (filterFn) {
			return items.filter(item => filterFn(item, term));
		}

		return items.filter(item => {
			if (typeof item === "object" && item !== null) {
				return Object.values(item as Record<string, unknown>).some(
					value =>
						typeof value === "string" && value.toLowerCase().includes(term)
				);
			}
			return false;
		});
	}

	filterByProperty<T>(items: T[], property: keyof T, searchTerm: string): T[] {
		if (!searchTerm || searchTerm.trim() === "") {
			return items;
		}

		const term = searchTerm.toLowerCase().trim();
		return items.filter(item => {
			const value = item[property];
			return typeof value === "string" && value.toLowerCase().includes(term);
		});
	}
}
