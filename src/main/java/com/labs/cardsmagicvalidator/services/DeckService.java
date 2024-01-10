package com.labs.cardsmagicvalidator.services;

import com.labs.cardsmagicvalidator.model.Card;
import com.labs.cardsmagicvalidator.model.Color;

import java.util.List;

public interface DeckService {
    List<Color> countNumberOfColor(List<Card> cards);

    float calculatePredominanceColor(Color color, int totalCards);

    boolean verifyCardIsValid(Card card, List<Color> colors);

    List<Card> findValidCards(List<Card> cards);

    List<Card> findInvalidCards(List<Card> cards);

    Color findColorMin(List<Color> colors);

    List<Card> findCardsWithColorMin(List<Card> cards, Color colorMin);
}
