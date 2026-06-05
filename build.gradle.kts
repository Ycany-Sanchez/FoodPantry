plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

fun loadEnvFile(file: java.io.File): Map<String, String> {
    if (!file.exists()) return emptyMap()
    return file.readLines().mapNotNull { rawLine ->
        val line = rawLine.trim()
        if (line.isEmpty() || line.startsWith("#")) return@mapNotNull null
        val index = line.indexOf("=")
        if (index <= 0) return@mapNotNull null
        val key = line.substring(0, index).trim()
        var value = line.substring(index + 1).trim()
        if ((value.startsWith("\"") && value.endsWith("\"")) || (value.startsWith("'") && value.endsWith("'"))) {
            value = value.substring(1, value.length - 1)
        }
        key to value
    }.toMap()
}

val envVars = loadEnvFile(project.file(".env"))
val spoonacularApiKey = envVars["SPOONACULAR_API_KEY"]
    ?: System.getenv("SPOONACULAR_API_KEY")
    ?: throw GradleException("SPOONACULAR_API_KEY must be provided in .env or environment variables")

android {
    namespace = "com.ycany.prefinals"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ycany.prefinals"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "SPOONACULAR_API_KEY", "\"$spoonacularApiKey\"")
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
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
}