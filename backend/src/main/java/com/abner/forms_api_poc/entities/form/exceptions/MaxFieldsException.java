package com.abner.forms_api_poc.entities.form.exceptions;

import com.abner.forms_api_poc.exceptions.BadRequestException;

public class MaxFieldsException extends BadRequestException {
  public MaxFieldsException(String message) {
    super(message);
  }
}
