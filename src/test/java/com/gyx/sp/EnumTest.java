package com.gyx.sp;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EnumTest {

//	private static final Logger LOGGER = LoggerFactory.getLogger(EnumTest.class);
//	private static final String  aa;
//	static {
//		 aa = "gyx";
//	}
	@Test
	public void main() {
		AtomicInteger integer = new AtomicInteger(1);
		System.out.println(integer.incrementAndGet());
		System.out.println(integer.getAndIncrement());
		System.out.println(integer.get());

	}

}
