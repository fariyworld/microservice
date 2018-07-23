package com.mace.microservice.oauth2.dao;

import com.mace.microservice.oauth2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * description:
 * <br />
 * Created by mace on 15:54 2018/7/17.
 */
public interface UserDao extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
