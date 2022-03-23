package com.jewelry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jewelry.domain.model.Customer;
import com.jewelry.domain.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	// TODO:
	@Autowired
	private CustomerService customerService;

	@GetMapping("/list")
	public String getList(Model model) {
		List<Customer> customerList = customerService.findAll();

		model.addAttribute("customerList", customerList);
		model.addAttribute("contents", "contents/customer/customerList :: customerList_contents");
		return "homeLayout";
	}
}
