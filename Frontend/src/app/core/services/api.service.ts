import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { Observable } from "rxjs";

export interface ApiOptions {
	params?: HttpParams | { [param: string]: string | string[] };
	headers?: { [header: string]: string | string[] };
	reportProgress?: boolean;
	responseType?: "json";
	withCredentials?: boolean;
}

@Injectable({ providedIn: "root" })
export class ApiService {
	private baseUrl = "http://localhost:8080/api";

	constructor(private http: HttpClient) {}

	get<T>(url: string, options?: ApiOptions): Observable<T> {
		const opts = {
			withCredentials: true,
			observe: "body" as const,
			...(options || {}),
		};
		return this.http.get<T>(this.baseUrl + url, opts);
	}

	post<T>(url: string, body: any, options?: ApiOptions): Observable<T> {
		const opts = {
			withCredentials: true,
			observe: "body" as const,
			...(options || {}),
		};
		return this.http.post<T>(this.baseUrl + url, body, opts);
	}

	put<T>(url: string, body: any, options?: ApiOptions): Observable<T> {
		const opts = {
			withCredentials: true,
			observe: "body" as const,
			...(options || {}),
		};
		return this.http.put<T>(this.baseUrl + url, body, opts);
	}

	delete<T>(url: string, options?: ApiOptions): Observable<T> {
		const opts = {
			withCredentials: true,
			observe: "body" as const,
			...(options || {}),
		};
		return this.http.delete<T>(this.baseUrl + url, opts);
	}
}
