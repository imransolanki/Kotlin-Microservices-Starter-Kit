package org.edu.api

import com.google.gson.FieldNamingPolicy
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.gson.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import org.edu.module
import org.edu.persistence.PetsTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.context.stopKoin

class PetRoutesIntegrationTest : FeatureSpec({

    beforeAny {
        Database.connect("jdbc:h2:mem:integration;DB_CLOSE_DELAY=-1;", driver = "org.h2.Driver")
        transaction { SchemaUtils.create(PetsTable) }
    }

    afterEach { stopKoin() }

    fun ApplicationTestBuilder.jsonClient() =
        createClient {
            install(ContentNegotiation) {
                gson { setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES) }
            }
        }

    feature("POST /pet/") {
        scenario("create a valid pet returns 201") {
            testApplication {
                environment { config = MapApplicationConfig() }
                application { module() }
                val client = jsonClient()

                val response =
                    client.post("/pet/") {
                        contentType(ContentType.Application.Json)
                        setBody(Pet(id = null, name = "Buddy", photoUrl = "https://cdn.example.com/buddy.png", status = "available"))
                    }

                response.status shouldBe HttpStatusCode.Created
                val pet = response.body<Pet>()
                pet.name shouldBe "Buddy"
                pet.status shouldBe "available"
            }
        }

        scenario("invalid pet returns 400") {
            testApplication {
                environment { config = MapApplicationConfig() }
                application { module() }
                val client = jsonClient()

                val response =
                    client.post("/pet/") {
                        contentType(ContentType.Application.Json)
                        setBody(Pet(id = null, name = "X", photoUrl = "bad-url", status = "invalid"))
                    }

                response.status shouldBe HttpStatusCode.BadRequest
            }
        }
    }

    feature("GET /pet/{petId}") {
        scenario("non-numeric petId returns 400") {
            testApplication {
                environment { config = MapApplicationConfig() }
                application { module() }
                val response = client.get("/pet/abc")
                response.status shouldBe HttpStatusCode.BadRequest
            }
        }

        scenario("non-existing pet returns 404") {
            testApplication {
                environment { config = MapApplicationConfig() }
                application { module() }
                val response = client.get("/pet/9999")
                response.status shouldBe HttpStatusCode.NotFound
            }
        }
    }
})
