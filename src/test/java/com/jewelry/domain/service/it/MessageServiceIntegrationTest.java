package com.jewelry.domain.service.it;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

import com.jewelry.Message;
import com.jewelry.WebConfig;
import com.jewelry.domain.service.MessageService;

@SpringBootTest(classes = {WebConfig.class, MessageService.class})
public class MessageServiceIntegrationTest {
	@Autowired
	MessageSource messageSource;
	@Autowired
	MessageService messageService;

	@Test
	void プロパティファイルから正しくメッセージを取得するか検証() {
		String expected = "登録が完了しました。";
		String actual = messageService.getMessage(Message.SIGNUP);
		assertEquals(expected, actual);
	}
}
