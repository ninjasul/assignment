package com.assignment.support.service;

import com.assignment.support.entity.Region;
import com.assignment.support.entity.Support;
import com.assignment.support.repository.RegionRepository;
import com.assignment.support.repository.SupportRepository;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class BaseServiceTest {
    protected static List<Region> regions = new ArrayList<>();
    protected static List<Support> supports = new ArrayList<>();

    @Autowired
    protected static RegionRepository regionRepository;

    @Autowired
    protected static SupportRepository supportRepository;
}