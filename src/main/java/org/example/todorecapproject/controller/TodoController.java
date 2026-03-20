package org.example.todorecapproject.controller;

import org.example.todorecapproject.domain.Todo;
import org.example.todorecapproject.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    //------------------ GET MAPPING -------------------//
    @GetMapping
    public ResponseEntity<List<Todo>> getTodos(){
        return ResponseEntity.ok(service.getAllTodos());
    }


    //------------------- POST MAPPING ------------------//
    @PostMapping
    public ResponseEntity<Todo> addTodo(@RequestBody Todo todo){
        return ResponseEntity.ok(service.addTodo(todo));
    }
}
