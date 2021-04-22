## CIE Academy - Test Driven Development
A simple exercise to get started with Test Driven Development

### Setup:

- IDE: [IntelliJ](https://www.jetbrains.com/idea/download/) 
- Programming Language: [Java](https://www.oracle.com/java/technologies/)
- Build: [Gradle](https://gradle.org/)
- Frameworks: 
    - Development:
        - [Spring Boot (Spring Framework)](https://spring.io/projects/spring-boot)
    - Testing:
        - [JUnit](https://junit.org/junit5/) - Foundation
        - [Mockito](https://site.mockito.org/) - Mocking
        - [MockMvc](https://spring.io/guides/gs/testing-web/) - Spring's MockMvc
        - [RestAssured](https://rest-assured.io/) - Famous for testing REST APIs
        - [PowerMockito](https://powermock.github.io/) - Advanced Mocking (not covered)
- Optionals:
    - Swagger Setup
    - Test Coverage - Jococo
    
  
## Where should we start?
To begin with learning to do test driven development we need a *Project*.

How about we start from scratch (sort of)?

Let's begin with visiting: https://start.spring.io/ (This will take care of the **hard-work** which is needed to get started, leaving us to do what we love) :)

The default selections should do the needful, however let's be a little picky here and make following selections:

1. Project: Gradle Project
2. Project Metadata - Change it as per your liking while following the examples
3. Click ADD DEPENDENCIES, and select following:
    - Spring Boot DevTools (Developer Tools - makes it easier to reload and...)
    - Spring Web (The name says it all, isn't it?)
4. Click Generate
5. Extract downloaded archive
6. Go to IntelliJ
7. File > Open > ...browse and select downloaded Project folder (extracted)

## Time to do some Ping-Pong

Implement **ping** API:

`/ping` -> `PingController.ping` -> `PingService.ping` -> `Utility.ping`

Under /src/main/java/{package}/
1. A library named `Utility` with method: ping (returns `UnsupportedOperationException`)
    ```java
    @Component
    public class Utility {   
        public String ping() {
            throw new UnsupportedOperationException();
        }
    }
    ```
   
2. A service named `PingService` with method: ping (returns `UnsupportedOperationException`)
    ```java
    @Service
    public class PingService {
      
        @Autowired
        public Utility utility;   
   
        public String ping() {
            throw new UnsupportedOperationException();
        }
    }
    ```

3. A controller named `PingController` with `GET` method: ping (returns `UnsupportedOperationException`)
    ```java
    @Controller
    public class PingController {
      
        @Autowired
        public PingService pingService;   
   
        @GetMapping("/ping")
        public String ping() {
            throw new UnsupportedOperationException();
        }
    }
    ```

Under /src/test/java/{package}/, add tests which ensure each is returning "pong" in response
1. A test for Utility: `UtilityTest`
2. A test for PingService: `PingServiceTest`
3. A test for PingController: `PingControllerTest`

Example for `PingServiceTest`:

````java
@SpringBootTest
public class PingServiceTest {

    @Autowired
    PingService pingService;

    @Test
    public void testPingService() {
        // Act
        String result = pingService.ping();
        // Assert
        assertEquals("pong", pingService.ping());
    }
}
````

Right-click on `/src/test` folder and click Run Tests

Now implement the methods

Right-click on `/src/test` folder and click Run Tests

Let's say the Utility isn't really a local library and uses some integration for example connecting to an external database or service to provide us with response. How can we make our implementation independent of external factors?

### Time to mock!

Update ``PingServiceTest`` as below:

````java
@SpringBootTest
public class PingServiceTest {

    @MockBean
    PingUtility pingUtility;

    @Autowired
    PingService pingService;

    @Test
    public void testPingService() {
        // Arrange
        when(pingUtility.ping()).thenReturn("pang");
        // Act
        String result = pingService.ping();
        // Assert
        assertEquals("pong", pingService.ping());
    }
}
````

Should the test `pass` or `fail`? Let's give it a try.

### Testing the API

* Using MockMvc

```java
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UsingMockMvc {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testPing() throws Exception {
        this.mockMvc.perform(
            get("/ping")
        ).andExpect(
            status().isOk()
        ).andExpect(
            content().string("pong")
        );
    }
}
```
* Using [RestAssured](https://rest-assured.io/)

To use this, add following dependencies to Project:
```gradle
testImplementation 'io.rest-assured:spring-mock-mvc'
compile 'com.sun.xml.bind:jaxb-osgi:3.0.0-M5'
```
Example:
```java
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.WebApplicationContext;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest
public class UsingRestAssured {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    @AfterEach
    public void reset() {
        RestAssuredMockMvc.reset();
    }

    @Test
    public void testPing() {
        given()
            .when()
                .get("/ping")
            .then()
                .statusCode(OK.value())
                .body(
                    is(
                        equalTo("pong")
                    )
                );
    }
}
```

* Using TestRestTemplate

```java
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsingTestTemplate {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    String basePath;

    @BeforeEach
    public void setup() {
        this.basePath = String.format("http://localhost:%d/", port);
    }

    @Test
    public void testPing() {
        // Arrange & Act
        String result = this.restTemplate.getForObject(basePath + "ping", String.class);
        // Assert
        assertThat(result).isEqualTo("pong");
    }
}
```

### Optional: Swagger Setup - an integrated API Client

1. Go to build.gradle
2. Under plugins, add following:

    `gradle
    id "io.swagger.core.v3.swagger-gradle-plugin" version "2.1.8"
    `
3. Go to `*Application.java` in `src/main/java/{package}/`
4. Add following method:
    ```java
    @Bean
    public Docket swagger() {
        return new Docket(DocumentationType.SWAGGER_2).select()
            .apis(RequestHandlerSelectors.any())
            .build();
    }
    ```
5. Right click `*Application.java` > Run and finally go to http://localhost:8080/swagger-ui/

### Optional: Jococo Setup - to see coverage (Why is coverage important?)

1. Go to build.gradle
2. Under plugins, add: `id 'jacoco'`
3. Under test, add: `finalizedBy jacocoTestReport`
4. Add following at the very end of build.gradle:
    ```gradle
    jacocoTestReport {
        dependsOn test // tests are required to run before generating the report
    }
    ```
5. Run all tests
6. Go to build/reports/jococo/test/html
7. Right click and open index.html in browser

