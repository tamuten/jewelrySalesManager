package com.jewelry.form;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.jewelry.constant.Const;

@SpringBootTest
public class ShozokuFormTest {
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

	@Test
	void 正常系_境界値_1() {
		ShozokuForm testShozokuForm = new ShozokuForm();
		testShozokuForm.setName("あ");

		 Set<ConstraintViolation<ShozokuForm>> violations = validator.validate(testShozokuForm);
		 assertEquals(violations.size(), 0);
	}

	@Test
	void 正常系_境界値_50() {
		ShozokuForm testShozokuForm = new ShozokuForm();
		testShozokuForm.setName(Const.CHARS_50);

		 Set<ConstraintViolation<ShozokuForm>> violations = validator.validate(testShozokuForm);
		 assertEquals(violations.size(), 0);
	}

	@Test
	void 異常系_空文字() {
		ShozokuForm testShozokuForm = new ShozokuForm();
		testShozokuForm.setName("");

		 Set<ConstraintViolation<ShozokuForm>> violations = validator.validate(testShozokuForm);
		 assertThat(violations.size()).isEqualTo(1);
	}

	@Test
	void 異常系_null() {
		ShozokuForm testShozokuForm = new ShozokuForm();
		testShozokuForm.setName(null);

		 Set<ConstraintViolation<ShozokuForm>> violations = validator.validate(testShozokuForm);
		 assertThat(violations.size()).isEqualTo(1);
	}

	@Test
	void 異常系_文字数オーバー() {
		ShozokuForm testShozokuForm = new ShozokuForm();
		testShozokuForm.setName(Const.CHARS_51);

		 Set<ConstraintViolation<ShozokuForm>> violations = validator.validate(testShozokuForm);
		 assertThat(violations.size()).isEqualTo(1);
	}
}
