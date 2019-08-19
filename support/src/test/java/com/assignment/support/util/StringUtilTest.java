package com.assignment.support.util;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringUtilTest {

    @Test
    public void isEmptyDefault_for_null() {
        String defaultString = "default";

        assertThat(StringUtil.isEmptyDefault(null, defaultString)).isEqualTo(defaultString);
    }

    @Test
    public void isEmptyDefault_for_empty() {
        String defaultString = "default";

        assertThat(StringUtil.isEmptyDefault("", defaultString)).isEqualTo(defaultString);
    }

    @Test
    public void isEmptyDefault() {
        String originalString = "original";
        String defaultString = "default";

        assertThat(StringUtil.isEmptyDefault(originalString, defaultString)).isEqualTo(originalString);
    }

}