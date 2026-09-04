package com.web.lawyer_backend_system.repository;

import com.web.lawyer_backend_system.entity.Client;
import com.web.lawyer_backend_system.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client,String>, JpaSpecificationExecutor<Client> {
    Optional<Client> findByClientId(String clientId);
    Optional<Client> findByEmail(String email);
    Optional<Client> findByNationalNumber(BigInteger nationalNumber);
    Optional<Client> findByPhoneNumber(BigInteger phoneNumber);
    Optional<Client> findByFullNameAndPhoneNumber(String fullName, BigInteger phoneNumber);
    Optional<List<Client>> findByFullName(String fullName);
    Page<Client> findAllByLawyer(User lawyer, Pageable pageable);
    Optional<Client> findByLawyerAndFullNameContainingIgnoreCase(User lawyer, String fullName);

    boolean existsByFullName(String fullName);
    boolean existsByEmail(String email);
    boolean existsByLawyer(User lawyer);
    void deleteByClientId(String clientId);

}
