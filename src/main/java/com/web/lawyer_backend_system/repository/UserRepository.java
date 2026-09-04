package com.web.lawyer_backend_system.repository;

import com.web.lawyer_backend_system.entity.User;
import com.web.lawyer_backend_system.enums.UserRole;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    Optional<List<User>> findByFullName(String fullName);

    Optional<User> findByEmail(String email);

    Optional<User> findByUserId(String userId);

    Page<User> findAllByRole(UserRole role, Pageable pageable);

    Page<User> findByActiveIsTrue(Pageable pageable);

    Page<User> findByActiveIsFalse(Pageable pageable);

    Page<User> findByIsDeletedFalse(Pageable pageable);

    Page<User> findAllUsersByIsDeletedFalse(Pageable pageable);

    boolean existsByEmail(String email);

    boolean existsByFullName(String fullName);

    boolean existsByUserId(String userId);

    boolean existsByRole(UserRole role);

    void deleteByUserId(String userId);

    @Query("""
            SELECT DISTINCT u FROM User u
            LEFT JOIN FETCH u.clients c
            WHERE u.role IN ("LAWYER", "ADMIN") AND LOWER(u.fullName) LIKE LOWER(CONCAT('%', :fullName, '%'))
            """)
    List<User> findLawyersWithClientsByName(@Param("fullName") String fullName);


}
