plugins {
    id("java")
    id("com.diffplug.spotless") version "6.11.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.bytebuddy:byte-buddy:1.14.6")
    implementation("org.reflections:reflections:0.10.2")
    implementation("mysql:mysql-connector-java:8.0.33")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

spotless {
    java {
        target("src/main/java/org/example/**/*.java")
        palantirJavaFormat()

        // Import 정렬 순서
        importOrder("lombok", "org", "com", "java", "javax", "org.example")
        // 사용하지 않는 Import 제거
        removeUnusedImports()

        // Custom Rule도 생성할 수 있다.
        custom("noWildcardImports") {
            when {
                it.contains("*;\n") -> throw Error("No wildcard imports allowed")
                else -> it
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()
}