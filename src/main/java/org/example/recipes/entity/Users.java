package org.example.recipes.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

import org.hibernate.annotations.DynamicInsert;

@Entity
@DynamicInsert
@Table(name = "users")
public class Users {

    @Id
    @Column(name = "user_id", length = 10, nullable = false)
    private String id;

    @Column(name = "username", nullable = false, unique = true, length = 25)
    private String username;

    @Column(name = "firstname", length = 25)
    private String firstName;

    @Column(name = "lastname", length = 25)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "role", nullable = false, length = 5)
    private String role;

    @Column(name="avatar_url", length=255)
    private String avatarUrl;

    @Column(name="date_of_birth")
    private LocalDate dateOfBirth;

    public Users() {}

    public Users(String id, String username, String firstName, String lastName, String email, String password, String role) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Transient
    public String getFullName() {
        return (firstName != null ? firstName : "") +
                (lastName  != null ? " " + lastName : "");
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
