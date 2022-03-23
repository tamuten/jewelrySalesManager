package com.jewelry.domain.model;

import java.sql.Date;
import java.util.List;

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
public class Customer {
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
