package reflectiontest;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述：测试反射的JavaBean
 * 生产商类
 *
 * @author RenShiWei
 * Date: 2020/6/19 9:16
 **/
@MyAnnotation("反射获取类注解信息")
public class Producer implements Person {

    @MyAnnotation("生产商姓名")
    private String name;

    int age;

    public int id;

    //生产的商品map
    public Map<String, String> productMap = new HashMap<>();

    public Producer () {
    }

    private Producer ( String name, int age, int id ) {
        this.name = name;
        this.age = age;
        this.id = id;
    }

    //get和set方法
    public String getName () {
        return name;
    }

    public void setName ( String name ) {
        this.name = name;
    }

    public int getAge () {
        return age;
    }

    public void setAge ( int age ) {
        this.age = age;
    }

    public int getId () {
        return id;
    }

    public void setId ( int id ) {
        this.id = id;
    }

    //生产产品
    @MyAnnotation
    public String produce ( String product ) {
        return "正在生产产品：" + product;
    }

    //查看产品
    void showProducts () {
        System.out.println("正在查看产品");
    }

    //实现接口的方法
    @Override
    public void eat ( String food ) {
        System.out.println("正在吃：" + food);
    }

    //内部类
    class Inner {}

    @Override
    public String toString () {
        return "Producer{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", id=" + id +
                '}';
    }
}

