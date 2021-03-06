buildscript {
    repositories {
        gradlePluginPortal()
    }
}

plugins {
    `java-library`
    kotlin("jvm") version "1.5.31"
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

version = "0.0.1"

allprojects {
    group = "dev.unethical"

    apply<JavaPlugin>()
    apply(plugin = "java-library")
    apply(plugin = "kotlin")
    apply(plugin = "com.github.johnrengelman.shadow")

    repositories {
        mavenCentral()
        jcenter()
        mavenLocal()
        maven {
            url = uri("https://repo.runelite.net")
        }
    }

    dependencies {
        api(group = "com.openosrs", name = "runelite-api", version = "4.20.0")
        api(group = "com.openosrs", name = "runelite-client", version = "4.20.0")
    }

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_11
    }

    tasks {
        build {
            doFirst {
                clean
            }

            finalizedBy("shadowJar")
        }

        withType<Jar> {
            doLast {
                copy {
                    from("build/libs")
                    into(System.getProperty("user.home") + "/.openosrs/scripts/")
                }
            }
        }

        compileKotlin {
            kotlinOptions.jvmTarget = "16"
        }
    }
}
