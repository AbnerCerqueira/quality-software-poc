package com.abner.forms_api_poc.entities.form.usecases;

import com.abner.forms_api_poc.entities.form.Form;
import com.abner.forms_api_poc.entities.form.repositories.FormRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateFormUseCase {
  private final FormRepository formRepository;

  public CreateFormUseCase(FormRepository formRepository) {
    this.formRepository = formRepository;
  }

  public Form execute(CreateFormCommand cmd) {

    Form maybeForm = Form.create(cmd.title(), cmd.status(), cmd.fields());

    return this.formRepository.save(maybeForm);
  }
}
