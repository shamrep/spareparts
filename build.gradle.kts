plugins {
	java
	id("org.springframework.boot") version "3.3.4"
	id("io.spring.dependency-management") version "1.1.6"
	id("idea")
}

group = "com.spareparts"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(23)
	}
}

//configurations {
//	compileOnly {
//		extendsFrom(configurations.annotationProcessor.get())
//	}
//}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")

//	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation ("org.liquibase:liquibase-core")
	runtimeOnly("org.postgresql:postgresql")


	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	
//	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("com.zaxxer:HikariCP:6.1.0")

	implementation("org.mapstruct:mapstruct:1.6.3")

	implementation ("org.springframework.security:spring-security-crypto")


	testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
	testImplementation("org.testcontainers:junit-jupiter:1.20.3")
	testImplementation("org.testcontainers:postgresql:1.20.3")
	testImplementation("org.junit.platform:junit-platform-launcher")

	testImplementation("org.assertj:assertj-core:3.26.3")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
