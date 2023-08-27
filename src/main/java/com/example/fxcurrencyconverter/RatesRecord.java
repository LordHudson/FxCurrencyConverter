package com.example.fxcurrencyconverter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;
@JsonIgnoreProperties(ignoreUnknown = true)
public record RatesRecord(String time_last_update_utc,
                          String time_next_update_utc,
                          String base_code,
                          Map<String, Double> conversion_rates) {
}
