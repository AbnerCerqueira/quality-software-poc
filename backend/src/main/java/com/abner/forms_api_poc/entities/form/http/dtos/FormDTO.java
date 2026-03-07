package com.abner.forms_api_poc.entities.form.http.dtos;

import com.abner.forms_api_poc.entities.field.http.dtos.FieldDTO;
import com.abner.forms_api_poc.entities.form.Form;
import com.abner.forms_api_poc.entities.form.FormStatus;
import java.time.Instant;
import java.util.List;

public record FormDTO(
    String id,
    String title,
    FormStatus status,
    List<FieldDTO> fieldDTOList,
    Instant publishedAt,
    Instant closedAt) {
  public static FormDTO fromDomain(Form form) {
    return new FormDTO(
        form.id,
        form.title,
        form.status,
        form.fields.stream().map(FieldDTO::fromDomain).toList(),
        form.publishedAt,
        form.closedAt);
  }
}
