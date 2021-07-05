package proxytest.jdkproxy;

/**
 * 功能描述：客户端调用
 *
 * @author RenShiWei
 * Date: 2020/6/19 17:45
 **/
public class client {

    public static void main ( String[] args ) {
        System.out.println("**方式一：使用MyInvocationHandler**");
        Producer cosmeticProducer = new CosmeticProducer();
        Producer productProxy = (Producer) ProductProxy.getProductProxy(cosmeticProducer);
        String s = productProxy.produce("欧莱雅男士洗面奶");
        System.out.println("被代理的产品：" + s);

        System.out.println("**方式二：使用匿名InvocationHandler接口实现类的方式**");
        ProductProxy proxy = new ProductProxy(new CosmeticProducer());
        Producer productProxy2 = (Producer) proxy.getProductProxy2();
        String s1 = productProxy2.produce("兰蔻洁面乳");
        System.out.println("被代理的产品：" + s1);
    }

}

