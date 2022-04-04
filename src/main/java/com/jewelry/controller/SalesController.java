package com.jewelry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jewelry.Message;
import com.jewelry.domain.model.Sales;
import com.jewelry.domain.service.CustomerService;
import com.jewelry.domain.service.MessageService;
import com.jewelry.domain.service.SalesService;
import com.jewelry.form.SalesForm;

@Controller
@RequestMapping("/sales")
public class SalesController {
	@Autowired
	private SalesService salesService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private MessageService messageService;

	@GetMapping("/signup")
	public String getSales(SalesForm form, Model model) {
		model.addAttribute("customerList", customerService.findAll());
		model.addAttribute("contents", "contents/sales/sales :: sales_contents");
		return "homeLayout";
	}

	@GetMapping("/list")
	public String salesList(Model model, @PageableDefault(page = 0, size = 10) Pageable pageable) {
		Page<Sales> salesPage = salesService.findPage(pageable);

		model.addAttribute("page", salesPage);
		model.addAttribute("salesList", salesPage.getContent());
		model.addAttribute("contents", "contents/sales/salesList :: salesList_contents");
		return "homeLayout";
	}

	@PostMapping("/signup")
	public String signup(@Validated SalesForm form, BindingResult result, Model model,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			model.addAttribute("customerList", customerService.findAll());
			model.addAttribute("contents", "contents/sales/sales :: sales_contents");
			return "homeLayout";
		}

		redirectAttributes.addFlashAttribute("message", messageService.getMessage(Message.SIGNUP));
		return "redirect:/sales/signup";
	}

}
