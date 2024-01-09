package com.labs.cardsmagicvalidator.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Color {
    private char acronym;
    private int quantity;
    private float predominance;

}
