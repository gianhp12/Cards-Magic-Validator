package com.labs.cardsmagicvalidator.services.impl;

import com.labs.cardsmagicvalidator.exception.InvalidCardFormatException;
import com.labs.cardsmagicvalidator.model.Card;
import com.labs.cardsmagicvalidator.services.FileService;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public List<String> readLinesFromFile(String filePath) throws IOException {
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
            throw new FileNotFoundException("O arquivo não foi encontrado: " + filePath);
        }
    }

    public List<Card> mapperFileCards(List<String> lines) throws InvalidCardFormatException {
        List<Card> cards = new ArrayList<>();
        String currentType = "";
        try {
            for (String line : lines) {
                Card card = new Card();
                line = line.trim();
                if (line.matches("^\\d.*")) {
                    String[] parts = line.split(" ", 2);
                    card.setQuantity(Integer.parseInt(parts[0]));
                    card.setName(parts[1]);
                    card.setType(currentType);
                    cards.add(card);
                } else if (line.matches("\\[.*\\]")) {
                    currentType = line.substring(1, line.length() - 1);
                }
            }
            return cards;
        } catch (Exception e) {
            throw new InvalidCardFormatException("Erro ao tentar ler o arquivo: " + e.getMessage());
        }
    }
}