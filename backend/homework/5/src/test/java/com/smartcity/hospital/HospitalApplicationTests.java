package com.smartcity.hospital;

import com.smartcity.hospital.config.JdbcConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootTest(classes = HospitalApplication.class)
@EnableAutoConfiguration(exclude = {
		DataSourceAutoConfiguration.class
})
@ComponentScan(excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JdbcConfig.class),
		@ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.smartcity.hospital.repository.*"),
		@ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.smartcity.hospital.service.*")
})
class HospitalApplicationTests {

	@Test
	void contextLoads() {
		// just verifies Spring context boots
	}
}
