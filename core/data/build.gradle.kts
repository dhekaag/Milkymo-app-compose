plugins {
    id("milkymo.kotlin.feature")
    id("milkymo.coroutines")
    id("org.jetbrains.kotlin.plugin.serialization")
}

dependencies {
    implementation(project(":core:commons"))
    api(libs.bundles.network)
    implementation(libs.datastore)

    testImplementation(project(":core:data-test"))
}
