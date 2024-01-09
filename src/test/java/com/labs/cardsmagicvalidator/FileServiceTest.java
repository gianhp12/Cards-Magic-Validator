package com.labs.cardsmagicvalidator;

import com.labs.cardsmagicvalidator.exception.InvalidCardFormatException;
import com.labs.cardsmagicvalidator.model.Card;
import com.labs.cardsmagicvalidator.services.FileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FileServiceTest {
    @Autowired
    FileService fileService;

    @Test
    void readLinesFromFile_FileExists() {
        String filePath = "data/deck.txt";
        assertDoesNotThrow(() -> {
            List<String> lines = fileService.readLinesFromFile(filePath);
            assertNotNull(lines, "A lista ou arquivo não pode ser nula");
            assertFalse(lines.isEmpty(), "A lista ou arquivo deve conter elementos");
        });
    }

    @Test
    void readLinesFromFile_NotFileExists() {
        String filePath = "notfound.txt";
        assertThrows(FileNotFoundException.class, () -> {
            fileService.readLinesFromFile(filePath);
        }, "Lançar FileNotFoundException para um arquivo não existente");
    }

    @Test
    void mapperFileCards_ValidLines() {
        List<String> validLines = List.of("[COMMANDER]", "1 Hakbal of the Surging Soul", "[CUSTO 1]", "1 Benthic Biomancer", "1 Hardened Scales", "1 Rapid Hybridization", "1 Sol Ring");

        assertDoesNotThrow(() -> {
            List<Card> cards = fileService.mapperFileCards(validLines);

            assertNotNull(cards, "A lista de cards não deve ser nula");

            // Adicione verificações específicas para cards válidos conforme necessário
            assertEquals("Hakbal of the Surging Soul", cards.get(0).getName());
            assertEquals(1, cards.get(0).getQuantity());
            assertEquals("COMMANDER", cards.get(0).getType());

            assertEquals("Benthic Biomancer", cards.get(1).getName());
            assertEquals(1, cards.get(1).getQuantity());
            assertEquals("CUSTO 1", cards.get(1).getType());

            assertEquals("Hardened Scales", cards.get(2).getName());
            assertEquals(1, cards.get(2).getQuantity());
            assertEquals("CUSTO 1", cards.get(2).getType());

            assertEquals("Rapid Hybridization", cards.get(3).getName());
            assertEquals(1, cards.get(3).getQuantity());
            assertEquals("CUSTO 1", cards.get(3).getType());

            assertEquals("Sol Ring", cards.get(4).getName());
            assertEquals(1, cards.get(4).getQuantity());
            assertEquals("CUSTO 1", cards.get(4).getType());
        });
    }

    @Test
    void mapperFileCards_InvalidLines() {
        List<String> invalidLines = List.of("COMAN", "ASS Hakbal of the Surging Soul", "CUSTO22", "123Benthic Biomancer", "12Hardened_Scales", "1_RapidHybridization", "1_Sol_Ring");
        assertThrows(InvalidCardFormatException.class, () -> {
            fileService.mapperFileCards(invalidLines);
        }, "Lançar InvalidCardFormatException se o formato da lista ou arquivo não estiver no formato esperado: Quantidade, Nome da Carta e entre colchetes [], o tipo da carta");
    }

    @Test
    void mapperFileCards_FileEmpty() {
        List<String> listEmpty = new ArrayList<>();
        assertDoesNotThrow(() -> {
            List<Card> cards = fileService.mapperFileCards(listEmpty);
            assertTrue(cards.isEmpty(), "A lista de cards deve estar vazia");
        });
    }
}
