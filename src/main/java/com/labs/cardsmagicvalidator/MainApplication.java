package com.labs.cardsmagicvalidator;

import com.labs.cardsmagicvalidator.exception.InvalidCardFormatException;
import com.labs.cardsmagicvalidator.model.Card;
import com.labs.cardsmagicvalidator.model.Color;
import com.labs.cardsmagicvalidator.services.ApiService;
import com.labs.cardsmagicvalidator.services.DeckService;
import com.labs.cardsmagicvalidator.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class MainApplication implements CommandLineRunner {
    @Autowired
    FileService fileService;

    @Autowired
    ApiService apiService;

    @Autowired
    DeckService deckService;

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        String filePath = "data/deck.txt";
        try {
            List<String> file = fileService.readLinesFromFile(filePath);
            try {
                List<Card> cards = fileService.mapperFileCards(file);
                cards.forEach(card -> {
                    try {
                        card = apiService.findCardByName(card);
                    } catch (WebClientResponseException ex) {
                        card.setValid(false);
                    }
                });
                List<Card> validCards = cards.stream()
                        .filter(Card::isValid)
                        .collect(Collectors.toList());
                List<Card> notFoundCards = cards.stream()
                        .filter(card -> !card.isValid())
                        .collect(Collectors.toList());
                List<Color> colors = deckService.countNumberOfColor(validCards);
                colors.forEach(color -> color.setPredominance(deckService.calculatePredominanceColor(color, validCards.size())));
                validCards.forEach(card -> card.setValid(deckService.verifyCardIsValid(card, colors)));
                Color colorMin = colors.stream()
                        .filter(color -> color.getPredominance() >= 5.0f)
                        .min(Comparator.comparing(Color::getPredominance))
                        .orElse(null);
                System.out.println("Cor inválida: "+ colorMin.getAcronym());
                System.out.println("Quota: " + new DecimalFormat("0.00").format(colorMin.getPredominance()) + "%");
                List<Card> cardsWithColorMin = validCards.stream()
                        .filter(card -> card.getColor_identity().contains(String.valueOf(colorMin.getAcronym())))
                        .collect(Collectors.toList());
                for(Card card : cardsWithColorMin){
                    System.out.println(card.getColor_identity() + " " + card.getName() + "/ Qtd: " + card.getQuantity());
                }
                System.out.println(notFoundCards.size() + " cartas não foram encontradas no Scryfall!");
            } catch (InvalidCardFormatException ex) {
                System.out.println("Formato definido no arquivo não reconhecido: " + ex.getMessage());
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Arquivo não encontrado no caminho definido: " + ex.getMessage());
        }
    }
}