package com.example.mockmvcexampletwo.component.configuration;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Configuration
public class TestContext {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    @Qualifier("mockMvc")
    public MockMvc mockMvc() {
        return MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Bean
    public ApplicationContext applicationContext() {
        return applicationContext.getParent();
    }
}
