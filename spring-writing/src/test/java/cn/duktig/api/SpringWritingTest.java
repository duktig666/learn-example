package cn.duktig.api;

import cn.duktig.api.config.AppConfig;
import cn.duktig.api.service.UserServiceImpl;
import cn.duktig.spring.ApplicationContext;

/**
 * description:测试手写的Spring
 *
 * @author RenShiWei
 * Date: 2021/8/23 16:26
 **/
public class SpringWritingTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ApplicationContext(AppConfig.class);
        UserServiceImpl userServiceImpl = (UserServiceImpl) applicationContext.getBean("userService");
        UserServiceImpl userServiceImpl2 = (UserServiceImpl) applicationContext.getBean("userService");
        System.out.println(userServiceImpl);
        System.out.println(userServiceImpl.equals(userServiceImpl2));

        userServiceImpl.test();
    }


}

