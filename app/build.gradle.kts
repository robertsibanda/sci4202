plugins {
    alias(libs.plugins.androidApplication)
}


android {
    namespace = "com.robert.sci4202"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.robert.sci4202"
        minSdk = 22
        targetSdk = 22
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        isCoreLibraryDesugaringEnabled = true

    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.preference)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(files("/home/rob/Desktop/jar_files2/accessors-smart-2.5.0.jar"))
    implementation(files("/home/rob/Desktop/jar_files2/accessors-smart-2.5.0.jar"))
    implementation(files("/home/rob/Desktop/jar_files2/asm-9.3.jar"))
    implementation(files("/home/rob/Desktop/jar_files2/json-smart-2.5.0.jar"))
    implementation(files("/home/rob/Desktop/bcprov-jdk16-1.46.jar"))
    implementation(files("/home/rob/Downloads/jsonrpc2-base-1.34.4.jar"))
    implementation(files("/home/rob/Downloads/jsonrpc2-client-2.1.1.jar"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.0.9")

    // volley for rest-api calls
    implementation("com.android.volley:volley:1.2.1")

    // Rounded Image
    implementation("com.makeramen:roundedimageview:2.3.0")

    // Navigation Component
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")

    // add below dependency for using room.
    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")

    // glide for remote image loading
    implementation("com.github.bumptech.glide:glide:4.15.1")

    implementation("com.journeyapps:zxing-android-embedded:4.3.0")

}