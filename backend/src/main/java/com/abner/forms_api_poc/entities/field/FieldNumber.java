package com.abner.forms_api_poc.entities.field;

public class FieldNumber extends FieldBase {
  public Integer min;

  public Integer max;

  private FieldNumber(String key, String label, Boolean required, Integer min, Integer max) {
    super(key, label, required);
    this.min = min;
    this.max = max;
  }

  public static FieldNumber create(
      String key, String label, Boolean required, Integer min, Integer max) {
    return new FieldNumber(key, label, required, min, max);
  }

  @Override
  public FieldType getType() {
    return FieldType.NUMBER;
  }

  @Override
  public Object getContent() {
    return new Content(this.min, this.max);
  }

  @Override
  public void validateAnswer(Object value) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  public record Content(Integer min, Integer max) {}
}
