package com.assignment.support.service;

import com.assignment.support.dto.RegionDto;
import com.assignment.support.entity.Region;
import com.assignment.support.entity.Support;
import com.assignment.support.repository.RegionRepository;
import com.assignment.support.repository.RegionRepositorySupport;
import com.assignment.support.repository.SupportRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegionServiceTest extends BaseServiceTest {

    @Autowired
    private RegionService regionService;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void findBestRegions_for_zero_region() {
        regionService.findBestRegions(0);
    }

    @Test
    public void findBestRegions() {
        for (int curSize = 1; curSize <= supports.size(); ++curSize ) {
            List<RegionDto> regionDtos = regionService.findBestRegions(curSize);
            List<RegionDto> bestRegionDtos = getSortedBestSupportRegions(curSize);

            assertThat(regionDtos).isNotNull();
            assertThat(regionDtos.size()).isEqualTo(curSize);

            for (int j = 0; j < curSize; ++j) {
                assertThat(regionDtos.get(j).getRegion()).isEqualTo(bestRegionDtos.get(j).getRegion());
            }
        }
    }

    private List<RegionDto> getSortedBestSupportRegions(int count) {
        return supports.stream()
                        .sorted(Comparator.comparing(Support::getLimitAmount)
                                        .reversed()
                                        .thenComparing(Support::getAvgRate))
                        .limit(count)
                        .map(Support::getRegion)
                        .map(RegionDto::of)
                        .collect(Collectors.toList());

    }

    @Test
    public void findSmallestMaxRateRegions() {
        RegionDto regionDto = regionService.findSmallestMaxRateRegion();
        RegionDto smallestMaxRateRegionDto = getSortedSmallestMaxRateRegion();

        assertThat(regionDto).isNotNull();
        assertThat(regionDto.getRegion()).isEqualTo(smallestMaxRateRegionDto.getRegion());
    }

    private RegionDto getSortedSmallestMaxRateRegion() {
        return supports.stream()
                .sorted(Comparator.comparing(Support::getMaxRate))
                .limit(1)
                .map(Support::getRegion)
                .map(RegionDto::of)
                .findFirst()
                .get();
    }

    @Override
    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }
}