package com.jewelry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jewelry.domain.model.Shozoku;
import com.jewelry.domain.service.ShozokuService;

@Controller
@RequestMapping("/shozoku")
public class ShozokuController {
	@Autowired
	private ShozokuService service;

	@GetMapping("/list")
	public String getList(Model model) {
		List<Shozoku> shozokuList = service.findAll();

		model.addAttribute("contents", "contents/shozokuList :: shozokuList_contents");
		model.addAttribute("shozokuList", shozokuList);

		return "homeLayout";
	}
}
