import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"
    application
    kotlin("kapt") version "1.5.31"
}

group = "com.github.iamr3m"
version = "1.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jsoup:jsoup:1.15.3")
    implementation("com.googlecode.json-simple:json-simple:1.1.1")
    implementation("info.picocli:picocli:4.7.0")
    kapt("info.picocli:picocli-codegen:4.7.0")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}
