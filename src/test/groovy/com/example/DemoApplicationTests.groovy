package com.example

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
        int result, x
        x = 1
        result = 0
        while (x <= 10) {
            if (x % 2 == 0) {
                result += x
                ++x
            }
        }
        println(result)
    }


}
