package cn.duktig.spring;

/**
 * description: BeanName回调接口
 *
 * @author RenShiWei
 * Date: 2021/8/23 20:29
 **/
public interface BeanNameAware {

    /**
     * 回调 bean的name
     *
     * @param name /
     */
    void setBeanName(String name);
}
