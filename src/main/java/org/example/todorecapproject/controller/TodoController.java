package org.example.todorecapproject.controller;

import org.example.todorecapproject.TodoDTO;
import org.example.todorecapproject.domain.Todo;
import org.example.todorecapproject.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodo(@PathVariable String id){
        Optional<Todo> todo = service.getTodoById(id);
        return todo.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }


    //------------------- POST MAPPING ------------------//
    @PostMapping
    public ResponseEntity<Todo> addTodo(@RequestBody TodoDTO todo){
        return ResponseEntity.ok(service.addTodo(todo));
    }

    // ------------------- PUT MAPPING -------------------//
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@RequestBody TodoDTO updateTodo, @PathVariable String id){
        return ResponseEntity.ok(service.update(updateTodo, id));
    }
}
