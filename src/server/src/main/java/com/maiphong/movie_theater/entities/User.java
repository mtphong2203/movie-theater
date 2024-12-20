package com.maiphong.movie_theater.entities;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends MasterEntity {
    @Column(columnDefinition = "NVARCHAR(25)")
    private String firstName;

    @Column(columnDefinition = "NVARCHAR(25)")
    private String lastName;

    @Transient
    private String displayName;

    @Column(nullable = false, unique = true, columnDefinition = "NVARCHAR(40)")
    private String username;

    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false, unique = true, columnDefinition = "NVARCHAR(50)")
    private String email;

    @Column(nullable = false, unique = true, columnDefinition = "NVARCHAR(25)")
    private String phoneNumber;

    private String identityCard;

    @Column(columnDefinition = "NVARCHAR(70)")
    private String address;

    @Column(nullable = false)
    private String password;

    public String getDisplayName() {
        return this.firstName + " " + this.lastName;
    }

    @ManyToMany()
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

}
