package org.example.todorecapproject.controller;

import org.example.todorecapproject.domain.exceptions.GlobalExceptionHandler;
import org.example.todorecapproject.domain.exceptions.TodoNotFoundException;
import org.example.todorecapproject.service.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest(TodoController.class)
@Import(GlobalExceptionHandler.class)
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TodoService todoService;

    @Test
    void updateTodo_shouldReturn404_whenTodoDoesNotExist() throws Exception {
        when(todoService.update(any(), eq("999")))
                .thenThrow(new TodoNotFoundException("ToDo with ID: 999 not found"));

        String requestBody = """
                {
                  "description": "Neue Beschreibung",
                  "status": "OPEN"
                }
                """;

        mockMvc.perform(put("/api/todo/{id}", "999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("ToDo with ID: 999 not found"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void updateTodo_shouldReturn400_whenDescriptionIsBlank() throws Exception {
        when(todoService.update(any(), eq("123")))
                .thenThrow(new IllegalArgumentException("Description must not be empty"));

        String requestBody = """
                {
                  "description": "   ",
                  "status": "OPEN"
                }
                """;

        mockMvc.perform(put("/api/todo/{id}", "123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Description must not be empty"))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}