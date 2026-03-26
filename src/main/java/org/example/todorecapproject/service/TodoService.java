package org.example.todorecapproject.service;

import org.example.todorecapproject.TodoDTO;
import org.example.todorecapproject.client.OpenAiClient;
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
    private final OpenAiClient openAiClient;

    public TodoService(TodoRepository todoRepository, IdService idService, OpenAiClient openAiClient) {
        this.todoRepository = todoRepository;
        this.idService = idService;
        this.openAiClient = openAiClient;
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
        // check grammar with OpenAi
        String correctedDescription = openAiClient.checkGrammar(content.description()).text();

        Todo newTodo = new Todo(idService.generateTodoId(), correctedDescription, content.status());
        return todoRepository.save(newTodo);
    }


    public Todo update(TodoDTO updateTodo, String id) {
        Optional<Todo> todo = todoRepository.findById(id);
        if(todo.isEmpty()) {
            throw new TodoNotFoundException("ToDo with ID: " + id + " not found");
        }
        if(updateTodo.description() == null || updateTodo.description().isBlank()){
            throw new IllegalArgumentException("Description must not be empty");
        }
        // check grammar with OpenAi
        String correctedDescription = openAiClient.checkGrammar(updateTodo.description()).text();

        return todoRepository.save(new Todo(id, correctedDescription, updateTodo.status()));
    }

    public void delete(String id) {
        if(!todoRepository.existsById(id)){
            throw new TodoNotFoundException("ToDo with ID: " + id + " not found");
        }
        todoRepository.deleteById(id);
    }
}
