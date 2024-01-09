package com.labs.cardsmagicvalidator.services.impl;

import com.labs.cardsmagicvalidator.model.Card;
import com.labs.cardsmagicvalidator.model.Records;
import com.labs.cardsmagicvalidator.services.ApiService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class ApiServiceImpl implements ApiService {
    public Card findCardByName(Card card) throws WebClientResponseException {
        try {
            String encodedName = "!%27" + card.getName().replace(" ", "%20") + "%27";
            String apiUrl = "https://api.scryfall.com/cards/search";
            URI uri = new URI(apiUrl + "?q=" + encodedName);
            WebClient webClient = WebClient.builder()
                    .clientConnector(new ReactorClientHttpConnector(HttpClient.create()))
                    .baseUrl(apiUrl)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .build();
            Mono<Records> monoCards = webClient
                    .get()
                    .uri(uri)
                    .accept(APPLICATION_JSON, MediaType.APPLICATION_XML)
                    .retrieve()
                    .bodyToMono(Records.class);
            Records records = monoCards.block();
            card.setColor_identity(records.getData().get(0).getColor_identity());
        } catch (URISyntaxException e) {
            String errorMessage = "Erro na construção da URI: " + e.getMessage();
            throw WebClientResponseException.create(400, errorMessage, null, null, null);
        }
        return card;
    }
}
