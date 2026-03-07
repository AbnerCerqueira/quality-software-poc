package com.abner.forms_api_poc.entities.form.exceptions;

import com.abner.forms_api_poc.exceptions.ConflictException;

public class ConflictFieldsException extends ConflictException {
  public ConflictFieldsException(String message) {
    super(message);
  }
}
