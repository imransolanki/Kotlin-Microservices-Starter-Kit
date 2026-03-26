## Description
Starter project with easy to
understand microservice developed
using below libraries
- ⚡  [ktor](https://ktor.io/) - A Kotlin framework for building asynchronous servers and clients in connected systems
- 💉 [koin](https://insert-koin.io/) - A lightweight dependency injection framework for Kotlin with a simple and flexible API
- 🗄️ [exposed](https://github.com/JetBrains/Exposed) - A Kotlin SQL library providing both DSL and DAO-based database access
- 🔀 [either](https://apidocs.arrow-kt.io/arrow-core/arrow.core/-either/index.html) - A Kotlin implementation of the Either monad for handling computations that can result in either success or failure
- ⚙️ [hoplite](https://github.com/sksamuel/hoplite) - A Kotlin library for loading and managing configuration in a type-safe manner from various formats
- 🧪 [kotest](https://kotest.io/) - A Kotlin testing framework that provides a flexible and expressive test structure with various matchers and property-based testing

### Features
- Simple, modular architecture for microservices. 
- Configurable and scalable components. 
- Follows best practices for Kotlin development.
- CI via GitHub Actions (build & test on every push/PR to `main`)

### Setup
- Clone the repository
- Install [PostgresSQL](https://www.postgresql.org/) database server and create a DB using details mentioned in `application.yaml`
- Install dependencies with gradle
- Run services using `./gradlew run`

### Configuration

The app uses profile-based configuration via [hoplite](https://github.com/sksamuel/hoplite). Set the `APP_PROFILE` env var to load a profile-specific config that overrides defaults from `application.yaml`.

| Profile | File | Use case |
|---------|------|----------|
| (none) | `application.yaml` | Default/test config |
| `dev` | `application-dev.yaml` | Local development |
| `prod` | `application-prod.yaml` | Production |
| `docker` | `application-docker.yaml` | Docker Compose |

```bash
# Run with dev profile
APP_PROFILE=dev ./gradlew run

# Run with Docker
docker compose up --build   # uses APP_PROFILE=docker automatically
```

Individual values can also be overridden via environment variables using `UPPER_SNAKE_CASE` with underscores as separators:

```bash
DATABASE_HOSTNAME=mydb.example.com DATABASE_PORT=5433 ./gradlew run
```


Reference Blog: [Building High-Quality Microservices with Kotlin: Best Practices for Developers](https://medium.com/technogise/building-high-quality-microservices-with-kotlin-best-practices-for-developers-b0058dc7ab99)
