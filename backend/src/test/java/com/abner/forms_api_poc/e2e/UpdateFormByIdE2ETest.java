package com.abner.forms_api_poc.e2e;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import com.abner.forms_api_poc.e2e.config.BaseE2ETest;
import com.abner.forms_api_poc.entities.field.http.dtos.CreateFieldNumberRequest;
import com.abner.forms_api_poc.entities.field.http.dtos.CreateFieldTextRequest;
import com.abner.forms_api_poc.entities.form.FormStatus;
import com.abner.forms_api_poc.entities.form.http.dtos.FormDTO;
import com.abner.forms_api_poc.entities.form.http.dtos.UpdateFormRequest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class UpdateFormByIdE2ETest extends BaseE2ETest {

  FormDTO form;

  @BeforeEach
  void createForm() {
    this.form = FormFactory.createDraft();
  }

  @Test
  void shouldUpdateFormTitle() {
    UpdateFormRequest request = new UpdateFormRequest("Novo Título", null, null, null);

    given()
        .contentType("application/json")
        .body(request)
        .when()
        .put("/api/form/" + form.id())
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("title", equalTo("Novo Título"))
        .body("status", equalTo(FormStatus.DRAFT.value()));
  }

  @Test
  void shouldUpdateFormFields() {
    CreateFieldTextRequest field = new CreateFieldTextRequest();
    field.key = "nome";
    field.label = "Nome";
    field.required = true;

    UpdateFormRequest request = new UpdateFormRequest(null, List.of(field), null, null);

    given()
        .contentType("application/json")
        .body(request)
        .when()
        .put("/api/form/" + form.id())
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("status", equalTo(FormStatus.DRAFT.value()))
        .body("fieldDTOList", hasSize(1))
        .body("fieldDTOList[0].key", equalTo("nome"))
        .body("fieldDTOList[0].label", equalTo("Nome"));
  }

  @Test
  void shouldUpdateFormTitleAndFields() {
    CreateFieldNumberRequest field = new CreateFieldNumberRequest();
    field.key = "nota";
    field.label = "Nota";
    field.required = true;
    field.min = 1;
    field.max = 5;

    UpdateFormRequest request = new UpdateFormRequest("Novo Título", null, List.of(field), null);

    given()
        .contentType("application/json")
        .body(request)
        .when()
        .put("/api/form/" + form.id())
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("title", equalTo("Novo Título"))
        .body("status", equalTo(FormStatus.DRAFT.value()))
        .body("fieldDTOList", hasSize(1))
        .body("fieldDTOList[0].key", equalTo("nota"))
        .body("fieldDTOList[0].label", equalTo("Nota"))
        .body("fieldDTOList[0].content.min", equalTo(1))
        .body("fieldDTOList[0].content.max", equalTo(5));
  }

  @Test
  void shouldReturn404ForNonExistentForm() {
    UpdateFormRequest request = new UpdateFormRequest("Título", null, null, null);

    given()
        .contentType("application/json")
        .body(request)
        .when()
        .put("/api/form/123456789012345678901234")
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value());
  }

  @Test
  void shouldNotUpdateWithDuplicateFields() {
    CreateFieldTextRequest field = new CreateFieldTextRequest();
    field.key = "nome";
    field.label = "Nome";
    field.required = true;

    UpdateFormRequest request = new UpdateFormRequest(null, List.of(field, field), null, null);

    given()
        .contentType("application/json")
        .body(request)
        .when()
        .put("/api/form/" + form.id())
        .then()
        .statusCode(HttpStatus.CONFLICT.value());
  }
}
