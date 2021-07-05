package reflectiontest;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 功能描述：测试创建运行时类的对象，并通过此对象调用方法和访问属性
 *
 * @author RenShiWei
 * Date: 2020/6/19 10:33
 **/
public class ReflectionTest3 {

    public static void main ( String[] args ) throws Exception {
        System.out.println("*****测试创建运行时类的对象，并通过此对象调用方法和访问属性*****");
        System.out.println("---测试创建运行时类的对象---");
        //获取Class对象
        Class<Producer> clazz = Producer.class;
        //获取构造器，这里先使用默认的空参构造器（也可以使用指定的构造器）
        Constructor<Producer> constructor = clazz.getConstructor();
        //通过调用构造器的newInstance方法创建对象
        Producer producer = constructor.newInstance();
        System.out.println(producer);
        //可以通过这个对象操作数据
        System.out.println("------使用这个对象操作类的属性和方法------");
        producer.setAge(22);
        producer.showProducts();
        System.out.println(producer);

        System.out.println("---测试通过反射调用方法---");
        Method method = clazz.getDeclaredMethod("showProducts");
        method.setAccessible(true);
        //传入的是创建的运行时类的对象
        method.invoke(producer);

        System.out.println("---测试通过反射访问属性---");
        Field field = clazz.getDeclaredField("name");
        field.setAccessible(true);
        field.set(producer, "我叫反射");
        System.out.println(field.get(producer));
        System.out.println(producer);
    }

}

