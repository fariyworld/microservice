package com.mace.microservice.provider.dept.dao;

import com.mace.microservice.common.entity.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * description:
 * <br />
 * Created by mace on 18:24 2018/6/30.
 */
@Mapper
public interface DeptDao {

    boolean insertOne(@Param("dept") Dept dept);

    List<Dept> findAll();
}
