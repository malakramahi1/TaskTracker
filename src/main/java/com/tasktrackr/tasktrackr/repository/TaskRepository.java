package com.tasktrackr.tasktrackr.repository;

import com.tasktrackr.tasktrackr.model.Task;
import com.tasktrackr.tasktrackr.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(Status status);

    List<Task> findByAssignee_userId(Integer assigneeUserId);

    List<Task> findByAssignee_userIdAndStatus(Integer assigneeUserId, Status status);
}


