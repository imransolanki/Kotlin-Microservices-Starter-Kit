val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "2.0.20"
    id("io.ktor.plugin") version "3.0.0-rc-2"
    id("org.jetbrains.kotlin.plugin.serialization") version "1+"
}

group = "org.edu"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("org.edu.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {

    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-config-yaml")
    implementation("net.logstash.logback:logstash-logback-encoder:7.4")

    implementation("io.konform:konform:0.7.0")

    implementation("io.ktor:ktor-serialization-gson")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")

    implementation(platform("io.arrow-kt:arrow-stack:1+"))
    implementation("io.arrow-kt:arrow-core")
    implementation("io.arrow-kt:arrow-fx-coroutines")

    testImplementation("io.ktor:ktor-server-test-host-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    testImplementation("io.kotest:kotest-runner-junit5:5.8.0")
    testImplementation("io.kotest:kotest-assertions-core:$version")
    testImplementation("io.kotest.extensions:kotest-assertions-arrow:1.4.0")

}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
