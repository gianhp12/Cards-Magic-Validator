package com.labs.cardsmagicvalidator.services.impl;

import com.labs.cardsmagicvalidator.model.Card;
import com.labs.cardsmagicvalidator.model.Color;
import com.labs.cardsmagicvalidator.services.DeckService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DeckServiceImpl implements DeckService {
    public List<Color> countNumberOfColor(List<Card> cards) {
        List<Color> colors = new ArrayList<>();
        Color green = new Color('G', 0, 0);
        Color blue = new Color('U', 0, 0);
        Color black = new Color('B', 0, 0);
        Color red = new Color('R', 0, 0);
        Color white = new Color('W', 0, 0);
        Color empty = new Color('E', 0, 0);
        Color invalid = new Color('I', 0, 0);
        for (Card card : cards) {
            if (card.getColor_identity() == null) {
                empty.setQuantity(empty.getQuantity() + card.getQuantity());
            } else {
                for (String color : card.getColor_identity()) {
                    switch (color) {
                        case "G":
                            green.setQuantity(green.getQuantity() + card.getQuantity());
                            break;
                        case "U":
                            blue.setQuantity(blue.getQuantity() + card.getQuantity());
                            break;
                        case "B":
                            black.setQuantity(black.getQuantity() + card.getQuantity());
                            break;
                        case "R":
                            red.setQuantity(red.getQuantity() + card.getQuantity());
                            break;
                        case "W":
                            white.setQuantity(white.getQuantity() + card.getQuantity());
                            break;
                        default:
                            invalid.setQuantity(invalid.getQuantity() + card.getQuantity());
                            break;
                    }
                }
            }
        }
        colors.addAll(List.of(green, blue, black, red, white, invalid, empty));
        return colors;
    }

    public float calculatePredominanceColor(Color color, int totalCards) {
        return ((float) color.getQuantity() / totalCards) * 100;
    }

    public boolean verifyCardIsValid(Card card, List<Color> colors) {
        List<Color> colorsWithMinPredominance = colors.stream()
                .filter(color -> color.getPredominance() >= 5.0f)
                .collect(Collectors.toList());

        if (card.getColor_identity() == null || card.getColor_identity().isEmpty()) {
            return true;
        }
        if (card.getColor_identity().size() == 1) {
            return colorsWithMinPredominance.stream()
                    .anyMatch(color -> String.valueOf(color.getAcronym())
                            .equals(card.getColor_identity().get(0)));
        }
        return card.getColor_identity().stream()
                .allMatch(colorAcronym ->
                        colorsWithMinPredominance.stream()
                                .anyMatch(color -> String.valueOf(color.getAcronym()).equals(colorAcronym)));
    }
}

