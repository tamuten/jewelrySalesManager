package com.jewelry.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jewelry.Message;
import com.jewelry.domain.model.Customer;
import com.jewelry.domain.model.CustomerMail;
import com.jewelry.domain.model.CustomerPhone;
import com.jewelry.domain.service.CustomerService;
import com.jewelry.domain.service.MessageService;
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
	@Autowired
	private MessageService messageService;

	@GetMapping("/list")
	public String getList(Model model, @PageableDefault(page = 0, size = 10) Pageable pageable) {
		Page<Customer> customerPage = customerService.findPage(pageable);

		model.addAttribute("page", customerPage);
		model.addAttribute("customerList", customerPage.getContent());
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

	@GetMapping("/signup")
	public String getSignup(CustomerForm form, Model model) {
		form.setDisplayMode("signup");
		model.addAttribute("radioBloodType", initRadioBloodType());
		model.addAttribute("tantoshaList", tantoshaService.findAll());
		model.addAttribute("contents", "contents/customer/customer :: customer_contents");

		return "homeLayout";
	}

	@PostMapping("/signup")
	public String postSignup(@Validated CustomerForm form, BindingResult result, Model model,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			model.addAttribute("radioBloodType", initRadioBloodType());
			model.addAttribute("tantoshaList", tantoshaService.findAll());
			model.addAttribute("contents", "contents/customer/customer :: customer_contents");

			return "homeLayout";
		}

		Customer customer = new Customer();
		BeanUtils.copyProperties(form, customer);
		customer.setBirthday(DateUtil.utilToSql(form.getBirthday()));
		customer.setSignupDate(DateUtil.utilToSql(form.getSignupDate()));

		customerService.create(customer);

		redirectAttributes.addAttribute("message", messageService.getMessage(Message.SIGNUP));
		return "redirect:/customer/detail/" + String.valueOf(customer.getId());
	}

	@GetMapping("/detail/{id}")
	public String detail(CustomerForm form, @PathVariable int id, Model model) {
		Customer customer = customerService.findByPk(id);
		BeanUtils.copyProperties(customer, form);
		form.setBirthday(DateUtil.sqlToUtil(customer.getBirthday()));
		form.setSignupDate(DateUtil.sqlToUtil(customer.getSignupDate()));

		form.setDisplayMode("update");
		model.addAttribute("radioBloodType", initRadioBloodType());
		model.addAttribute("tantoshaList", tantoshaService.findAll());
		model.addAttribute("contents", "contents/customer/customer :: customer_contents");
		return "homeLayout";
	}

	@PostMapping("/phone/addRow")
	public String addPhoneRow(CustomerForm form, Model model) {

		form.getCustomerPhoneList()
			.add(new CustomerPhone());

		model.addAttribute("radioBloodType", initRadioBloodType());
		model.addAttribute("tantoshaList", tantoshaService.findAll());
		model.addAttribute("contents", "contents/customer/customer :: customer_contents");
		return "homeLayout";
	}

	@PostMapping("/phone/removeRow")
	public String removePhoneRow(CustomerForm form, Model model) {
		List<CustomerPhone> customerPhoneList = form.getCustomerPhoneList();
		for (int i = customerPhoneList.size() - 1; i >= 0; i--) {
			CustomerPhone cp = customerPhoneList.get(i);
			if (cp.isRowDelete()) {
				customerPhoneList.remove(i);
			}
		}

		model.addAttribute("radioBloodType", initRadioBloodType());
		model.addAttribute("tantoshaList", tantoshaService.findAll());
		model.addAttribute("contents", "contents/customer/customer :: customer_contents");
		return "homeLayout";
	}

	@PostMapping("/mail/addRow")
	public String addMailRow(CustomerForm form, Model model) {
		form.getCustomerMailList()
			.add(new CustomerMail());

		model.addAttribute("radioBloodType", initRadioBloodType());
		model.addAttribute("tantoshaList", tantoshaService.findAll());
		model.addAttribute("contents", "contents/customer/customer :: customer_contents");
		return "homeLayout";
	}

	@PostMapping("/mail/removeRow")
	public String removeMailRow(CustomerForm form, Model model) {
		List<CustomerMail> customerMailList = form.getCustomerMailList();
		for (int i = customerMailList.size() - 1; i >= 0; i--) {
			CustomerMail cp = customerMailList.get(i);
			if (cp.isRowDelete()) {
				customerMailList.remove(i);
			}
		}

		model.addAttribute("radioBloodType", initRadioBloodType());
		model.addAttribute("tantoshaList", tantoshaService.findAll());
		model.addAttribute("contents", "contents/customer/customer :: customer_contents");
		return "homeLayout";
	}

}
