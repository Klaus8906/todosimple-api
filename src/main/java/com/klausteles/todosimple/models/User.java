package com.klausteles.todosimple.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.klausteles.todosimple.models.enums.ProfileEnum;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "user")
public class User {
    public interface CreateUser {}
    public interface UpdateUser {}
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "username", nullable = false, length = 255, unique = true)
    @NotBlank(groups = CreateUser.class)
    @Size(groups = CreateUser.class, min = 2, max = 255)
    private String username;

    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(name = "password", nullable = false, length = 100)
    @NotBlank(groups = {CreateUser.class, UpdateUser.class})
    @Size(groups = {CreateUser.class, UpdateUser.class}, min = 4, max = 100)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Task> todos = new ArrayList<Task>();

    @CollectionTable(name = "user_profiles")
    @Column(name = "profile")
    @ElementCollection(fetch = FetchType.EAGER)
    @JsonProperty(access = Access.WRITE_ONLY)
    private Set<Integer> profiles = new HashSet<>();

    public User() {}

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public List<Task> getTodos() {
        return this.todos;
    }

    public void setTodos(List<Task> todos) {
        this.todos = todos;
    }

    public Set<ProfileEnum> getProfiles() {
        return this.profiles.stream().map(x -> ProfileEnum.toEnum(x))
                                     .collect(Collectors.toSet());
    }

    public void addProfile(ProfileEnum profile) {
        this.profiles.add(profile.getCode());
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto)
            return true;
        if (objeto == null)
            return false;
        if (!(objeto instanceof User)) {
            return false;
        }

        User outro = (User) objeto;
        if (outro.getId() == null) {
            if(outro.getId() == null)
                return false;
            else if (!outro.getId().equals(this.id))
                return false;
        }
        return Objects.equals(this.id, outro.getId()) && Objects.equals(this.username, outro.getUsername()) &&
         Objects.equals(this.password, outro.getPassword());
    }

    public void setProfiles(Set<Integer> profiles) {
        this.profiles = profiles;
    }
}
