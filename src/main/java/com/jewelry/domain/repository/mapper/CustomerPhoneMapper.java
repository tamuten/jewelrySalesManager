package com.jewelry.domain.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.jewelry.domain.model.CustomerPhone;

@Mapper
public interface CustomerPhoneMapper {
	public int insertBulk(List<CustomerPhone> customerPhones);
	public int upsertBulk(List<CustomerPhone> customerPhones);
	public int deleteBulk(List<Integer> phoneIds);
}
