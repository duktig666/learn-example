package cn.duktig.spring;

import cn.duktig.spring.annotation.Autowired;
import cn.duktig.spring.annotation.Component;
import cn.duktig.spring.annotation.ComponentScan;
import cn.duktig.spring.annotation.Scope;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * description:自定义ApplicationContext
 *
 * @author RenShiWei
 * Date: 2021/8/23 16:12
 **/
public class ApplicationContext {

    private Class<?> configClass;

    /** 单例池 */
    private ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>();
    /** Bean定义的map k:Component注解上写的 name 值 */
    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    private List<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();

    public ApplicationContext(Class<?> configClass) {
        this.configClass = configClass;
        // 解析配置类
        // ComponentScan注解--->扫描路径--->扫描--->Beandefinition--->BeanDefinitionMap
        scan(configClass);

        //初始化 单例bean
        // TODO:@RenShiWei 2021/8/23 description: spring中也是初始化时直接创建 所有bean 吗？
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            String beanName = entry.getKey();
            BeanDefinition beanDefinition = entry.getValue();
            if ("singleton".equals(beanDefinition.getScope())) {
                // 创建 单例Bean
                Object bean = createBean(beanName, beanDefinition);
                singletonObjects.put(beanName, Objects.requireNonNull(bean));
            }
        }

    }

    private void scan(Class<?> configClass) {
        ComponentScan componentScanAnnotation = configClass.getDeclaredAnnotation(ComponentScan.class);
        //得到扫描的路径
        String path = componentScanAnnotation.value();
        path = path.replace(".", "/");
        //扫描 Bootstrap--->jre/lib Ext--->jre/ext/lib/ App--->classpath
        ClassLoader classLoader = ApplicationContext.class.getClassLoader();
        URL resource = classLoader.getResource(path);
        File file = new File(Objects.requireNonNull(resource).getFile());

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : Objects.requireNonNull(files)) {
                //结构： D:\IDE\……\target\classes\cn\duktig\api\service\UserService.class
                String fileName = f.getAbsolutePath();

                // 截取结果：cn.duktig.api.service.UserService
                // modify @RenShiWei 2021/8/23 description:（这里的截取不够通用！！！！！）
                String className = fileName.substring(fileName.indexOf("cn"), fileName.indexOf(".class"));
                className = className.replace("\\", ".");

                try {
                    Class<?> clazz = classLoader.loadClass(className);
                    if (clazz.isAnnotationPresent(Component.class)) {

                        if (BeanPostProcessor.class.isAssignableFrom(clazz)) {
                            // 如果实现 BeanPostProcessor 接口 ，创建对象，并添加集合中
                            // TODO:@RenShiWei 2021/8/23 description:Spring中是获得bean，而不是创建
                            BeanPostProcessor instance =
                                    (BeanPostProcessor) clazz.getDeclaredConstructor().newInstance();
                            beanPostProcessorList.add(instance);
                        }

                        // 表示当前这个类是一个Bean  解析类--->BeanDefinition
                        Component componentAnnotation = clazz.getDeclaredAnnotation(Component.class);
                        String beanName = componentAnnotation.value();
                        BeanDefinition beanDefinition = new BeanDefinition();
                        beanDefinition.setClazz(clazz);
                        if (clazz.isAnnotationPresent(Scope.class)) {
                            Scope scopeAnnotation = clazz.getDeclaredAnnotation(Scope.class);
                            beanDefinition.setScope(scopeAnnotation.value());
                        } else {
                            //如果没有这个注解，代表默认为单例（也可以在注解中给默认值）
                            beanDefinition.setScope("singleton");
                        }
                        // 存储对象的定义
                        beanDefinitionMap.put(beanName, beanDefinition);
                    }

                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     * 获取bean对象
     *
     * @param beanName bean的name
     * @return bean对象
     */
    public Object getBean(String beanName) {
        if (beanDefinitionMap.containsKey(beanName)) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if ("singleton".equals(beanDefinition.getScope())) {
                // 单例模式，从单例池中获取
                return singletonObjects.get(beanName);
            } else {
                // 否则直接创建
                return createBean(beanName, beanDefinition);
            }
        } else {
            // 不存在对应的Bean
            throw new NullPointerException();
        }
    }

    /**
     * 创建 bean对象
     *
     * @param beanName       /
     * @param beanDefinition 对象定义
     * @return bean对象
     */
    private Object createBean(String beanName, BeanDefinition beanDefinition) {
        Class<?> clazz = beanDefinition.getClazz();
        try {
            //创建对象
            Object instance = clazz.getDeclaredConstructor().newInstance();

            //依赖注入
            for (Field declaredField : clazz.getDeclaredFields()) {
                if (declaredField.isAnnotationPresent(Autowired.class)) {
                    Object bean = getBean(declaredField.getName());
                    declaredField.setAccessible(true);
                    declaredField.set(instance, bean);
                }
            }

            // Aware回调
            if (instance instanceof BeanNameAware) {
                ((BeanNameAware) instance).setBeanName(beanName);
            }

            //初始化前执行  （instance不一定是原来的对象）
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                instance = beanPostProcessor.postProcessBeforeInitialization(instance, beanName);
            }

            // 初始化
            if (instance instanceof InitializingBean) {
                try {
                    ((InitializingBean) instance).afterPropertiesSet();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // 初始化后执行
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                instance = beanPostProcessor.postProcessAfterInitialization(instance, beanName);
            }

            return instance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }


}

