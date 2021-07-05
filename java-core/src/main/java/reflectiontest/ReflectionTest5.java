package reflectiontest;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 功能描述：测试反射获取泛型信息
 *
 * @author RenShiWei
 * Date: 2020/6/19 15:28
 **/
public class ReflectionTest5 {

    public static void main ( String[] args ) throws Exception {
        System.out.println("*******测试反射获取泛型*******");
        System.out.println("---不使用泛型的情况---");
        Person producer = (Producer) getInstance(Producer.class);

        System.out.println("---使用泛型的情况---");
        Person producer2 = getInstance2(Producer.class);

        //false,因为一个是Object类型，一个是Producer类型
        System.out.println(producer == producer2);

        System.out.println("***使用反射获取泛型信息****");
        Class<Producer> clazz = Producer.class;
        Field f = clazz.getDeclaredField("productMap");
        Class<?> a = f.getType();
        System.out.println("获取参数类型" + a);
        Type genericType = f.getGenericType();
        System.out.println("获取泛型参数类型" + genericType);
        if (genericType instanceof ParameterizedType) {
            //将Type强转为ParameterizedType
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            //获取原始类型
            System.out.println("原始类型：" + parameterizedType.getRawType());
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            for (Type t : actualTypeArguments) {
                System.out.print(t + " ");
            }
        }
    }

    /**
     * 不使用泛型的情况
     */
    public static Object getInstance ( Class clazz ) throws Exception {
        return clazz.getConstructor().newInstance();
    }

    /**
     * 使用泛型的情况
     */
    public static <T> T getInstance2 ( Class<T> clazz ) throws Exception {
        return clazz.getConstructor().newInstance();
    }
}

