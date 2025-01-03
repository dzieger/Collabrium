package com.dzieger.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Schema(name = "UserRole", description = "The user role entity")
@Component
@Entity
@Table(name = "user_roles")
public class UserRole {

    @Schema(name = "id", description = "The id of the user role", example = "123e4567-e89b-12d3-a456-426614174000")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Schema(name = "user", description = "The user of the user role")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private AppUser appUser;

    @Schema(name = "role", description = "The role of the user role")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="role_id")
    private Role role;

    public UserRole() {
    }

    public UserRole(UUID id, AppUser appUser, Role role) {
        this.id = id;
        this.appUser = appUser;
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public AppUser getUser() {
        return appUser;
    }

    public void setUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", role=" + role +
                '}';
    }
}
