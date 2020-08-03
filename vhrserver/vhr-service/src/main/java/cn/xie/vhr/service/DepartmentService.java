package cn.xie.vhr.service;

import cn.xie.vhr.mapper.DepartmentMapper;
import cn.xie.vhr.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: xie
 * @create: 2020-06-19 14:22
 **/
@Service
public class DepartmentService {

    @Autowired
    DepartmentMapper departmentMapper;

    public List<Department> getAllDepartments() {
        return  departmentMapper.getAllDepartmentsByParentId(-1);
    }

    public void addDep(Department dep) {
        departmentMapper.addDep(dep);
    }

    public void deleteDepById(Department dep) {
        departmentMapper.deleteDepById(dep);
    }
}
