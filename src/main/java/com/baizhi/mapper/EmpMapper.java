package com.baizhi.mapper;

import com.baizhi.entity.Employee;

import java.util.List;

/**
 * @author MiracloW
 */
public interface EmpMapper {
    //员工列表
    List<Employee> lists();

    //保存员工信息
    void save(Employee employee);

    //根据id查询一个
    Employee findById(Integer id);

    //更新员工信息
    void update(Employee employee);

    //删除员工信息
    void delete(Integer id);
}
