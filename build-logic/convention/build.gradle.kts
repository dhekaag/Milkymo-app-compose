plugins {
    `kotlin-dsl`
}

group = "com.milkymo.milky_mo.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.gradle.plugin.android)
    compileOnly(libs.gradle.plugin.kotlin)
}

gradlePlugin {
    plugins {
        register("coroutines") {
            id = "milkymo.coroutines"
            implementationClass = "CoroutinesConventionPlugin"
        }

        register("kotlinFeature") {
            id = "milkymo.kotlin.feature"
            implementationClass = "KotlinFeatureConventionPlugin"
        }
    }
}
