package com.jewelry.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jewelry.domain.model.Customer;
import com.jewelry.domain.service.CustomerService;
import com.jewelry.domain.service.TantoshaService;
import com.jewelry.form.CustomerForm;
import com.jewelry.util.DateUtil;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	// TODO:
	@Autowired
	private CustomerService customerService;
	@Autowired
	private TantoshaService tantoshaService;

	@GetMapping("/list")
	public String getList(Model model) {
		List<Customer> customerList = customerService.findAll();

		model.addAttribute("customerList", customerList);
		model.addAttribute("contents", "contents/customer/customerList :: customerList_contents");
		return "homeLayout";
	}

	private Map<String, String> initRadioBloodType() {
		Map<String, String> radio = new LinkedHashMap<>();

		radio.put("A", "A");
		radio.put("B", "B");
		radio.put("O", "O");
		radio.put("AB", "AB");

		return radio;
	}

	@GetMapping("/detail/{id}")
	public String detail(CustomerForm form, @PathVariable int id, Model model) {
		Customer customer = customerService.findByPk(id);
		BeanUtils.copyProperties(customer, form);
		form.setBirthday(DateUtil.sqlToUtil(customer.getBirthday()));
		form.setSignupDate(DateUtil.sqlToUtil(customer.getSignupDate()));

		model.addAttribute("displayMode", "update");
		model.addAttribute("radioBloodType", initRadioBloodType());
		model.addAttribute("tantoshaList", tantoshaService.findAll());
		model.addAttribute("contents", "contents/customer/customer :: customer_contents");
		return "homeLayout";
	}

}
