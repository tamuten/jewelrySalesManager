package com.jewelry.domain.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import com.jewelry.domain.model.Customer;
import com.jewelry.domain.model.CustomerSearch;

@Mapper
public interface CustomerMapper {
    public long count();

    public long searchCount(CustomerSearch customerSearch);

    public List<Customer> findAll();

    public List<Customer> findPage(@Param("pageable") Pageable pageable);

    public List<Customer> search(@Param("customerSearch") CustomerSearch customerSearch,
            @Param("pageable") Pageable pageable);

    public Customer selectOne(Integer id);

    public int insertOne(Customer customers);

    public int updateOne(Customer customers);

    public int deleteOne(Integer id);
}
