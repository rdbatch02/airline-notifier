plugins {
    kotlin("jvm") version "1.8.0"
    application
}

group = "com.batchofcode"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url= uri("https://jitpack.io") }
}

val ktorVersion = "2.2.2"

dependencies {
    implementation("io.ktor:ktor-client-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-client-java:$ktorVersion")
    implementation("com.github.ivanisidrowu.KtRssReader:kotlin:v2.1.2")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}

application {
    mainClass.set("MainKt")
}