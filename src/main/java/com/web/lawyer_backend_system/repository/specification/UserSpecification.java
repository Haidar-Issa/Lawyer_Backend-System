package com.web.lawyer_backend_system.repository.specification;

import com.web.lawyer_backend_system.dto.user.UserFilterDto;
import com.web.lawyer_backend_system.entity.User;
import com.web.lawyer_backend_system.enums.UserRole;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class UserSpecification {

    public static Specification<User> build(UserFilterDto userFilterDto){
        if(userFilterDto==null) {
            return null;
        }
        return Specification.where(hasEmail(userFilterDto.getEmail())
                .and(hasName(userFilterDto.getFullName()))
                .and(hasRole(userFilterDto.getRole()))
                .and(hasIsActive(userFilterDto.getIsActive())));
    }

    public static Specification<User> hasName(String keywords) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(keywords)) {
                return null;
            }
            String pattern = '%' + keywords.trim().toLowerCase() + '%';

            return cb.like(cb.lower(root.get("fullName")), pattern.toLowerCase());
        };
    }

    public static Specification<User> hasEmail(String email) {
        return (root, query, cb) ->{
            if(!StringUtils.hasText(email)){
                return null;
            }
            return cb.equal(root.get("email"), email);
        };
    }

    public static Specification<User> hasRole(UserRole role) {
        return (root,query,cb)-> {
            if(role == null){
                return null;
            }
            return cb.equal(root.get("role"), role);
        };
    }

    public static Specification<User> hasIsActive(Boolean isActive) {
        if(isActive == null){
            return null;
        }
        return (root, query, cb) -> cb.equal(root.get("isActive"), isActive);
    }



}
