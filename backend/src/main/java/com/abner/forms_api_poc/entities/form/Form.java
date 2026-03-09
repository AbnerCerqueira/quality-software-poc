package com.abner.forms_api_poc.entities.form;

import com.abner.forms_api_poc.entities.Base;
import com.abner.forms_api_poc.entities.field.FieldBase;
import com.abner.forms_api_poc.entities.form.exceptions.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("forms")
public class Form extends Base {
  public String title;

  public FormStatus status;

  public List<FieldBase> fields = new ArrayList<>();

  public Instant publishedAt;

  public Instant closedAt;

  public Form() {}

  private Form(String title, FormStatus status, List<FieldBase> fields) {
    this.title = title;

    if (status == FormStatus.PUBLISHED) {
      this.publish();
    } else {
      this.status = status;
    }

    fields.forEach(this::addField);
  }

  public static Form create(String title, FormStatus status, List<FieldBase> fields) {

    if (status == FormStatus.CLOSED) {
      throw new EnsureNotClosedException("Não pode ser criado um formulário fechado");
    }

    if (fields.isEmpty()) {
      throw new MinFieldsException("O fomulário precisa de pelo menos 1 campo");
    }

    return new Form(title, status, fields);
  }

  public Form update(String title, List<FieldBase> fields) {
    if (this.status != FormStatus.DRAFT) {
      throw new ForbiddenStatusException("Somente formulários no status DRAFT podem ser editados");
    }

    if (title != null) {
      this.title = title;
    }

    if (fields != null && !fields.isEmpty()) {
      this.fields = new ArrayList<>();
      fields.forEach(this::addField);
    }

    return this;
  }

  public void addField(FieldBase field) {
    if (this.fields.size() == 100) {
      throw new MaxFieldsException("Este fomulário atingiu o máximo de campos suportados");
    }

    boolean duplicatedFields =
        this.fields.stream()
            .anyMatch(
                existingField -> {
                  Boolean sameKey = existingField.key.equals(field.key);

                  Boolean sameLabel = existingField.label.equals(field.label);

                  return sameKey || sameLabel;
                });

    if (duplicatedFields) {
      throw new ConflictFieldsException("Campos duplicados no formulário");
    }

    this.fields.add(field);
  }

  public void publish() {
    if (this.status == FormStatus.PUBLISHED) {
      return;
    }

    if (this.status == FormStatus.CLOSED) {
      throw new ForbiddenStatusException("Formulários fechados não podem ser abertos novamente");
    }

    this.status = FormStatus.PUBLISHED;
    this.publishedAt = Instant.now();
  }

  public void close() {
    if (this.status == FormStatus.CLOSED) {
      return;
    }

    this.status = FormStatus.CLOSED;
    this.closedAt = Instant.now();
  }
}
