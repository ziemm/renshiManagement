package cn.xie.vhr.model;

import java.util.List;

/**
 * 分页显示的model
 * @author: xie
 * @create: 2020-05-01 16:02
 **/
public class RespPageBean {
    private Long total;
    private List<?> data;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
