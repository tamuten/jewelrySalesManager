package com.jewelry.form;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShozokuFormTest2 {
	private static Validator validator;

	@BeforeEach
	void setup() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}

	@Test
	void 正常系() {
		ShozokuForm testShozokuForm = new ShozokuForm();
		testShozokuForm.setName("ああああ");

		 Set<ConstraintViolation<ShozokuForm>> violations = validator.validate(testShozokuForm);
		 assertEquals(violations.size(), 0);

	}
}
