package com.jewelry.domain.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.jewelry.domain.model.Sales;
import com.jewelry.domain.repository.mapper.SalesMapper;

@Repository
public class SalesRepository {
	@Autowired
	private SalesMapper mapper;

	/**
	 * 受注テーブルのレコード件数を返却します。
	 *
	 * @return
	 */
	public long count() {
		return mapper.count();
	}

	/**
	 * 受注テーブルの顧客IDに紐づくレコード件数を返却します。
	 *
	 * @param customerId
	 * @return
	 */
	public long countByCustomer(Integer customerId) {
		return mapper.countByCustomer(customerId);
	}

	/**
	 * 受注テーブルの全てのレコードを取得します。
	 *
	 * @return
	 */
	public List<Sales> findAll() {
		return mapper.findAll();
	}

	public List<Sales> findPage(Pageable pageable) {
		return mapper.findPage(pageable);
	}

	public List<Sales> findByCustomer(Pageable pageable, Integer customerId) {
		return mapper.findByCustomer(pageable, customerId);
	}

	/**
	 *受注IDに紐づくレコード１件を取得します。
	 *
	 * @param id
	 * @return
	 */
	public Sales findByPk(Integer id) {
		return mapper.selectOne(id);
	}

	public int create(Sales sales) {
		return mapper.insertOne(sales);
	}

	public int createAll(List<Sales> salesList) {
		return mapper.insertBulk(salesList);
	}

	public int update(Sales sales) {
		return mapper.updateOne(sales);
	}

	public int delete(Integer id) {
		return mapper.deleteOne(id);
	}
}
