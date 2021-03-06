plugins {
    kotlin("jvm") version "1.4.31"
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.31")
    implementation(project(":hexagonal-invoice-kata-external-services:hexagonal-invoice-kata-external-services-common"))
}
