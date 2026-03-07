package com.abner.forms_api_poc.entities.form.http.dtos;

import com.abner.forms_api_poc.entities.field.http.dtos.CreateFieldNumberRequest;
import com.abner.forms_api_poc.entities.field.http.dtos.CreateFieldRequest;
import com.abner.forms_api_poc.entities.field.http.dtos.CreateFieldSelectRequest;
import com.abner.forms_api_poc.entities.field.http.dtos.CreateFieldTextRequest;
import com.abner.forms_api_poc.entities.form.FormStatus;
import com.abner.forms_api_poc.entities.form.usecases.CreateFormCommand;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public record CreateFormRequest(
    @NotBlank(message = "Preencha o titluo") String title,
    @NotNull(message = "Preencha o status") @Valid FormStatus status,
    @Size(min = 1, max = 100, message = "Um formulário pode ter no máximo 100 aulas") @Valid
        List<CreateFieldTextRequest> textFields,
    List<CreateFieldNumberRequest> numberFields,
    List<CreateFieldSelectRequest> selectFields,
    Instant publishedAt) {
  public CreateFormCommand toCommand() {
    List<CreateFieldTextRequest> textFields =
        this.textFields() != null ? this.textFields() : Collections.emptyList();
    List<CreateFieldNumberRequest> numberFields =
        this.numberFields() != null ? this.numberFields() : Collections.emptyList();
    List<CreateFieldSelectRequest> selectFields =
        this.selectFields() != null ? this.selectFields() : Collections.emptyList();

    var fields =
        Stream.concat(
                Stream.concat(
                    textFields.stream().map(CreateFieldRequest::toDomain),
                    numberFields.stream().map(CreateFieldRequest::toDomain)),
                selectFields.stream().map(CreateFieldRequest::toDomain))
            .toList();

    return new CreateFormCommand(this.title(), this.status(), fields, this.publishedAt());
  }
}
