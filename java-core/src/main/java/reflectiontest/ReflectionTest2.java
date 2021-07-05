package reflectiontest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 功能描述：测试通过Class对象获取类的信息
 *
 * @author RenShiWei
 * Date: 2020/6/19 10:33
 **/
public class ReflectionTest2 {

    public static void main(String[] args) throws Exception {
        Class<Producer> clazz = Producer.class;
        System.out.println("************测试获取类的信息****************");
        System.out.println("---测试获取Class对象的全部构造器---");
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        for (Constructor<?> c : constructors) {
            System.out.println(c);
        }

        System.out.println("---测试获取Class对象的全部为public的构造器---");
        Constructor<?>[] publicConstructors = clazz.getConstructors();
        for (Constructor<?> c : publicConstructors) {
            System.out.println(c);
        }

        System.out.println("---测试获取全部方法---");
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method m : declaredMethods) {
            System.out.println(m);
        }

        System.out.println("---测试获取指定的方法---");
        Method method = clazz.getMethod("produce", String.class);
        System.out.println(method);

        System.out.println("---测试获取类的全部注解---");
        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation a : annotations) {
            System.out.println(a);
        }

        System.out.println("---测试获取元素的注解---\n" + Arrays.toString(clazz.getAnnotationsByType(MyAnnotation.class)));

        System.out.println("---测试获取全部的内部类---");
        Class<?>[] declaringClass = clazz.getDeclaredClasses();
        for (Class<?> inner : declaringClass) {
            System.out.println(inner);
        }

        System.out.println("---测试使用Class的forName的方法获取内部类---");
        System.out.println(Class.forName("reflectiontest.Producer$Inner"));

        //还可以获取很多信息，暂略.....
    }

}

