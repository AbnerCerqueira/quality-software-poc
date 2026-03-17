package com.abner.forms_api_poc.entities.form.http;

import com.abner.forms_api_poc.entities.form.Form;
import com.abner.forms_api_poc.entities.form.http.dtos.FormDTO;
import com.abner.forms_api_poc.entities.form.http.dtos.UpdateFormRequest;
import com.abner.forms_api_poc.entities.form.usecases.UpdateFormByIdUseCase;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
  @ApiResponse(responseCode = "200", description = "Formulario editado com sucesso")
  @ApiResponse(responseCode = "400", description = "Dados invalidos")
  @ApiResponse(responseCode = "404", description = "Form não encontrado")
  @ApiResponse(responseCode = "409", description = "Campos duplicados")
  public ResponseEntity<FormDTO> handle(
      @Valid @RequestBody UpdateFormRequest req, @PathVariable String id) {
    Form updatedForm = this.updateFormByIdUseCase.execute(id, req.toCommand());

    return ResponseEntity.ok(FormDTO.fromDomain(updatedForm));
  }
}
