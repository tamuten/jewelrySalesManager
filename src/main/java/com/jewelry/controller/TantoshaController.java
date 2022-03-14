package com.jewelry.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jewelry.domain.model.Tantosha;
import com.jewelry.domain.service.TantoshaService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/tantosha")
@RequiredArgsConstructor
public class TantoshaController {
	private final TantoshaService service;

	@GetMapping("/list")
	public String getList(Model model) {
		List<Tantosha> tantoshaList = service.findAll();

		model.addAttribute("contents", "contents/tantoshaList :: tantoshaList_contents");
		model.addAttribute("tantoshaList", tantoshaList);
		return "homeLayout";
	}
}
