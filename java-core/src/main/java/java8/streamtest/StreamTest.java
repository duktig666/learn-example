package java8.streamtest;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 功能描述：测试Stream的使用
 *
 * @author RenShiWei
 * Date: 2020/6/21 15:03
 **/
public class StreamTest {

    //---测试创建Stream---

    @Test
    public void test1() {
        //1. Collection 提供了两个方法  stream() 与 parallelStream()
        List<String> list = new ArrayList<>();
        //获取一个顺序流
        Stream<String> stream = list.stream();
        //获取一个并行流
        Stream<String> parallelStream = list.parallelStream();

        //2. 通过 Arrays 中的 stream() 获取一个数组流
        Integer[] nums = new Integer[10];
        Stream<Integer> stream1 = Arrays.stream(nums);

        //3. 通过 Stream 类中静态方法 of()
        Stream<Integer> stream2 = Stream.of(1, 2, 3, 4, 5, 6);

        //4. 创建无限流
        //迭代
        Stream<Integer> stream3 = Stream.iterate(0, (x) -> x + 2).limit(10);
        stream3.forEach(System.out::println);

        //生成
        Stream<Double> stream4 = Stream.generate(Math::random).limit(2);
        stream4.forEach(System.out::println);
    }

    //---测试Stream中间操作---

    List<Student> studentList = Arrays.asList(
            new Student("李四", 59, Student.Gender.MAN),
            new Student("张三", 18, Student.Gender.MAN),
            new Student("王五", 28, Student.Gender.MAN),
            new Student("赵小苗", 8, Student.Gender.WOMAN),
            new Student("赵小苗", 8, Student.Gender.WOMAN),
            new Student("李芳", 8, Student.Gender.WOMAN)
    );


    @Test
    public void test2() {
        System.out.println("---测试filter，过滤信息---");
        Stream<Student> studentStream = studentList.stream()
                .filter(s -> s.getAge() > 20);
        studentStream.forEach(System.out::println);

        System.out.println("---测试limit,取前1个元素---");
        studentList.stream()
                .filter((e) -> e.getAge() >= 20)
                .limit(1)
                .forEach(System.out::println);

        System.out.println("---测试skip，跳过1个元素---");
        studentList.stream()
                .filter((e) -> e.getAge() >= 20)
                .skip(1)
                .forEach(System.out::println);

        System.out.println("---测试distinct，去重---");
        studentList.stream()
                .distinct()
                .forEach(System.out::println);
    }

    @Test
    public void test3() {
        Stream<String> stringStream = studentList.stream()
                .map(Student::getName);
        stringStream.forEach(System.out::println);

        //map与flatMap的区别    与list的add、addAll方法区别类似
        List<String> strList = Arrays.asList("aaa", "bbb", "ccc", "ddd", "eee");

        Stream<String> stream = strList.stream()
                .map(String::toUpperCase);

        stream.forEach(System.out::println);

        Stream<Stream<Character>> stream2 = strList.stream()
                .map(StreamTest::filterCharacter);

        stream2.forEach((sm) -> {
            sm.forEach(System.out::println);
        });

        System.out.println("---------------------------------------------");
        Stream<Character> stream3 = strList.stream()
                .flatMap(StreamTest::filterCharacter);

        stream3.forEach(System.out::println);
    }

    private static Stream<Character> filterCharacter(String str) {
        List<Character> list = new ArrayList<>();

        for (Character ch : str.toCharArray()) {
            list.add(ch);
        }

        return list.stream();
    }

    @Test
    public void test4() {
        System.out.println("---自然排序---");
        studentList.stream()
                .map(Student::getName)
                .sorted()
                .forEach(System.out::println);

        System.out.println("---定制排序---");
        studentList.stream()
                .sorted((x, y) -> {
                    if (x.getAge() == y.getAge()) {
                        return x.getName().compareTo(y.getName());
                    } else {
                        return Integer.compare(x.getAge(), y.getAge());
                    }
                })
                .forEach(System.out::println);
    }

    @Test
    public void test5() {
        //测试查看和匹配
        boolean b1 = studentList.stream()
                .allMatch(e -> e.getGender().equals(Student.Gender.WOMAN));
        System.out.println("是否有女学生：" + b1);

        Optional<Student> first = studentList.stream()
                .sorted((x, y) -> {
                    return x.getAge() - y.getAge();
                })
                .findFirst();
        System.out.println("得到年龄最小的学生：\n" + first.get());

        long count = studentList.stream()
                .filter(e -> e.getGender().equals(Student.Gender.WOMAN))
                .count();
        System.out.println("女生数量：" + count);
    }

    @Test
    public void test6() {
        //测试规约
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Integer sum = list.stream()
                .reduce(0, (x, y) -> x + y);

        System.out.println(sum);

        System.out.println("----------------------------------------");

        Optional<Integer> op = studentList.stream()
                .map(Student::getAge)
                .reduce(Integer::sum);

        System.out.println("所有学生年龄之和" + op.get());
    }

    @Test
    public void test7() {
        //测试收集collect
        List<String> list = studentList.stream()
                .map(Student::getName)
                .collect(Collectors.toList());
        System.out.println("学生姓名转集合：" + list);

        Set<String> set = studentList.stream()
                .map(Student::getName)
                .collect(Collectors.toSet());
        System.out.println("学生姓名转Set，去重：" + set);

        //求最小的学生年龄
        Optional<Integer> minAge = studentList.stream()
                .map(Student::getAge)
                .collect(Collectors.minBy(Integer::compare));
        System.out.println("求最小的学生年龄：" + minAge.get());

        System.out.println("---根据性别分组---");
        Map<Student.Gender, List<Student>> map = studentList.stream()
                .collect(Collectors.groupingBy(Student::getGender));
        System.out.println(map);

        System.out.println("---根据年龄和性别多级分组---");
        Map<Student.Gender, Map<String, List<Student>>> map2 = studentList.stream()
                .collect(Collectors.groupingBy(Student::getGender, Collectors.groupingBy(e -> {
                    if (e.getAge() >= 60) {
                        return "老年";
                    } else if (e.getAge() >= 35) {
                        return "中年";
                    } else {
                        return "成年";
                    }
                })));
        System.out.println(map2);

        System.out.println("---根据年龄分区---");
        Map<Boolean, List<Student>> map3 = studentList.stream()
                .collect(Collectors.partitioningBy((e) -> e.getAge() >= 30));
        System.out.println(map3);

        System.out.println("连接流的字符串");
        String str = studentList.stream()
                .map(Student::getName)
                .collect(Collectors.joining(",", "----", "----"));
        System.out.println(str);

        System.out.println("规约收集，求所有学生年龄的和");
        Optional<Integer> sum = studentList.stream()
                .map(Student::getAge)
                .collect(Collectors.reducing(Integer::sum));
        System.out.println(sum.get());
    }

}

