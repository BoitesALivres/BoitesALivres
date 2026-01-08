import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable } from "rxjs";
import { ToastService } from "../toast.service";

export interface LoadingState {
	loading: boolean;
	error: string | null;
}

@Injectable({
	providedIn: "root",
})
export class LoadingService {
	private loadingStates = new Map<string, BehaviorSubject<LoadingState>>();

	constructor(private toast: ToastService) {}

	getLoadingState(key: string): Observable<LoadingState> {
		if (!this.loadingStates.has(key)) {
			this.loadingStates.set(
				key,
				new BehaviorSubject<LoadingState>({
					loading: false,
					error: null,
				})
			);
		}
		return this.loadingStates.get(key)!.asObservable();
	}

	setLoading(key: string, loading: boolean, error: string | null = null): void {
		const state = this.loadingStates.get(key);
		if (state) {
			state.next({ loading, error });
		} else {
			this.loadingStates.set(
				key,
				new BehaviorSubject<LoadingState>({ loading, error })
			);
		}

		if (error) {
			this.toast.error(error);
		}
	}

	startLoading(key: string): void {
		this.setLoading(key, true, null);
	}

	stopLoading(key: string): void {
		this.setLoading(key, false, null);
	}

	setError(key: string, error: string): void {
		this.setLoading(key, false, error);
	}

	async executeWithLoading<T>(
		key: string,
		operation: () => Promise<T>,
		errorMessage: string = "Operation failed"
	): Promise<T | null> {
		try {
			this.startLoading(key);
			const result = await operation();
			this.stopLoading(key);
			return result;
		} catch (error) {
			this.setError(key, errorMessage);
			return null;
		}
	}

	wrapObservable<T>(
		key: string,
		observable: Observable<T>,
		errorMessage: string = "Operation failed"
	): Observable<T> {
		this.startLoading(key);
		return new Observable<T>(subscriber => {
			const subscription = observable.subscribe({
				next: value => {
					this.stopLoading(key);
					subscriber.next(value);
				},
				error: error => {
					this.setError(key, errorMessage);
					subscriber.error(error);
				},
				complete: () => {
					subscriber.complete();
				},
			});

			return () => subscription.unsubscribe();
		});
	}
}
