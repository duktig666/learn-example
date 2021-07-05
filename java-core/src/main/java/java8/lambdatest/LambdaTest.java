package java8.lambdatest;


import java8.streamtest.Student;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.*;

/**
 * 功能描述：
 * 1. 测试Lambda表达式
 * - 语法格式
 * - 函数式接口
 *
 * @author RenShiWei
 * Date: 2020/6/20 21:48
 **/
public class LambdaTest {
    //--------------------------语法格式--------------------------

    /**
     * 语法格式一：无参数，无返回值
     */
    @Test
    public void test1() {
        //java8之前的写法
        //jdk 1.7 前，内部类引用外部的局部变量必须是 final
        String str = "java8之前的接口内部实现类写法";
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println(str);
            }
        };
        r.run();

        //java8之后的写法
        String str1 = "测试“无参数，无返回值”的Lambda表达式，实现接口内部实现类写法";
        Runnable r1 = () -> System.out.println(str1);
        r1.run();
    }

    /**
     * 语法格式二：有一个参数，并且无返回值
     * 语法格式三：若只有一个参数，小括号可以省略不写
     */
    @Test
    public void test2() {
        Consumer<String> consumer = (x) -> System.out.println(x);
        Consumer<String> consumer1 = x -> System.out.println(x);
        consumer.accept("测试“有一个参数，并且无返回值”的lambda表达式");
    }

    /**
     * 语法格式四：有两个以上的参数，有返回值，并且 Lambda 体中有多条语句
     */
    @Test
    public void test3() {
        BiFunction<Integer, Integer, Integer> biFunction = (x, y) -> {
            System.out.println("测试“有两个以上的参数，有返回值，并且 Lambda 体中有多条语句”");
            return x + y;
        };
        Integer sum = biFunction.apply(1, 2);
        System.out.println(sum);
    }

    /**
     * 语法格式五：若 Lambda 体中只有一条语句， return 和 大括号都可以省略不写
     * 改写语法格式四的示例
     */
    @Test
    public void test4() {
        BiFunction<Integer, Integer, Integer> biFunction = (x, y) -> x + y;
        Integer sum = biFunction.apply(1, 2);
        System.out.println(sum);
    }

    /**
     * 语法格式六：Lambda 表达式的参数列表的数据类型可以省略不写，因为JVM编译器通过上下文推断出，数据类型，即“类型推断”
     * 改写语法格式二的示例
     */
    @Test
    public void test5() {
        Consumer<String> consumer = (String x) -> System.out.println(x);
        consumer.accept("测试“有一个参数，并且无返回值”的lambda表达式");
    }

    //--------------------------函数式接口--------------------------

    /**
     * 自定义函数式接口
     * 不带泛型
     */
    @FunctionalInterface
    interface MyFunctional {

        /**
         * 处理整数方法
         *
         * @param num 整数
         * @return 处理后的整数
         */
        Integer handleNumber(Integer num);
    }

    /**
     * 自定义函数式接口
     * 带泛型
     */
    @FunctionalInterface
    interface MyFunc<T> {

        /**
         * 处理泛型数据
         *
         * @param t 目标泛型数据
         * @return 处理后的泛型数据
         */
        T handle(T t);
    }

    @Test
    public void test6() {
        MyFunctional myFunctional = x -> x * x;
        Integer n = myFunctional.handleNumber(4);
        System.out.println("测试不带泛型的自定义函数式接口，结果：" + n);

        MyFunc<String> myFunc = x -> {
            if (x.length() > 5) {
                x = x.substring(0, 5);
            }
            return x;
        };
        String s = myFunc.handle("测试带泛型的自定义函数式接口");
        System.out.println(s);
    }

    /**
     * 测试四大内置函数式接口
     */
    @Test
    public void test7() {
        handleStr("aaBb", x -> {
            System.out.println("---测试消费型函数式接口，处理字符串---");
            x = x.toUpperCase();
            System.out.println(x);
        });

        System.out.println("---利用供给型接口：创建指定数量的随机数，存储到List并返回---");
        List<Integer> list = handleStr(5, () -> (int) (Math.random() * 100));
        for (Integer num : list) {
            System.out.println(num);
        }

        System.out.println("---利用函数型接口接口，处理字符串类型数据（一个参数，有返回值）;改写消费型接口的示例---");
        String s = handleStr2("aaBb", x -> x = x.toUpperCase());
        System.out.println(s);

        System.out.println("---利用断言型接口，判断字符串是否符合条件---");
        handleStr3("利用断言型接口，判断字符串是否符合条件", x -> x.length() > 3);
    }

    /**
     * 利用消费型函数式接口，处理字符串类型数据（一个参数，无返回值）
     */
    private void handleStr(String str, Consumer<String> consumer) {
        consumer.accept(str);
    }

    /**
     * 利用供给型接口：创建指定数量的随机数，存储到List并返回
     */
    private List<Integer> handleStr(int num, Supplier<Integer> sup) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Integer n = sup.get();
            list.add(n);
        }
        return list;
    }

    /**
     * 利用函数型接口接口，处理字符串类型数据（一个参数，有返回值）
     * 改写消费型接口的示例
     */
    private String handleStr2(String str, Function<String, String> function) {
        return function.apply(str);
    }

    /**
     * 利用断言型接口，判断字符串是否符合条件
     */
    private void handleStr3(String str, Predicate<String> pre) {
        if (pre.test(str)) {
            System.out.println("符合条件");
        }
    }

    //--------------------测试方法引用、构造器引用、数组引用-------------------------

    @Test
    public void test8() {
        System.out.println("---方法引用  对象的引用 :: 实例方法名---");
        Student student = new Student("小明", 22, Student.Gender.MAN);
        Supplier<String> supplier = () -> student.getName();
        //使用方法引用，可以进行如下改写
        Supplier<String> supplier2 = student::getName;
        String s = supplier.get();
        String s2 = supplier2.get();
        System.out.println(s + " " + s2 + " " + "是否相同：" + s.equals(s2));

        System.out.println("---方法引用  类名 :: 静态方法名---");
        Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
        //改写
        Comparator<Integer> com2 = Integer::compare;
        com2.compare(1, 2);

        System.out.println("---方法引用 类名 :: 实例方法名 ---");
        BiPredicate<String, String> bp = (x, y) -> x.equals(y);
        System.out.println(bp.test("abcde", "abcde"));
        //改写
        BiPredicate<String, String> bp2 = String::equals;
        System.out.println(bp2.test("abc", "abc"));
    }

    @Test
    public void test9() {
        System.out.println("---测试构造器引用---");
        Function<String, String> fun = String::new;
        String s = fun.apply("测试");
        System.out.println(s);

        //使用无参构造器引用，创建对象
        Supplier<Student> student = Student::new;
    }

    @Test
    public void test10() {
        System.out.println("---测试数组引用---");
        Function<Integer, String[]> fun = String[]::new;
        String[] strs = fun.apply(10);
        System.out.println(strs.length);
    }


}

