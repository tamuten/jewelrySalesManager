package com.jewelry.domain.service;

import java.util.Collections;
import java.util.List;

import com.jewelry.domain.model.Customer;
import com.jewelry.domain.model.CustomerSearch;
import com.jewelry.domain.repository.CustomerRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CustomerService {
  private final CustomerRepository repository;

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

  public Page<Customer> search(CustomerSearch customerSearch, Pageable pageable) {
    long amount = repository.searchCount(customerSearch);
    List<Customer> customerList = Collections.emptyList();

    if (amount > 0) {
      customerList = repository.search(customerSearch, pageable);
    }

    log.debug("[CustomerList]" + customerList.toString());

    return new PageImpl<Customer>(customerList, pageable, amount);
  }

  public Customer findByPk(Integer id) {
    return repository.findByPk(id);
  }

  public int create(Customer customer) {
    return repository.create(customer);
  }

  public int update(Customer customer) {
    return repository.update(customer);
  }

  public int delete(Integer id) {
    return repository.delete(id);
  }
}
