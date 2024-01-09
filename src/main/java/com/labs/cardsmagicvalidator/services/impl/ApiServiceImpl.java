package com.labs.cardsmagicvalidator.services.impl;

import com.labs.cardsmagicvalidator.model.Card;
import com.labs.cardsmagicvalidator.model.Records;
import com.labs.cardsmagicvalidator.services.ApiService;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class ApiServiceImpl implements ApiService {
    private final RestTemplate restTemplate;

    public ApiServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }
    public List<String> searchCardByName(Card card) {
        List<String> colorIdentityList = new ArrayList<>();

        try {
            String encodedName = "!%27" + card.getName().replace(" ", "%20") + "%27";
            String apiUrl = "https://api.scryfall.com/cards/search";

            // Criar a URI manualmente usando a string codificada
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

            Records cardColor = monoCards.block();
            card.setColor_identity(cardColor.getData().get(0).getColor_identity());
            System.out.println(card);
        } catch (URISyntaxException e) {
            // Tratar a exceção de URI inválida
            e.printStackTrace();
        } catch (Exception e) {
            // Tratar de acordo com sua lógica de erro
            e.printStackTrace();
        }

        return colorIdentityList;
    }
}
