package com.abner.forms_api_poc.e2e;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class BaseE2ETest {

  @BeforeAll
  static void startContainer() {
    MongoDBContainerSingleton.getInstance().start();
  }

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.mongodb.uri", MongoDBContainerSingleton::getUrl);
  }

  @Autowired
  MongoTemplate mongoTemplate;

  @LocalServerPort
  int port;

  @BeforeEach
  void setup() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = port;
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

    RestAssured.config =
      RestAssured.config()
                 .objectMapperConfig(
                   new ObjectMapperConfig()
                     .jackson2ObjectMapperFactory(
                       (cls, charset) -> new ObjectMapper().registerModule(new JavaTimeModule())));

    cleanDatabase();
  }

  void cleanDatabase() {
    mongoTemplate.dropCollection("forms");
    mongoTemplate.dropCollection("submissions");
  }
}
