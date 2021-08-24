package cn.duktig.api.service;

import cn.duktig.spring.BeanNameAware;
import cn.duktig.spring.BeanPostProcessor;
import cn.duktig.spring.InitializingBean;
import cn.duktig.spring.annotation.Autowired;
import cn.duktig.spring.annotation.Component;

/**
 * description:用户服务
 *
 * @author RenShiWei
 * Date: 2021/8/23 16:20
 **/
@Component("userService")
public class UserServiceImpl implements BeanNameAware, InitializingBean, BeanPostProcessor, IUserService {

    @Autowired
    private OrderService orderService;

    private String name;

    /**
     * 回调 bean的name
     *
     * @param name /
     */
    @Override
    public void setBeanName(String name) {
        this.name = name;
    }

    /**
     * 初始化bean时执行
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("我刚初始化了：" + name);
    }

    /**
     * 初始化前，执行逻辑
     *
     * @param bean     bean对象
     * @param beanName bean名字
     * @return /
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if (bean instanceof UserServiceImpl) {
            System.out.println("初始化前:" + beanName);
//            return Proxy.newProxyInstance(UserServiceImpl.class.getClassLoader(),
//                    bean.getClass().getInterfaces(), (proxy, method, args) -> {
//                        // 找切点
//                        System.out.println("代理逻辑");
//                        return method.invoke(bean, args);
//                    });
        }
        return bean;
    }

    /**
     * 初始化后，执行逻辑
     *
     * @param bean     bean对象
     * @param beanName bean名字
     * @return /
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (bean instanceof UserServiceImpl) {
            System.out.println("初始化后:" + beanName);
        }
        return bean;
    }


    @Override
    public void test() {

    }
}

