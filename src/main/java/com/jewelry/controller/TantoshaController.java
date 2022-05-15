package com.jewelry.controller;

import org.springframework.beans.BeanUtils;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jewelry.Message;
import com.jewelry.domain.model.Tantosha;
import com.jewelry.domain.service.MessageService;
import com.jewelry.domain.service.ShozokuService;
import com.jewelry.domain.service.TantoshaService;
import com.jewelry.form.TantoshaForm;
import com.jewelry.form.validator.TantoshaFormValidator;

@Controller
@RequestMapping("/tantosha")
public class TantoshaController {
	@Autowired
	private TantoshaService tantoshaService;
	@Autowired
	private ShozokuService shozokuService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private TantoshaFormValidator validator;

	/**
	 * 未入力のStringをnullに設定する
	 *
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@InitBinder("tantoshaForm")
	public void validatorBinder(WebDataBinder binder) {
		binder.addValidators(validator);
	}

	@GetMapping("/list")
	public String getList(Model model, @PageableDefault(page = 0, size = 10) Pageable pageable) {
		Page<Tantosha> tantoshaPage = tantoshaService.findPage(pageable);

		model.addAttribute("contents", "contents/tantosha/tantoshaList :: tantoshaList_contents");
		model.addAttribute("tantoshaList", tantoshaPage.getContent());
		model.addAttribute("page", tantoshaPage);
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
	public String update(@Validated TantoshaForm form, BindingResult result, Model model,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			model.addAttribute("displayMode", "update");
			model.addAttribute("contents", "contents/tantosha/tantosha :: tantosha_contents");
			model.addAttribute("shozokuList", shozokuService.findAll());
			return "homeLayout";
		}

		Tantosha tantosha = new Tantosha();
		BeanUtils.copyProperties(form, tantosha);

		tantoshaService.update(tantosha);

		redirectAttributes.addFlashAttribute("message", messageService.getMessage(Message.UPDATE));
		return "redirect:/tantosha/detail/" + String.valueOf(tantosha.getId());
	}

	@GetMapping("/signup")
	public String getSignup(TantoshaForm form, Model model) {
		model.addAttribute("displayMode", "signup");
		model.addAttribute("contents", "contents/tantosha/tantosha :: tantosha_contents");
		model.addAttribute("shozokuList", shozokuService.findAll());
		return "homeLayout";
	}

	@PostMapping("/signup")
	public String postSignup(@Validated TantoshaForm form, BindingResult result, Model model,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return getSignup(form, model);
		}

		Tantosha tantosha = new Tantosha();
		BeanUtils.copyProperties(form, tantosha);

		tantoshaService.create(tantosha);

		redirectAttributes.addFlashAttribute("message", messageService.getMessage(Message.SIGNUP));
		return "redirect:/tantosha/detail/" + String.valueOf(tantosha.getId());
	}

	@PostMapping("/delete")
	public String delete(TantoshaForm form, Model model, RedirectAttributes redirectAttributes) {
		tantoshaService.delete(form.getId());

		redirectAttributes.addFlashAttribute("message", messageService.getMessage(Message.DELETE));
		return "redirect:/tantosha/list";
	}

}
