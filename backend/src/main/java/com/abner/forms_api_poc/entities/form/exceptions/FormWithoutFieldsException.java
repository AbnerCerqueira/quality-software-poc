package com.abner.forms_api_poc.entities.form.exceptions;

import com.abner.forms_api_poc.exceptions.BadRequestException;

public class FormWithoutFieldsException extends BadRequestException {
  public FormWithoutFieldsException(String message) {
    super(message);
  }
}
