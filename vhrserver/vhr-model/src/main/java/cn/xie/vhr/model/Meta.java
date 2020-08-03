package cn.xie.vhr.model;

/**
 * 在前端路由信息中，无法出现的属性可以放置在meta元素中，故在此设置Meta类存放额外的信息
 * menu菜单的扩展
 * @author: xie
 * @create: 2020-04-09 22:41
 **/
public class Meta {
    private Boolean keepAlive;

    private Boolean requireAuth;

    public Boolean getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(Boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public Boolean getRequireAuth() {
        return requireAuth;
    }

    public void setRequireAuth(Boolean requireAuth) {
        this.requireAuth = requireAuth;
    }
}
