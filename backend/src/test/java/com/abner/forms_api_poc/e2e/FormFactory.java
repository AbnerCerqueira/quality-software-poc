package com.abner.forms_api_poc.e2e;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

import com.abner.forms_api_poc.entities.field.http.dtos.CreateFieldTextRequest;
import com.abner.forms_api_poc.entities.form.FormStatus;
import com.abner.forms_api_poc.entities.form.http.dtos.CreateFormRequest;
import com.abner.forms_api_poc.entities.form.http.dtos.FormDTO;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;

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
            .statusCode(201)
            .body("id", notNullValue())
            .extract();

    return response.as(FormDTO.class);
  }
}
