package com.gyx.sp.controller.index;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index")
public class index {
	
	@RequestMapping("/hello")
	public String hello() {
		return "hello";
	}
	@RequestMapping("/hi")
	public String hi() {
		return "hi";
	}

}
