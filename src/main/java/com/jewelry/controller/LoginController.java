package com.jewelry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
	@GetMapping("/login")
	public String getLogin() {
		return "login";
	}

	/**
	 * ログイン画面のPOSTメソッド用処理.
	 */
	@PostMapping("/login")
	public String postLogin(Model model) {
		return "redirect:/home";
	}

	@GetMapping("/home")
	public String home(Model model) {
		model.addAttribute("contents", "contents/salesList :: salesList_contents");
		return "homeLayout";
	}

	@GetMapping("/sales")
	public String sales(Model model) {
		model.addAttribute("contents", "contents/sales :: sales_contents");
		return "homeLayout";
	}

	@GetMapping("/salesList")
	public String salesList(Model model) {
		model.addAttribute("contents", "contents/salesList :: salesList_contents");
		return "homeLayout";
	}
}
