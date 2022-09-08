package com.in28minutes.springbootapimongo.Service;

import com.in28minutes.springbootapimongo.Entity.TodoDTO;
import com.in28minutes.springbootapimongo.exception.TodoException;

import java.util.List;

public interface TodoService {

    public void createdTodo(TodoDTO todo) throws TodoException;

    public List<TodoDTO> findAllTodos();

    public TodoDTO findOneTodo(String id) throws TodoException;

    public TodoDTO updatedTodo(String id, TodoDTO todo) throws TodoException;

    public void deleteTodo(String id) throws TodoException;
}
