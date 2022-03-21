package com.jewelry.domain.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.jewelry.domain.model.CustomerMail;
@Mapper
public interface CustomerMailMapper {
	public int insertBulk(List<CustomerMail> customerMails);
	public int upsertBulk(List<CustomerMail> customerMails);
	public int deleteBulk(List<Integer> mailIds);
}
