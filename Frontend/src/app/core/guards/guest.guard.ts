import { inject } from "@angular/core";
import { CanActivateFn, Router } from "@angular/router";
import { UserStore } from "../services/user-store.service";

export const guestGuard: CanActivateFn = () => {
	const userStore = inject(UserStore);
	const router = inject(Router);

	const user = userStore.user;
	if (user) {
		return router.parseUrl("/");
	}
	return true;
};
