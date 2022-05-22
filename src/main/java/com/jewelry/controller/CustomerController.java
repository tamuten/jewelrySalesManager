package com.jewelry.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import com.jewelry.Message;
import com.jewelry.domain.model.Customer;
import com.jewelry.domain.model.CustomerSearch;
import com.jewelry.domain.model.Sales;
import com.jewelry.domain.service.CustomerService;
import com.jewelry.domain.service.MessageService;
import com.jewelry.domain.service.SalesService;
import com.jewelry.domain.service.TantoshaService;
import com.jewelry.form.CustomerForm;
import com.jewelry.form.CustomerSearchForm;

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

@Controller
@RequestMapping("/customer")
public class CustomerController {
  // TODO:
  @Autowired
  private CustomerService customerService;
  @Autowired
  private TantoshaService tantoshaService;
  @Autowired
  private SalesService salesService;
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

  /**
   * 顧客リストを表示します。<br>
   * ページングあり。
   *
   * @param model
   * @param pageable
   * @return
   */
  @GetMapping("/list")
  public String getList(CustomerSearchForm form, Model model, @PageableDefault(page = 0, size = 10) Pageable pageable) {
    CustomerSearch customerSearch = new CustomerSearch();
    BeanUtils.copyProperties(form, customerSearch);
    Page<Customer> customerPage = customerService.search(customerSearch, pageable);

    model.addAttribute("page", customerPage);
    model.addAttribute("customerList", customerPage.getContent());
    model.addAttribute("contents", "contents/customer/customerList :: customerList_contents");
    return "homeLayout";
  }

  /**
   * 顧客単位の購入履歴を表示します。<br>
   * ページングあり。
   *
   * @param model
   * @param customerId
   * @param pageable
   * @return
   */
  @GetMapping("/purchase/{id}")
  public String getPurchaseList(Model model, @PathVariable("id") int customerId,
      @PageableDefault(page = 0, size = 10) Pageable pageable) {
    Page<Sales> purchasePage = salesService.findByCustomer(pageable, customerId);

    model.addAttribute("customer", customerService.findByPk(customerId));
    model.addAttribute("page", purchasePage);
    model.addAttribute("purchaseList", purchasePage.getContent());
    model.addAttribute("contents", "contents/customer/customerPurchase :: customerPurchase_contents");
    return "homeLayout";
  }

  private Map<String, String> initRadioBloodType() {
    Map<String, String> radio = new LinkedHashMap<>();

    radio.put("A", "A");
    radio.put("B", "B");
    radio.put("O", "O");
    radio.put("AB", "AB");

    return radio;
  }

  /**
   * 顧客新規登録ページを表示します。
   *
   * @param form
   * @param model
   * @return
   */
  @GetMapping("/signup")
  public String getSignup(CustomerForm form, Model model) {
    form.setDisplayMode("signup");
    model.addAttribute("radioBloodType", initRadioBloodType());
    model.addAttribute("tantoshaList", tantoshaService.findAll());
    model.addAttribute("contents", "contents/customer/customer :: customer_contents");

    return "homeLayout";
  }

  /**
   * 顧客登録処理を行い、詳細ページにリダイレクトします。
   *
   * @param form
   * @param result
   * @param model
   * @param redirectAttributes
   * @return
   */
  @PostMapping("/signup")
  public String postSignup(@Validated CustomerForm form, BindingResult result, Model model,
      RedirectAttributes redirectAttributes) {
    if (result.hasErrors()) {
      model.addAttribute("radioBloodType", initRadioBloodType());
      model.addAttribute("tantoshaList", tantoshaService.findAll());
      model.addAttribute("contents", "contents/customer/customer :: customer_contents");

      return "homeLayout";
    }

    Customer customer = new Customer();
    BeanUtils.copyProperties(form, customer);
    // Calendar cal = Calendar.getInstance();
    // cal.set(form.getBirthdayYear(), form.getBirthdayMonth() + 1,
    // form.getBirthdayDay());
    // customer.setBirthday(new Date(cal.getTime()
    // .getTime()));

    customerService.create(customer);

    redirectAttributes.addFlashAttribute("message", messageService.getMessage(Message.SIGNUP));
    return "redirect:/customer/detail/" + String.valueOf(customer.getId());
  }

  /**
   * idに紐づく顧客詳細ページを表示します。
   *
   * @param form
   * @param id
   * @param model
   * @return
   */
  @GetMapping("/detail/{id}")
  public String detail(CustomerForm form, @PathVariable int id, Model model) {
    Customer customer = customerService.findByPk(id);
    BeanUtils.copyProperties(customer, form);

    form.setDisplayMode("update");
    model.addAttribute("radioBloodType", initRadioBloodType());
    model.addAttribute("tantoshaList", tantoshaService.findAll());
    model.addAttribute("contents", "contents/customer/customer :: customer_contents");
    return "homeLayout";
  }

  @PostMapping("/update")
  public String update(@Validated CustomerForm form, BindingResult result, Model model,
      RedirectAttributes redirectAttributes) {
    if (result.hasErrors()) {
      model.addAttribute("radioBloodType", initRadioBloodType());
      model.addAttribute("tantoshaList", tantoshaService.findAll());
      model.addAttribute("contents", "contents/customer/customer :: customer_contents");

      return "homeLayout";
    }

    Customer customer = new Customer();
    BeanUtils.copyProperties(form, customer);
    // Calendar cal = Calendar.getInstance();
    // cal.set(form.getBirthdayYear(), form.getBirthdayMonth() - 1,
    // form.getBirthdayDay());
    // customer.setBirthday(new Date(cal.getTime()
    // .getTime()));

    customerService.update(customer);

    redirectAttributes.addFlashAttribute("message", messageService.getMessage(Message.UPDATE));
    return "redirect:/customer/detail/" + String.valueOf(customer.getId());
  }

  @PostMapping("/delete")
  public String delete(CustomerForm form, Model model,
      RedirectAttributes redirectAttributes) {
    // TODO: 売上に存在する場合は削除しない
    customerService.delete(form.getId());

    redirectAttributes.addFlashAttribute("message", messageService.getMessage(Message.DELETE));
    return "redirect:/customer/list";
  }
}