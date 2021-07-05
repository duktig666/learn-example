package proxytest.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 功能描述：代理对象调用被代理对象方法，所必须实现的接口
 *
 * @author RenShiWei
 * Date: 2020/6/19 17:10
 **/
public class MyInvocationHandler implements InvocationHandler {

    /** 需要被代理的对象 */
    private Object target;

    public void setTarget ( Object target ) {
        this.target = target;
    }

    @Override
    public Object invoke ( Object proxy, Method method, Object[] args ) throws Throwable {
        //调用被代理对象方法前，执行的方法
        ProductUtil.prepareMaterial();

        //调用被代理对象的方法
        Object obj = method.invoke(target, args);

        //调用被代理对象方法后，执行的方法
        ProductUtil.sellProduct();

        //返回被代理对象方法的返回值
        return obj;
    }
}

