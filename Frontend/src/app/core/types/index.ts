export interface User {
	id: number;
	username: string;
	firstName: string;
	lastName: string;
	email: string;
	roles?: string[];
}

export interface Box {
	id: number;
	name: string;
	description?: string | null;
	quantity: number;
	coordinateId: number | null;
	latitude: number;
	longitude: number;
}

export interface Reservation {
	userId: number;
	username: string;
	boxId: number;
	boxName: string;
	reservation: number;
}

export type ValidationResult<T> =
	| { success: true; data: T }
	| { success: false; errors: { [key: string]: string } };
