package com.mace.microservice.provider.dept.controller;

import com.mace.microservice.common.annotation.EnableControllerLog;
import com.mace.microservice.common.base.RestPackResponse;
import com.mace.microservice.common.entity.Dept;
import com.mace.microservice.provider.dept.service.IDeptService;
import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * description:
 * <br />
 * Created by mace on 18:35 2018/6/30.
 */
@RestController
@RequestMapping("/dept")
@Api2Doc(id = "dept", name = "部门接口")
@ApiComment(seeClass = Dept.class)
@EnableControllerLog(description = "部门接口")
public class DeptController {

    @Autowired
    private IDeptService iDeptService;

    @Value("${server.port}")
    String port;

    @EnableControllerLog(description = "查询所有部门信息")
    @ApiComment(value = "查询所有部门信息")
    @RequestMapping(value = "/findAll.do", name = "查询所有部门信息", method = RequestMethod.GET)
    public RestPackResponse<List<Dept>> findAll(){

        List<Dept> deptList = iDeptService.findAll();

        return RestPackResponse.createBySuccess("查询部门信息成功 : " + port, deptList);
    }

}
