package com.abner.forms_api_poc.entities.form.http;

import com.abner.forms_api_poc.entities.form.Form;
import com.abner.forms_api_poc.entities.form.http.dtos.CreateFormRequest;
import com.abner.forms_api_poc.entities.form.http.dtos.FormDTO;
import com.abner.forms_api_poc.entities.form.usecases.CreateFormUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateFormController extends FormController {
  private final CreateFormUseCase createFormUseCase;

  public CreateFormController(CreateFormUseCase createFormUseCase) {
    this.createFormUseCase = createFormUseCase;
  }

  @PostMapping("")
  @Operation(
      summary = "Cria formulario",
      description = "Como usuário Quero criar um formulário Para editá-lo posteriormente")
  @ApiResponse(responseCode = "201", description = "Formulario criado com sucesso")
  @ApiResponse(responseCode = "400", description = "Dados invalidos")
  @ApiResponse(responseCode = "409", description = "Campos duplicados")
  public ResponseEntity<FormDTO> handle(@Valid @RequestBody CreateFormRequest request) {
    Form newForm = this.createFormUseCase.execute(request.toCommand());

    return ResponseEntity.status(HttpStatus.CREATED).body(FormDTO.fromDomain(newForm));
  }
}
