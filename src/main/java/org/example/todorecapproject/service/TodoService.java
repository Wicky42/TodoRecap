package org.example.todorecapproject.service;

import org.example.todorecapproject.domain.Todo;
import org.example.todorecapproject.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Todo addTodo(Todo content) {
        Todo newTodo = new Todo("1", content.description(), content.status());
        return todoRepository.save(newTodo);
    }
}
