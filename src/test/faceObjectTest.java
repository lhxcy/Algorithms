package test;

import java.util.Properties;

/**
 * Created by XCY on 2017/8/31.
 */
class Outer1
{
    private int x = 3;
    private class Inner//内部类  可以被私有，私有不能
    {
        void function()
        {
            System.out.println("Inner :" + x);
        }
    }
    void method()
    {
//        System.out.println(x);
        Inner in = new Inner();
        in.function();

    }
}
public class faceObjectTest {
    public static void main(String[] args){
        Properties prop = System.getProperties();
        prop.list(System.out);
    }
}
