package com.ciglink.authcenter.test.resource.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WANGKairen
 * @since 2023-02-01 10:38:43
 */
@RestController
public class MessagesController {

	@GetMapping("/messages")
	public String[] getMessages() {
		return new String[] {"Message 1", "Message 2", "Message 3"};
	}
}
