package com.example.rgjalpha1.model;

import com.example.rgjalpha1.role.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User implements UserDetails {

    @Id
    private String username;
    private String password;
    private String email;
    private Boolean isEnabled;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnore
    private List<Game> games = new ArrayList<>();

    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnore
    private List<GameSystem> gameSystems = new ArrayList<>();

    @Basic(fetch = FetchType.EAGER)
    @Lob
    private byte[] profilePhotoData;
    private String profilePhotoFileName;

    @Basic(fetch = FetchType.EAGER)
    @Lob
    private byte[] gameRoomPhotoData;
    private String gameRoomPhotoFileName;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
        // exclude gameSystems from equals to prevent StackOverFlow exception
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
        // exclude gameSystems from hashCode to prevent StackOverFlow exception
    }

}
