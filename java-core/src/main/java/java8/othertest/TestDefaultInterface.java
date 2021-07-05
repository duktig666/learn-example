package java8.othertest;

public class TestDefaultInterface {

    public static void main(String[] args) {
        SubClass sc = new SubClass();
        System.out.println(sc.getName());

        MyInterface.show();
    }

    @MyAnnotation("哈哈")
    @MyAnnotation("嘿嘿")
    public void show() {
        System.out.println("测试重复注解");
    }

}
