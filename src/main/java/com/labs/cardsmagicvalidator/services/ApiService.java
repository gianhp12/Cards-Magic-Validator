package com.labs.cardsmagicvalidator.services;

import com.labs.cardsmagicvalidator.model.Card;

public interface ApiService {
    Card findCardByName(Card card);
}
