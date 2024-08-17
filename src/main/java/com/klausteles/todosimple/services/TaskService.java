package com.klausteles.todosimple.services;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.klausteles.todosimple.models.Task;
import com.klausteles.todosimple.models.User;
import com.klausteles.todosimple.repositories.TaskRepository;

@Service
public class TaskService {
    
    @Autowired
    UserService userService;

    @Autowired
    TaskRepository taskRepository;

    @Transactional
    public Task findTaskById(Long id) {
        
        Optional<Task> newTask = this.taskRepository.findById(id);

        return newTask.orElseThrow(() -> new RuntimeException("A tarefa nao foi encontrada. ID: "+ id));
    }

    @Transactional
    public Task createTask(Task task) {

        User user = this.userService.findUserById(task.getUser().getId());
        task.setId(null);
        task.setUser(user);

        return this.taskRepository.save(task);
    }

    @Transactional
    public Task updateTask(Task task) {

        Task newTask = findTaskById(task.getId());
        newTask.setDescription(task.getDescription());

        return this.taskRepository.save(task); 
    }

    public void deleteTask(Long id) {

        Task task = findTaskById(id);
        this.taskRepository.delete(task);

    }

    @Transactional
    public List<Task> findAllTasksPerUser(Long id) {

        List<Task> tasks = this.taskRepository.findByUser_Id(id);
        return tasks;
    }
}
