import { Routes } from "@angular/router";
import { LoginComponent } from "./pages/auth/login/login.component";
import { RegisterComponent } from "./pages/auth/register/register.component";
import { guestGuard } from "$core/guards/guest.guard";
import { authGuard } from "$core/guards/auth.guard";
import { HomeComponent } from "./pages/home/home.component";
import { BoxesComponent } from "./pages/boxes/boxes.component";
import { ReservationsComponent } from "./pages/reservations/reservations.component";

export const routes: Routes = [
	{
		path: "auth/login",
		component: LoginComponent,
		canActivate: [guestGuard],
	},
	{
		path: "auth/register",
		component: RegisterComponent,
		canActivate: [guestGuard],
	},

	{ path: "", component: HomeComponent, canActivate: [authGuard] },
	{ path: "boxes", component: BoxesComponent, canActivate: [authGuard] },
	{
		path: "reservations",
		component: ReservationsComponent,
		canActivate: [authGuard],
	},

	{ path: "**", redirectTo: "" },
];
