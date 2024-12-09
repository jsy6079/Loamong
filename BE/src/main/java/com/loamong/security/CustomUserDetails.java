package com.loamong.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.loamong.entity.UserEntity;

public class CustomUserDetails implements UserDetails {

	@Autowired
    private UserEntity ue;



    public CustomUserDetails(UserEntity userEntity) {

        this.ue = userEntity;
    }

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {

                return ue.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {

        return ue.getPassword();
    }

    @Override
    public String getUsername() {

        return ue.getUsername();
    }
    
    
    public String getNickname() {

        return ue.getNickname();
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
