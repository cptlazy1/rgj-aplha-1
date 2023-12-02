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

import java.util.Collection;
import java.util.List;

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
    private Boolean profileIsPrivate;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Game> games;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL)
    @JsonIgnore
    private List<GameSystem> gameSystems;

//    @OneToOne(
//            mappedBy = "user",
//            cascade = CascadeType.ALL,
//            fetch = FetchType.EAGER,
//            orphanRemoval = true)
//    @JoinColumn(name = "photo_id")
//    @JsonIgnore
//    private UserProfilePhoto userProfilePhoto;
//
//    @OneToOne(
//            mappedBy = "user",
//            cascade = CascadeType.ALL,
//            fetch = FetchType.EAGER,
//            orphanRemoval = true)
//    @JoinColumn(name = "photo_id")
//    @JsonIgnore
//    private UserGameRoomPhoto userGameRoomPhoto;

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
}
