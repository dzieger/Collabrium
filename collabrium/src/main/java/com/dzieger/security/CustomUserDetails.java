package com.dzieger.security;

import com.dzieger.models.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * CustomUserDetails
 *
 * This class is used to provide the UserDetails for the Spring Security
 * framework. It is used to provide the user's roles and permissions to the
 * framework.
 *
 * @see org.springframework.security.core.userdetails.UserDetails
 * @see com.dzieger.models.AppUser
 *
 * @version 1.0
 */
public class CustomUserDetails implements UserDetails {

    private final AppUser user;

    public CustomUserDetails(AppUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream()
                .map(userRole -> new GrantedAuthority() {
                    @Override
                    public String getAuthority() {
                        return userRole.getRole().getName();
                    }
                }).collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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

    public int getTokenVersion() {
        return user.getTokenVersion();
    }
}
