package com.mace.microservice.provider.dept.service;

import com.mace.microservice.common.entity.Dept;

import java.util.List;

/**
 * description:
 * <br />
 * Created by mace on 18:32 2018/6/30.
 */
public interface IDeptService {

    boolean insertOne(Dept dept);

    List<Dept> findAll();
}
