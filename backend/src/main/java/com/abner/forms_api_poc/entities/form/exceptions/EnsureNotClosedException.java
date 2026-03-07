package com.abner.forms_api_poc.entities.form.exceptions;

import com.abner.forms_api_poc.exceptions.BadRequestException;

public class EnsureNotClosedException extends BadRequestException {
  public EnsureNotClosedException(String message) {
    super(message);
  }
}
