package com.mace.microservice.authserver.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

/**
 * description:
 * <br />
 * Created by mace on 14:11 2018/7/23.
 */
public class AdminAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = -7985625659135475811L;
    private Collection<GrantedAuthority> authorities = new ArrayList<>();

    private Object principal;
    private Object credentials;
    private String username;

    public AdminAuthenticationToken(String username, String password) {
        super(null);
        this.username = username;
        this.credentials = password;
    }

    public void setPrincipal(Object principal) {
        this.principal = principal;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    public String getUsername() {
        return username;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities.addAll(authorities);
    }
}
