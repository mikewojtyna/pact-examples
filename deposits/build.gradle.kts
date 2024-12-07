import java.io.ByteArrayOutputStream

plugins {
    java
    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.6"
    id("au.com.dius.pact") version "4.6.9"
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

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("au.com.dius.pact.provider:junit5:4.6.3")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("au.com.dius.pact.provider:junit5:4.6.3")
}

tasks.test {
    useJUnitPlatform()
    systemProperty("pact.provider.branch", getGitBranch())
    systemProperty("pact.provider.version", getGitHash())
    systemProperty("pact.verifier.publishResults", "true")
    systemProperty("pactbroker.host", "localhost")
    systemProperty("pactbroker.port", "9292")
}