import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs";
import { User } from "../types";

@Injectable({ providedIn: "root" })
export class UserStore {
	private _user$ = new BehaviorSubject<User | null>(null);
	user$ = this._user$.asObservable();

	get user() {
		return this._user$.getValue();
	}

	setUser(user: User) {
		this._user$.next(user);
	}

	clear() {
		this._user$.next(null);
	}
}
