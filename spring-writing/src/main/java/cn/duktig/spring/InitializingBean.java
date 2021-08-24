package cn.duktig.spring;

/**
 * description:初始化Bean接口——属性设置完后 执行
 *
 * @author RenShiWei
 * Date: 2021/8/23 20:36
 **/
public interface InitializingBean {

    /**
     * 属性设置完后 执行的方法
     */
    void afterPropertiesSet() throws Exception;
}
