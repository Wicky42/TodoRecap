package org.example.todorecapproject.service;

import org.example.todorecapproject.TodoDTO;
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

    @Test
    void getTodoById_shouldReturnEmptyOptional_whenGivenNonExisting(){
        when(mockRepo.findById("99")).thenReturn(Optional.empty());

        Optional<Todo> result = service.getTodoById("99");
        assertTrue(result.isEmpty());
        verify(mockRepo).findById("99");
    }

    @Test
    void addTodo_shouldAddTodoToRepository() {
        TodoDTO input = new TodoDTO("Description", Status.OPEN);

        when(idService.generateTodoId()).thenReturn("1");
        when(mockRepo.save(any(Todo.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Todo result = service.addTodo(input);

        verify(idService).generateTodoId();
        verify(mockRepo).save(any(Todo.class));
        assertEquals("1", result.id());
        assertEquals("Description", result.description());
        assertEquals(Status.OPEN, result.status());
    }

    @Test
    void addTodo_shouldThrowIllegalArgumentException_whenCalledWithEmptyDescription(){
        TodoDTO input = new TodoDTO("", Status.OPEN);
        assertThrows(IllegalArgumentException.class, ()-> service.addTodo(input));
        verify(mockRepo, never()).save(any());
    }


}