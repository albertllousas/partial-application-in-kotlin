plugins {
    id("org.jetbrains.kotlin.jvm") version "1.5.0"
}

group = "com.partialapp"
version = "1.0.0"

object Versions {
    const val JUNIT = "5.7.0"
    const val MOCKK = "1.12.0"
    const val ASSERTJ = "3.20.2"
    const val ARROW = "0.12.1"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation(group = "io.mockk", name = "mockk", version = Versions.MOCKK)
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.7.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testImplementation(group = "org.assertj", name = "assertj-core", version = Versions.ASSERTJ)
}



tasks.apply {
    test {
        enableAssertions = true
        useJUnitPlatform {}
    }
}

