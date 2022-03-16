package com.jewelry.form;

import com.jewelry.domain.model.Shozoku;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class TantoshaForm {
	private Integer id;
	private String name;
	private Shozoku shozoku;
	private String role;
}
