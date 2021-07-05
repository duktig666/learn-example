package reflectiontest;

import java.lang.reflect.Array;

/**
 * 功能描述：测试反射操作数组
 *
 * @author RenShiWei
 * Date: 2020/6/19 11:46
 **/
public class ReflectionTest4 {

    public static void main ( String[] args ) {
        System.out.println("*******测试反射操作数组********");
        System.out.println("---操作一维数组---");
        //创建数组
        Object arr = Array.newInstance(String.class, 10);
        //赋值
        Array.set(arr, 2, "测试反射");
        Array.set(arr, 4, "测试反射操作数组");
        //获取指定元素的值
        System.out.println(Array.get(arr, 2));
        System.out.println(Array.get(arr, 4));
        System.out.println(Array.get(arr, 6));

        //三维数组其实是数组元素为二维数组的特殊一维数组
        System.out.println("---操作三维数组---");
        Object arr3 = Array.newInstance(String.class, 3, 4, 10);
        //
        System.out.println("------获取index为2的数组元素，应该是一个二维数组------");
        //获取arr3index为2的数组元素，应该是一个二维数组
        Object arrObj = Array.get(arr3, 2);
        //赋值
        Array.set(arrObj, 2, new String[]{"反射测试1", "反射测试2"});
        //获取arrObj数组的第三个元素，应该是一维数组
        Object anArr = Array.get(arrObj, 3);
        Array.set(anArr, 8, "测试一维反射");
        String[][][] cast = (String[][][]) arr3;
        System.out.println(cast[2][3][8]);
        System.out.println(cast[2][2][0]);
        System.out.println(cast[2][2][1]);
    }

}

