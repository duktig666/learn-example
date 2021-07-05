package proxytest.jdkproxy;

import java.lang.reflect.Proxy;

/**
 * 功能描述：产品代理商（代理对象）
 * <p>
 * 采用自定义的外部类MyInvocationHandler来处理
 *
 * @author RenShiWei
 * Date: 2020/6/19 16:46
 **/
public class ProductProxy {

    //***方式一：使用自定义的外部类MyInvocationHandler实现InvocationHandler接口处理***

    public ProductProxy() {}

    //通过代理对象，调用被代理对象的方法
    public static Object getProductProxy(Object target) {
        proxytest.jdkproxy.MyInvocationHandler handler = new proxytest.jdkproxy.MyInvocationHandler();
        handler.setTarget(target);
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), handler);
    }

    //***方式二：使用匿名实现类来处理（可以减少类的数量）

    private Object target;

    public ProductProxy(Object target) {
        this.target = target;
    }

    //通过代理对象，调用被代理对象的方法
    public Object getProductProxy2() {
        proxytest.jdkproxy.MyInvocationHandler handler = new proxytest.jdkproxy.MyInvocationHandler();
        handler.setTarget(target);
        //这里的InvocationHandler的实现类，也可以采用接口的匿名实现类来处理，减少类的数量
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), (proxy,
                                                                                                              method
                , args) -> {
            //调用被代理对象方法前，执行的方法
            ProductUtil.prepareMaterial();

            //调用被代理对象的方法
            Object obj = method.invoke(target, args);

            //调用被代理对象方法后，执行的方法
            ProductUtil.sellProduct();

            //返回被代理对象方法的返回值
            return obj;
        });
    }
}

