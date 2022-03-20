package com.jewelry.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	// TODO:

	@GetMapping("/list")
	public String getList(Model model) throws Exception {
		throw new DataIntegrityViolationException("test");
	}
}
