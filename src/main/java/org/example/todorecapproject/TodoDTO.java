package org.example.todorecapproject;

import org.example.todorecapproject.domain.Status;

public record TodoDTO(
        String description,
        Status status
) {
}
