package com.web.lawyer_backend_system.entity;

import com.web.lawyer_backend_system.enums.UserRole;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET is_deleted = true WHERE user_id = ?")
@SQLRestriction("is_deleted = false")
public class User {
    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(nullable = false, name = "full_name")
    @Size(min = 3, max = 100, message = "Full name must be between 3 and 100 characters")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Full name must contain only letters and spaces")
    private String fullName;

    @Column(nullable = false)
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @Column(nullable = false)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, one special character, and be at least 8 characters long."
    )
    private String password;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserRole role = UserRole.USER;

    @Column(name = "phone_number", unique = true)
    @Digits(integer = 9, fraction = 0, message = "Phone number must be a valid number with up to 9 digits")
    private BigInteger phoneNumber;

    @Column(name = "national_number")
    @Digits(integer = 10, fraction = 0, message = "National number must be a valid number with up to 10 digits")
    private BigInteger nationalNumber;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private boolean active = true;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    //    Relation with tables
    @OneToMany(mappedBy = "lawyer", fetch = FetchType.LAZY)
    private List<Client> clients = new ArrayList<>();

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    private List<Case> cases = new ArrayList<>();


    @OneToMany(mappedBy = "assignedLawyer", fetch = FetchType.LAZY)
    private List<Case> casesForAssignedLawyer = new ArrayList<>();


    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    private List<CaseNote> caseNotes = new ArrayList<>();

    @OneToMany(mappedBy = "uploader", fetch = FetchType.LAZY)
    private List<Document> documents = new ArrayList<>();

    @OneToMany(mappedBy = "assignedLawyer", fetch = FetchType.LAZY)
    private List<Task> tasks = new ArrayList<>();

    @OneToMany(mappedBy = "lawyerId", fetch = FetchType.LAZY)
    private List<Appointment> appointments = new ArrayList<>();

    //    Create & Update Date
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if (this.userId == null) {
            this.userId = UUID.randomUUID().toString();
        }
    }

}
