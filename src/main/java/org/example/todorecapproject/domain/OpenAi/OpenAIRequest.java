package org.example.todorecapproject.domain.OpenAi;

import java.util.Collections;
import java.util.List;

public record OpenAIRequest(String model,
                            List<OpenAIMessage> messages) {
    public OpenAIRequest(String message) {
        this("gpt-5.4", Collections.singletonList(new OpenAIMessage("user", message)));
    }
}
