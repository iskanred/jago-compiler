plugins {
    kotlin("jvm") version "1.8.0"
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
    antlr("org.antlr:antlr4:4.12.0")
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("MainKt")
}

tasks {
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
}
