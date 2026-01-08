# Frontend BoiteALivre

Ce projet est une application Angular pour l’interface utilisateur des Boîtes à Livres.

## Prérequis

- **Node.js** (version recommandée : 18+)
- **npm** (installé avec Node.js)
- **Angular CLI** (optionnel, pour les commandes en ligne)
- **Un navigateur web moderne** (Chrome, Firefox, Edge…)

## Installation

### 1. Cloner le projet

```bash
git clone <url-du-repo>
```

### 2. Installer les dépendances npm

Dans le dossier du projet :

```bash
cd Frontend
bun install # ou npm install ou pnpm install ou yarn install
```

### 3. Configurer l’environnement

Si besoin, adaptez les paramètres dans ces fichiers :
- `src/app/core/services/api.service.ts` pour l’URL de l’API backend

### 4. Lancer l’application en développement

```bash
bun start # ou npm start ou pnpm start ou yarn start
```
ou
```bash
ng serve
```

L’application sera accessible sur [http://localhost:4200](http://localhost:4200).

### 5. Accéder à l’application

Ouvrez votre navigateur et rendez-vous sur [http://localhost:4200](http://localhost:4200).

## Structure du projet

- `src/app/` : code source principal
  - `core/` : modules, services, composants partagés
  - `pages/` : pages principales (auth, home, boxes, reservations)
  - `assets/` : fichiers statiques (images, icônes…)

## Commandes utiles

**Lancer en développement** :
  - npm : `npm run start` ou `ng serve`
  - pnpm : `pnpm start` ou `pnpm ng serve`
  - yarn : `yarn start` ou `yarn ng serve`
  - bun : `bun start` ou `bun ng serve`

**Build pour production** :
  - npm : `npm run build` ou `ng build`
  - pnpm : `pnpm build` ou `pnpm ng build`
  - yarn : `yarn build` ou `yarn ng build`
  - bun : `bun build` ou `bun ng build`

**Tests unitaires** :
  - npm : `npm run test` ou `ng test`
  - pnpm : `pnpm test` ou `pnpm ng test`
  - yarn : `yarn test` ou `yarn ng test`
  - bun : `bun test` ou `bun ng test`

**Lint** :
  - npm : `npm run lint` ou `ng lint`
  - pnpm : `pnpm lint` ou `pnpm ng lint`
  - yarn : `yarn lint` ou `yarn ng lint`
  - bun : `bun lint` ou `bun ng lint`

## Notes

- L’URL de l’API backend doit être correcte dans la configuration pour que l’application fonctionne.
- Pour modifier le style, éditez les fichiers CSS dans `src/app/` ou `src/styles.css`.
- Les dépendances sont gérées via `package.json`.
