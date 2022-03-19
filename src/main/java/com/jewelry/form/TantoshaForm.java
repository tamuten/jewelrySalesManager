package com.jewelry.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.jewelry.constant.Constant;
import com.jewelry.domain.model.Shozoku;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class TantoshaForm {
	private Integer id;
	@NotNull
	@Size(min = 1, max = 50)
	private String name;
	private Shozoku shozoku;
	@NotNull
	@Size(min = 1, max = 50)
	private String role;

	public TantoshaForm() {
		this.shozoku = new Shozoku();
		this.role = Constant.ROLE_GENERAL;
	}
}
