package com.abner.forms_api_poc.entities.form.http;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/form")
@Tag(name = "Form")
public abstract class FormController {}
