package com.jewelry.controller;

import com.jewelry.Message;
import com.jewelry.domain.model.Shozoku;
import com.jewelry.domain.service.MessageService;
import com.jewelry.domain.service.ShozokuService;
import com.jewelry.form.ShozokuForm;

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

@Controller
@RequestMapping("/shozoku")
public class ShozokuController {
	@Autowired
	private ShozokuService shozokuService;
	@Autowired
	private MessageService messageService;

	@GetMapping("/list")
	public String getList(Model model, @PageableDefault(page = 0, size = 10) Pageable pageable) {
		Page<Shozoku> shozokuPage = shozokuService.findPage(pageable);

		model.addAttribute("contents", "contents/shozoku/shozokuList :: shozokuList_contents");
		model.addAttribute("page", shozokuPage);
		model.addAttribute("shozokuList", shozokuPage.getContent());

		return "homeLayout";
	}

	@GetMapping("/detail/{id}")
	public String detail(ShozokuForm form, @PathVariable("id") int id, Model model) {
		Shozoku shozoku = shozokuService.findByPk(id);
		BeanUtils.copyProperties(shozoku, form);

		model.addAttribute("displayMode", "update");
		model.addAttribute("contents", "contents/shozoku/shozoku :: shozoku_contents");
		return "homeLayout";
	}

	@PostMapping("/update")
	public String update(@Validated ShozokuForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("displayMode", "update");
			model.addAttribute("contents", "contents/shozoku/shozoku :: shozoku_contents");
			return "homeLayout";
		}

		Shozoku shozoku = new Shozoku();
		BeanUtils.copyProperties(form, shozoku);

		shozokuService.update(shozoku);

		model.addAttribute("message", messageService.getMessage(Message.UPDATE));
		return detail(form, form.getId(), model);
	}

	@GetMapping("/signup")
	public String getSignup(ShozokuForm form, Model model) {
		model.addAttribute("displayMode", "signup");
		model.addAttribute("contents", "contents/shozoku/shozoku :: shozoku_contents");
		return "homeLayout";
	}

	@PostMapping("/signup")
	public String postSignup(@Validated ShozokuForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return getSignup(form, model);
		}
		Shozoku shozoku = new Shozoku();
		BeanUtils.copyProperties(form, shozoku);

		shozokuService.create(shozoku);

		model.addAttribute("message", messageService.getMessage(Message.SIGNUP));
		return detail(form, shozoku.getId(), model);
	}

	@PostMapping("/delete")
	public String delete(ShozokuForm form, RedirectAttributes redirectAttributes) {
		shozokuService.delete(form.getId());

		redirectAttributes.addFlashAttribute("message", messageService.getMessage(Message.DELETE));
		return "redirect:/shozoku/list";
	}

}
