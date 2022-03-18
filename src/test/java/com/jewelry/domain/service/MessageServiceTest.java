package com.jewelry.domain.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;

import com.jewelry.Message;

@SpringBootTest
public class MessageServiceTest {
	@Autowired
	MessageService messageService;
	@MockBean
	MessageSource messageSource;

	@Test
	void 指定したメッセージを返すか検証() {
		when(messageSource.getMessage(Message.SIGNUP.getKey(), null, Locale.JAPAN))
			.thenReturn("signup completed.");

		String expected = "signup completed.";
		String actual = messageService.getMessage(Message.SIGNUP);
		assertEquals(expected, actual);

	}
}
