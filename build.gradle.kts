plugins {
    kotlin("jvm") version "1.8.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"

    antlr
    application
    java
}

group = "inno.jago"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val junitVersion = "5.9.2"
dependencies {
    implementation("org.ow2.asm:asm-all:5.2")
    antlr("org.antlr:antlr4:4.12.0")
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

application {
    mainClass.set("MainKt")
}

tasks {
    test {
        useJUnitPlatform()
    }

    generateGrammarSource {
        outputDirectory = File("$buildDir/generated-src/antlr/main/inno/jago/")
        copy {
            from("${buildDir}/generated-src/antlr/main/inno/jago")
            into("src/main/kotlin/antlr-generated")
        }
        copy {
            from("${buildDir}/generated-src/antlr/main/inno/jago/GoLexer.tokens")
            into("src/main/antlr/")
        }
    }

    compileKotlin { dependsOn.add(generateGrammarSource) }
    compileTestKotlin {
        dependsOn.add(generateTestGrammarSource)
    }

    jar {
        manifest.attributes["Main-Class"] = "inno.jago.MainKt"
    }

    shadowJar {
        archiveBaseName.set("jago")
        archiveClassifier.set("")
        archiveVersion.set("")
    }
}
