package com.web.lawyer_backend_system.repository.specification;

import com.web.lawyer_backend_system.entity.Client;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.math.BigInteger;

public class ClientSpecifications {
    public static Specification<Client> hasClientId(String clientId) {
        return (root, query, cb) ->
                StringUtils.hasText(clientId) ? cb.equal(root.get("clientId"), clientId) : null;
    }

    public static Specification<Client> containsFullName(String fullName) {
        return (root, query, cb) ->
                StringUtils.hasText(fullName) ? cb.like(cb.lower(root.get("fullName")), "%" + fullName.toLowerCase() + "%") : null;
    }

    public static Specification<Client> hasEmail(String email) {
        return (root, query, cb) ->
                StringUtils.hasText(email) ? cb.equal(cb.lower(root.get("email")), email.toLowerCase()) : null;
    }

    public static Specification<Client> hasNationalNumber(BigInteger nationalNumber) {
        return (root, query, cb) ->
                nationalNumber != null ? cb.equal(root.get("nationalNumber"), nationalNumber) : null;
    }

    public static Specification<Client> hasPhoneNumber(BigInteger phoneNumber) {
        return (root, query, cb) ->
                phoneNumber != null ? cb.equal(root.get("phoneNumber"), phoneNumber) : null;
    }

    public static Specification<Client> hasLawyerId(String lawyerId) {
        return (root, query, cb) ->
                StringUtils.hasText(lawyerId) ? cb.equal(root.get("lawyer").get("userId"), lawyerId) : null;
    }
}
