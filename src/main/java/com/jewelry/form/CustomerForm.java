package com.jewelry.form;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
	@NotEmpty
	@Size(max = 50)
	private String name;
	@Size(max = 100)
	private String nameKana;
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date birthday;
	private Integer birthdayYear;
	private Integer birthdayMonth;
	private Integer birthdayDay;
	private String gender;
	private String bloodType;
	@Size(max = 20)
	private String phoneNo1;
	@Size(max = 20)
	private String phoneNo2;
	@Size(max = 20)
	private String phoneNo3;
	@Size(max = 100)
	@Email
	private String mailAddress;
	//	private String zipcode;
	@Size(max = 100)
	private String address;
	@Size(max = 200)
	private String memo;
	@NotNull
	@DateTimeFormat(pattern = "yyyy/MM/dd")
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
