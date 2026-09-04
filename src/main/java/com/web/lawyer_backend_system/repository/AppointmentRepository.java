package com.web.lawyer_backend_system.repository;

import com.web.lawyer_backend_system.entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,String>, JpaSpecificationExecutor<Appointment> {
    Page<Appointment> findAll(Specification<Appointment> spec, Pageable pageable);

    Optional<Appointment> findByAppointmentId(String appointmentId);
}
