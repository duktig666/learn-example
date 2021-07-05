package reflectiontest;

/**
 * 功能描述：测试反射的应用
 * 1. 测试获取Class的对象
 *
 * @author RenShiWei
 * Date: 2020/6/19 9:13
 **/
public class ReflectionTest {

    public static void main(String[] args) {
        System.out.println("**************测试获取Class的对象******************");
        System.out.println("----方式一：调用类的class属性获取----");
        Class<Producer> clazz1 = Producer.class;
        System.out.println(clazz1);

        System.out.println("---方式二：通过Class类的静态方法forName()获取----");
        Class<?> clazz2 = null;
        try {
            clazz2 = Class.forName("reflectiontest.Producer");
            System.out.println(clazz2);
        } catch (ClassNotFoundException e) {
            System.out.println("没有通过全限类名找到此类，不能使用反射获取Class对象");
            e.printStackTrace();
        }

        System.out.println("---方式三：调用对象的getClass()方法（此方法为java.lang.Object的方法）获取---");
        Producer producer = new Producer();
        Class<? extends Producer> clazz3 = producer.getClass();
        System.out.println(clazz3);

        System.out.println("---方式四：通过类加载器ClassLoader获取---");
        ClassLoader classLoader = ReflectionTest.class.getClassLoader();
        Class<?> clazz4 = null;
        try {
            clazz4 = classLoader.loadClass("reflectiontest.Producer");
            System.out.println(clazz4);
        } catch (ClassNotFoundException e) {
            System.out.println("没有通过全限类名找到此类，不能使用反射获取Class对象");
            e.printStackTrace();
        }
        System.out.println("对比四种方式获取的Class对象是否是同一个");
        System.out.println(clazz1 == clazz2);
        System.out.println(clazz1 == clazz3);
        System.out.println(clazz1 == clazz4);
    }


}


