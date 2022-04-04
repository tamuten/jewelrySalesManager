package com.jewelry.form;

import java.util.ArrayList;
import java.util.List;

import com.jewelry.domain.model.Sales;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Builder
@AllArgsConstructor
public class SalesForm {
	// TODO:
	private List<Sales> salesList;

	public SalesForm() {
		this.salesList = new ArrayList<Sales>() {
			{
				add(new Sales());
			}
		};
	}
}
