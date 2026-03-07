package com.abner.forms_api_poc.entities.form.exceptions;

import com.abner.forms_api_poc.exceptions.BadRequestException;

public class MinFieldsException extends BadRequestException {
  public MinFieldsException(String message) {
    super(message);
  }
}
