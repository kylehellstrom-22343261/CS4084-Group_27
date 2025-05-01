//import java.util.Properties
//
//val localProperties = Properties()
//val localPropertiesFile = rootProject.file("local.properties")
//if (localPropertiesFile.exists()) {
//    localProperties.load(localPropertiesFile.inputStream())
//}
//val orsApiKey = localProperties.getProperty("ORS_API_KEY") ?: ""

plugins {
    alias(libs.plugins.android.application) version "8.8.0"
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.unieats"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.unieats"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

//        buildConfigField("String", "ORS_API_KEY", "\"$orsApiKey\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.database)
    implementation(libs.work.runtime)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("org.osmdroid:osmdroid-android:6.1.11")

}