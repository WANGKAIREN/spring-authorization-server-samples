package com.ciglink.authcenter.test.client.credentials.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WANGKairen
 * @since 2023-02-01 10:38:43
 */
@RestController
public class TestController {

	@GetMapping("/test")
	public String[] getMessages() {
		return new String[] {"test 1", "test 2", "test 3"};
	}
}
