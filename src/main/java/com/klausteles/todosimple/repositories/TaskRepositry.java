package com.klausteles.todosimple.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.klausteles.todosimple.models.Task;

@Repository
public interface TaskRepositry extends JpaRepository<Task, Long>{

    // Jeito facilitado pelo Spring
    List<Task> findByUser_Id(Long id);    

    // Jeito personalizado mas com ajuda do Spring
    // @Query(value = "SELECT task FROM Task task WHERE task.user.id = :id")
    // List<Task> procuraTaskPorUserId(@Param("id") Long id);

    // Jeito com SQL nativo
    // @Query(value = "SELECT * FROM tarefas t WHERE t.user_id = :id", nativeQuery = true)
    // List<Task> procuraTaskPorUserId(@Param("id") Long id);
}