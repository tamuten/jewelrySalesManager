package com.jewelry.domain.model;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode
public class Tantosha {
	private Integer id;
	private String name;
	private Boolean deleteFlg;
	private Timestamp signupDatetime;
	private Timestamp updateDatetime;
	private Timestamp deleteDatetime;
}
