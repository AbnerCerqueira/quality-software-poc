package com.abner.forms_api_poc.entities.field;

public class FieldText extends FieldBase {
  private FieldText(String key, String label, Boolean required) {
    super(key, label, required);
  }

  public static FieldText create(String key, String label, Boolean required) {
    return new FieldText(key, label, required);
  }

  @Override
  public FieldType getType() {
    return FieldType.TEXT;
  }

  @Override
  public Object getContent() {
    return new Content();
  }

  @Override
  public void validateAnswer(Object value) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  public record Content() {}
}
