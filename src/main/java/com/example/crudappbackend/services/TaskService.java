package com.example.crudappbackend.services;

import com.example.crudappbackend.exception.TaskNotFoundException;
import com.example.crudappbackend.model.Task;
import com.example.crudappbackend.model.dto.CreateTaskRequestDto;
import com.example.crudappbackend.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        log.info("Getting all tasks");
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        log.info("Getting task by id: {}", id);
        Optional<Task> task = taskRepository.findById(id);
        return task.orElseThrow(() ->
                new TaskNotFoundException("Task with id %d not found".formatted(id)));
    }

    public boolean createTask(CreateTaskRequestDto createTaskRequestDto) {
        log.info("Creating task: {}", createTaskRequestDto);
        taskRepository.save(Task.builder()
                .title(createTaskRequestDto.title())
                .description(createTaskRequestDto.description())
                .completed(false)
                .build());
        return true;
    }

    public boolean updateCompleted(Long id) {
        log.info("Updating completed task with id: {}", id);
        Optional<Task> task = taskRepository.findById(id);

        task.ifPresent(task1 -> {
            task1.setCompleted(!task1.isCompleted());
        });

        taskRepository.save(task.orElseThrow(() ->
                new TaskNotFoundException("Task with id %d not found".formatted(id))));

        return true;
    }

    public boolean deleteTask(Long id) {
        log.info("Deleting task by id: {}", id);

        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
