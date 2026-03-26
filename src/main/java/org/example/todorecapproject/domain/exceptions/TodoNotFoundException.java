package org.example.todorecapproject.domain.exceptions;

public class TodoNotFoundException extends RuntimeException{
    public TodoNotFoundException (String s){
        super(s);
    }
}
