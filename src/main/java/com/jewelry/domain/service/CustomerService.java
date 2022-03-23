package com.jewelry.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jewelry.domain.model.Customer;
import com.jewelry.domain.repository.CustomerMailRepository;
import com.jewelry.domain.repository.CustomerPhoneRepository;
import com.jewelry.domain.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {
	// TODO:
	private final CustomerRepository repository;
	private final CustomerPhoneRepository phoneRepository;
	private final CustomerMailRepository mailRepository;

	public List<Customer> findAll() {
		return repository.findAll();
	}

	public void create(Customer customer) {
		repository.create(customer);
		phoneRepository.createAll(customer.getCustomerPhoneList());
		mailRepository.createAll(customer.getCustomerMailList());
	}
}
