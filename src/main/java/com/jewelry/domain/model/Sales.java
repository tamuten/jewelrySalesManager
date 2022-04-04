package com.jewelry.domain.model;

import java.sql.Date;

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
public class Sales {
	// TODO:
	private Integer id;
	private Date jutyuDate;
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
	private Date shikiriDate;
	private String shikiriNo;
	private Customer customer;

	// フォーム用
	private boolean rowDelete;
}
