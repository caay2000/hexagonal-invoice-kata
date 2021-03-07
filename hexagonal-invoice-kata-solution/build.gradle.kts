plugins {
    kotlin("jvm") version "1.4.31"
}

group = "com.github.caay2000"
version = "1.0-SNAPSHOT"

val externalServicesVersion = 3
val kotlinVersion = "1.4.31"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(files("../kata-libs/hexagonal-invoice-kata-external-services-common.jar"))
    implementation(files("../kata-libs/hexagonal-invoice-kata-external-services-version-$externalServicesVersion.jar"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    implementation("org.koin:koin-core:2.2.2")
    implementation("org.koin:koin-ktor:2.2.2")
    implementation("io.ktor:ktor-server:1.5.1")
    implementation("io.ktor:ktor-server-netty:1.5.1")
    implementation("io.ktor:ktor-jackson:1.5.1")

    testImplementation("org.junit.jupiter:junit-jupiter:5.6.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlinVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:$kotlinVersion")
    testImplementation("org.koin:koin-test:2.2.2")
    testImplementation("io.ktor:ktor-server-tests:1.5.1")
    testImplementation("org.assertj:assertj-core:3.14.0")
}

tasks.compileKotlin {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

tasks.compileTestKotlin {
    kotlinOptions {
        jvmTarget = "1.8"
    }
    kotlinOptions.freeCompilerArgs += "-Xuse-experimental=io.ktor.util.KtorExperimentalAPI"
}

tasks.test {
    useJUnitPlatform()
}