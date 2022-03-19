package com.jewelry.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jewelry.Message;
import com.jewelry.domain.model.Tantosha;
import com.jewelry.domain.service.MessageService;
import com.jewelry.domain.service.ShozokuService;
import com.jewelry.domain.service.TantoshaService;
import com.jewelry.form.TantoshaForm;

@Controller
@RequestMapping("/tantosha")
public class TantoshaController {
	@Autowired
	private TantoshaService tantoshaService;
	@Autowired
	private ShozokuService shozokuService;
	@Autowired
	private MessageService messageService;

	@GetMapping("/list")
	public String getList(Model model) {
		List<Tantosha> tantoshaList = tantoshaService.findAll();

		model.addAttribute("contents", "contents/tantosha/tantoshaList :: tantoshaList_contents");
		model.addAttribute("tantoshaList", tantoshaList);
		return "homeLayout";
	}

	@GetMapping("/detail/{id}")
	public String detail(@PathVariable int id, TantoshaForm form, Model model) {
		Tantosha tantosha = tantoshaService.findByPk(id);
		BeanUtils.copyProperties(tantosha, form);

		model.addAttribute("displayMode", "update");
		model.addAttribute("contents", "contents/tantosha/tantosha :: tantosha_contents");
		model.addAttribute("shozokuList", shozokuService.findAll());
		return "homeLayout";
	}

	@PostMapping("/update")
	public String update(@Validated TantoshaForm form, BindingResult result, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("displayMode", "update");
			model.addAttribute("contents", "contents/tantosha/tantosha :: tantosha_contents");
			model.addAttribute("shozokuList", shozokuService.findAll());
			return "homeLayout";
		}

		Tantosha tantosha = new Tantosha();
		BeanUtils.copyProperties(form, tantosha);

		tantoshaService.update(tantosha);

		model.addAttribute("message", messageService.getMessage(Message.UPDATE));
		return detail(form.getId(), form, model);
	}

	@GetMapping("/signup")
	public String getSignup(TantoshaForm form, Model model) {
		model.addAttribute("displayMode", "signup");
		model.addAttribute("contents", "contents/tantosha/tantosha :: tantosha_contents");
		model.addAttribute("shozokuList", shozokuService.findAll());
		return "homeLayout";
	}

}
