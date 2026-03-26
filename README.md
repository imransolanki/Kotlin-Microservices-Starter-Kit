## Kotlin Microservices Starter Kit

> A production-ready microservice template — clone, run, and start building.

### 🚀 Get Running in 30 Seconds

```bash
git clone https://github.com/imransolanki/Kotlin-Microservices-Starter-Kit.git
cd Kotlin-Microservices-Starter-Kit
docker compose up --build
```

That's it. Open http://localhost:8080/swagger to explore the API.

### 🧪 Try It Out

<details>
<summary>Create a pet</summary>

```bash
curl -s -X POST http://localhost:8080/pet/ \
  -H "Content-Type: application/json" \
  -d '{"name":"Buddy","photo_url":"https://cdn.example.com/buddy.png","status":"available"}'
```
</details>

<details>
<summary>Get a pet</summary>

```bash
curl -s http://localhost:8080/pet/1
```
</details>

<details>
<summary>Update a pet</summary>

```bash
curl -s -X PUT http://localhost:8080/pet/ \
  -H "Content-Type: application/json" \
  -d '{"id":1,"name":"Buddy","photo_url":"https://cdn.example.com/buddy.png","status":"sold"}'
```
</details>

<details>
<summary>Delete a pet</summary>

```bash
curl -s -X DELETE http://localhost:8080/pet/Buddy
```
</details>

<details>
<summary>Health check</summary>

```bash
curl -s http://localhost:8080/health
# {"status":"UP"}
```
</details>

### 🏗️ Architecture

```
┌──────────────────────────────────────────────────┐
│                   Ktor Server                    │
│                                                  │
│  ┌──────────┐   ┌──────────┐   ┌─────────────┐  │
│  │  Routes   │──▶│ Service  │──▶│ Repository  │  │
│  │ (API)     │   │ (Logic)  │   │ (DB Access) │  │
│  └──────────┘   └──────────┘   └──────┬──────┘  │
│       │                               │          │
│       │              ┌────────────────┘          │
│       ▼              ▼                           │
│  ┌──────────┐   ┌──────────┐                     │
│  │Validator  │   │ Exposed  │                     │
│  │(Konform)  │   │  + DSL   │                     │
│  └──────────┘   └────┬─────┘                     │
│                      │                           │
│  ┌──────────────┐    │    ┌───────────────────┐  │
│  │ StatusPages  │    │    │ Koin (DI)         │  │
│  │ (Errors)     │    │    │ Hoplite (Config)  │  │
│  └──────────────┘    │    └───────────────────┘  │
└──────────────────────┼───────────────────────────┘
                       │
                       ▼
              ┌────────────────┐
              │   PostgreSQL   │
              │  (HikariCP +   │
              │   Flyway)      │
              └────────────────┘
```

### ✨ What's Included

| Feature | Details |
|---------|---------|
| 🔀 Layered architecture | Route → Service → Repository |
| ⚡ Async DB access | `newSuspendedTransaction` (coroutine-friendly) |
| 🛡️ Error handling | StatusPages + custom exceptions → proper HTTP codes |
| 🗃️ Migrations | Flyway runs on startup |
| 💚 Health check | `GET /health` with DB connectivity verification |
| 📖 Swagger UI | Interactive API docs at `/swagger` |
| 📋 Structured logging | JSON logs via logstash encoder |
| 🌐 CORS | Sensible defaults configured |
| ⚙️ Profile configs | dev / prod / docker via `APP_PROFILE` |
| 🐳 Docker | `docker compose up` — app + Postgres |
| ✅ CI | GitHub Actions — build, test, lint |
| 🧹 Linting | ktlint enforced in build |

### 🛠️ Built With

| Library | Purpose |
|---------|---------|
| [ktor](https://ktor.io/) | Async server framework |
| [koin](https://insert-koin.io/) | Dependency injection |
| [exposed](https://github.com/JetBrains/Exposed) | Kotlin SQL (DSL + DAO) |
| [hoplite](https://github.com/sksamuel/hoplite) | Type-safe configuration |
| [kotest](https://kotest.io/) | Testing framework |
| [konform](https://github.com/konform-kt/konform) | Input validation |
| [flyway](https://flywaydb.org/) | Database migrations |

### 📁 Project Structure

<details>
<summary>Click to expand</summary>

```
src/main/kotlin/
├── Application.kt                  # Entry point, plugin wiring
└── org/edu/
    ├── api/
    │   ├── Pet.kt                  # Data model
    │   ├── PetRoutes.kt            # Route definitions
    │   ├── HealthRoute.kt          # GET /health
    │   ├── Validator.kt            # Input validation
    │   ├── Exceptions.kt           # NotFoundException, BadRequestException
    │   └── ErrorResponse.kt        # Consistent error model
    ├── service/
    │   └── PetService.kt           # Business logic
    ├── repository/
    │   ├── PetRepository.kt        # Interface
    │   └── PetRepositoryImpl.kt    # DB implementation
    ├── persistence/
    │   └── PetEntity.kt            # Exposed table + entity
    └── plugin/
        ├── AppModule.kt            # Koin DI module
        ├── Configuration.kt        # Hoplite config loader
        ├── Routing.kt              # Route registration
        ├── Serialization.kt        # Gson setup
        ├── StatusPages.kt          # Global exception handling
        ├── Cors.kt                 # CORS defaults
        ├── DatabaseConfig.kt       # HikariCP config
        ├── DataSource.kt           # DataSource provider
        ├── EnvironmentConfig.kt    # Config data classes
        └── SchemaMigrator.kt       # Flyway migration runner

src/main/resources/
├── application.yaml                # Default config
├── application-dev.yaml            # Dev profile
├── application-prod.yaml           # Prod profile
├── application-docker.yaml         # Docker profile
├── db/migration/
│   └── V1__create_pet.sql          # Flyway migration
├── openapi/
│   └── documentation.yaml          # OpenAPI spec
└── logback.xml                     # Structured JSON logging
```
</details>

### ⚙️ Configuration

<details>
<summary>Profile-based config</summary>

Set `APP_PROFILE` to load a profile-specific config that overrides `application.yaml`:

| Profile | File | Use case |
|---------|------|----------|
| (none) | `application.yaml` | Default/test |
| `dev` | `application-dev.yaml` | Local development |
| `prod` | `application-prod.yaml` | Production |
| `docker` | `application-docker.yaml` | Docker Compose |

```bash
APP_PROFILE=dev ./gradlew run
```
</details>

<details>
<summary>Environment variable overrides</summary>

Any config value can be overridden with `UPPER_SNAKE_CASE` env vars:

```bash
DATABASE_HOSTNAME=mydb.example.com DATABASE_PORT=5433 ./gradlew run
```
</details>

### 📝 Adding a New Entity

1. Create the Exposed table + entity in `persistence/`
2. Add a Flyway migration in `src/main/resources/db/migration/`
3. Create a `Repository` interface + implementation in `repository/`
4. Create a `Service` class in `service/`
5. Add routes in `api/` and register them in `plugin/Routing.kt`
6. Wire the DI in `plugin/AppModule.kt`
7. Update `openapi/documentation.yaml`

### 🧑‍💻 Development

```bash
./gradlew build          # Build, test, and lint
./gradlew test           # Run tests only
./gradlew ktlintFormat   # Auto-fix code style
```

**Running without Docker:**
```bash
psql -U user -h localhost -c "CREATE DATABASE \"pet-store\";"
./gradlew run
```

---

Reference Blog: [Building High-Quality Microservices with Kotlin: Best Practices for Developers](https://medium.com/technogise/building-high-quality-microservices-with-kotlin-best-practices-for-developers-b0058dc7ab99)
