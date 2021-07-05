package proxytest.cglibproxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 功能描述：代理类
 *
 * @author RenShiWei
 * Date: 2020/7/6 14:19
 **/
public class ProxyFactory implements MethodInterceptor {

    //维护一个目标对象
    private Object target;

    //构造器，传入一个被代理的对象
    public ProxyFactory ( Object target ) {
        this.target = target;
    }

    //返回一个代理对象:  是 target 对象的代理对象
    public Object getProxyInstance () {
        //1. 创建一个工具类
        Enhancer enhancer = new Enhancer();
        //2. 设置父类
        enhancer.setSuperclass(target.getClass());
        //3. 设置回调函数
        enhancer.setCallback(this);
        //4. 创建子类对象，即代理对象
        return enhancer.create();
    }

    //重写  intercept 方法，会调用目标对象的方法
    @Override
    public Object intercept ( Object o, Method method, Object[] objects, MethodProxy methodProxy ) throws Throwable {
        System.out.println("---代理商准备材料");
        Object returnVal = method.invoke(target, objects);
        System.out.println("---代理商售卖汽车");
        return returnVal;
    }

}

