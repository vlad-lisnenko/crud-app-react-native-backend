package com.example.crudappbackend.model.dto;

public record CreateTaskRequestDto(
        String title,
        String description
){}
