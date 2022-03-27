package com.jewelry.form;

import java.util.Date;
import java.util.List;

import com.jewelry.domain.model.CustomerMail;
import com.jewelry.domain.model.CustomerPhone;
import com.jewelry.domain.model.Tantosha;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class CustomerForm {
	// TODO:
	private Integer id;
	private String name;
	private String nameKana;
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
}
