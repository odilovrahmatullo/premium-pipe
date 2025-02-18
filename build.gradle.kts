	import org.apache.tools.ant.filters.ReplaceTokens
	import org.springframework.boot.gradle.tasks.run.BootRun

	plugins {
		// https://github.com/spring-gradle-plugins/dependency-management-plugin/releases
		id("io.spring.dependency-management") version "1.1.6"

		// https://github.com/spring-projects/spring-boot/releases
		id("org.springframework.boot") version "3.3.6"

		// https://github.com/spotbugs/spotbugs-gradle-plugin/releases
		id("com.github.spotbugs") version "6.0.7"

		// https://github.com/diffplug/spotless/tree/master/plugin-gradle
		// https://mvnrepository.com/artifact/com.diffplug.spotless/spotless-plugin-gradle
	//	id("com.diffplug.spotless") version "6.25.0"

		// https://github.com/researchgate/gradle-release
		// https://mvnrepository.com/artifact/net.researchgate.release/net.researchgate.release.gradle.plugin
		id("net.researchgate.release") version "3.0.2"

		// https://github.com/ben-manes/gradle-versions-plugin/releases
		id("com.github.ben-manes.versions") version "0.51.0"

		java
		idea
		kotlin("jvm")
	}

	group = "com.ndc"
	version = "0.0.1-SNAPSHOT"

	java {
		toolchain {
			languageVersion = JavaLanguageVersion.of(21)
		}
	}


	configurations {
		all {
			exclude("org.springframework.boot", "spring-boot-starter-logging")

			// Can"t exclude because of this: https://github.com/testcontainers/testcontainers-java/issues/970
			// exclude("junit", "junit")
		}
	}
	configurations.named("spotbugs").configure {
		resolutionStrategy.eachDependency {
			if (requested.group == "org.ow2.asm") {
				useVersion("9.5")
				because("Asm 9.5 is required for JDK 21 support")
			}
		}
	}

	repositories {
		mavenLocal()
		mavenCentral()
		google()
	}


	dependencyManagement {
		imports {
			// https://github.com/spring-projects/spring-boot/releases
			mavenBom("org.springframework.boot:spring-boot-dependencies:3.3.5")

			// To avoid specifying the version of each dependency, use a BOM or Bill Of Materials.
			// https://github.com/testcontainers/testcontainers-java/releases
			mavenBom("org.testcontainers:testcontainers-bom:1.18.3")

			//https://immutables.github.io/
			mavenBom("org.immutables:bom:2.9.2")
		}

		dependencies {
			// https://github.com/apache/logging-log4j2/tags
			dependencySet("org.apache.logging.log4j:2.20.0") {
				entry("log4j-core")
				entry("log4j-api")
				entry("log4j-web")
			}
		}
	}

	dependencies {
		// https://github.com/spotbugs/spotbugs/tags
		compileOnly("com.github.spotbugs:spotbugs-annotations:4.8.3")
		testCompileOnly("com.github.spotbugs:spotbugs-annotations:4.8.3")

		// https://github.com/KengoTODA/findbugs-slf4j
		spotbugsPlugins("jp.skypencil.findbugs.slf4j:bug-pattern:1.5.0@jar")

		spotbugsPlugins("com.h3xstream.findsecbugs:findsecbugs-plugin:1.12.0")

		annotationProcessor("org.immutables:value")
		compileOnly("org.immutables:builder")
		compileOnly("org.immutables:value-annotations")

		// https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#configuration-metadata-annotation-processor
		annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

		implementation("org.springframework.boot:spring-boot-starter-data-jpa")
		implementation("org.springframework.boot:spring-boot-starter-data-redis")
		implementation("org.springframework.boot:spring-boot-starter-mail")
		implementation("org.springframework.boot:spring-boot-starter-security")
		implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
		implementation("org.springframework.boot:spring-boot-starter-validation")
		implementation("org.springframework.boot:spring-boot-starter-web")
		implementation("org.liquibase:liquibase-core")
		implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")

		// https://mvnrepository.com/artifact/org.thymeleaf.extras/thymeleaf-extras-java8time
		implementation("org.thymeleaf.extras:thymeleaf-extras-java8time:3.0.4.RELEASE")


		compileOnly("org.projectlombok:lombok")
		developmentOnly("org.springframework.boot:spring-boot-devtools")
		runtimeOnly("com.mysql:mysql-connector-j")
		annotationProcessor("org.projectlombok:lombok")

		// https://mvnrepository.com/artifact/org.hibernate/hibernate-core
		implementation("org.hibernate:hibernate-core:6.5.2.Final")

		// https://mvnrepository.com/artifact/commons-fileupload/commons-fileupload
		implementation("commons-fileupload:commons-fileupload:1.5")

		// https://mvnrepository.com/artifact/net.coobird/thumbnailator
		implementation("net.coobird:thumbnailator:0.4.1")
		implementation("io.jsonwebtoken:jjwt-api:0.12.5")

		// https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-impl
		runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.5")

		// https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-jackson
		runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.5")

		// https://mvnrepository.com/artifact/me.paulschwarz/spring-dotenv
		implementation("me.paulschwarz:spring-dotenv:4.0.0")

		// https://mvnrepository.com/artifact/commons-codec/commons-codec
		implementation("commons-codec:commons-codec:1.17.1")

		implementation("org.mapstruct:mapstruct:1.6.2")
		annotationProcessor("org.mapstruct:mapstruct-processor:1.6.2")

		// https://mvnrepository.com/artifact/org.slf4j/slf4j-api
		implementation("org.slf4j:slf4j-api:2.0.16")

		implementation("org.slf4j:slf4j-log4j12:2.0.16")

		testImplementation("org.slf4j:slf4j-simple:2.0.16")

		// google auth
		implementation("com.google.api-client:google-api-client:2.7.0")

		// tests
		testImplementation("org.springframework.boot:spring-boot-starter-test")
		testImplementation("org.springframework.security:spring-security-test")
		testRuntimeOnly("org.junit.platform:junit-platform-launcher")

		// https://mvnrepository.com/artifact/org.junit.vintage/junit-vintage-engine
		testImplementation("org.junit.vintage:junit-vintage-engine:5.11.2")


		implementation(kotlin("stdlib-jdk8"))

		// https://mvnrepository.com/artifact/com.github.slugify/slugify
		implementation("com.github.slugify:slugify:3.0.7") {
			capabilities {
				requireCapability("com.github.slugify:slugify-transliterator")
			}
		}
	}

	spotbugs {
		toolVersion.set("4.7.3")
		excludeFilter.set(file("${project.rootDir}/findbugs-exclude.xml"))
	}



	tasks {
		spotbugsMain {
			effort.set(com.github.spotbugs.snom.Effort.MAX)
			reports.create("html") {
				enabled = true
			}
		}

		val bootRun by getting(BootRun::class) {
			jvmArgs = listOf("-Duser.timezone=Asia/Tashkent")
		}

		spotbugsTest {
			ignoreFailures = true
			reportLevel.set(com.github.spotbugs.snom.Confidence.HIGH)
			effort.set(com.github.spotbugs.snom.Effort.MIN)
			reports.create("html") {
				enabled = true
			}
		}
	}

	tasks.compileJava {
		dependsOn("processResources")
		options.release.set(21)
		options.encoding = "UTF-8"
		options.compilerArgs.addAll(listOf("-Xlint:deprecation"))
	}

	tasks.processResources {
		val tokens = mapOf(
			"application.version" to project.version,
			"application.description" to project.description
		)
		filesMatching("**/*.yml") {
			filter<ReplaceTokens>("tokens" to tokens)
		}
	}


	tasks.test {
		failFast = false
		enableAssertions = true

		// Enable JUnit 5 (Gradle 4.6+).
		useJUnitPlatform()

		testLogging {
			events("PASSED", "STARTED", "FAILED", "SKIPPED")
			// Set to true if you want to see output from tests
			showStandardStreams = false
			setExceptionFormat("FULL")
		}

		systemProperty("io.netty.leakDetectionLevel", "paranoid")
	}

	defaultTasks("spotlessApply", "build")


	kotlin {
		jvmToolchain(21)
	}