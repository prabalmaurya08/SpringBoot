package com.example.SpringBootAPI.controller;

public class EmployeeNotFoundException extends RuntimeException{
    public EmployeeNotFoundException(Long id){
        super("could not Found Employee with id"+id);
    }
}
