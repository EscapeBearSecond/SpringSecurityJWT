package com.camellia.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
public class VoUser implements UserDetails, Serializable {
    private Suser suser;
    @Override
    public List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (suser.getRoles().size() == 0 || suser.getRoles() == null){
            return null;
        }else {
            for (Role role : suser.getRoles()){
                authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
            }
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return suser.getPassword();
    }

    @Override
    public String getUsername() {
        return suser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !suser.getLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return suser.getEnabled();
    }
}
