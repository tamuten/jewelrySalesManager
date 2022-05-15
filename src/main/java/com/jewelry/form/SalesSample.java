package com.jewelry.form;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.jewelry.domain.model.Customer;

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
public class SalesSample {
	private Integer id;
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date jutyuDate;
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date keijoDate;
	private String uriba;
	private String ksTanto;
	private String hinmei1;
	private String hinmei2;
	private String hinmei3;
	private String number1;
	private String number2;
	private Integer jodai;
	private Integer gedai;
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date shikiriDate;
	private String shikiriNo;
	private Customer customer;

	// フォーム用
	private boolean rowDelete;
}
