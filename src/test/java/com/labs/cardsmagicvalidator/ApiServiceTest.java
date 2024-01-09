package com.labs.cardsmagicvalidator;

import com.labs.cardsmagicvalidator.model.Card;
import com.labs.cardsmagicvalidator.services.ApiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ApiServiceTest {
    @Autowired
    ApiService apiService;

    @Test
    void findCardByName_ExistingCard() {
        Card card = new Card();
        card.setName("Benthic Biomancer");
        assertDoesNotThrow(() -> {
            apiService.findCardByName(card);
        }, "Não lançar exceção quando a carta for localizada.");
    }

    @Test
    void findCardByName_NonExistingCard() {
        Card card = new Card();
        card.setName("Yugioh");
        WebClientResponseException exception = assertThrows(WebClientResponseException.class, () -> {
            apiService.findCardByName(card);
        }, "Lançar exceção para uma carta que não existe.");
        assertEquals(404, exception.getRawStatusCode(), "Será lançado o código 404 para uma carta que não for encontrada pela Api.");
    }

    @Test
    void findCardByName_EmptyCardName() {
        Card card = new Card();
        card.setName("");
        WebClientResponseException exception = assertThrows(WebClientResponseException.class, () -> {
            apiService.findCardByName(card);
        }, "Lançar exceção para uma carta com nome vazio.");

        assertEquals(400, exception.getRawStatusCode(), "Será lançado o código 400, para cartas que o nome estiver vazio.");
    }
}
