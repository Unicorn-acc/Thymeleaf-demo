package com.baizhi.service.impl;

import com.baizhi.entity.Employee;
import com.baizhi.mapper.EmpMapper;
import com.baizhi.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author MiracloW
 */
@Service
@Transactional
public class EmpServiceimpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;

    @Override
    public void delete(Integer id) {
        empMapper.delete(id);
    }

    @Override
    public void update(Employee employee) {
        empMapper.update(employee);
    }

    @Override
    public Employee findById(Integer id) {
        return empMapper.findById(id);
    }

    @Override
    public void save(Employee employee) {
        empMapper.save(employee);
    }

    @Override
    public List<Employee> lists() {
        return empMapper.lists();
    }
}
