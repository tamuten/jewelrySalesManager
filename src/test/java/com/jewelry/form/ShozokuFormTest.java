package com.jewelry.form;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

@SpringBootTest
public class ShozokuFormTest {
	@Autowired
	@Qualifier("mvcValidator")
	Validator validator;

	private ShozokuForm testShozokuForm = new ShozokuForm();
	private BindingResult bindingResult = new BindException(testShozokuForm, "shozokuForm");

	@Test
	void 正常系() {
		testShozokuForm.setName("ああああ");
		validator.validate(testShozokuForm, bindingResult);
		assertNull(bindingResult.getFieldError());
	}

	@Test
	void 異常系_０文字() {
		testShozokuForm.setName("");
		validator.validate(testShozokuForm, bindingResult);
		//		assertTrue(bindingResult.getFieldError()
		//			.toString()
		//			.contains("名前は1文字以上50文字以下で入力してください。"));
		assertNotNull(bindingResult.getFieldError());
		System.out.println(bindingResult.getFieldError()
			.getDefaultMessage());
	}
}
