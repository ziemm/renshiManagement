package cn.xie.vhr.service;

import cn.xie.vhr.mapper.EmployeeMapper;
import cn.xie.vhr.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author: xie
 * @create: 2020-07-27 17:21
 **/
@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
    DecimalFormat decimalFormat = new DecimalFormat("##.00");

    public RespPageBean getEmployeeByPage(Integer page, Integer size, Employee employee, Date[] beginDateScope) {
        if(page!=null&&size!=null){
            page=(page-1)*size; //数据库中的页码是从0开始，mysql的查询limt([数据起始位置]，[长度])
        }
        List<Employee> data = employeeMapper.getEmployeeByPage(page, size, employee, beginDateScope);
        Long total = employeeMapper.getTotal(employee,beginDateScope);
        RespPageBean bean = new RespPageBean();

        bean.setData(data);
        bean.setTotal(total);
        return bean;
    }

    public Integer addEmp(Employee employee) {

        //自动计算合同起止日期
        Date beginContract = employee.getBeginContract();
        Date endContract =  employee.getEndContract();
        //合同持续月份数
        Double month = (Double.parseDouble(yearFormat.format(endContract))-Double.parseDouble(yearFormat.format(beginContract)))*12+
                (Double.parseDouble(monthFormat.format(endContract))-Double.parseDouble(monthFormat.format(beginContract)));
        employee.setContractTerm(Double.parseDouble(decimalFormat.format(month/12)));
        return employeeMapper.insertSelective(employee);
    }

    public Integer maxWorkID() {
        return employeeMapper.maxWorkID();
    }

    public Integer updateEmp(Employee employee) {
        return employeeMapper.updateByPrimaryKeySelective(employee);
    }

    public Integer deleteEmpByEid(Integer id) {
        return employeeMapper.deleteByPrimaryKey(id);
    }

    public Integer addEmps(List<Employee> list){
        return employeeMapper.addEmps(list);
    }
}
