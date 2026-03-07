package com.abner.forms_api_poc.entities.field.http.dtos;

import jakarta.validation.constraints.NotNull;

public class CreateFieldNumberRequest extends CreateFieldRequest {
  @NotNull public Integer min;
  @NotNull public Integer max;
}
