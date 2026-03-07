package com.abner.forms_api_poc.entities.form.usecases;

import com.abner.forms_api_poc.entities.form.Form;
import com.abner.forms_api_poc.entities.form.exceptions.FormNotFoundException;
import com.abner.forms_api_poc.entities.form.repositories.FormRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateFormByIdUseCase {
  private final FormRepository formRepository;

  public UpdateFormByIdUseCase(FormRepository formRepository) {
    this.formRepository = formRepository;
  }

  public Form execute(String id, UpdateFormCommand cmd) {
    var existingForm = this.formRepository.findById(id).orElseThrow(FormNotFoundException::new);

    existingForm.update(cmd.title(), cmd.fields());

    return this.formRepository.save(existingForm);
  }
}
