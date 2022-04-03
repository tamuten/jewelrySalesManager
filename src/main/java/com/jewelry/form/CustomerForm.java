package com.jewelry.form;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

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
	private String phoneNo1;
	private String phoneNo2;
	private String phoneNo3;
	private String mailAddress;
	//	private String zipcode;
	private String address;
	private String memo;
	private Date signupDate;

	private Tantosha tantosha;

	private String displayMode;

	public CustomerForm() {
		this.gender = "unknown";
		this.bloodType = "A";
		this.tantosha = new Tantosha();
		this.signupDate = new Date();
	}

}
