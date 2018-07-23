package com.mace.microservice.authserver.dao;

import com.mace.microservice.authserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * description:
 * <br />
 * Created by mace on 15:54 2018/7/17.
 */
public interface UserDao extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
