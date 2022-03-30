package com.jewelry.domain.service;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jewelry.domain.model.Customer;
import com.jewelry.domain.repository.CustomerMailRepository;
import com.jewelry.domain.repository.CustomerPhoneRepository;
import com.jewelry.domain.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CustomerService {
	// TODO:
	private final CustomerRepository repository;
	private final CustomerPhoneRepository phoneRepository;
	private final CustomerMailRepository mailRepository;

	public List<Customer> findAll() {
		return repository.findAll();
	}

	public Page<Customer> findPage(Pageable pageable) {
		long customerCnt = repository.count();
		List<Customer> customerList = Collections.emptyList();

		if (customerCnt > 0) {
			customerList = repository.findPage(pageable);
		}

		log.info("[CustomerList]" + customerList.toString());

		return new PageImpl<Customer>(customerList, pageable, customerCnt);
	}

	public Customer findByPk(Integer id) {
		return repository.findByPk(id);
	}

	public void create(Customer customer) {
		repository.create(customer);
		customer.getCustomerPhoneList()
			.stream()
			.forEach(cu -> cu.setCustomerId(customer.getId()));
		customer.getCustomerMailList()
			.stream()
			.forEach(cu -> cu.setCustomerId(customer.getId()));

		phoneRepository.createAll(customer.getCustomerPhoneList());
		mailRepository.createAll(customer.getCustomerMailList());
	}

	public void update(Customer customer) {
		// TODO:
	}

	public void delete(Integer id) {
		// TODO:
	}
}
