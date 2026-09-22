package com.jpmc.midascore.foundation;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Incentive {
    private BigDecimal amount; // The field returned by the API

    // Default Constructor (required for Jackson deserialization)
    public Incentive() {}

    // Getters and Setters
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
