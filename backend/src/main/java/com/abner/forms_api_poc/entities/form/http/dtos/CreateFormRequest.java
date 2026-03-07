package com.abner.forms_api_poc.entities.form.http.dtos;

import com.abner.forms_api_poc.entities.field.http.dtos.CreateFieldNumberRequest;
import com.abner.forms_api_poc.entities.field.http.dtos.CreateFieldSelectRequest;
import com.abner.forms_api_poc.entities.field.http.dtos.CreateFieldTextRequest;
import com.abner.forms_api_poc.entities.form.FormStatus;
import com.abner.forms_api_poc.entities.form.usecases.CreateFormCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CreateFormRequest(
    @NotBlank(message = "Preencha o titluo") String title,
    @NotNull(message = "Preencha o status") FormStatus status,
    List<CreateFieldTextRequest> textFields,
    List<CreateFieldNumberRequest> numberFields,
    List<CreateFieldSelectRequest> selectFields) {
  public CreateFormCommand toCommand() {
    return new CreateFormCommand(
        this.title(),
        this.status(),
        ConcatFields.execute(this.textFields, this.numberFields, this.selectFields));
  }
}
