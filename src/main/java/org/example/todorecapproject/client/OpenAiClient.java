package org.example.todorecapproject.client;

import org.example.todorecapproject.domain.OpenAi.OpenAIRequest;
import org.example.todorecapproject.domain.OpenAi.OpenAIResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class OpenAiClient {

    private final RestClient restClient;

    public OpenAiClient(@Value("${API_KEY}") String apiKey) {
        this.restClient = RestClient.builder()
                .baseUrl("https://api.openai.com/v1/chat/completions")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();
    }

    public OpenAIResponse checkGrammar(String text){
        return restClient.post()
                .body(new OpenAIRequest("Bitte prüfe den Text auf rechtschreibfehler und gib eine korrigierte Version zurück: " + text))
                .retrieve()
                .body(OpenAIResponse.class);
    }
}
