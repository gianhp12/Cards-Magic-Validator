package com.labs.cardsmagicvalidator.services.impl;

import com.labs.cardsmagicvalidator.model.Card;
import com.labs.cardsmagicvalidator.services.FileService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public List<String> readLinesFromFile(String filePath) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(filePath);

            if (inputStream != null) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                    List<String> lines = new ArrayList<>();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        lines.add(line);
                    }
                    return lines;
                }
            } else {
                System.err.println("O arquivo não foi encontrado: " + filePath);
                return List.of();
            }
        } catch (Exception e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
            return List.of();
        }
    }
    public List<Card> mapperFileCards(List<String> lines) {
        List<Card> cards = new ArrayList<>();
        String currentType = null;
        for (String line : lines) {
            if (line.startsWith("[")) {
                currentType = line.substring(1, line.length() - 1);
            } else {
                String[] parts = line.split(" ", 2);
                int quantity = Integer.parseInt(parts[0]);
                String name = parts[1];

                Card card = new Card();
                card.setQuantity(quantity);
                card.setName(name);
                card.setType(currentType);
                cards.add(card);
            }
        }
        return cards;
    }
}