package com.assignment.support.service;

import com.assignment.support.repository.RegionRepository;
import com.assignment.support.repository.SupportRepository;
import com.assignment.support.entity.Region;
import com.assignment.support.entity.Support;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CsvReadService {

    private static final String CSV_PATH = "data/";
    private static final String CSV_FILE_NAME = "서버개발_사전과제1_지자체협약지원정보_16년12월현재__최종.csv";
    private static final String CSV_SPLITTER = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
    private static final String REGION_PREFIX = "reg";

    @Autowired
    SupportRepository supportRepository;

    @Autowired
    RegionRepository regionRepository;

    public void readCsvAndSaveDb() throws Exception {
        String line = "";
        int lineCount = 0;

        List<Region> regions = new ArrayList<>();
        List<Support> supports = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(ResourceUtils.getFile("classpath:" + CSV_PATH + CSV_FILE_NAME)));

        while ((line = br.readLine()) != null) {
            if (lineCount > 0) {
                String [] splittedData = line.split(CSV_SPLITTER);
                Region region = getNewRegion(splittedData);
                regions.add(region);
                supports.add(getNewSupport(splittedData, region));
            }
            lineCount++;
        }

        regionRepository.saveAll(regions);
        supportRepository.saveAll(supports);
    }

    private Region getNewRegion(String[] splittedData) {
        return new Region().builder()
                .code(getLocalAuthorityCode(splittedData[0]))
                .name(splittedData[1])
                .build();
    }

    private String getLocalAuthorityCode(String id) {
        return String.format("%s%04d", REGION_PREFIX, Integer.parseInt(id));
    }

    private Support getNewSupport(String[] splittedData, Region region) {
        return new Support().builder()
                .region(region)
                .target(splittedData[2])
                .usage(splittedData[3])
                .limits(splittedData[4])
                .rate(splittedData[5])
                .institute(splittedData[6])
                .mgmt(splittedData[7])
                .reception(splittedData[8])
                .createdTime(LocalDateTime.now())
                .build();
    }
}