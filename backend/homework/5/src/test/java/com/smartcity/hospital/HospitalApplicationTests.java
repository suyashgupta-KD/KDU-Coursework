package com.smartcity.hospital;

import com.smartcity.hospital.config.JdbcConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootTest
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JdbcConfig.class))
class HospitalApplicationTests {

	@Test
	void contextLoads() {
	}
}
