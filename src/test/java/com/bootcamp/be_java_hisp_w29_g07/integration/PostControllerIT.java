package com.bootcamp.be_java_hisp_w29_g07.integration;

import com.bootcamp.be_java_hisp_w29_g07.controller.PostController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration test for the {@link PostController} controller.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerIT {
    /**
     * Instance of {@link MockMvc} that allows simulating HTTP requests and verifying controller responses.
     */
    @Autowired
    private MockMvc mockMvc;
}
