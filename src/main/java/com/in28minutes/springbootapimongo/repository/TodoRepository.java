package com.in28minutes.springbootapimongo.repository;

import com.in28minutes.springbootapimongo.Entity.TodoDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface TodoRepository extends MongoRepository<TodoDTO, String> {



    @Query("{'todo': ?0}")
    Optional<TodoDTO> findByTodo(String id);
}
