package com.example.crudappbackend.controller;

import com.example.crudappbackend.model.Task;
import com.example.crudappbackend.model.dto.BooleanResponseDto;
import com.example.crudappbackend.model.dto.CreateTaskRequestDto;
import com.example.crudappbackend.model.dto.TaskResponseDto;
import com.example.crudappbackend.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService service;

    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> getAllTasks() {
        return ResponseEntity.ok(service.getAllTasks().stream().map(this::convertToDto).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(this.convertToDto(service.getTaskById(id)));
    }

    @PostMapping
    public ResponseEntity<BooleanResponseDto> createTask(@RequestBody CreateTaskRequestDto task) {
        return ResponseEntity.ok(new BooleanResponseDto(service.createTask(task)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BooleanResponseDto> updateCompleted(@PathVariable Long id) {
        boolean isUpdated = service.updateCompleted(id);
        return ResponseEntity.ok(new BooleanResponseDto(isUpdated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BooleanResponseDto> deleteTaskById(@PathVariable Long id) {
        boolean isDeleted = service.deleteTask(id);
        return ResponseEntity.ok(new BooleanResponseDto(isDeleted));
    }

    private TaskResponseDto convertToDto(Task task) {
        return TaskResponseDto.builder().id(task.getId()).title(task.getTitle()).description(task.getDescription()).completed(task.isCompleted()).build();
    }
}
