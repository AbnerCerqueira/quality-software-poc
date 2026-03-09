package com.abner.forms_api_poc.entities.form;

public enum FormStatus {
  DRAFT("DRAFT"),
  PUBLISHED("PUBLISHED"),
  CLOSED("CLOSED");

  private final String value;

  FormStatus(String value) {
    this.value = value;
  }

  public String value() {
    return this.value;
  }
}
