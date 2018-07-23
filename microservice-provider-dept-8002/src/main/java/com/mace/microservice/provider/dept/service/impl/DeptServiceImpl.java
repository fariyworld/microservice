package com.mace.microservice.provider.dept.service.impl;

import com.mace.microservice.common.entity.Dept;
import com.mace.microservice.provider.dept.dao.DeptDao;
import com.mace.microservice.provider.dept.service.IDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * description:
 * <br />
 * Created by mace on 18:33 2018/6/30.
 */
@Service("iDeptService")
public class DeptServiceImpl implements IDeptService {

    @Autowired
    private DeptDao deptDao;

    @Override
    public boolean insertOne(Dept dept) {

        return deptDao.insertOne(dept);
    }

    @Override
    public List<Dept> findAll() {

        return deptDao.findAll();
    }
}
