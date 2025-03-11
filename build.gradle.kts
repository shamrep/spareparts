plugins {
	java
//	id("org.springframework.boot") version "3.3.4"
//	id("io.spring.dependency-management") version "1.1.6"
	id("idea")
}

group = "com.spareparts"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(23)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web:3.4.3")

	implementation("org.liquibase:liquibase-core:4.31.1")
	implementation("org.postgresql:postgresql:42.7.5")

	implementation("org.springframework:spring-jdbc:6.2.3")
	implementation("org.springframework:spring-context:6.2.3")
	implementation ("org.springframework:spring-core:6.2.3")
	implementation("org.springframework:spring-beans:6.2.3")

	// YAML support  (application.yml)
	implementation("org.yaml:snakeyaml:2.4")

	implementation("org.apache.tomcat.embed:tomcat-embed-core:11.0.2")
	implementation("org.apache.tomcat.embed:tomcat-embed-jasper:11.0.2")

	compileOnly("org.projectlombok:lombok:1.18.36")
	annotationProcessor("org.projectlombok:lombok:1.18.36")

	implementation("com.zaxxer:HikariCP:6.1.0")

	implementation("org.springframework.security:spring-security-crypto:6.4.3")

	implementation("io.jsonwebtoken:jjwt-api:0.12.6")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")

	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.16.1")


	compileOnly("jakarta.servlet:jakarta.servlet-api:6.1.0")
	implementation("com.fasterxml.jackson.core:jackson-core:2.18.2")


	testImplementation("org.testcontainers:junit-jupiter:1.20.3")
	testImplementation("org.testcontainers:postgresql:1.20.3")

	testImplementation("org.mockito:mockito-core:5.14.2")
	testImplementation("org.mockito:mockito-junit-jupiter:5.14.2")

	testImplementation("org.junit.platform:junit-platform-launcher:1.12.0")
	testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")

	testImplementation("org.assertj:assertj-core:3.26.3")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
