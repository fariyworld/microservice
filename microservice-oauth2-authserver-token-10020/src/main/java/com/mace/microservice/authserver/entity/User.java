package com.mace.microservice.authserver.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * description:
 * <br />
 * Created by mace on 14:22 2018/7/17.
 */
@Entity
@Table(name = "sys_user")
public class User implements Serializable, UserDetails {

    private static final long serialVersionUID = -4516654521210638774L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column
    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "sys_role_user", // 指定该连接表的表名
                // 该属性值可接受多个@JoinColumn，用于配置连接表中外键列的信息，这些外键列参照当前实体对应表的主键列
                joinColumns = @JoinColumn(name = "sys_user_id", referencedColumnName = "id"),
                // 该属性值可接受多个@JoinColumn，用于配置连接表中外键列的信息，这些外键列参照当前实体的关联实体对应表的主键列
                inverseJoinColumns = @JoinColumn(name = "sys_role_id", referencedColumnName = "id")
    )
    private Set<Role> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        // 需将 List<Role> 转成 List<SimpleGrantedAuthority>，否则前端拿不到角色列表名称
        List<GrantedAuthority> simpleAuthorities = new ArrayList<>();
        for(GrantedAuthority authority : this.authorities){
            simpleAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }
        return simpleAuthorities;

    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
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
