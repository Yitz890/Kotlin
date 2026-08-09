plugins {
    kotlin("jvm") version "2.0.0"
    id("application")
}

group = "com.example"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:2.3.0")
    implementation("io.ktor:ktor-server-netty-jvm:2.3.0")
    implementation("io.ktor:ktor-server-content-negotiation:2.3.0")
    implementation("io.ktor:ktor-serialization-jackson:2.3.0")
}

application {
    mainClass.set("MainKt")   // matches filename Main.kt
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "MainKt"
    }
    // Create a fat JAR with all dependencies
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}
