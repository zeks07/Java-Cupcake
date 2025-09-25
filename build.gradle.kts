plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.25"
    id("org.jetbrains.intellij.platform") version "2.6.0"
    id("org.jetbrains.grammarkit") version "2022.3.2.2"
}

group = "com.zeks"
version = "1.05-SNAPSHOT"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        create("IC", "2024.2.5")
        testFramework(org.jetbrains.intellij.platform.gradle.TestFrameworkType.Platform)

        bundledPlugin("com.intellij.java")
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
      Added Java injection to all code strings (code between {: and :}).
      Added folding.
      "error" is now shown as a special symbol.
      Improved inspection.
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
