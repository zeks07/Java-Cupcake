plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.25"
    id("org.jetbrains.intellij.platform") version "2.6.0"
    id("org.jetbrains.grammarkit") version "2022.3.2.2"
}

group = "com.zeks"
version = "1.03-SNAPSHOT"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin.html
dependencies {
    intellijPlatform {
        create("IC", "2024.2.5")
        testFramework(org.jetbrains.intellij.platform.gradle.TestFrameworkType.Platform)

        // Add necessary plugin dependencies for compilation here, example:
        // bundledPlugin("com.intellij.java")
    }
    testImplementation("io.mockk:mockk:1.13.8")
}

intellijPlatform {
    pluginConfiguration {
        id = "com.zeks.javacupcake"
        name = "Java Cupcake"
        version = project.version as String
        ideaVersion {
            sinceBuild = "242"
            untilBuild = provider { null }
        }
        description =
            "Enhances Java CUP file editing with smart code completion and language support for easier parser development"

        changeNotes = """
      Improved completion with context aware suggestions.
    """.trimIndent()
    }

    publishing {
        token = (project.properties["org.jetbrains.intellij.publish.token"] as String)
        channels = listOf("beta")
    }
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "21"
        targetCompatibility = "21"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "21"
    }
    buildSearchableOptions {
        enabled = false
    }
}

sourceSets {
    main {
        kotlin.srcDirs("src/main/kotlin", "src/main/gen")
        java.srcDirs("src/main/gen")
    }
    test {
        kotlin.srcDirs("src/test")
    }
}
