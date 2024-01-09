package com.labs.cardsmagicvalidator.services;

import com.labs.cardsmagicvalidator.model.Card;

import java.util.List;

public interface ApiService {
    public List<String> searchCardByName(Card card);
}
