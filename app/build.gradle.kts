
plugins {
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinAndroidExtensions)
    id(BuildPlugins.kotlinKapt)
}

android {
    compileSdkVersion(AndroidSdk.compile)
    defaultConfig {
        applicationId = "com.example.weatherapp"
        minSdkVersion(AndroidSdk.min)
        targetSdkVersion(AndroidSdk.target)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "SECRET_KEY", "\"65d00499677e59496ca2f318eb68c049\"")
    }
    buildTypes {
        getByName("release") {
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
    }

    androidExtensions {
        isExperimental = true
    }
    dataBinding {
        isEnabled = true
    }
}
dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    implementation(Libraries.recyclerView)
    implementation(Libraries.materialDesign)
    implementation(Libraries.androidAnnotation)

    implementation(Libraries.appCompat)
    implementation(Libraries.constraintLayout)

    implementation(Libraries.rxJava2Runtime)
    implementation(Libraries.rxJava2RxAndroid)

    implementation(Libraries.lifecycleExtension)
    implementation(Libraries.lifecycleCommonJava8)

    implementation(Libraries.retrofit)
    implementation(Libraries.retrofitGsonConverter)
    implementation(Libraries.retrofitRxJava2Adapter)

    implementation(Libraries.okhttpLogging)

    implementation(Libraries.circularImage)
    implementation(Libraries.glide)
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${rootProject.extra["kotlin_version"]}")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    kapt(Libraries.glideCompiler)

    implementation(Libraries.ktxCore)
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.lifecycleViewmodelKt)
    implementation(Libraries.okhttp)
    implementation(Libraries.androidCoroutine)

    testImplementation(TestLibraries.mockitoCore)
    testImplementation(TestLibraries.mockitoCoreArc)
    testImplementation(TestLibraries.mockitoInline)
    testImplementation(TestLibraries.junit4)
    androidTestImplementation(TestLibraries.testRunner)
    androidTestImplementation(TestLibraries.espresso)
    testImplementation(TestLibraries.mockServer)
    testImplementation(TestLibraries.nharmanKotlin)
    testImplementation(TestLibraries.kotlinxCoroutines)


}
