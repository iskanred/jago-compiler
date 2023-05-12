plugins {
    antlr
    application
    java

    kotlin("jvm") version "1.8.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "inno.jago"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    @Suppress("DEPRECATION")
    jcenter()
}

val junitVersion = "5.9.2"
dependencies {
    antlr("org.antlr:antlr4:4.12.0")

    implementation("org.ow2.asm:asm-all:5.2")
    implementation("com.andreapivetta.kolor:kolor:1.0.0")

    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

application {
    mainClass.set("inno.jago.MainKt")
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
        manifest.attributes["Main-Class"] = application.mainClass
    }

    shadowJar {
        archiveBaseName.set("jago")
        archiveClassifier.set("")
        archiveVersion.set("")
    }
}

subprojects {
    apply(plugin = "xcode")
    apply(plugin = "visual-studio")
}
