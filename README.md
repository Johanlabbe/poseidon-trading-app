# Poseidon Trading App (OpenClassrooms P7)

Backend Spring Boot avec CRUD pour 6 entités, sécurité session-based, validation et tests unitaires sur le service Bid.

## Démarrage
```bash
mvn spring-boot:run
# UI H2: http://localhost:8080/h2-console  (JDBC URL: jdbc:h2:mem:poseidon)
```

## Endpoints :
### Health (/api/health)
- /api/health → OK

### Bid (/api/bids)
- /api/bids → Liste paginée des Bid.
- /api/bids/{id} → Détail d’un Bid.
- /api/bids → Crée un Bid.
- /api/bids/{id} → Met à jour un Bid.
- /api/bids/{id} → Supprime un Bid.

### CurvePoints (/api/curve-points)
- /api/curve-points → Liste paginée des CurvePoints.
- /api/curve-points/{id} → Détail d’un CurvePoint.
- /api/curve-points → Crée un CurvePoint.
- /api/curve-points/{id} → Met à jour un CurvePoint.
- /api/curve-points/{id} → Supprime un CurvePoint.

## À faire (voir limitations plus bas)
- Compléter DTO/Service/Controller pour Rating, Rule, Trade, User (patron identique à Bid).
- Compléter les tests pour Rating, Rule, Trade, User.

## Sécurité
- Authentification par **session** avec `formLogin` Spring Security.
- CSRF activé (sauf /api/health).
- Rôles: ROLE_USER (lecture), ROLE_ADMIN (écriture).

## Tests
- `BidServiceImplTest` (Mockito)
- `StrongPasswordValidatorTest`