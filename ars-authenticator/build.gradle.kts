plugins {
    id("java")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("com.google.protobuf")
}

group = "dev.jakubw"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    //    grpc
    implementation("org.springframework.boot:spring-boot-starter-grpc-client")
    implementation("com.google.protobuf:protobuf-java:4.35.1")
    implementation("io.grpc:grpc-stub:1.82.2")
    implementation("io.grpc:grpc-protobuf:1.82.2")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.0.2")
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.projectlombok:lombok")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    annotationProcessor("org.projectlombok:lombok")
    implementation("com.nimbusds:nimbus-jose-jwt:10.0.2")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-opentelemetry")

    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.springframework.security:spring-security-test")
    runtimeOnly("org.postgresql:postgresql")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}