package cn.xie.vhr.model;

/**
 * @author: xie
 * @create: 2020-05-20 20:46
 **/
public class RespBean {
    private Integer status;
    private String msg;
    private Object obj;

    //建造者模式,需要更改set方法的写法
    public static RespBean build(){
        return new RespBean();
    }

    public RespBean(){}

    public RespBean(Integer status, String msg, Object obj) {
        this.status=status;
        this.msg=msg;
        this.obj=obj;
    }

    public static RespBean ok(String msg){
        return new RespBean(200,msg,null);
    }
    public static RespBean ok(String msg,Object obj){
        return new RespBean(200,msg,obj);
    }

    public static RespBean error(String msg){
        return new RespBean(500,msg,null);
    }
    public static RespBean error(String msg,Object obj){
        return new RespBean(500,msg,obj);
    }

    public Integer getStatus() {
        return status;
    }

    public RespBean setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public RespBean setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getObj() {
        return obj;
    }

    public RespBean setObj(Object obj) {
        this.obj = obj;
        return this;
    }
}
