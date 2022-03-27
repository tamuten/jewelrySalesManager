package com.jewelry.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ShozokuForm {
	public static interface DeleteShozoku {
	};

	
	private Integer id;
	@NotNull
	@Size(max = 50, min = 1)
	private String name;
}
