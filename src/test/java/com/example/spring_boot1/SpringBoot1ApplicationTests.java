package com.example.spring_boot1;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

import static org.junit.Assert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBoot1ApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;

    private static final GenericContainer<?> devApp = new GenericContainer<>("devapp")
            .withExposedPorts(8080);
    private static final GenericContainer<?> prodApp = new GenericContainer<>("prodapp")
            .withExposedPorts(8081);

    @BeforeAll
    public static void setUp() {
        devApp.start();
        prodApp.start();
    }

    @Test
    void contextLoadsFirst() {
        long firstAppPort = devApp.getMappedPort(8080);
        ResponseEntity<String> entityFromFirst = restTemplate.getForEntity("http://localhost:" + firstAppPort + "/profile", String.class);

        assertEquals("Current profile is dev", entityFromFirst.getBody());

    }
    @Test
    void contextLoadsSecond() {
        long secondAppPort = prodApp.getMappedPort(8081);
        ResponseEntity<String> entityFromSecond = restTemplate.getForEntity("http://localhost:" + secondAppPort + "/profile", String.class);

        assertEquals("Current profile is production", entityFromSecond.getBody());
    }

}
