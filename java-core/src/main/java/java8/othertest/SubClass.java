package java8.othertest;

public class SubClass extends MyClass implements MyFun, MyInterface {

    @Override
    public String getName() {
        return MyInterface.super.getName();
    }

}
