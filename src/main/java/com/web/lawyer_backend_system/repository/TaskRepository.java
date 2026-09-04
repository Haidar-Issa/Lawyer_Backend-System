package com.web.lawyer_backend_system.repository;

import com.web.lawyer_backend_system.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
    Page<Task> findAll(Specification<Task> spec, Pageable pageable);
}
