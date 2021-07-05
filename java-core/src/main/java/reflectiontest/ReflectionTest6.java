package reflectiontest;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * 功能描述：读取配置文件，利用反射进行操作
 *
 * @author RenShiWei
 * Date: 2020/6/19 20:29
 **/
public class ReflectionTest6 {

    public static void main ( String[] args ) throws Exception {
        //加载配置文件
        Properties properties = new Properties();
        InputStream is = ReflectionTest6.class.getClassLoader().getResourceAsStream("Producer.properties");
        assert is != null;
        //InputStreamReader将字节流转为字符流（解决乱码问题）
        InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
        properties.load(isr);

        String className = properties.getProperty("className");
        String methodName = properties.getProperty("methodName");
        String argsType = properties.getProperty("argsType");
        String name = properties.getProperty("name");

        //创建运行时类的对象
        Class<?> clazz = Class.forName(className);
        Producer producer = (Producer) clazz.newInstance();

        Method method = clazz.getDeclaredMethod(methodName);
        method.invoke(producer);
        Field field = clazz.getDeclaredField("name");
        field.setAccessible(true);
        field.set(producer, name);
        System.out.println(producer);

        isr.close();
    }


}

