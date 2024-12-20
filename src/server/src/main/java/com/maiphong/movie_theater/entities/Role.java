package com.maiphong.movie_theater.entities;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role extends MasterEntity {
    @Column(unique = true, nullable = false, columnDefinition = "NVARCHAR(50)")
    private String name;

    @Column(columnDefinition = "NVARCHAR(500)")
    private String description;

    @OneToMany(mappedBy = "role")
    private Set<User> users;

}
