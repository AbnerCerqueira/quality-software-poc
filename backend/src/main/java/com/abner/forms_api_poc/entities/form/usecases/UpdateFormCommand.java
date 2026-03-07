package com.abner.forms_api_poc.entities.form.usecases;

import com.abner.forms_api_poc.entities.field.FieldBase;
import java.util.List;

public record UpdateFormCommand(String title, List<FieldBase> fields) {}
