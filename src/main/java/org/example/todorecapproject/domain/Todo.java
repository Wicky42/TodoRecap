package org.example.todorecapproject.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public record Todo(
        @Id
        String id,
        String description,
        Status status
) {
}
