package com.labs.cardsmagicvalidator.model;

import lombok.Data;

import java.util.List;

@Data
public class Card {
    private int quantity;
    private String name;
    private String type;
    private List<String> color_identity;
    private boolean valid;
    public Card() {
        this.valid = true;
    }
}
