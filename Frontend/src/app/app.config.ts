import {
	ApplicationConfig,
	APP_INITIALIZER,
	importProvidersFrom,
} from "@angular/core";
import { provideRouter } from "@angular/router";

import { routes } from "./app.routes";
import { provideAnimationsAsync } from "@angular/platform-browser/animations/async";
import { MatSnackBarModule } from "@angular/material/snack-bar";

import {
	provideHttpClient,
	withInterceptorsFromDi,
} from "@angular/common/http";
import { ApiService } from "$core/services/api.service";
import { UserStore } from "$core/services/user-store.service";
import { firstValueFrom } from "rxjs";

export const appConfig: ApplicationConfig = {
	providers: [
		provideRouter(routes),
		provideAnimationsAsync(),
		{
			provide: APP_INITIALIZER,
			useFactory: initAuthFactory,
			deps: [ApiService, UserStore],
			multi: true,
		},
		importProvidersFrom(MatSnackBarModule),
		provideHttpClient(withInterceptorsFromDi()),
	],
};

function initAuthFactory(api: ApiService, userStore: UserStore) {
	return () => {
		return firstValueFrom(api.get("/auth/me"))
			.then((user: any) => {
				userStore.setUser(user);
			})
			.catch(() => {});
	};
}
