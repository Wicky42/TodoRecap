package org.example.todorecapproject.domain.OpenAi;

import java.util.List;

public record OpenAIResponse(String model,
                             List<OpenAIChoice> choices) {
    public String text(){
        return choices.getFirst().message().content();
    }
}
