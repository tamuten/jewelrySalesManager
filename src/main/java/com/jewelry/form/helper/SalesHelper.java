package com.jewelry.form.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.jewelry.domain.model.Sales;
import com.jewelry.form.SalesSample;
import com.jewelry.util.DateUtil;

public class SalesHelper {
	public static List<Sales> createSalesList(List<SalesSample> sampleList) {
		List<Sales> retList = new ArrayList<>();
		for (SalesSample sample : sampleList) {
			Sales main = new Sales();
			BeanUtils.copyProperties(sample, main);
			main.setJutyuDate(DateUtil.utilToSql(sample.getJutyuDate()));
			main.setKeijoDate(DateUtil.utilToSql(sample.getKeijoDate()));
			main.setShikiriDate(DateUtil.utilToSql(sample.getShikiriDate()));

			retList.add(main);
		}
		return retList;
	}
}
