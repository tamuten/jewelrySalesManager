package com.jewelry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jewelry.Message;
import com.jewelry.domain.model.Sales;
import com.jewelry.domain.service.CustomerService;
import com.jewelry.domain.service.MessageService;
import com.jewelry.domain.service.SalesService;
import com.jewelry.form.SalesForm;
import com.jewelry.form.SalesSample;
import com.jewelry.form.helper.SalesHelper;

@Controller
@RequestMapping("/sales")
public class SalesController {
	@Autowired
	private SalesService salesService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private MessageService messageService;

	/**
	 * 未入力のStringをnullに設定する
	 *
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

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

		List<Sales> salesList = SalesHelper.createSalesList(form.getSalesList());
		salesService.createAll(salesList);

		redirectAttributes.addFlashAttribute("message", messageService.getMessage(Message.SIGNUP));
		return "redirect:/sales/signup";
	}

	@PostMapping("/addRow")
	public String addRow(SalesForm form, Model model) {
		form.getSalesList()
			.add(new SalesSample());

		model.addAttribute("customerList", customerService.findAll());
		model.addAttribute("contents", "contents/sales/sales :: sales_contents");
		return "homeLayout";
	}

	@PostMapping("/removeRow")
	public String removeRow(SalesForm form, Model model) {
		List<SalesSample> list = form.getSalesList();
		for (int i = list.size() - 1; i >= 0; i--) {
			if (list.get(i)
				.isRowDelete()) {
				list.remove(i);
			}
		}

		if (list.size() == 0) {
			list.add(new SalesSample());
		}
		model.addAttribute("customerList", customerService.findAll());
		model.addAttribute("contents", "contents/sales/sales :: sales_contents");
		return "homeLayout";
	}

}
