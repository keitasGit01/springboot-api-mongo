package com.in28minutes.springbootapimongo.Service;

import com.in28minutes.springbootapimongo.Entity.TodoDTO;
import com.in28minutes.springbootapimongo.exception.TodoException;
import com.in28minutes.springbootapimongo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class TodoServiceImpl implements TodoService{

    @Autowired
    private TodoRepository repository;


    @Override
    public void createdTodo(TodoDTO todo) throws TodoException {
        Optional<TodoDTO> byTodo = repository.findByTodo(todo.getTodo());
        if (byTodo.isPresent()){
            throw new TodoException(TodoException.TodoAlreadyExits());
        }else {
            todo.setCreatedAt(new Date(System.currentTimeMillis()));
            repository.save(todo);
        }


    }

    @Override
    public List<TodoDTO> findAllTodos() {
        List<TodoDTO> todos = repository.findAll();
        if (todos.size() > 0){
            return todos;
        }else {
            return new ArrayList<>();
        }

    }

    @Override
    public TodoDTO findOneTodo(String id) throws TodoException{
        Optional<TodoDTO> getById = repository.findById(id);
        if (!getById.isPresent()){
            throw new TodoException(TodoException.NotFoundException("Todo with this id " + id + " Already exists"));
        }else {
            return getById.get();
        }

    }

    @Override
    public TodoDTO updatedTodo(String id, TodoDTO todo) throws TodoException {
        Optional<TodoDTO> updateTodo = repository.findById(id);
        Optional<TodoDTO> todoExist = repository.findByTodo(todo.getId());
        if (updateTodo.isPresent()){
            if (todoExist.isPresent() && !todoExist.get().getId().equals(id)){
                throw new TodoException(TodoException.TodoAlreadyExits());
            }
            TodoDTO todos = updateTodo.get();
            todos.setTodo(todo.getTodo());
            todos.setCompleted(todo.getCompleted());
            todos.setDescription(todo.getDescription());
            todos.setCreatedAt(new Date(System.currentTimeMillis()));
            return repository.save(todos);
        }else {
            throw new TodoException(TodoException.TodoAlreadyExits());
        }
    }

    @Override
    public void deleteTodo(String id) throws TodoException {
        Optional<TodoDTO> delete = repository.findById(id);
        if (!delete.isPresent()){
            throw new TodoException(TodoException.NotFoundException(id));
        }else {
            repository.deleteById(id);
        }
    }
}
