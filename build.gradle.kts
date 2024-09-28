import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.10"
    application
}

group = "net.onedsix.ratils"
version = "1.0.0"

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
    maven { url = uri("https://oss.sonatype.org/content/repositories/releases/") }
}

dependencies {
    val lombokVersion = "1.18.32"
    val jmhVersion = "1.17.5"

	implementation("org.projectlombok:lombok:$lombokVersion")
	annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    compileOnly("org.jetbrains:annotations:23.0.0")
	implementation("org.slf4j:slf4j-api:1.7.32")
	implementation("ch.qos.logback:logback-classic:1.2.6")

    testImplementation("org.openjdk.jmh:jmh-core:$jmhVersion")
    testImplementation("org.openjdk.jmh:jmh-generator-annprocess:$jmhVersion")
    testImplementation("junit:junit:4.13.1")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
