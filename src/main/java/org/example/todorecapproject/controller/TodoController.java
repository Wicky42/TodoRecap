package org.example.todorecapproject.controller;

import org.example.todorecapproject.TodoDTO;
import org.example.todorecapproject.domain.Todo;
import org.example.todorecapproject.service.TodoService;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodo(@PathVariable String id){
        Todo todo = service.getTodoById(id);
        return ResponseEntity.ok(todo);
    }

    //------------------- POST MAPPING ------------------//
    @PostMapping
    public ResponseEntity<Todo> addTodo(@RequestBody TodoDTO todo){
        Todo created = service.addTodo(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ------------------- PUT MAPPING -------------------//
    @PutMapping("/{id}")
    public Todo updateTodo(@RequestBody TodoDTO updateTodo, @PathVariable String id){
        return service.update(updateTodo, id);
    }

    // ------------------- DELETE MAPPING ---------------- //
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable String id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
