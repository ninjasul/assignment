package com.assignment.support.repository;

import com.assignment.support.service.CsvReadService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.assignment.support.entity.Support;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SupportRepositoryTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private CsvReadService csvReadService;

    @Autowired
    private SupportRepository supportRepository;

    @Before
    public void setUp() throws Exception {
        csvReadService.readCsvAndSaveDb();
    }

    @Test
    public void findAll() throws JsonProcessingException {
        for (Support support : supportRepository.findAll()) {
            log.info(objectMapper.writeValueAsString(support));
        }
    }

    @Test
    public void findByRegion() {
        /*Support localAuthority = new Support().builder()
                                                            .name("강릉시")
                                                            .build();
        supportRepository.findByRegion(localAuthority);*/
    }
}