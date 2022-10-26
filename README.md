# user-service

Microservice using [Spring Boot](http://projects.spring.io/spring-boot/) and [H2 DataBase](https://www.h2database.com/html/main.html) to expose User REST API.



## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Gradle 4](https://gradle.org/install/)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.improving.userservice.UserServiceApplication` class from your IDE.

Alternatively you can use the [Spring Boot Gradle plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins.html#build-tool-plugins.gradle) in root folder:

```shell
./gradlew bootRun
```

And for running Unit Test just run

```shell
./gradlew clean test
```

or for more details

```shell
./gradlew clean test --info
```

After running the application you could open [Swagger](https://swagger.io/specification/) to consult all the endpoints and field requirements

```
http://localhost:8080/improving-user/api/swagger-ui/index.html
```


# Libraries used

- [Spring Boot Starter Data JPA](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa) : Starter for using Spring Data JPA with Hibernate.
- [Spring Boot Starter Web](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web) : Starter for building web, including RESTful, applications using Spring MVC. Uses Tomcat as the default embedded container.
- [Spring Boot Starter Test](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-test): Starter for testing Spring Boot applications with libraries including JUnit Jupiter, Hamcrest and Mockito
- [Mockito Core 4.8.0](https://mvnrepository.com/artifact/org.mockito/mockito-core): Mockito mock objects library core API and implementation
- [Spring Doc Open API UI](https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-ui/1.6.11): Helps automating the generation of API documentation using Spring Boot projects.

## License
[MIT](https://choosealicense.com/licenses/mit/)
