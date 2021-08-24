package cn.duktig.spring;

/**
 * description:自定义Spring创建的 Bean对象的定义
 *
 * @author RenShiWei
 * Date: 2021/8/23 17:17
 **/
public class BeanDefinition {

    private Class<?> clazz;

    private String scope;

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

}

