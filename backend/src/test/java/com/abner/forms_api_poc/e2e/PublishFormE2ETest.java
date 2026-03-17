package com.abner.forms_api_poc.e2e;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import com.abner.forms_api_poc.e2e.config.BaseE2ETest;
import com.abner.forms_api_poc.entities.form.FormStatus;
import com.abner.forms_api_poc.entities.form.http.dtos.FormDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class PublishFormE2ETest extends BaseE2ETest {

  @Test
  void shouldPublishForm() {
    FormDTO form = FormFactory.createDraft();

    given()
        .contentType("application/json")
        .when()
        .patch("/api/form/publish/" + form.id())
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("id", equalTo(form.id()))
        .body("status", equalTo(FormStatus.PUBLISHED.value()))
        .body("publishedAt", notNullValue());
  }

  @Test
  void shouldReturn204IfAlreadyPublished() {
    FormDTO form = FormFactory.createPublished();
    given()
        .contentType("application/json")
        .when()
        .patch("/api/form/publish/" + form.id())
        .then()
        .statusCode(HttpStatus.NO_CONTENT.value());
  }

  @Test
  void shouldReturn204IfNotFound() {
    String randomObjectId = "69af3f61c7d558e80f03e761";

    given()
        .contentType("application/json")
        .when()
        .patch("/api/form/publish/" + randomObjectId)
        .then()
        .statusCode(HttpStatus.NO_CONTENT.value());
  }

  @Test
  void shouldReturn403IfFormClosed() {
    FormDTO closedForm = FormFactory.createClosed();

    given()
        .contentType("application/json")
        .when()
        .patch("/api/form/publish/" + closedForm.id())
        .then()
        .statusCode(HttpStatus.FORBIDDEN.value());
  }
}
