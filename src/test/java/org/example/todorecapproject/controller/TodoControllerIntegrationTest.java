package org.example.todorecapproject.controller;

import org.example.todorecapproject.client.OpenAiClient;
import org.example.todorecapproject.domain.OpenAi.OpenAIChoice;
import org.example.todorecapproject.domain.OpenAi.OpenAIMessage;
import org.example.todorecapproject.domain.OpenAi.OpenAIResponse;
import org.example.todorecapproject.domain.Status;
import org.example.todorecapproject.domain.Todo;
import org.example.todorecapproject.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TodoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoRepository repo;

    @MockitoBean
    private OpenAiClient openAiClient;

    // ─── Helper ────────────────────────────────────────────────────────────────

    private OpenAIResponse openAiResponseWith(String text) {
        return new OpenAIResponse(
                "gpt-4o-mini",
                List.of(new OpenAIChoice(new OpenAIMessage("assistant", text)))
        );
    }

    // ─── GET /api/todo ──────────────────────────────────────────────────────────

    @Test
    void getTodos_shouldReturnEmptyList_whenNoTodosExist() throws Exception {
        mockMvc.perform(get("/api/todo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getTodos_shouldReturnList_whenTodosExist() throws Exception {
        repo.save(new Todo("1", "Todo 1", Status.OPEN));

        mockMvc.perform(get("/api/todo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].description").value("Todo 1"))
                .andExpect(jsonPath("$[0].status").value("OPEN"));
    }

    // ─── GET /api/todo/{id} ─────────────────────────────────────────────────────

    @Test
    void getTodo_shouldReturnTodo_whenCalledWithValidId() throws Exception {
        repo.save(new Todo("1", "Todo 1", Status.OPEN));

        mockMvc.perform(get("/api/todo/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.description").value("Todo 1"))
                .andExpect(jsonPath("$.status").value("OPEN"));
    }

    @Test
    void getTodo_shouldReturn404_whenCalledWithInvalidId() throws Exception {
        mockMvc.perform(get("/api/todo/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("ToDo with ID: 999 not found"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    // ─── POST /api/todo ─────────────────────────────────────────────────────────

    @Test
    void addTodo_shouldCreateTodo_andReturnIt() throws Exception {
        when(openAiClient.checkGrammar(anyString()))
                .thenReturn(openAiResponseWith("New Todo"));

        String requestBody = """
                {
                  "description": "New Todo",
                  "status": "OPEN"
                }
                """;

        mockMvc.perform(post("/api/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.description").value("New Todo"))
                .andExpect(jsonPath("$.status").value("OPEN"));

        List<Todo> todos = repo.findAll();
        assertEquals(1, todos.size());
        assertEquals("New Todo", todos.getFirst().description());
        assertEquals(Status.OPEN, todos.getFirst().status());
    }

    @Test
    void addTodo_shouldReturn400_whenDescriptionIsBlank() throws Exception {
        String requestBody = """
                {
                  "description": "   ",
                  "status": "OPEN"
                }
                """;

        mockMvc.perform(post("/api/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Description must not be empty"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    // ─── PUT /api/todo/{id} ─────────────────────────────────────────────────────

    @Test
    void updateTodo_shouldUpdateTodo_andReturnUpdatedValues() throws Exception {
        repo.save(new Todo("1", "Old Description", Status.OPEN));
        when(openAiClient.checkGrammar(anyString()))
                .thenReturn(openAiResponseWith("Updated Description"));

        String requestBody = """
                {
                  "description": "Updated Description",
                  "status": "IN_PROGRESS"
                }
                """;

        mockMvc.perform(put("/api/todo/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.description").value("Updated Description"))
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"));

        Todo updated = repo.findById("1").orElseThrow();
        assertEquals("Updated Description", updated.description());
        assertEquals(Status.IN_PROGRESS, updated.status());
    }

    @Test
    void updateTodo_shouldReturn404_whenCalledWithInvalidId() throws Exception {
        String requestBody = """
                {
                  "description": "Updated Description",
                  "status": "OPEN"
                }
                """;

        mockMvc.perform(put("/api/todo/999")
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
        repo.save(new Todo("1", "Old Description", Status.OPEN));

        String requestBody = """
                {
                  "description": "   ",
                  "status": "OPEN"
                }
                """;

        mockMvc.perform(put("/api/todo/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Description must not be empty"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    // ─── DELETE /api/todo/{id} ──────────────────────────────────────────────────

    @Test
    void deleteTodo_shouldDeleteTodo_andReturn204() throws Exception {
        repo.save(new Todo("1", "Todo to delete", Status.OPEN));

        mockMvc.perform(delete("/api/todo/1"))
                .andExpect(status().isNoContent());

        assertFalse(repo.existsById("1"));
    }

    @Test
    void deleteTodo_shouldReturn404_whenCalledWithInvalidId() throws Exception {
        mockMvc.perform(delete("/api/todo/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("ToDo with ID: 999 not found"))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}