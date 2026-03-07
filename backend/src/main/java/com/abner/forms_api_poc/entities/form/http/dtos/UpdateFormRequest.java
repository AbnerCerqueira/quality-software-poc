package com.abner.forms_api_poc.entities.form.http.dtos;

import com.abner.forms_api_poc.entities.field.http.dtos.CreateFieldNumberRequest;
import com.abner.forms_api_poc.entities.field.http.dtos.CreateFieldSelectRequest;
import com.abner.forms_api_poc.entities.field.http.dtos.CreateFieldTextRequest;
import com.abner.forms_api_poc.entities.form.usecases.UpdateFormCommand;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.util.List;

public record UpdateFormRequest(
    String title,
    @Size(min = 1, max = 100, message = "Um formulário pode ter no máximo 100 aulas") @Valid
        List<CreateFieldTextRequest> textFields,
    List<CreateFieldNumberRequest> numberFields,
    List<CreateFieldSelectRequest> selectFields) {
  public UpdateFormCommand toCommand() {
    return new UpdateFormCommand(
        this.title(), ConcatFields.execute(textFields, numberFields, selectFields));
  }
}
