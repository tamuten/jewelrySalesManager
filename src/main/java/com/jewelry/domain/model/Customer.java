package com.jewelry.domain.model;

import java.time.LocalDate;

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
	public Customer(int id, String name) {
		this.id = id;
		this.name = name;
	}

	private Integer id;
	private String name;
	private String nameKana;
	private LocalDate birthday;
	private String gender;
	private String bloodType;
	private String phoneNo1;
	private String phoneNo2;
	private String phoneNo3;
	private String mailAddress;
	//	private String zipcode;
	private String address;
	private String memo;
	private LocalDate signupDate;

	private Tantosha tantosha;
}
