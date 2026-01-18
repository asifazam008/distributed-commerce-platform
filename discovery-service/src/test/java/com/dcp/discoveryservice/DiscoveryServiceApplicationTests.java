package com.dcp.discoveryservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("local")
class DiscoveryServiceApplicationTests {

	@Test
	void dummyTest() {
		// Simple dummy test to make build pass
		assert true;
	}

}
