package com.amaurote.octopus2.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class MyUserDetails implements UserDetails {

    private String userName;
    private String password;
    private boolean active;
    private boolean enabled;
    private Set<GrantedAuthority> authorities;

    public MyUserDetails(String userName, String password, Set<GrantedAuthority> authorities) {
        this.userName = userName;
        this.password = password;
        this.active = true;
        this.enabled = true;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
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
        return enabled && active;
    }
}
