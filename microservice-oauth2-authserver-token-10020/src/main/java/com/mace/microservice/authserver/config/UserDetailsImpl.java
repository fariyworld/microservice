package com.mace.microservice.authserver.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * description:
 * <br />
 * Created by mace on 14:19 2018/7/23.
 */
public class UserDetailsImpl extends User {

    public UserDetailsImpl(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
