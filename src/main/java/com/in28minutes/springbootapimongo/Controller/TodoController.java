package com.in28minutes.springbootapimongo.Controller;


import com.in28minutes.springbootapimongo.Entity.TodoDTO;
import com.in28minutes.springbootapimongo.Service.TodoService;
import com.in28minutes.springbootapimongo.exception.TodoException;
import com.in28minutes.springbootapimongo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class TodoController {

    @Autowired
    private TodoService service;

    @Autowired
    private TodoRepository repository;

    @GetMapping("/todos")
    private ResponseEntity<?> getAllTodo() {
        List<TodoDTO> allTodos = service.findAllTodos();
            return new ResponseEntity<>(allTodos, allTodos.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
        }


    @PostMapping("/todos")
    public ResponseEntity<?> saveTodo(@RequestBody TodoDTO todo) {

        try {
            service.createdTodo(todo);
            return new ResponseEntity<>(todo, HttpStatus.CREATED);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }catch (TodoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


    @GetMapping("/todos/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        try {

            return new ResponseEntity<>(service.findOneTodo(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Todo not found with id" + id, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<?> updatedTodo(@PathVariable String id, @RequestBody TodoDTO todo) throws TodoException {
        try {
        TodoDTO todoDTO = service.updatedTodo(id, todo);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }catch (ConstraintViolationException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }catch (TodoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable String id){
        try {
            service.deleteTodo(id);
            return new ResponseEntity<>("This " + id  + " has been deleted successfully", HttpStatus.OK);

        }catch (TodoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
