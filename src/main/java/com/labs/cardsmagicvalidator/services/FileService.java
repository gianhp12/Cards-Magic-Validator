package com.labs.cardsmagicvalidator.services;

import com.labs.cardsmagicvalidator.exception.InvalidCardFormatException;
import com.labs.cardsmagicvalidator.model.Card;

import java.io.IOException;
import java.util.List;

public interface FileService {
    List<String> readLinesFromFile(String filePath) throws IOException;

    List<Card> mapperFileCards(List<String> lines) throws InvalidCardFormatException;
}
