package com.assignment.support.controller;

import com.assignment.support.service.CsvReadService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SupportControllerTest {

    @Autowired
    private CsvReadService csvReadService;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        csvReadService.readCsvAndSaveDb();
    }

    @Test
    public void querySupports() {
        //mockMvc.perform(get("/api/supports"));
    }
}