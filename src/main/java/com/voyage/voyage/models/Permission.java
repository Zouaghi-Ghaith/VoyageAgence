package com.voyage.voyage.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Permission {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles;

    // Constructeurs, getters et setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}