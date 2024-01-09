package com.labs.cardsmagicvalidator.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Records {
    @JsonProperty
    private List<CardApi> data;
}
