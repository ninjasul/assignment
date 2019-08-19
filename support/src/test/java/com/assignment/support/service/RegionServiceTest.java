package com.assignment.support.service;

import com.assignment.support.repository.RegionRepositorySupport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegionServiceTest extends BaseServiceTest {

    @Autowired
    private RegionRepositorySupport regionRepositorySupport;

    @Test
    public void findBestRegions() {
        assertThat(regions.size()).isEqualTo(6);
    }
}