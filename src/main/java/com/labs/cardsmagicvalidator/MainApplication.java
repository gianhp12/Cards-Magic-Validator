package com.labs.cardsmagicvalidator;

import com.labs.cardsmagicvalidator.model.Card;
import com.labs.cardsmagicvalidator.services.ApiService;
import com.labs.cardsmagicvalidator.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class MainApplication implements CommandLineRunner {
	@Autowired
	FileService fileService;

	@Autowired
	ApiService apiService;

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class,args);
	}
	@Override
	public void run(String... args) throws Exception {
		String filePath = "data/deck.txt";
		List<String> file = fileService.readLinesFromFile(filePath);
		List<Card> cards = fileService.mapperFileCards(file);
        for (Card card : cards) {
            card.setColor_identity(apiService.searchCardByName(card));
        }
	}
}