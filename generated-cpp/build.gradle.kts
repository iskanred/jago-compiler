plugins {
    id("cpp-application")
}

group = "inno.jago"
version = "1.0-SNAPSHOT"

application {
    targetMachines.set(
        listOf(
            machines.windows.x86_64,
            machines.macOS.x86_64,
            machines.linux.x86_64
        )
    )
    baseName.set("executable-output")
}

