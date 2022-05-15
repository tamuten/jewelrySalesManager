package com.jewelry.form;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Builder
@AllArgsConstructor
public class SalesForm {
	private List<SalesSample> salesList;

	public SalesForm() {
		this.salesList = new ArrayList<SalesSample>() {
			{
				add(new SalesSample());
			}
		};
	}
}
