package com.challenge.provider.challengeprovider.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createProjectWithFileTest() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("projectFile", "project1.zip",
                "application/octet-stream", "some data".getBytes());

        mockMvc.perform(multipart("/projects").file(multipartFile).param("description", "Description1"))
                .andDo(result -> Optional.ofNullable(result.getResolvedException()).ifPresent(Exception::printStackTrace))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects"))
                .andReturn();
    }

    @Test
    public void createProjectWithoutFileTest() throws Exception {
        mockMvc.perform(multipart("/projects").param("description", "Description2"))
                .andDo(result -> Optional.ofNullable(result.getResolvedException()).ifPresent(Exception::printStackTrace))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects"))
                .andReturn();
    }
}
