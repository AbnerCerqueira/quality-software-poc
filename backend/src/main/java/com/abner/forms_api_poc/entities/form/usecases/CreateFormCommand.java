package com.abner.forms_api_poc.entities.form.usecases;

import com.abner.forms_api_poc.entities.field.FieldBase;
import com.abner.forms_api_poc.entities.form.FormStatus;
import java.time.Instant;
import java.util.List;

public record CreateFormCommand(
    String title, FormStatus status, List<FieldBase> fields, Instant publishedAt) {}
