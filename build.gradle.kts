project.setProperty("mainClassName", "MainKt")
plugins {
    kotlin("jvm") version "1.8.0"
    id("com.github.johnrengelman.shadow") version "6.1.0"
    application
}

group = "com.batchofcode"

repositories {
    mavenCentral()
    maven { url= uri("https://jitpack.io") }
}

val ktorVersion = "2.2.2"
val awsSdkVersion = "0.20.0-beta"

dependencies {
    implementation("io.ktor:ktor-client-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-client-java:$ktorVersion")
    implementation("com.github.ivanisidrowu.KtRssReader:kotlin:v2.1.2")
    implementation("aws.sdk.kotlin:aws-core-jvm:$awsSdkVersion")
    implementation("aws.sdk.kotlin:ses-jvm:$awsSdkVersion")
    implementation("aws.sdk.kotlin:ssm-jvm:$awsSdkVersion")
    implementation("com.amazonaws:aws-lambda-java-events:3.11.0")
    testImplementation(kotlin("test"))
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveFileName.set("${project.name}-${project.version}.jar")
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