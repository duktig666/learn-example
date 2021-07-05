package proxytest.jdkproxy;

/**
 * 功能描述：化妆品生产者（被代理对象）
 *
 * @author RenShiWei
 * Date: 2020/6/19 16:15
 **/
public class CosmeticProducer implements Producer {

    /**
     * 生产化妆品
     */
    @Override
    public String produce ( String product ) {
        System.out.println("正在生产化妆品" + product);
        return product;
    }

}

