package com.labs.cardsmagicvalidator;

import com.labs.cardsmagicvalidator.exception.InvalidCardFormatException;
import com.labs.cardsmagicvalidator.model.Card;
import com.labs.cardsmagicvalidator.services.ApiService;
import com.labs.cardsmagicvalidator.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.FileNotFoundException;
import java.util.List;

@SpringBootApplication
public class MainApplication implements CommandLineRunner {
    @Autowired
    FileService fileService;

    @Autowired
    ApiService apiService;

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
                for (Card card : cards) {
                    try {
                        card = apiService.findCardByName(card);
                        System.out.println(card);
                    } catch (WebClientResponseException ex) {
                        System.out.println("Erro na consulta da API: " + ex.getStatusText());
                    }
                }
            } catch (InvalidCardFormatException ex) {
                System.out.println("Formato definido no arquivo não reconhecido: " + ex.getMessage());
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Arquivo não encontrado no caminho definido: " + ex.getMessage());
        }
    }
}