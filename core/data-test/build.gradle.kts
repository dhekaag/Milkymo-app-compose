plugins {
    id("milkymo.kotlin.feature")
    id("milkymo.coroutines")
    id("org.jetbrains.kotlin.plugin.serialization")
}

dependencies {
    implementation(project(":core:commons"))
    implementation(project(":core:data"))

    implementation(libs.ktor.engine.mock)
}
