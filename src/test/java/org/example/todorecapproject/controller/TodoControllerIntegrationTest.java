package org.example.todorecapproject.controller;

import org.example.todorecapproject.domain.Status;
import org.example.todorecapproject.domain.Todo;
import org.example.todorecapproject.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TodoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoRepository repo;

    @Test
    void getTodos_shouldReturnList_whenCalled() throws Exception {
        Todo newTodo = new Todo("1", "Todo 1", Status.OPEN);
        repo.save(newTodo);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Todo 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].status").value("OPEN"));
    }

    @Test
    void getTodo_shouldReturnTodo_whenCalledWithValidId() throws Exception {
        Todo newTodo = new Todo("1", "Todo 1", Status.OPEN);
        repo.save(newTodo);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Todo 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OPEN"));
    }

    @Test
    void getTodo_shouldReturn404_whenCalledWithInvalidId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo/999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Not Found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ToDo with ID: 999 not found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }

    @Test
    void addTodo() {
    }

    @Test
    void updateTodo() {
    }

    @Test
    void deleteTodo() {
    }
}