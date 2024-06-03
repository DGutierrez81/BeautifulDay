plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    //DCS - Dagger Hilt
    kotlin("kapt")
    id("com.google.dagger.hilt.android")

    id("com.google.relay") version "0.3.02"
}

android {
    namespace = "com.project.beautifulday"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.project.beautifulday"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.wear.compose:compose-material:1.3.0")
    implementation ("androidx.compose.material:material:1.6.3")
    //implementation (platform("androidx.compose:compose-bom:2023.01.00"))
    //implementation ("androidx.compose.runtime:runtime")


    // Exoplayer- video asincrono
    implementation("androidx.media3:media3-exoplayer:1.2.1")
    implementation("androidx.media3:media3-exoplayer-dash:1.2.1")
    implementation("androidx.media3:media3-ui:1.2.1")


    // Glide
    implementation("com.github.bumptech.glide:glide:4.15.1")




    //Test
    testImplementation("junit:junit:4.13.2")
    testImplementation("io.mockk:mockk:1.12.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")





    implementation(platform("com.google.firebase:firebase-bom:32.7.2"))
    implementation("com.google.firebase:firebase-analytics")
    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    // https://firebase.google.com/docs/android/setup#available-libraries
    // DCS - Servicio de Autenticación
    implementation("com.google.firebase:firebase-auth-ktx:23.0.0")
    // DCS - Base de datos Firestore
    implementation("com.google.firebase:firebase-firestore-ktx:25.0.0")

    // Base de datos de Storage
    implementation("com.google.firebase:firebase-storage-ktx")

    //DCS - Versión Alpha donde está el componente SearchBar
    implementation(platform("androidx.compose:compose-bom:2023.05.01"))

    //DCS - Cambiamos la versión a la misma: 2023.05.01
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.05.01"))


    //ML-KIT
    implementation("com.google.mlkit:translate:17.0.0")

    //Map compose
    implementation("com.google.maps.android:maps-compose:2.11.4")


    // google map services
    implementation ("com.google.android.gms:play-services-location:21.2.0")
    implementation("com.google.android.gms:play-services-maps:18.2.0")

    // google maps utils
    implementation ("com.google.maps.android:android-maps-utils:3.4.0")


    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation ("androidx.compose.runtime:runtime-livedata:1.5.4")
    implementation ("androidx.lifecycle:lifecycle-livedata:2.3.1")
    implementation ("androidx.compose.animation:animation-core:1.6.5")

    //DCS - Dagger Hilt
    implementation("com.google.dagger:hilt-android:2.47")
    kapt("com.google.dagger:hilt-android-compiler:2.47")

    //DCS - Navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")

    //DCS - Retrofit
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //Map compose
    implementation("com.google.maps.android:maps-compose:2.11.4")


    // google map services
    implementation ("com.google.android.gms:play-services-location:21.2.0")
    implementation("com.google.android.gms:play-services-maps:18.2.0")

    // google maps utils
    implementation ("com.google.maps.android:android-maps-utils:3.4.0")

    //Accompanist Permissions
    implementation ("com.google.accompanist:accompanist-permissions:0.20.0")

    //DCS - Coil
    //DCS - Coil es una biblioteca de carga de imágenes para Android que es compatible con Jetpack Compose.
    implementation("io.coil-kt:coil-compose:2.4.0")
}

//DCS - Dagger Hilt - Allow references to generated code
kapt {
    correctErrorTypes = true
}


//"com.project.beautifulday"