package com.abner.forms_api_poc.e2e;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import com.abner.forms_api_poc.entities.field.http.dtos.CreateFieldTextRequest;
import com.abner.forms_api_poc.entities.form.FormStatus;
import com.abner.forms_api_poc.entities.form.http.dtos.CreateFormRequest;
import com.abner.forms_api_poc.entities.form.http.dtos.FormDTO;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.springframework.http.HttpStatus;

public class FormFactory {

  public static FormDTO createDraft() {
    String title = "Meu formulário";
    CreateFieldTextRequest field = new CreateFieldTextRequest();
    field.key = "teste";
    field.label = "Teste";
    field.required = false;

    CreateFormRequest request =
        new CreateFormRequest(title, FormStatus.DRAFT, List.of(field), null, null);

    ExtractableResponse<Response> response =
        given()
            .contentType("application/json")
            .body(request)
            .when()
            .post("/api/form")
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .body("id", notNullValue())
            .body("status", equalTo(FormStatus.DRAFT.value()))
            .extract();

    return response.as(FormDTO.class);
  }

  public static FormDTO createClosed() {
    String title = "Meu formulário";
    CreateFieldTextRequest field = new CreateFieldTextRequest();
    field.key = "teste";
    field.label = "Teste";
    field.required = false;

    CreateFormRequest request =
        new CreateFormRequest(title, FormStatus.DRAFT, List.of(field), null, null);

    ExtractableResponse<Response> response =
        given()
            .contentType("application/json")
            .body(request)
            .when()
            .post("/api/form")
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .body("id", notNullValue())
            .extract();

    FormDTO createdForm = response.as(FormDTO.class);

    ExtractableResponse<Response> closedForm =
        given()
            .contentType("application/json")
            .when()
            .patch("/api/form/close/" + createdForm.id())
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("id", equalTo(createdForm.id()))
            .body("status", equalTo(FormStatus.CLOSED.value()))
            .body("closedAt", notNullValue())
            .extract();

    return closedForm.as(FormDTO.class);
  }
}
