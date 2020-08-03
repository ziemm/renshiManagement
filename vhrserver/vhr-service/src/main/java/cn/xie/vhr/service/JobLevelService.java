package cn.xie.vhr.service;

import cn.xie.vhr.mapper.JobLevelMapper;
import cn.xie.vhr.model.Joblevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author: xie
 * @create: 2020-06-10 15:52
 **/
@Service
public class JobLevelService {
    @Autowired
    JobLevelMapper jobLevelMapper;

    public List<Joblevel> getAllJobLevels() {
        return jobLevelMapper.getAllJobLevels();
    }

    public Integer addJobLevel(Joblevel joblevel) {
        joblevel.setCreateDate(new Date());
        joblevel.setEnabled(true);
        return jobLevelMapper.insertSelective(joblevel);
    }

    public Integer updateJobLevelById(Joblevel joblevel) {
        return jobLevelMapper.updateByPrimaryKeySelective(joblevel);
    }

    public Integer deleteJobLevelById(Integer id){
        return jobLevelMapper.deleteByPrimaryKey(id);
    }

    public Integer deleteJobLevelsByIds(Integer[] ids) {
        return jobLevelMapper.deleteJobLevelsByIds(ids);
    }
}
