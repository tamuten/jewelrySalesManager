package com.jewelry.domain.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jewelry.domain.model.CustomerMail;
import com.jewelry.domain.repository.mapper.CustomerMailMapper;

@Repository
public class CustomerMailRepository {
	@Autowired
	private CustomerMailMapper mapper;

	public int createAll(List<CustomerMail> customerMails) {
		return mapper.insertBulk(customerMails);
	}

	public int upsertAll(List<CustomerMail> customerMails) {
		return mapper.upsertBulk(customerMails);
	}

	public int deleteAll(List<Integer> mailIds) {
		return mapper.deleteBulk(mailIds);
	}
}
