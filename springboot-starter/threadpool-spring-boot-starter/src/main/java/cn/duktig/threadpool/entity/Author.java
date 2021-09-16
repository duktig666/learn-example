package cn.duktig.threadpool.entity;

/**
 * description: 作者信息
 *
 * @author RenShiWei
 * Date: 2021/9/15 21:45
 **/
public class Author {

    private String name;

    private Integer age;

    private String email;

    public Author() {
    }

    public Author(String name, Integer age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }
}

