package com.jewelry.domain.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.jewelry.domain.model.Customer;
import com.jewelry.domain.model.CustomerSearch;
import com.jewelry.domain.repository.mapper.CustomerMapper;

@Repository
public class CustomerRepository {
    @Autowired
    private CustomerMapper mapper;

    public long count() {
        return mapper.count();
    }

    public long searchCount(CustomerSearch customerSearch) {
        return mapper.searchCount(customerSearch);
    }

    public List<Customer> findAll() {
        return mapper.findAll();
    }

    public List<Customer> findPage(Pageable pageable) {
        return mapper.findPage(pageable);
    }

    public List<Customer> search(CustomerSearch customerSearch, Pageable pageable) {
        return mapper.search(customerSearch, pageable);
    }

    public Customer findByPk(Integer id) {
        return mapper.selectOne(id);
    }

    public int create(Customer customers) {
        return mapper.insertOne(customers);
    }

    public int update(Customer customers) {
        return mapper.updateOne(customers);
    }

    public int delete(Integer id) {
        return mapper.deleteOne(id);
    }
}
