package test;

/**
 * Created by XCY on 2017/9/25.
 */
class superclass{
    static {
        System.out.println("superclass init");
    }
    public static int value = 123;
}
class subclass extends superclass{
    static {
        System.out.println("subclass init");
    }
}
class outputrestult{
    public static void main(String[] args){
        System.out.println(subclass.value);
        //这里只会输出superclass init 和123，而不会输出subclass init
        //对于静态字段，只有直接定义这个字段的类才会被初始化，因此通过其子类来引用
        //父类中定义的静态字段，只会触发父类的初始化字段而不会触发子类的初始化
    }
}

class outputrestult1{
    public static void main(String[] args){
        superclass[] acr = new superclass[10];
        //只是建立对象，没有初始化，所以没有输出
    }
}



class ConstClass{
    static {
        System.out.println("ConstClass init");
    }
    public static final String HELLOWORLD = "hello world";
}
class NotInitialzation{
    public static void main(String[] args){
        System.out.println(ConstClass.HELLOWORLD);
        //只是输出 hello world
        //因为虽然在Java源码中引用了ConstClass类中的常量，但是在编译阶段将此常量
        //的值存储到了NotInitialzation类的常量池中，对常量ConstClass.HELLOWORLD
        //的引用实际都被转化为NotInitialzation类对自身常量池的引用
        //也就是说NotInitialzation的class文件中没有ConstClass类的符号引用入口
        //这两个类在编译成class之后就不存在任何联系了
    }
}
class staticTset{
    static class Parent{
        public static int K = 1;
        static {//静态代码块中只能访问到定义在静态代码块之前的变量
            K = 2;
        }
    }
    static class Sub extends Parent{
        public static int B = K;
    }

    public static void main(String[] args){
        System.out.println(Sub.B);//这里输出2
        //<clinit>()方法是由编译器自动收集类中的所有变量的赋值动作和静态代码块中的语句合并而成的
        //编译器收集的顺序就是由语句在源文件中出现的顺序决定的。
        //<clinit>()方法对于类不是必须的，如果一个类中没有静态代码块，
        // 也没有对变量的赋值操作，那么编译器就可以不为这个类生成<clinit>()方法
        //由于父类的<clinit>()方法先执行，
        // 也就意味着父类中定义的静态代码块要优于子类的变量赋值的操作
    }
}


public class JVM_example {
}
