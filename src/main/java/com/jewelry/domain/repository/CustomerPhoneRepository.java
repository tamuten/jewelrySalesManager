package com.jewelry.domain.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jewelry.domain.model.CustomerPhone;
import com.jewelry.domain.repository.mapper.CustomerPhoneMapper;

@Repository
public class CustomerPhoneRepository {
	@Autowired
	private CustomerPhoneMapper mapper;

	public int createAll(List<CustomerPhone> customerPhones) {
		return mapper.insertBulk(customerPhones);
	}

	public int upsertAll(List<CustomerPhone> customerPhones) {
		return mapper.upsertBulk(customerPhones);
	}

	public int deleteAll(List<Integer> phoneIds) {
		return mapper.deleteBulk(phoneIds);
	}
}
