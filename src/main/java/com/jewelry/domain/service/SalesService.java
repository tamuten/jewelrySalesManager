package com.jewelry.domain.service;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jewelry.domain.model.Sales;
import com.jewelry.domain.repository.SalesRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SalesService {
	private final SalesRepository repository;

	public Page<Sales> findPage(Pageable pageable) {
		long count = repository.count();
		List<Sales> salesList = Collections.emptyList();

		if (count > 0) {
			salesList = repository.findPage(pageable);
		}

		log.info("[SalesList]" + salesList.toString());

		return new PageImpl<Sales>(salesList, pageable, count);
	}

	public Page<Sales> findByCustomer(Pageable pageable, Integer customerId) {
		long count = repository.countByCustomer(customerId);
		List<Sales> customerPurchaseList = Collections.emptyList();

		if (count > 0) {
			customerPurchaseList = repository.findByCustomer(pageable, customerId);
		}

		log.info(
				"[PurchaseList :: Customer_id = " + String.valueOf(customerId) + "]" + customerPurchaseList.toString());

		return new PageImpl<Sales>(customerPurchaseList, pageable, count);
	}

	public int createAll(List<Sales> salesList) {
		return repository.createAll(salesList);
	}
}