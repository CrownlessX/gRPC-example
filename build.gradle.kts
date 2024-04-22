import com.google.protobuf.gradle.id
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.serialization") version "1.9.23"
    id("com.google.protobuf") version "0.9.4"
}

group = "org.jaco"
version = "1.0-SNAPSHOT"

val ktorVersion = "2.3.8"
val grpcVersion = "1.57.2"
val protobufVersion = "3.25.1"

repositories {
    mavenCentral()
}

dependencies {
    // gRPC
    implementation("io.grpc:grpc-kotlin-stub:1.4.0")
    implementation("io.grpc:grpc-protobuf:$grpcVersion")
    implementation("io.grpc:grpc-netty:$grpcVersion")

    // Protobuf
    implementation("com.google.protobuf:protobuf-kotlin:$protobufVersion")

    // Required for proto related dependencies like google/api/annotations.proto
    implementation("com.google.api.grpc:proto-google-common-protos:2.37.1")

    // Async
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")

    // For REST
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-client-java:$ktorVersion")

    // JSON
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")

    // Logging
    implementation("io.ktor:ktor-server-call-logging:$ktorVersion")
    implementation("io.ktor:ktor-client-logging:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:1.5.5")

    testImplementation("io.grpc:grpc-testing:$grpcVersion")
    testImplementation(kotlin("test"))
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:$protobufVersion"
    }

    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:$grpcVersion"
        }

        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:1.4.1:jdk8@jar"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {task ->
            task.plugins {
                id("grpc") {}
                id("grpckt") {}
            }

            task.builtins {
                id("kotlin")
            }
        }
    }
}

// Optional in our case, but added to demonstrate how to specify where to search for protos in
// others than default folder - src/main/proto
sourceSets {
    main {
        proto {
            srcDir("path/to/proto/folder") // put a folder path where protos located
            // You can add more srcDir lines for additional folders
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

tasks.test {
    useJUnitPlatform()
}