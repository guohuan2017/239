package com.gyx.sp.controller.iwant;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.hash.BeanUtilsHashMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gyx.sp.dao.iwant.Iwant;
import com.gyx.sp.redis.RedisUtils;





@RestController
@RequestMapping("/iwant")
@Configuration
public class IwantController implements ApplicationContextAware {

	@Bean
	public Logger getLogger() {
		return LoggerFactory.getLogger("hillo");
	}

	@Autowired
	private RedisUtils redisUtils;

	@Autowired
	Logger LOG;
	

	@RequestMapping(value = "/in",produces="application/json;charset=UTF-8")
	public List in(Iwant iwant) {
		LOG.warn("hello");
		redisUtils.hPutAll(iwant.getName(), new BeanUtilsHashMapper(Iwant.class).toHash(iwant));
		Collection<Object> arrayList = new ArrayList<Object>();
		arrayList.add("name");
		arrayList.add("iwant");
		arrayList.add("contact");
//		redisUtils.set("aa", "bb");
		return redisUtils.hMultiGet(iwant.getName(), arrayList);
//		return (String) redisUtils.get("aa");
//		RedisUtils.hMultiGet(iwant.getName(), arrayList)
	}
	@RequestMapping(value = "/out",produces="application/json;charset=utf-8")
	public List out(Iwant iwant) {
//		LOG.warn("hello");
//		redisUtils.hPutAll(iwant.getName(), new BeanUtilsHashMapper(Iwant.class).toHash(iwant));
		Collection<Object> arrayList = new ArrayList<Object>();
		arrayList.add("name");
		arrayList.add("iwant");
		arrayList.add("contact");
		redisUtils.set("aa", "bb");
		return redisUtils.hMultiGet(iwant.getName(), arrayList);
//		return (String) redisUtils.get("aa");
//		RedisUtils.hMultiGet(iwant.getName(), arrayList)
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub

	}
}
