package cn.duktig.spring;

/**
 * description:Bean 扩展机制
 * 初始化前/后，执行逻辑
 *
 * @author RenShiWei
 * Date: 2021/8/23 20:40
 **/
public interface BeanPostProcessor {

    /**
     * 初始化前，执行逻辑
     *
     * @param bean     bean对象
     * @param beanName bean名字
     * @return /
     */
    default Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    /**
     * 初始化后，执行逻辑
     *
     * @param bean     bean对象
     * @param beanName bean名字
     * @return /
     */
    default Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }

}

