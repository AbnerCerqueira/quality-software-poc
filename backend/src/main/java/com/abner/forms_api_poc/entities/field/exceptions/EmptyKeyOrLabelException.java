package com.abner.forms_api_poc.entities.field.exceptions;

import com.abner.forms_api_poc.exceptions.BadRequestException;

public class EmptyKeyOrLabelException extends BadRequestException {
  public EmptyKeyOrLabelException(String message) {
    super(message);
  }
}
