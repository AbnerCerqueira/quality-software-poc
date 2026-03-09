package com.abner.forms_api_poc.e2e;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import com.abner.forms_api_poc.e2e.config.BaseE2ETest;
import com.abner.forms_api_poc.entities.form.FormStatus;
import com.abner.forms_api_poc.entities.form.http.dtos.FormDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class CloseFormE2ETest extends BaseE2ETest {
  FormDTO form;

  @BeforeEach
  void createForm() {
    this.form = FormFactory.createDraft();
  }

  @Test
  void shouldCloseForm() {
    given()
        .contentType("application/json")
        .when()
        .patch("/api/form/close/" + form.id())
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("id", equalTo(form.id()))
        .body("status", equalTo(FormStatus.CLOSED.value()))
        .body("closedAt", notNullValue());
  }

  @Test
  void shouldReturn204IfAlreadyClosed() {
    given()
        .contentType("application/json")
        .when()
        .patch("/api/form/close/" + form.id())
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("id", equalTo(form.id()))
        .body("status", equalTo(FormStatus.CLOSED.value()))
        .body("closedAt", notNullValue());

    given()
        .contentType("application/json")
        .when()
        .patch("/api/form/close/" + form.id())
        .then()
        .statusCode(HttpStatus.NO_CONTENT.value());
  }

  @Test
  void shouldReturn204IfNotExists() {
    String randomObjectId = "69af3f61c7d558e80f03e761";
    given()
        .contentType("application/json")
        .when()
        .patch("/api/form/close/" + randomObjectId)
        .then()
        .statusCode(HttpStatus.NO_CONTENT.value());
  }
}
