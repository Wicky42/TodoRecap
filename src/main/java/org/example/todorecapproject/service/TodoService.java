package org.example.todorecapproject.service;

import org.example.todorecapproject.TodoDTO;
import org.example.todorecapproject.domain.Todo;
import org.example.todorecapproject.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final IdService idService;

    public TodoService(TodoRepository todoRepository, IdService idService) {
        this.todoRepository = todoRepository;
        this.idService = idService;
    }

    // HELPER
    // Map To DTO
    private TodoDTO mapToDto(Todo todo){
        return new TodoDTO(todo.description(), todo.status());
    }

    // Get Functions
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Optional<Todo> getTodoById(String id) {
        return todoRepository.findById(id);
    }


    // Add Function
    public Todo addTodo(TodoDTO content) {
        Todo newTodo = new Todo(idService.generateTodoId(), content.description(), content.status());
        return todoRepository.save(newTodo);
    }


}
