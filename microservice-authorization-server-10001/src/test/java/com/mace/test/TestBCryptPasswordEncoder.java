package com.mace.test;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * description:
 * <br />
 * Created by mace on 15:15 2018/7/17.
 */
public class TestBCryptPasswordEncoder {

    @Test
    public void test(){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String admin = passwordEncoder.encode("fariy");

        System.out.println(admin);
    }
}
