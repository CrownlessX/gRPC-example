import com.google.protobuf.gradle.id
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.23"
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

    // Ktor for HTTP calls
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    // Engine
    implementation("io.ktor:ktor-client-cio:$ktorVersion")

    implementation("com.google.protobuf:protobuf-java-util:$protobufVersion")

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

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

tasks.test {
    useJUnitPlatform()
}