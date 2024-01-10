package com.labs.cardsmagicvalidator;

import com.labs.cardsmagicvalidator.model.Card;
import com.labs.cardsmagicvalidator.model.Color;
import com.labs.cardsmagicvalidator.services.DeckService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DeckServiceTest {
    @Autowired
    DeckService deckService;

    @Test
    void countNumberOfColorTest_ListNotNull() {
        List<Card> cards = Arrays.asList(
                new Card(2, "Exodia", Arrays.asList("G"), true),
                new Card(1, "Dragão branco de olhos azuis", Arrays.asList("U", "B"), true),
                new Card(3, "Mago Negro", Arrays.asList("R", "W"), true));

        List<Color> result = deckService.countNumberOfColor(cards);
        assertEquals(2, result.get(0).getQuantity()); // Espera 2 cores da primeira carta.
        assertEquals(1, result.get(1).getQuantity()); // Espera 1º cor da segunda carta.
        assertEquals(1, result.get(2).getQuantity()); // Espera 2º cor da segunda carta.
        assertEquals(3, result.get(3).getQuantity()); // Espera 3 cores R da terceira carta.
        assertEquals(3, result.get(4).getQuantity()); //Espera 3 cores W da terceira carta.
    }

    @Test
    void countNumberOfColorTest_ListEmpty() {
        List<Card> cards = new ArrayList<>();
        List<Color> result = deckService.countNumberOfColor(cards);
        assertTrue(result.isEmpty(), "A lista de cores resultante deve estar vazia.");
    }

    @Test
    void calculatePredominanceColor() {
        Color color1 = new Color('G', 10, 0);
        Color color2 = new Color('U', 25, 0);
        Color color3 = new Color('W', 1, 0);

        int deckSize = color1.getQuantity() + color2.getQuantity() + color3.getQuantity();
        float resultColor1 = deckService.calculatePredominanceColor(color1, deckSize);
        float resultColor2 = deckService.calculatePredominanceColor(color2, deckSize);
        float resultColor3 = deckService.calculatePredominanceColor(color3, deckSize);

        assertEquals(27.77, resultColor1, 0.01);
        assertEquals(69.44, resultColor2, 0.01);
        assertEquals(2.77, resultColor3, 0.01);
    }

    @Test
    void findValidCards_List() {
        List<Card> cards = Arrays.asList(
                new Card(2, "Exodia", Arrays.asList("G"), true),
                new Card(1, "Dragão branco de olhos azuis", Arrays.asList("U", "B"), true),
                new Card(3, "Mago Negro", Arrays.asList("R", "W"), false));
        List<Card> result = deckService.findValidCards(cards);
        assertEquals(2, result.size()); //Deve ter 2 resultados na lista após o filtro.
        assertTrue(result.get(0).isValid()); // Deve ser verdadeiro.
        assertTrue(result.get(1).isValid()); // Deve ser verdadeiro.
    }

    @Test
    void findInvalidCards_List() {
        List<Card> cards = Arrays.asList(
                new Card(2, "Exodia", Arrays.asList("G"), false),
                new Card(1, "Dragão branco de olhos azuis", Arrays.asList("U", "B"), false),
                new Card(3, "Mago Negro", Arrays.asList("R", "W"), true));
        List<Card> result = deckService.findInvalidCards(cards);
        assertEquals(2, result.size()); //Deve ter 2 resultados na lista após o filtro.
        assertFalse(result.get(0).isValid()); // Deve ser falso.
        assertFalse(result.get(1).isValid()); // Deve ser falso.
    }

    @Test
    void findColorMin_List() {
        Color color1 = new Color('G', 50, 50);
        Color color2 = new Color('U', 40, 40);
        Color color3 = new Color('U', 40, 6);
        Color color4 = new Color('W', 10, 2);
        List<Color> colors = new ArrayList<>(List.of(color1, color2, color3, color4));
        Color colorMin = deckService.findColorMin(colors);
        assertEquals('U', colorMin.getAcronym()); //Espera a color "U" como cor minima.
    }

    @Test
    void findCardsWithColorMin() {
        Color color1 = new Color('G', 50, 50);
        Color color2 = new Color('U', 35, 35);
        Color color3 = new Color('R', 21, 21);
        Color color4 = new Color('W', 4, 4);
        List<Color> colors = new ArrayList<>(List.of(color1, color2, color3, color4));
        List<Card> cards = Arrays.asList(
                new Card(2, "Exodia", Arrays.asList("G"), true),
                new Card(1, "Dragão branco de olhos azuis", Arrays.asList("U", "B"), false),
                new Card(3, "Mago Negro", Arrays.asList("R", "W"), true));
        Color colorMin = deckService.findColorMin(colors);

        List<Card> result = deckService.findCardsWithColorMin(cards, colorMin);
        assertTrue(result.get(0).getColor_identity().contains("R")); //Espera R como a cor inválida não predominante.
        assertEquals(1, result.size()); //Espera apenas um registro de acordo com o filtro aplicado.
    }
    @Test
    public void testVerifyCardIsValid() {
        Color colorG = new Color('G', 50, 50);
        Color colorU = new Color('U', 40, 40);
        Color colorW = new Color('W', 10, 10);

        List<Color> colorsWithMinPredominance = Arrays.asList(colorG, colorU, colorW);
        Card cardNotColor = new Card(1,"CardNotColor",null,false);
        Card validCard = new Card(1, "ValidCard", Arrays.asList("G"), false);
        Card validCardTwoColors = new Card(1, "ValidCardTwoColors", Arrays.asList("G","U"), false);
        Card invalidCard = new Card(2, "InvalidCard", Arrays.asList("B"), false);
        Card multiColorInvalidCard = new Card(3, "MultiColorInvalidCard", Arrays.asList("G", "R", "B"), false);

        assertTrue(deckService.verifyCardIsValid(cardNotColor, colorsWithMinPredominance)); //Verdadeiro se não tiver cores.
        assertTrue(deckService.verifyCardIsValid(validCard, colorsWithMinPredominance)); //Verdadeiro se ambas cores forem predominantes.
        assertTrue(deckService.verifyCardIsValid(validCardTwoColors,colorsWithMinPredominance));//Verdadeiro se tiver um registro e a cor for predominante no deck.
        assertFalse(deckService.verifyCardIsValid(invalidCard, colorsWithMinPredominance)); //Invalido se tiver uma cor naõ predominante.
        assertFalse(deckService.verifyCardIsValid(multiColorInvalidCard, colorsWithMinPredominance)); // Invalido se tiver apenas uma cor predominante.

    }

}
