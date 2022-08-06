plugins {
    kotlin("jvm")
    id("org.openapi.generator")
}

dependencies {
    val jacksonVersion: String by project
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
    testImplementation(kotlin("test-junit"))
}

sourceSets {
    main {
        java.srcDir("$buildDir/generate-resources/main/src/main/kotlin")
    }
}

/**
 * Настраиваем генерацию здесь
 */
openApiGenerate {
    val openapiGroup = "${rootProject.group}.api.v1"
    generatorName.set("kotlin") // Это и есть активный генератор
    packageName.set(openapiGroup)
    apiPackage.set("$openapiGroup.api")
    modelPackage.set("$openapiGroup.models")
    invokerPackage.set("$openapiGroup.invoker")
    inputSpec.set("$rootDir/specs/specs-ad-v1.yaml")

    /**
     * Здесь указываем, что нам нужны только модели, все остальное не нужно
     */
    globalProperties.apply {
        put("models", "")
        put("modelDocs", "false")
    }

    /**
     * Настройка дополнительных параметров из документации по генератору
     * https://github.com/OpenAPITools/openapi-generator/blob/master/docs/generators/kotlin.md
     */
    configOptions.set(
        mapOf(
            "dateLibrary" to "string",
            "enumPropertyNaming" to "UPPERCASE",
            "serializationLibrary" to "jackson",
            "collectionType" to "list"
        )
    )
}

tasks {
    compileKotlin {
        dependsOn(openApiGenerate)
    }
}
