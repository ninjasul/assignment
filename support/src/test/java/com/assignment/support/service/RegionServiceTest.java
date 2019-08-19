package com.assignment.support.service;

import com.assignment.support.dto.RegionDto;
import com.assignment.support.entity.Region;
import com.assignment.support.entity.Support;
import com.assignment.support.repository.RegionRepository;
import com.assignment.support.repository.RegionRepositorySupport;
import com.assignment.support.repository.SupportRepository;
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
public class RegionServiceTest {

    protected static List<Region> regions = new ArrayList<>();
    protected static List<Support> supports = new ArrayList<>();

    @Autowired
    protected RegionRepository regionRepository;

    @Autowired
    protected SupportRepository supportRepository;

    @Autowired
    private RegionService regionService;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        regions.add(new Region("testRgn0001", "경기도"));
        regions.add(new Region("testRgn0002", "부산광역시"));
        regions.add(new Region("testRgn0003", "대구광역시"));
        regions.add(new Region("testRgn0004", "인천광역시"));
        regions.add(new Region("testRgn0005", "광주광역시"));
        regions.add(new Region("testRgn0006", "울산광역시"));

        supports.add(new Support().builder()
                .region(regions.get(0))
                .target("대상1")
                .usage("용도1")
                .limits("추천금액 이내")
                .rate("10.5%")
                .institute("추천기관1")
                .mgmt("관리점1")
                .reception("취급점1")
                .createdTime(LocalDateTime.now())
                .build());

        supports.add(new Support().builder()
                .region(regions.get(1))
                .target("대상2")
                .usage("용도2")
                .limits("10억원 이내")
                .rate("10.1%")
                .institute("추천기관2")
                .mgmt("관리점2")
                .reception("취급점2")
                .createdTime(LocalDateTime.now())
                .build());

        supports.add(new Support().builder()
                .region(regions.get(2))
                .target("대상3")
                .usage("용도3")
                .limits("10억원 이내")
                .rate("5.5%~15.0%")
                .institute("추천기관3")
                .mgmt("관리점3")
                .reception("취급점3")
                .createdTime(LocalDateTime.now())
                .build());

        supports.add(new Support().builder()
                .region(regions.get(3))
                .target("대상4")
                .usage("용도4")
                .limits("30백만원 이내")
                .rate("3.7%~6.1%")
                .institute("추천기관4")
                .mgmt("관리점4")
                .reception("취급점4")
                .createdTime(LocalDateTime.now())
                .build());

        supports.add(new Support().builder()
                .region(regions.get(4))
                .target("대상5")
                .usage("용도5")
                .limits("30백만원 이내")
                .rate("1.6%~6.0%")
                .institute("추천기관5")
                .mgmt("관리점5")
                .reception("취급점5")
                .createdTime(LocalDateTime.now())
                .build());

        supports.add(new Support().builder()
                .region(regions.get(5))
                .target("대상6")
                .usage("용도6")
                .limits("5천만원 이내")
                .rate("20.5%")
                .institute("추천기관6")
                .mgmt("관리점6")
                .reception("취급점6")
                .createdTime(LocalDateTime.now())
                .build());

        regionRepository.saveAll(regions);
        supportRepository.saveAll(supports);
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
}