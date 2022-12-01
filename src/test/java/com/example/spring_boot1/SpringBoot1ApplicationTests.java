package com.example.spring_boot1;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;

    private final GenericContainer<?> PyDev = new GenericContainer<>("devapp")
            .withExposedPorts(8080);
    private final GenericContainer<?> approved = new GenericContainer<>("prodapp")
            .withExposedPorts(8081);

    @BeforeAll
    public void setUp() {
        PyDev.start();
        approved.start();
    }

    @Test
    void contextLoads() {
        Integer portdev = approved.getMappedPort(8080);
        Integer portprod = approved.getMappedPort(8081);

//        Assertions.assertEquals(portdev, 8080);
//        Assertions.assertEquals(portprod, 8081);

        restTemplate.getForEntity("http://localhost:" + portdev, String.class);
    }

}
