package com.jewelry.domain.model;

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
public class CustomerPhone {
	private Integer id;
	private Integer customerId;
	private String phoneNumber;
	private String memo;

	// フォーム用
	private boolean rowDelete;
}
