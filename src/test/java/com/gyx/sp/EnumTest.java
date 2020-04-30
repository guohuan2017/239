package com.gyx.sp;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public enum EnumTest {
	a,b;
	private static final Logger LOGGER = LoggerFactory.getLogger(EnumTest.class);
	private static final String  aa;
	static {
		 aa = "gyx";
	}
	static void hello() {
		LOGGER.info(aa);

	}

}
