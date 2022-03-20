package com.jewelry.form;

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
import com.jewelry.constant.Constant;

@SpringBootTest
public class TantoshaFormTest {
	private static Validator validator;

	@BeforeEach
	void setup() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}

	@Test
	void 正常系() {
		正常系のテスト("ああああ", 1, Constant.ROLE_ADMIN);
		正常系のテスト("あ", 2, Constant.ROLE_GENERAL);
		正常系のテスト(Const.CHARS_50, 3,  Constant.ROLE_GENERAL);
	}

	void 正常系のテスト(String name, Integer shozokuId, String role) {
		TantoshaForm testForm = new TantoshaForm();
		testForm.setName(name);
		testForm.getShozoku().setId(shozokuId);
		testForm.setRole(role);

		 Set<ConstraintViolation<TantoshaForm>> violations = validator.validate(testForm);
		 assertEquals(violations.size(), 0);
	}

	@Test
	void 異常系() {
		異常系のテスト(null, 0, null,2);
		異常系のテスト("", 10000, "",2);
		異常系のテスト(Const.CHARS_51, 1, "test",1);
	}

	void 異常系のテスト(String name, Integer shozokuId, String role, int expectedErrorCount) {
		TantoshaForm testForm = new TantoshaForm();
		testForm.setName(name);
		testForm.getShozoku().setId(shozokuId);
		testForm.setRole(role);

		 Set<ConstraintViolation<TantoshaForm>> violations = validator.validate(testForm);
		 assertEquals(violations.size(), expectedErrorCount);
	}
}
