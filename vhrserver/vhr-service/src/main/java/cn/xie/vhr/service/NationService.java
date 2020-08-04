package cn.xie.vhr.service;

import cn.xie.vhr.mapper.NationMapper;
import cn.xie.vhr.model.Nation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: xie
 * @create: 2020-07-27 20:00
 **/
@Service
public class NationService {

    @Autowired
    NationMapper nationMapper;

    public List<Nation> getAllNations() {
        return nationMapper.getAllNations();
    }
}
