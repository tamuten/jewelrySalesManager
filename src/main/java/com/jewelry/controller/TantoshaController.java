package com.jewelry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jewelry.domain.model.Tantosha;
import com.jewelry.domain.service.TantoshaService;

@Controller
@RequestMapping("/tantosha")
public class TantoshaController {
	@Autowired
	private TantoshaService service;

	@GetMapping("/list")
	public String getList(Model model) {
		List<Tantosha> tantoshaList = service.findAll();

		model.addAttribute("contents", "contents/tantosha/tantoshaList :: tantoshaList_contents");
		model.addAttribute("tantoshaList", tantoshaList);
		return "homeLayout";
	}
}
