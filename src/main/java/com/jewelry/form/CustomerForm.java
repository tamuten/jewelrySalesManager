package com.jewelry.form;

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
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@Builder
@NoArgsConstructor
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
}
