package cn.xie.vhr.service;

import cn.xie.vhr.mapper.HrMapper;
import cn.xie.vhr.mapper.HrRoleMapper;
import cn.xie.vhr.model.Hr;
import cn.xie.vhr.utils.HrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 实现UserDetailService接口重写接口中的loadUserByUsername方法返回SpringSecurity需要用的UserDetails对象
 *
 * @author: xie
 * @create: 2020-05-20 14:05
 **/
@Service
public class HrService implements UserDetailsService {

    @Autowired
    HrMapper hrMapper;

    @Autowired
    HrRoleMapper hrRoleMapper;

    /*加载用户信息，方法返回SpringSecurity需要用的UserDetails对象*/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Hr hr = hrMapper.loadUserByUsername(username);
        if (hr == null)
            throw new UsernameNotFoundException("用户名不存在！");
        //给用户添加角色信息
        hr.setRoles(hrMapper.getHrRolesById(hr.getId()));
        return hr;
    }

    /*获取除本身之外的所有hr*/
    public List<Hr> getAllHrs(String keywords) {
        //需要获取本身的id
       return hrMapper.getAllHrs(HrUtils.getCurrentHr().getId(), keywords);
    }

    public Integer updateHr(Hr hr) {
        return hrMapper.updateByPrimaryKeySelective(hr);
    }

    @Transactional    //有多步数据库操作，设置事务
    public boolean updateHrRole(Integer hrid, Integer[] rids) {
        //删除用户对应的角色
        hrRoleMapper.deleteByHrid(hrid);
        //重新添加用户对应的角色
        return hrRoleMapper.addRole(hrid, rids) == rids.length;
    }
    public Integer deleteHrById(Integer id) {
        return hrMapper.deleteByPrimaryKey(id);
    }
}
