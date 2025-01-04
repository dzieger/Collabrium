package com.dzieger.security;

import com.dzieger.models.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * CustomUserDetails
 *
 * It adopts the AppUser entity for Spring Security.
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

    /**
     * The user
     */
    private final AppUser user;

    /**
     * Constructor
     *
     * @param user The user to create the UserDetails for
     */
    public CustomUserDetails(AppUser user) {
        this.user = user;
    }

    /**
     * Get the user's authorities
     *
     * @return The user's authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream()
                .map(userRole -> (GrantedAuthority) () -> userRole.getRole().getName())
                .collect(Collectors.toList());
    }

    /**
     * Get the user's password
     *
     * @return The user's password
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Get the user's username
     *
     * @return The user's username
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * Check if the user's account is not expired
     *
     * @return True if the account is not expired, false otherwise
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Check if the user's account is not locked
     *
     * @return True if the account is not locked, false otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Check if the user's credentials are not expired
     *
     * @return True if the credentials are not expired, false otherwise
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Check if the user's account is enabled
     *
     * @return True if the account is enabled, false otherwise
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Get the user's token version
     *
     * @return The user's token version
     */
    public int getTokenVersion() {
        return user.getTokenVersion();
    }
}
