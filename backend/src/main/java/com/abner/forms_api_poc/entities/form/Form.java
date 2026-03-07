package com.abner.forms_api_poc.entities.form;

import com.abner.forms_api_poc.entities.Base;
import com.abner.forms_api_poc.entities.field.FieldBase;
import com.abner.forms_api_poc.entities.form.exceptions.ConflictFieldsException;
import com.abner.forms_api_poc.entities.form.exceptions.EnsureNotClosedException;
import com.abner.forms_api_poc.entities.form.exceptions.MaxFieldsException;
import com.abner.forms_api_poc.entities.form.exceptions.MinFieldsException;
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

  private Form(String title, FormStatus status, List<FieldBase> fields, Instant publishedAt) {
    this.title = title;

    if (status == FormStatus.CLOSED) {
      throw new EnsureNotClosedException("Não pode ser criado um formulário fechado");
    }

    this.status = status;
    fields.forEach(this::addField);
    this.publishedAt = publishedAt;
  }

  public static Form create(
      String title, FormStatus status, List<FieldBase> fields, Instant publishedAt) {

    Form maybeForm = new Form(title, status, fields, publishedAt);

    if (maybeForm.fields.isEmpty()) {
      throw new MinFieldsException("O fomulário precisa de pelo menos 1 campo");
    }

    return maybeForm;
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
}
