package com.jewelry.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.jewelry.domain.model.CustomerMail;
import com.jewelry.domain.model.CustomerPhone;
import com.jewelry.domain.model.Tantosha;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Builder
@AllArgsConstructor
public class CustomerForm {
	// TODO:
	private Integer id;
	private String name;
	private String nameKana;
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date birthday;
	private String gender;
	private String bloodType;
	//	private String zipcode;
	private String address;
	private String memo;
	private Date signupDate;

	private Tantosha tantosha;

	private List<CustomerPhone> customerPhoneList;
	private List<CustomerMail> customerMailList;

	private String displayMode;

	public CustomerForm() {
		this.gender = "unknown";
		this.bloodType = "A";
		this.tantosha = new Tantosha();
		this.customerPhoneList = new ArrayList<CustomerPhone>() {
		};
		this.customerMailList = new ArrayList<CustomerMail>() {
		};
		this.signupDate = new Date();
	}

}
