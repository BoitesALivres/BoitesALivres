# Backend BoiteALivre

Ce projet est une API Spring Boot connectée à une base MariaDB, utilisant un fichier `.env` pour la configuration.

## Prérequis

- **Java 21** ou supérieur
- **Maven**
- **MariaDB** (ou MySQL)
- **IntelliJ IDEA** (ou tout autre IDE Java)
- **HeidiSQL** (ou tout autre client SQL graphique)

## Installation

### 1. Cloner le projet

```bash
git clone <url-du-repo>
```

### 2. Configurer le fichier `.env`

Copiez le fichier `.env.example` en `.env` et adaptez les valeurs si besoin :

```bash
cp .env.example .env
```

Exemple de contenu :

```env
DB_HOST=localhost
DB_PORT=3306
DB_NAME=boitesalivres
DB_USER=root
DB_PASSWORD=root

AUTH_EXPIRATION=86400000
AUTH_SECRET=<MY_SUPER_SECRET_KEY>

HIBERNATE_DDL_AUTO=update
HIBERNATE_DIALECT=org.hibernate.dialect.MariaDBDialect
SHOW_SQL=true
APP_NAME=Backend
```

Il faut générer une clé secrète pour `AUTH_SECRET` (une chaîne aléatoire longue, minimum 32 caractères avec des minuscules et des chiffres. Vous pouvez aller sur [ce site](https://jwtsecrets.com/#generator) pour en générer une).

### 3. Installer les dépendances Maven

Dans le dossier du projet :

```bash
mvn clean install compile
```

### 4. Lancer la base de données MariaDB

Assurez-vous que MariaDB est démarré et accessible sur le port 3306.

#### Créer la base de données (optionnel car Spring Boot la crée automatiquement)

1. **Ouvrir un client SQL** (HeidiSQL, IntelliJ IDEA, MySQL Workbench ou terminal)
2. **Se connecter au serveur MariaDB** avec les identifiants (host : `localhost`, port : `3306`, user : `root`, password : `root`).
   - Exemple en ligne de commande :

     ```bash
     mysql -u root -p
     # puis entrer le mot de passe
     ```

3. **Exécuter la commande SQL suivante dans le client** :

   ```sql
   CREATE DATABASE IF NOT EXISTS boitealivres CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

4. **Vérifier la création** :

   ```sql
   SHOW DATABASES;
   ```

   La base `boitealivres` doit apparaître dans la liste.

5. **Relancer l'application Spring Boot** (dans IntelliJ ou avec `mvn spring-boot:run`).

### 5. Initialiser la base de données (premier lancement uniquement)

**⚠️ À effectuer une seule fois lors du premier lancement de l'application.**

Les tables seront créées automatiquement par Hibernate au démarrage.

Pour peupler la base de données avec des données initiales (utilisateur de test, coordonnées et boîtes à livres), exécutez la commande suivante :

```bash
mvn spring-boot:run -D spring-boot.run.arguments="--seeder=UserTestSeeder,CoordinateSeeder,BoxSeeder"
```

Cette commande va créer :

- **Un utilisateur de test** :
  - Email : `user@example.com`
  - Username : `user`
  - Mot de passe : `Password@1`
- **52 coordonnées géographiques** pour les boîtes à livres de Tours
- **52 boîtes à livres** avec leurs informations (nom, description, quantité de livres)

Cette commande va aussi lancer l'application. Ainsi, vous n'avez pas besoin de la relancer après, ou d'attendre qu'elle ne soit terminée. (Le dernier message que vous verrez dans la console sera : `INFO 16440 --- [Backend] [main] f.p.b.backend.seeders.BoxSeeder: Success run BoxSeeder seeder`, après ca, l'application est lancée et prête à être utilisée).

**Note** : Les données restent en base après cette initialisation, il n'est donc pas nécessaire de réexécuter cette commande.

### 6. Lancer l'application

1. Ouvrez le projet dans IntelliJ
2. Vérifiez que le JDK utilisé est bien Java 21
3. Lancez la classe `BackendApplication.java`

#### En ligne de commande

```bash
mvn spring-boot:run
```

## Accéder à la base de données

### Avec IntelliJ IDEA

1. Ouvrez l'onglet **Database** (View → Tool Windows → Database)
2. Ajoutez une nouvelle source de données MariaDB
3. Renseignez :
   - Host : `localhost`
   - Port : `3306`
   - Database : `boitealivres`
   - User : `root`
   - Password : `root`
4. Testez la connexion et explorez les tables

### Avec HeidiSQL

1. Créez une nouvelle session
2. Type de réseau : MariaDB or MySQL (TCP/IP)
3. Host : `localhost`
4. User : `root`
5. Password : `root`
6. Port : `3306`
7. Ouvrez la session et accédez à la base `boitealivres`

## Notes

- Les tables sont créées automatiquement au démarrage grâce à Hibernate.
- Les paramètres de connexion sont centralisés dans le fichier `.env`.
- Pour changer de base, d'utilisateur ou de mot de passe, modifiez simplement le `.env`.
