package com.abner.forms_api_poc.entities.form.http;

import com.abner.forms_api_poc.entities.form.Form;
import com.abner.forms_api_poc.entities.form.http.dtos.FormDTO;
import com.abner.forms_api_poc.entities.form.http.dtos.UpdateFormRequest;
import com.abner.forms_api_poc.entities.form.usecases.UpdateFormByIdUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateFormByIdController extends FormController {
  private final UpdateFormByIdUseCase updateFormByIdUseCase;

  public UpdateFormByIdController(UpdateFormByIdUseCase updateFormByIdUseCase) {
    this.updateFormByIdUseCase = updateFormByIdUseCase;
  }

  @PutMapping("/{id}")
  public ResponseEntity<FormDTO> handle(
      @Valid @RequestBody UpdateFormRequest req, @PathVariable String id) {
    Form updatedForm = this.updateFormByIdUseCase.execute(id, req.toCommand());

    return ResponseEntity.ok(FormDTO.fromDomain(updatedForm));
  }
}
