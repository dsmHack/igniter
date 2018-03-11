package com.dsmhack.igniter;

import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(
        classes = IgniterApplication.class,
        loader = SpringBootContextLoader.class
)
public class SpringContextConfiguration {
}
