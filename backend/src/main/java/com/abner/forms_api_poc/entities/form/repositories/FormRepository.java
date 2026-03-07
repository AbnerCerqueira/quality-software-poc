package com.abner.forms_api_poc.entities.form.repositories;

import com.abner.forms_api_poc.entities.form.Form;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FormRepository extends MongoRepository<Form, String> {}
