package com.web.lawer_backend_system.entity;

import com.web.lawer_backend_system.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
public class User {
    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(nullable = false, name = "full_name")
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserRole role = UserRole.USER;

    @Column(name = "national_number")
    private Long nationalNumber;

    @Builder.Default
    private boolean isActive = true;

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
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime updated_at;

    @PrePersist
    public void prePersist() {
        if (this.userId == null) {
            this.userId = UUID.randomUUID().toString();
        }
    }

}
