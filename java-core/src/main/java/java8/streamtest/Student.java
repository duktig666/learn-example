package java8.streamtest;

import java.util.Objects;

/**
 * 功能描述：学生类
 *
 * @author RenShiWei
 * Date: 2020/6/21 9:42
 **/
public class Student {

    private String name;
    private int age;
    private Gender gender;

    public Student() {
    }

    public Student(String name, int age, Gender gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student student = (Student) o;
        return age == student.age &&
                Objects.equals(name, student.name) &&
                gender == student.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, gender);
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                '}';
    }

    /** 性别枚举 */
    public enum Gender {
        /** 男 */
        MAN,
        /** 女 */
        WOMAN;
    }

}

