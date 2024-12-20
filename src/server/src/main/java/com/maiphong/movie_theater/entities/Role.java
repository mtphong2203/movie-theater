package com.maiphong.movie_theater.entities;

import java.util.Set;

import jakarta.persistence.*;
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

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

}
