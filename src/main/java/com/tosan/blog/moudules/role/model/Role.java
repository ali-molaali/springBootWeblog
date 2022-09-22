package com.tosan.blog.moudules.role.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.tosan.blog.moudules.authority.model.Authority;
import com.tosan.blog.moudules.user.model.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="roles_tbl")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Role implements Serializable  {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
        @Column(unique = true)
        private String name;

        @ManyToMany(mappedBy = "roles")
        private List<User> users;

        @ManyToMany
        @JoinTable(name = "role_authority" , joinColumns = {@JoinColumn(name = "role", referencedColumnName = "id")},
                inverseJoinColumns = {@JoinColumn(name = "authority", referencedColumnName = "id")})
        @LazyCollection(LazyCollectionOption.FALSE)
        private List<Authority> authorities;

        @CreationTimestamp
        @Column(name = "created_at",updatable = false)
        private LocalDateTime createdAt;
        @UpdateTimestamp
        @Column(name = "updated_at")
        private LocalDateTime updatedAt;

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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }
}
