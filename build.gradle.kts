buildscript {
    repositories {
        gradlePluginPortal()
    }
}

plugins {
    `java-library`
    kotlin("jvm") version "1.5.31"
}

version = "0.0.1"

subprojects {
    group = "dev.unethical"

    apply<JavaPlugin>()
    apply(plugin = "java-library")
    apply(plugin = "kotlin")

    repositories {
        mavenCentral()
        jcenter()
        mavenLocal()
    }

    dependencies {
        compileOnly(group = "com.openosrs", name = "runelite-api", version = "4.17.1")
        compileOnly(group = "com.openosrs", name = "runelite-client", version = "4.17.1")
    }

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_16
    }

    tasks {
        build {
            doFirst {
                clean
            }
        }

        withType<Jar> {
            doLast {
                copy {
                    from("build/libs")
                    into(System.getProperty("user.home") + "/.hoot/scripts/")
                }
            }
        }

        compileKotlin {
            kotlinOptions.jvmTarget = "16"
        }
    }
}
