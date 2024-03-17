plugins {
    id("milkymo.kotlin.feature")
    id("milkymo.coroutines")
}

dependencies {
    implementation(project(":core:commons"))
    implementation(project(":core:data"))

    implementation(libs.bundles.javax)
}
