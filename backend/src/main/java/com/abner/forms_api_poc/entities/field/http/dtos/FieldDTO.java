package com.abner.forms_api_poc.entities.field.http.dtos;

import com.abner.forms_api_poc.entities.field.FieldBase;
import com.abner.forms_api_poc.entities.field.FieldType;

public record FieldDTO(
    String key, String label, Boolean required, FieldType fieldType, Object content) {
  public static FieldDTO fromDomain(FieldBase field) {
    return new FieldDTO(
        field.key, field.label, field.required, field.getType(), field.getContent());
  }
}
