package com.jewelry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SalesController {
	@GetMapping("/sales")
	public String sales(Model model) {
		model.addAttribute("contents", "contents/sales/sales :: sales_contents");
		return "homeLayout";
	}

	@GetMapping("/salesList")
	public String salesList(Model model) {
		model.addAttribute("contents", "contents/sales/salesList :: salesList_contents");
		return "homeLayout";
	}
}
