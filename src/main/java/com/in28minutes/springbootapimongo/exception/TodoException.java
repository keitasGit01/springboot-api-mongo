package com.in28minutes.springbootapimongo.exception;

public class TodoException extends Exception{

    private static final long serialVersionUID = 1L;

    public TodoException(String message){
        super(message);
    }

    public static String NotFoundException(String id){
        return "Todo with id " + id + " not found";
    }

    public static String TodoAlreadyExits(){
        return "Todo already exist";
    }
}
