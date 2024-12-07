import java.io.ByteArrayOutputStream

plugins {
    java
    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.6"
    id("au.com.dius.pact") version "4.6.9"
}

dependencies {
    testImplementation("au.com.dius.pact.consumer:junit5:4.6.3")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

val getGitHash = {
    val stdout = ByteArrayOutputStream()
    exec {
        commandLine("git", "rev-parse", "--short", "HEAD")
        standardOutput = stdout
    }
    stdout.toString().trim()
}

val getGitBranch = {
    val stdout = ByteArrayOutputStream()
    exec {
        commandLine("git", "rev-parse", "--abbrev-ref", "HEAD")
        standardOutput = stdout
    }
    stdout.toString().trim()
}

pact {
    publish {
        pactBrokerUrl = "http://localhost:9292"
        consumerBranch = getGitBranch()
        consumerVersion = getGitHash()

    }
}

tasks.test {
    useJUnitPlatform()
    dependsOn("pactPublish")
}
