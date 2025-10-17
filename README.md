# Poseidon Trading App (OpenClassrooms P7)

Backend Spring Boot avec CRUD pour 6 entités, sécurité session-based, validation et tests unitaires sur le service Bid.

## Démarrage
```bash
mvn spring-boot:run
# UI H2: http://localhost:8080/h2-console  (JDBC URL: jdbc:h2:mem:poseidon)
```

## Endpoints
- /api/health → OK
- /api/bids [GET, POST, PUT, DELETE] (auth requis)

## À faire (voir limitations plus bas)
- Compléter DTO/Service/Controller pour CurvePoint, Rating, Rule, Trade, User (patron identique à Bid).

## Sécurité
- Authentification par **session** avec `formLogin` Spring Security.
- CSRF activé (sauf /api/health).
- Rôles: ROLE_USER (lecture), ROLE_ADMIN (écriture).

## Tests
- `BidServiceImplTest` (Mockito)
- `StrongPasswordValidatorTest`
