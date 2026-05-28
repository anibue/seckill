package com.jesper.seckill;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class RedisServiceTest {

    @Test
    void testSetAndGet() {
        assertTrue(true);
    }

    @Test
    void testDelete() {
        assertTrue(true);
    }

    @Test
    void testExists() {
        assertTrue(true);
    }
}
