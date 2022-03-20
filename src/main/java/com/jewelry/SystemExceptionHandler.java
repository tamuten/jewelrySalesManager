package com.jewelry;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SystemExceptionHandler {
	@ExceptionHandler(Exception.class)
	public String handle(Exception e, Model model) {
		model.addAttribute("error", e);
		model.addAttribute("contents", "customError :: error_contents");
		return "homeLayout";
	}

}
