package com.abner.forms_api_poc.e2e;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import com.abner.forms_api_poc.entities.field.http.dtos.CreateFieldTextRequest;
import com.abner.forms_api_poc.entities.form.FormStatus;
import com.abner.forms_api_poc.entities.form.http.dtos.CreateFormRequest;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class FormE2ETest extends BaseE2ETest {
  @Test
  void shouldCreateForm() {
    CreateFieldTextRequest field = new CreateFieldTextRequest();
    field.key = "nome";
    field.label = "Nome";
    field.required = true;

    CreateFormRequest request =
        new CreateFormRequest(
            "Meu Formulário", FormStatus.DRAFT, List.of(field), null, null, Instant.now());

    given()
        .contentType("application/json")
        .body(request)
        .when()
        .post("/api/form")
        .then()
        .statusCode(HttpStatus.CREATED.value())
        .body("title", equalTo("Meu Formulário"))
        .body("status", equalTo("DRAFT"));
  }

  @Test
  void shouldNotCreateInvalidForm() {
    CreateFieldTextRequest field = new CreateFieldTextRequest();
    field.key = "nome";
    field.label = "Nome";
    field.required = true;

    CreateFormRequest request =
        new CreateFormRequest(
            "Meu Formulário", FormStatus.CLOSED, List.of(field), null, null, Instant.now());

    given()
        .contentType("application/json")
        .body(request)
        .when()
        .post("/api/form")
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  void shouldNotCreateFormWithDuplicateFields() {
    CreateFieldTextRequest field1 = new CreateFieldTextRequest();
    field1.key = "nome";
    field1.label = "Nome";
    field1.required = true;

    CreateFormRequest request =
        new CreateFormRequest(
            "Meu Formulário", FormStatus.DRAFT, List.of(field1, field1), null, null, Instant.now());

    given()
        .contentType("application/json")
        .body(request)
        .when()
        .post("/api/form")
        .then()
        .statusCode(HttpStatus.CONFLICT.value());
  }
}
