plugins {
  id("kotlin")
}

dependencies {
  api(project(":core:domain"))
  // Networking
  implementation("com.squareup.retrofit2:retrofit:2.9.0")
  implementation("com.squareup.moshi:moshi-kotlin:1.14.0")
}
