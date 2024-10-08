package com.klausteles.todosimple.controllers;

import java.net.URI;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.klausteles.todosimple.models.Task;
import com.klausteles.todosimple.services.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/task")
@Validated
public class TaskController {
    
    @Autowired
    TaskService taskService;

    @GetMapping("/{id}")
    public ResponseEntity<Task> get(@PathVariable Long id) {

        Task newTask = this.taskService.findTaskById(id);
        return ResponseEntity.ok(newTask);
    }

    @PostMapping()
    @Validated
    public ResponseEntity<Void> create(@Valid @RequestBody Task task) {

        this.taskService.createTask(task);
        URI uri = ServletUriComponentsBuilder
                                    .fromCurrentRequest()
                                    .path("/{id}")
                                    .buildAndExpand(task.getId())
                                    .toUri();
        
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @Validated
    public ResponseEntity<Void> update(@Valid @PathVariable Task task, @PathVariable Long id) {

        task.setId(id);
        this.taskService.updateTask(task);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        this.taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> getAll(@PathVariable Long userId) {

        List<Task> tasks = this.taskService.findAllTasksPerUser(userId);
        return ResponseEntity.ok().body(tasks);
    }
    
}
