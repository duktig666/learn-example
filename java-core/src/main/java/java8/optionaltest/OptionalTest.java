package java8.optionaltest;

import java8.streamtest.Student;
import org.junit.Test;

import java.util.Optional;

/**
 * 功能描述：测试Optional类的使用
 *
 * @author RenShiWei
 * Date: 2020/6/21 16:30
 **/
public class OptionalTest {

    @Test
    public void test1() {
        //测试创建Optional
        Optional<Student> op = Optional.of(new Student("张三", 18, Student.Gender.MAN));

        Optional<String> s = op.map(Student::getName);
        String name = s.get();
        System.out.println(name);

        Optional<Student> op1 = Optional.ofNullable(null);
        if (op1.isPresent()) {
            System.out.println(op1.get());
        } else {
            System.out.println("此对象为空");
        }
        System.out.println(op1.orElse(new Student("默认", 18, Student.Gender.MAN)));

    }

}

