package com.abner.forms_api_poc.entities.form.http;

import com.abner.forms_api_poc.entities.form.Form;
import com.abner.forms_api_poc.entities.form.http.dtos.FormDTO;
import com.abner.forms_api_poc.entities.form.usecases.CloseFormUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CloseFormController extends FormController {
  private final CloseFormUseCase useCase;

  public CloseFormController(CloseFormUseCase useCase) {
    this.useCase = useCase;
  }

  @PatchMapping("/close/{id}")
  public ResponseEntity<FormDTO> handle(@PathVariable String id) {
    Form closedForm = this.useCase.execute(id);

    return closedForm == null
        ? ResponseEntity.noContent().build()
        : ResponseEntity.ok(FormDTO.fromDomain(closedForm));
  }
}
