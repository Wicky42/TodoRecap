package org.example.todorecapproject.service;

import org.example.todorecapproject.TodoDTO;
import org.example.todorecapproject.domain.Todo;
import org.example.todorecapproject.domain.exceptions.TodoNotFoundException;
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

    // Get Functions
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Optional<Todo> getTodoById(String id) {
        return todoRepository.findById(id);
    }


    // Add Function
    public Todo addTodo(TodoDTO content) {
        if(content.description() == null || content.description().isBlank()  ){
            throw new IllegalArgumentException("Description must not be empty");
        }
        Todo newTodo = new Todo(idService.generateTodoId(), content.description(), content.status());
        return todoRepository.save(newTodo);
    }


    public Todo update(TodoDTO updateTodo, String id) {
        Optional<Todo> todo = todoRepository.findById(id);
        if(todo.isEmpty()) {
            throw new TodoNotFoundException();
        }
        return todoRepository.save(new Todo(id, updateTodo.description(), updateTodo.status()));
    }

    public void delete(String id) {
        todoRepository.deleteById(id);
    }
}
