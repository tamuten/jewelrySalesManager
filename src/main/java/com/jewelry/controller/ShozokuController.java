package com.jewelry.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jewelry.domain.model.Shozoku;
import com.jewelry.domain.service.ShozokuService;
import com.jewelry.form.ShozokuForm;

@Controller
@RequestMapping("/shozoku")
public class ShozokuController {
	@Autowired
	private ShozokuService service;

	@GetMapping("/list")
	public String getList(Model model) {
		List<Shozoku> shozokuList = service.findAll();

		model.addAttribute("contents", "contents/shozoku/shozokuList :: shozokuList_contents");
		model.addAttribute("shozokuList", shozokuList);

		return "homeLayout";
	}

	@GetMapping("/detail/{id}")
	public String detail(ShozokuForm form, @PathVariable("id") int id, Model model) {
		Shozoku shozoku = service.findByPk(id);
		BeanUtils.copyProperties(shozoku, form);

		model.addAttribute("displayMode", "update");
		model.addAttribute("contents", "contents/shozoku/shozoku :: shozoku_contents");
		return "homeLayout";
	}
}
