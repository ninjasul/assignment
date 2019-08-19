package com.assignment.support.entity;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SupportTest {
    @Test
    public void getIntegerAmount_for_null() {
        assertThat(new Support().getIntegerAmount(null)).isEqualTo(0);
    }

    @Test
    public void getIntegerAmount_empty() {
        assertThat(new Support().getIntegerAmount("")).isEqualTo(0);
    }

    @Test(expected = NumberFormatException.class)
    public void getIntegerAmount_string() {
        new Support().getIntegerAmount("aaa");
    }

    @Test
    public void getIntegerAmount() {
        assertThat(new Support().getIntegerAmount("8억원 이하")).isEqualTo(8);
        assertThat(new Support().getIntegerAmount("500억원 이하")).isEqualTo(500);
    }

    @Test
    public void setRates_for_null() {
        Support support = new Support();
        support.setRates(null);

        assertThat(support.getMinRate()).isNull();
        assertThat(support.getMaxRate()).isNull();
    }

    @Test
    public void setRates_for_empty() {
        Support support = new Support();
        support.setRates(null);

        assertThat(support.getMinRate()).isNull();
        assertThat(support.getMaxRate()).isNull();
    }
}