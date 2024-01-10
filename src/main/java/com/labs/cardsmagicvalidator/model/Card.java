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

    public Card(int quantity, String name, List<String> color_identity, boolean valid) {
        this.name = name;
        this.color_identity = color_identity;
        this.quantity = quantity;
        this.valid = valid;
    }

}
