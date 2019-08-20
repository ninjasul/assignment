package com.assignment.support.service;

import com.assignment.support.dto.RegionDto;
import com.assignment.support.dto.SupportDto;
import com.assignment.support.entity.Support;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SupportServiceTest extends BaseServiceTest {
    private static Random random = new Random();

    @Autowired
    private SupportService supportService;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void findAll() {
        List<SupportDto> foundDtos = supportService.findAll();
        List<SupportDto> dtos = getAllSupports();

        assertThat(foundDtos).isNotNull();
        assertThat(foundDtos.size()).isEqualTo(dtos.size());

        for (int i = 0; i < foundDtos.size(); ++i ) {
            assertThat(foundDtos.get(i).getRegion()).isEqualTo(dtos.get(i).getRegion());
            assertThat(foundDtos.get(i).getTarget()).isEqualTo(dtos.get(i).getTarget());
            assertThat(foundDtos.get(i).getUsage()).isEqualTo(dtos.get(i).getUsage());
            assertThat(foundDtos.get(i).getLimits()).isEqualTo(dtos.get(i).getLimits());
            assertThat(foundDtos.get(i).getRate()).isEqualTo(dtos.get(i).getRate());
            assertThat(foundDtos.get(i).getInstitute()).isEqualTo(dtos.get(i).getInstitute());
            assertThat(foundDtos.get(i).getMgmt()).isEqualTo(dtos.get(i).getMgmt());
            assertThat(foundDtos.get(i).getReception()).isEqualTo(dtos.get(i).getReception());
        }
    }

    private List<SupportDto> getAllSupports() {
        return supports.stream()
                .map(SupportDto::of)
                .collect(Collectors.toList());
    }

    @Test
    public void findByRegionName() {
        List<SupportDto> supportDtos = getAllSupports();

        for (int i = 0; i < supportDtos.size(); ++i ) {
            SupportDto foundDto = supportService.findByRegionName(new RegionDto(supportDtos.get(i).getRegion()));

            assertThat(foundDto).isNotNull();
            assertThat(foundDto.getRegion()).isEqualTo(supportDtos.get(i).getRegion());
            assertThat(foundDto.getTarget()).isEqualTo(supportDtos.get(i).getTarget());
            assertThat(foundDto.getUsage()).isEqualTo(supportDtos.get(i).getUsage());
            assertThat(foundDto.getLimits()).isEqualTo(supportDtos.get(i).getLimits());
            assertThat(foundDto.getRate()).isEqualTo(supportDtos.get(i).getRate());
            assertThat(foundDto.getInstitute()).isEqualTo(supportDtos.get(i).getInstitute());
            assertThat(foundDto.getMgmt()).isEqualTo(supportDtos.get(i).getMgmt());
            assertThat(foundDto.getReception()).isEqualTo(supportDtos.get(i).getReception());
        }
    }

    private List<SupportDto> findByRegionName(String region) {
        return supports.stream()
                .filter(s -> region.equals(s.getRegion().getName()))
                .map(SupportDto::of)
                .collect(Collectors.toList());
    }

    @Test
    public void updateByRegionName() {
        List<SupportDto> updatedDtos = getUpdatedSupportDtos("updated");

        for (int i = 0; i < updatedDtos.size(); ++i ) {
            SupportDto updatedDto = supportService.updateByRegionName(updatedDtos.get(i));

            assertThat(updatedDto).isNotNull();
            assertThat(updatedDto.getRegion()).isEqualTo(updatedDtos.get(i).getRegion());
            assertThat(updatedDto.getTarget()).isEqualTo(updatedDtos.get(i).getTarget());
            assertThat(updatedDto.getUsage()).isEqualTo(updatedDtos.get(i).getUsage());
            assertThat(updatedDto.getLimits()).isEqualTo(updatedDtos.get(i).getLimits());
            assertThat(updatedDto.getRate()).isEqualTo(updatedDtos.get(i).getRate());
            assertThat(updatedDto.getInstitute()).isEqualTo(updatedDtos.get(i).getInstitute());
            assertThat(updatedDto.getMgmt()).isEqualTo(updatedDtos.get(i).getMgmt());
            assertThat(updatedDto.getReception()).isEqualTo(updatedDtos.get(i).getReception());
        }
    }

    private List<SupportDto> getUpdatedSupportDtos(String updated) {

        return supports.stream()
                        .map(SupportDto::of)
                        .map(dto -> SupportDto.builder()
                                .region(dto.getRegion())
                                .target(appendPrefix(updated, dto.getTarget()))
                                .usage(appendPrefix(updated, dto.getUsage()))
                                .limits(getRandomLimits())
                                .rate(getRandomRate())
                                .institute(appendPrefix(updated, dto.getInstitute()))
                                .mgmt(appendPrefix(updated, dto.getMgmt()))
                                .reception(appendPrefix(updated, dto.getReception()))
                                .build()
                        )
                        .collect(Collectors.toList());
    }

    private String getRandomLimits() {
        return random.nextInt(300) + "억원 이내";
    }

    private String getRandomRate() {
        return random.nextInt(10) + random.nextFloat()  + "%~" +
                (10 + random.nextInt(10) + random.nextFloat()) + "%";
    }
    private String appendPrefix(String updated, String original) {
        return updated + " " + original;
    }

    @Override
    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }
}