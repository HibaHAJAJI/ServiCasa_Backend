# Cours personnel du projet ServiCasa

> **Périmètre documenté :** démarrage Spring Boot en test, CI GitHub Actions, MySQL, Flyway et le mapping des entités `User` et `Artisan`, à partir de l'analyse de l'erreur `Schema-validation: missing table [artisans]`.
>
> Le dépôt inspecté est **ServiCasa** (`org.example:ServiCasa`, packages `ServiCasa.*`). Le nom « AssetPilot » ne correspond pas aux identifiants de ce dépôt. Cette documentation décrit donc uniquement ce que le code réel de ServiCasa contient et sera complétée lors des prochaines parties étudiées.

## 1. Architecture du démarrage et des tests

Le test de chargement du contexte suit ce parcours :

```text
Maven Surefire
  ↓ active le profil Spring "test"
Spring Boot charge la configuration et les variables d'environnement
  ↓
DataSource se connecte à MySQL
  ↓
Flyway applique les migrations SQL
  ↓
Hibernate valide les entités avec ddl-auto=validate
  ↓
ApplicationTests.contextLoads réussit ou échoue
```

| Élément | Rôle dans ServiCasa |
|---|---|
| Maven Surefire | Exécute les tests et définit le profil `test` dans `pom.xml`. |
| Spring Boot | Assemble le contexte de l'application et choisit les propriétés de configuration. |
| MySQL | Stocke les tables. Le workflow CI démarre un service MySQL 8.0 avec le schéma `servicasa_db`. |
| Flyway | Applique les scripts SQL versionnés de `src/main/resources/db/migration`. |
| Hibernate/JPA | Mappe les entités Java sur les tables et vérifie leur compatibilité. |
| `ApplicationTests` | Vérifie que le contexte Spring peut démarrer. |

## 2. Fichier `pom.xml`

### Rôle

Le fichier Maven décrit les dépendances, la compilation et l'exécution des tests. Le plugin `maven-surefire-plugin` contient :

```xml
<systemPropertyVariables>
    <spring.profiles.active>test</spring.profiles.active>
</systemPropertyVariables>
```

Surefire transmet donc `spring.profiles.active=test` aux tests. Spring charge alors `application-test.properties` depuis les ressources de test. Les propriétés de ce fichier et les variables d'environnement du workflow sont déterminantes pour connaître la base effectivement utilisée et l'état de Flyway.

Les dépendances observées incluent `spring-boot-starter-data-jpa`, `mysql-connector-j`, `flyway-core` et `flyway-mysql`. Elles fournissent respectivement l'intégration JPA, le pilote JDBC MySQL et l'intégration Flyway, mais leur présence ne suffit pas à exécuter Flyway si celui-ci est désactivé par configuration.

## 3. Configuration Spring et choix de la base

### `src/main/resources/application.properties`

Les propriétés principales inspectées sont :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/servicasa?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.jpa.hibernate.ddl-auto=validate
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
```

La configuration standard vise la base `servicasa`. `spring.flyway.locations` indique l'emplacement du SQL à migrer. `ddl-auto=validate` demande à Hibernate de vérifier le schéma, pas de créer ou mettre à jour ses tables.

### `src/test/resources/application-test.properties`

Cette configuration est chargée pour les tests grâce au profil défini par Surefire. La version locale examinée pointe elle aussi vers MySQL (`servicasa`) et active Flyway. La configuration ayant été modifiée localement, il faut distinguer son état de la version committée et de la configuration exécutée par GitHub Actions.

### `.github/workflows/ci.yml`

Le service du workflow est configuré avec MySQL 8.0 et `MYSQL_DATABASE: servicasa_db`. L'étape Maven fournit notamment :

```yaml
SPRING_DATASOURCE_URL: jdbc:mysql://localhost:3306/servicasa_db
SPRING_DATASOURCE_USERNAME: root
SPRING_DATASOURCE_PASSWORD: ""
SPRING_FLYWAY_ENABLED: "true"
SPRING_JPA_HIBERNATE_DDL_AUTO: validate
```

Les noms de variables correspondent aux propriétés Spring Boot. Dans cette étape CI, l'URL surcharge celle du fichier de propriétés pour que Flyway et Hibernate se connectent à `servicasa_db`. La variable `SPRING_FLYWAY_ENABLED` force l'exécution de Flyway même si une autre configuration chargée pour le profil test le désactive. `JWT_SECRET` est fourni par un secret GitHub ; sa valeur n'est pas documentée ici.

Le workflow lance `./mvnw clean package`, qui inclut les tests dans le cycle Maven. Une modification locale du workflow ne s'applique pas sur GitHub tant qu'elle n'est pas dans le commit exécuté par le workflow.

## 4. Flyway et migrations SQL

### Fonctionnement

Flyway recherche les scripts indiqués par `spring.flyway.locations`. Les noms suivent la convention `V<version>__<description>.sql`. Il compare les migrations disponibles avec l'historique `flyway_schema_history`, puis exécute les versions manquantes dans l'ordre numérique.

Si Flyway est désactivé, aucun script n'est appliqué. Si une migration échoue, le démarrage échoue pendant l'initialisation Flyway, avant la validation du schéma par Hibernate. Une erreur Flyway ne se transforme donc pas normalement en succès silencieux suivi uniquement d'une erreur Hibernate.

### Quelle migration crée `artisans` ?

`src/main/resources/db/migration/V1__init_schema.sql` crée explicitement la table :

```sql
CREATE TABLE artisans (
    id BIGINT PRIMARY KEY,
    annees_experience INT,
    tarif_horaire DECIMAL(10, 2),
    description TEXT,
    zone_intervention VARCHAR(255),
    specialite VARCHAR(255)
);
```

La migration existe bien et son nom respecte la convention de version. Elle crée aussi `users` et les autres tables initiales.

### Ordre et dépendances des migrations

| Migration | Effet pertinent pour `artisans` |
|---|---|
| `V1__init_schema.sql` | Crée `users` et `artisans`. |
| `V2__add_foreign_keys.sql` | Ajoute `artisans.id` comme clé étrangère vers `users.id`, ainsi que d'autres clés étrangères. Dépend des tables créées en V1. |
| `V3__add_missing_columns.sql` | Ajoute `statut_compte` à `artisans`. Dépend de V1. |
| `V4__create_avis_table.sql` | Crée `avis`, qui référence `artisans`. |
| `V5__create_services_artisan_table.sql` | Crée `services_artisan`, qui référence `artisans`. |
| `V6__create_notifications_table.sql` | Crée `notifications`, qui référence `users`. |
| `V7__add_paiement_columns.sql` | Modifie les colonnes de `paiements`. |
| `V8__align_villes_specialites_enum.sql` | Crée `villes` et `specialites`, transforme les anciennes colonnes de `users` et `artisans`, et ajoute `artisans.specialite_id`. Suppose que les tables existent déjà. |

Une base vide doit donc recevoir V1 avant les migrations suivantes. La table est créée dès V1 ; elle ne dépend pas d'une migration ultérieure pour apparaître.

### Correspondance entre SQL et entité

`Artisan.java` porte `@Table(name = "artisans")`. Ce nom correspond exactement à la table créée par V1 ; aucun décalage `artisan`/`artisans` n'est présent.

## 5. Entité `User`

Fichier : `src/main/java/ServiCasa/entity/User.java`.

`@Entity` fait de `User` une entité gérée par JPA et `@Table(name = "users")` la mappe à la table SQL `users`. L'identifiant `id` est annoté `@Id` et `@GeneratedValue(strategy = GenerationType.IDENTITY)`, ce qui utilise la génération d'identifiants de la base, compatible avec une colonne MySQL auto-incrémentée.

`User` contient les informations communes d'identité (nom, prénom, téléphone, courriel, mot de passe, rôle) et une relation `@ManyToOne` vers `Ville` avec la colonne `ville_id`. Il implémente aussi `UserDetails`, l'interface Spring Security dont l'usage pour l'authentification doit être étudié avec les classes de sécurité.

### Héritage JPA

`@Inheritance(strategy = InheritanceType.JOINED)` indique que la classe parent et chaque sous-type persistant utilisent des tables distinctes reliées par leur identifiant. Ainsi, les données communes de l'artisan sont dans `users` et ses données spécifiques dans `artisans`.

## 6. Entité `Artisan`

Fichier : `src/main/java/ServiCasa/entity/Artisan.java`.

`Artisan extends User` : un artisan est un utilisateur doté d'informations métier supplémentaires, comme les années d'expérience, le tarif horaire, la description, la zone d'intervention, le statut de compte et la spécialité.

| Annotation | Effet dans cette classe |
|---|---|
| `@Entity` | Rend la classe persistante pour JPA. |
| `@Table(name = "artisans")` | Associe la partie spécifique à la table `artisans`. |
| `@PrimaryKeyJoinColumn(name = "id")` | Relie la ligne `artisans` à la ligne parent `users` avec le même `id`. |
| `@Enumerated(EnumType.STRING)` | Stocke le nom du statut de compte plutôt qu'un ordinal numérique. |
| `@ManyToOne` + `@JoinColumn(name = "specialite_id")` | Relie l'artisan à une spécialité par la clé `specialite_id`. |
| `@OneToMany(mappedBy = "artisan")` | Déclare les collections inverses de réservations, disponibilités, avis et services ; le côté propriétaire est l'attribut `artisan` de l'entité associée. |
| `@JsonIgnore` | Évite de sérialiser directement ces collections lors de la conversion en JSON, notamment pour prévenir des parcours récursifs. |

Ces annotations décrivent le mapping objet-relationnel. Avec `ddl-auto=validate`, elles ne créent pas les tables : les migrations restent responsables du schéma SQL.

## 7. Test `ApplicationTests`

Fichier : `src/test/java/ServiCasa/ApplicationTests.java`.

```java
@SpringBootTest
class ApplicationTests {
    @Test
    void contextLoads() {
    }
}
```

`@SpringBootTest` démarre le contexte Spring complet pour ce test. Le corps de `contextLoads()` est vide : le test réussit si l'initialisation du contexte aboutit. La connexion à la base, l'exécution éventuelle de Flyway et l'initialisation de JPA/Hibernate se produisent durant ce démarrage. Une exception de validation Hibernate fait échouer le test avant l'exécution utile du corps de méthode.

## 8. Diagnostic de `Schema-validation: missing table [artisans]`

Cette erreur signifie qu'au moment où Hibernate valide le mapping de `Artisan`, la base effectivement consultée ne contient pas la table `artisans`. Elle ne prouve pas que le script de création est absent : V1 le contient et le nom concorde avec l'entité.

La cause établie dans l'analyse est la configuration réellement chargée pour les tests : Surefire active le profil `test`, dont la configuration committée désactivait Flyway, tandis que le workflow committé ne surchargait pas cette valeur. Aucune migration ne créait alors le schéma avant l'étape `validate`. La correction minimale identifiée est de faire exécuter le workflow avec `SPRING_FLYWAY_ENABLED: "true"`, en gardant MySQL et `ddl-auto=validate`.

Les logs locaux observés lors de `.\mvnw.cmd clean test` indiquaient que Flyway avait validé 8 migrations et que le schéma local `servicasa` était à la version 8, puis Hibernate avait initialisé l'EntityManagerFactory. Ces logs prouvent le comportement local, pas directement celui de la base CI `servicasa_db`. Pour affirmer ce qui s'est passé lors d'un job distant précis, il faut ses logs Flyway et l'URL/catalogue affiché par ces logs.

## 9. Flux de démarrage présenté au jury

```text
Surefire active le profil test
  ↓
Spring lit la configuration de test et les variables CI
  ↓
Le DataSource se connecte à servicasa_db dans GitHub Actions
  ↓
Flyway applique V1, V2, ... dans l'ordre
  ↓
V1 crée users et artisans
  ↓
Hibernate vérifie les tables avec ddl-auto=validate
  ↓
ApplicationTests vérifie que le contexte démarre
```

### 🎤 Comment l'expliquer devant le jury ?

> « Dans ServiCasa, Flyway gère l'évolution du schéma MySQL avec des migrations versionnées. La migration V1 crée notamment `users` et `artisans`, puis les versions suivantes ajoutent les contraintes et les colonnes nécessaires. Hibernate est en mode `validate` : il contrôle la correspondance entre les entités et la base, mais ne crée pas les tables. En CI, je dois donc m'assurer que le profil de test et les variables du workflow activent Flyway et que Flyway et Hibernate utilisent la même base MySQL. »

## 10. Sujets restant à documenter à partir du code

Les autres aspects du projet — packages et classes métier, méthodes de contrôleurs/services/repositories, DTOs et mappers, sécurité JWT détaillée, rôles, erreurs HTTP, Swagger, Docker, cache éventuel, workflows fonctionnels et éventuel frontend — ne sont pas expliqués ici faute d'avoir encore analysé leurs fichiers pour ce cours. Ils devront être ajoutés à ce même fichier au fil de leur étude, sans créer de fichiers d'explication parallèles ni présenter comme existante une fonctionnalité qui n'a pas été vérifiée.
