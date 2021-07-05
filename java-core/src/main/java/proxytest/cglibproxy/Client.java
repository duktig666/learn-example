package proxytest.cglibproxy;

/**
 * 功能描述：客户端调用
 *
 * @author RenShiWei
 * Date: 2020/7/6 14:23
 **/
public class Client {

    public static void main(String[] args) {
        //获取代理对象，并强转成被代理对象的数据类型
        proxytest.cglibproxy.CarFactory proxyInstance =
                (proxytest.cglibproxy.CarFactory) new ProxyFactory(new proxytest.cglibproxy.CarFactory()).getProxyInstance();
        //执行代理对象的方法，触发intecept 方法，从而实现 对目标对象的调用
        proxyInstance.productCar();
    }

}

