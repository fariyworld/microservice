package com.mace.microservice.common.entity;

import com.alibaba.fastjson.annotation.JSONType;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * description: 部门表
 * <br />
 * Created by mace on 13:16 2018/6/30.
 */
@NoArgsConstructor
@Data
@Accessors(chain = true) // 链式编程
@JSONType(orders = {
        "id",
        "deptno",
        "dname",
        "db_source"
})
public class Dept implements Serializable {

    private static final long serialVersionUID = -1417417718858613602L;

    @ApiComment(value = "主键", sample = "1")
    private Long        id;

    @ApiComment(value = "部门编号：前八位是成立日期，后五位是编号 部门根节点为 *0001", sample = "2018063010001")
    private Long        deptno;

    @ApiComment(value = "部门名称", sample = "研发部门")
    private String      dname;

    @ApiComment(value = "来自哪个数据库", sample = "cloudDB01")
    private String      db_source;

    public Dept(Long deptno, String dname) {
        super();
        this.deptno = deptno;
        this.dname = dname;
    }
}
