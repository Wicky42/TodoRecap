package org.example.todorecapproject.service;

import org.example.todorecapproject.domain.Status;
import org.example.todorecapproject.domain.Todo;
import org.example.todorecapproject.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    TodoRepository mockRepo;

    @Mock
    IdService idService;

    @InjectMocks
    TodoService service;

    public final List<Todo> VALID_TODO_LIST = List.of(
            new Todo("1", "TODO 1", Status.OPEN),
            new Todo("2", "TODO 2", Status.IN_PROGRESS));

    @Test
    void getAllTodos_shouldReturnAllToDos(){
        when(mockRepo.findAll()).thenReturn(VALID_TODO_LIST);

        List<Todo> todoList = service.getAllTodos();

        assertEquals(2, todoList.size());
        assertTrue(todoList.contains(new Todo("1", "TODO 1", Status.OPEN)));
        assertTrue(todoList.contains(new Todo("2", "TODO 2", Status.IN_PROGRESS)));
    }

    @Test
    void getAllTodos_shouldReturnEmptyListInitially(){
        List<Todo> todoList = service.getAllTodos();
        assertTrue(todoList.isEmpty());
    }

    @Test
    void getTodoById_shouldReturnTodo_whenGivenValidId(){
        when(mockRepo.findById("1")).thenReturn(Optional.of(VALID_TODO_LIST.getFirst()));

        Optional<Todo> response = service.getTodoById("1");
        assertTrue(response.isPresent());
        assertEquals("TODO 1", response.get().description());
        assertEquals(Status.OPEN, response.get().status());
        verify(mockRepo).findById("1");

    }

}