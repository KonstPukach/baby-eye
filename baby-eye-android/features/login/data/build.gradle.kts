plugins {
    id("kotlin")
}

dependencies {
    implementation(project(":remote:networking"))
    implementation(project(":features:login:domain"))

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")
}
