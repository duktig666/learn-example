package java8.othertest;

import org.junit.Test;

import java.lang.reflect.Method;

/**
 * 功能描述：
 *
 * @author RenShiWei
 * Date: 2020/6/23 21:49
 **/
public class MyAnnationTest {

    @MyAnnotation("哈哈")
    @MyAnnotation("嘿嘿")
    public void show() {
        System.out.println("测试重复注解");
    }

    @Test
    public void test() throws Exception {
        Class<MyAnnationTest> clazz = MyAnnationTest.class;
        Method method = clazz.getDeclaredMethod("show");
        MyAnnotation[] annotations = method.getAnnotationsByType(MyAnnotation.class);
        System.out.println("---获取注解信息---");
        for (MyAnnotation annotation : annotations) {
            System.out.println(annotation.value());
        }
    }

}

