package com.jewelry.domain.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.jewelry.domain.model.Customer;
import com.jewelry.domain.model.CustomerMail;
import com.jewelry.domain.model.CustomerPhone;
import com.jewelry.domain.model.Tantosha;
import com.jewelry.domain.repository.CustomerMailRepository;
import com.jewelry.domain.repository.CustomerPhoneRepository;
import com.jewelry.domain.repository.CustomerRepository;

@SpringBootTest
public class CustomerServiceTest {
	@Autowired
	CustomerService service;
	@MockBean
	CustomerRepository repository;
	@MockBean
	CustomerPhoneRepository phoneRepository;
	@MockBean
	CustomerMailRepository mailRepository;

	private final Customer TEST_CUSTOMER = Customer.builder()
		.id(1)
		.name("佐藤一郎")
		.nameKana("サトウイチロウ")
		.birthday(Date.valueOf("1993-2-20"))
		.gender("male")
		.bloodType("B")
		.address("埼玉県さいたま市北区〇〇町1-1-1")
		.memo("サンプル")
		.tantosha(Tantosha.builder()
			.id(1)
			.build())
		.signupDate(Date.valueOf("2022-3-22"))
		.customerPhoneList(new ArrayList<CustomerPhone>() {
			{
				add(new CustomerPhone(1, 1, "00000000000", "memo1"));
				add(new CustomerPhone(2, 1, "00000000001", "memo2"));
			}
		})
		.customerMailList(new ArrayList<CustomerMail>() {
			{
				add(new CustomerMail(1, 1, "sample@example.com", "memo1"));
			}
		})
		.build();

	@Test
	void createはrepository_create時に取得したIDをphoneListとmailListにi詰める() {
		service.create(TEST_CUSTOMER);

		assertThat(TEST_CUSTOMER.getCustomerPhoneList()
			.get(0)
			.getCustomerId()).isEqualTo(1);
		assertThat(TEST_CUSTOMER.getCustomerPhoneList()
			.get(1)
			.getCustomerId()).isEqualTo(1);
		assertThat(TEST_CUSTOMER.getCustomerMailList()
			.get(0)
			.getCustomerId()).isEqualTo(1);

		verify(repository, times(1)).create(TEST_CUSTOMER);
		verify(phoneRepository, times(1)).createAll(TEST_CUSTOMER.getCustomerPhoneList());
		verify(mailRepository, times(1)).createAll(TEST_CUSTOMER.getCustomerMailList());
	}

	@Test
	void findPageは件数が1件のときrepository_findPageで取得したリストを含むページを返す() {
		Pageable pageable = PageRequest.of(0, 10);
		List<Customer> returnList = new ArrayList<>();
		returnList.add(Customer.builder()
			.id(1)
			.name("佐藤一郎")
			.build());

		when(repository.count()).thenReturn(1L);
		when(repository.findPage(pageable)).thenReturn(returnList);

		Page<Customer> actual = service.findPage(pageable);

		assertThat(actual.getContent()
			.size()).isEqualTo(1);
		assertThat(actual.getContent()
			.get(0)).isEqualTo(Customer.builder()
				.id(1)
				.name("佐藤一郎")
				.build());
		assertThat(actual.getPageable()).isEqualTo(pageable);
		assertThat(actual.getTotalPages()).isEqualTo(1);
		assertThat(actual.getSize()).isEqualTo(10);
		assertThat(actual.getNumber()).isEqualTo(0);

		verify(repository, times(1)).count();
		verify(repository, times(1)).findPage(pageable);
	}

	@Test
	void findPageは件数が0件のとき空のリストを含むページを返すANDrepository_findPageメソッドは呼ばれない() {
		when(repository.count()).thenReturn(0L);
		Pageable pageable = PageRequest.of(0, 10);

		Page<Customer> actual = service.findPage(pageable);

		assertThat(actual.getContent()).isEqualTo(Collections.emptyList());
		assertThat(actual.getPageable()).isEqualTo(pageable);
		assertThat(actual.getTotalPages()).isEqualTo(0);
		assertThat(actual.getSize()).isEqualTo(10);
		assertThat(actual.getNumber()).isEqualTo(0);

		verify(repository, times(1)).count();
		verify(repository, times(0)).findPage(pageable);
	}
}
