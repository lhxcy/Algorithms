package test;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 * Created by XCY on 2017/8/26.
 *
 *类：对现实生活中事物的描述
 *对象：就是这类事物实实在在存在的个体
 * 封装：将变化隔离，便于使用，提高重用性，提高安全性
 * 私有仅仅是一种表现形式，封装不仅仅是私有
 * 构造函数：与类名一致，没有返回类型，不可以写return,    可以私有化
 * 系统会默认一个构造函数，如果没有定义的话。若定义了，则不存在默认的构造函数
 *  对象一调用就会调用与之对应的构造函数
 *  作用：可以给对象初始化
 *  构造函数和一般函数写法上不同，在运行上也不同，构造函数是在对象一建立就执行‘
 *  而一般函数必须被调用才执行，一个对象构造函数只调用一次
 *默认构造函数的权限和所属类权限一样
 *
 * 构造代码块：定义不同对象的共性初始化内容（可以使用this）
 *  作用：给对象初始化
 *  对象一建立就运行，而且鱿鱼构造函数执行
 *  和构造函数区别
 *      构造代码块给所有对象进行统一初始化
 *      而构造函数是给对应对象初始化
 *
 *  static : 一个修饰符，用于修饰成员（成员变量，成员函数）
 *  当成员被静态修饰后，就多了一个调用方式,除了可以被对象调用外，还可以直接被类名调用。类名.静态成员
 *  static 特点：1、随着类的加载而加载（也就是说静态会随着累的消失而消失，说明它的生命周期最长），
 *              2、优先于对象存在，
 *              3、被所有对象共享，
 *              4、可以直接被类名调用
 *
 *  类变量和实例变量区别：
 *      1：存放位置
 *          类变量随着类的加载而存在与方法中（静态变量）
 *          实例变量随着对象的建立而存在于堆内存中
 *      2：生命周期
 *          类变量生命周期最长，随着累类的消失而消失
 *          实例变量的生命周期随着对象的消失而消失
 *
 *  静态变量使用注意事项：
 *      1：静态方法只能访问静态成员
 *          非静态方法既可以访问静态也可以访问非静态
 *      2：静态方法中不可以定义this，super关键字
 *          因为静态优先于对象存在，所以静态中不可以出现this
 *
 *  静态：
 *      利：对对象的共享数据进行单独空间存储，节省空间
 *          可以被类名直接调用
 *      弊：生命周期过长
 *          访问出现局限性（静态虽好，只能访问静态）
 *
 *  主函数：
 *      public ：代表该函数访问权限是最大的
 *      static： 代表主函数随着类的加载就已经存在
 *      void: 主函数没有返回值
 *      main：不是关键字，但是是一个特殊的单词，可以被jvm识别
 *      (String[] args)： 函数参数，参数类型是一个数组，该数组中的元素是字符串，字符串类型的数组
 *
 *      主函数是固定格式的：jvm识别
 *
 *  什么时候定义静态函数：
 *      当功能内部没有访问到非静态数据（对象特有的数据），该函数可以定义成静态
 *
 *  想让一个类不能建立对象，可以将构造函数私有化
 *
 *  静态代码块
 *      随着类的加载而执行，只执行一次,并优先于主函数
 *      用于给类进行初始化
 *
 *  先静态代码块 -- 构造代码块（可以使用this）  -- 构造函数
 *
 *  先默认初始化  --  构造代码块  --  构造函数
 *
 *   staticCode s = new staticCode();
 *      1：先加载staticCode.class到内存
 *      2：执行该类的static代码块，如果有的话，给staticCode.class类进行初始化
 *      3：在堆内存开辟空间，分配内存地址
 *      4：在堆内存中建立对象的所有属性，并进行默认初始化
 *      5：对属性进行显示初始化
 *      6：对对象进行构造代码块初始化
 *      7：对对象进行构造函数初始化
 *      8：将内存地址赋值给栈内存中的s变量
 *
 *
 *  设计模式：解决某一类问题最行之有效的方法
 *            Java一共23种设计模式
 *      单例设计模式：解决一个类在内存只存在一个对象
 *      保证对象唯一：
 *          1：禁止其他程序建立对象
 *          2：在本类中自定义一个对象，以便于让外部程序访问
 *          3：定义访问一些访问方式
 *
 *      public static synchronized single getInstance（）{}
 *
 *  继承：is a
 *      1：提高代码复用性
 *      2：让类与类产生了关系，有了这个关系，才有了堕胎的特性
 *   Java只支持单继承，不支持多继承，
 *   因为多继承容易带来安全隐患：但多个父类中定义了相同功能，当功能内容不同时，子类对象不确定要运行哪一个
 *   但是Java保留了这种机制，并用另一种体现形式来完成表示， 多实现
 *    class A
 *    {
 *        void show()
 *        {
 *            System.out.println("a");
 *        }
 *    }
 *    class B
 *    {
 *        void show()
 *        {
 *            System.out.println("a");
 *        }
 *    }
 *    class C extends A,B
 *    {}
 *    C c = new C();
 *    c.show();//不知道打印哪一个
 *
 *Java支持多层继承 也就是一个继承体系
 *   如何使用一个继承体系中的功能：先查询体系父类功能，因为父类中定义的是该体系中共性功能
 *          那么这个体系已经可以基本使用了
 *          那么在具体调用时，要创建最子类对象：1：因为有可能父类不能创建对象
 *                                        2：创建子类对象可以使用更多的功能，包括基本的也包括特有的
 *    查阅父类功能，创建子类对象使用功能
 *
 *  聚集：has a 按照紧密程度分为聚合，组合
 *  聚合：
 *  组合：
 *
 *  加载子类之前先加载父类
 *
 *
 */

/**注释模板
 * 文档注释：@author  @version v1.1
 * 获取。。。
 * @param
 *@return
 */
/*
子父类出现后，类成员的特点
类中成员
1：变量 如果子类中出现非私有的同名成员变量时，子类要想访问本类中的变量，用this。访问父类中的同名变量，用super
2：函数
3：构造函数
 */
class fu
{
    int num1 = 4;
    int num = 4;
}
class zi extends fu
{
    int num2 = 4;
    int num = 5;
    void show()
    {
        System.out.println(num);//打印子类   this 打印子类
        System.out.println(super.num);//打印父类num  super 打印父类 父类num定义为private后不能访问
    }
}
class zi2 extends fu
{
    void show()
    {
        System.out.println(this.num);//打印4 ,因为子类相当于有父类num22
        System.out.println(super.num);//打印4
    }
}

//zi z = new zi();
//System.out.println(z.num + z.num)//打印都是子类num
//System.out.println(z.num1 + z.num2)//打印fu，zi

/*
将学生和工人的共性描述提取出来，单独进行描述
只要让学生和工人与单独描述的这个类有关系，就可以了
*/
class person
{
    String name;
    int age;
}
class Student extends person
{
    void study()
    {
        System.out.println("good study");
    }
}

class Worker extends person
{
    void work()
    {
        System.out.println("work");
    }
}

class single
{//想表示对象唯一，加上这三步就成
    private single(){}//建议使用这种方式，即饿汉式
    private static single s = new single();
    public static single getInstance()
    {
        return s;
    }

//    private static single s = null;//懒汉式
//    public static  single getInstance(){
//        if (s == null)
//        {
//            synchronized (single.class)//加上同步锁
//            {
//                if (s == null)
//                {
//                    return new single();
//                }
//            }
//        }
//        return s;
//    }

}

class staticCode
{
    int num = 9;
    static String str = "d";
    static//静态代码块
    {
        System.out.println("a");
    }
    {//构造代码块
        System.out.println("b" +"...."+ this.num);
    }
}


class Person
{

    {//构造代码块
//        System.out.println("person code run");
        cry();
    }
    private int age;
    private String name;
    Person()
    {
        System.out.println("name = " + name + ",,age = " + age);
//        cry();
    }
    Person(int age)
    {
        this.age = age;
    }
    Person(String name)
    {
        this.name = name;
        System.out.println("name = " + name + ",,age = " + age);
//        cry();
    }
    Person(String name, int age)
    {
        this(name);//this 用于构造函数之间互相调用  p（name），
        // 构造函数间调用只能用this语句，this语句只能放在构造函数第一行
//        this.name = name;
        this.age = age;
        System.out.println("name = " + name + ",,age = " + age);
//        cry();
    }
    public void cry()
    {
        System.out.println("cry...");
    }
    public void setAge(int a)
    {
        if (a > 0 && a < 130)
        {
           this. age = a;
            speak();
        }
        else
            System.out.println("error age");
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName()
    {
        System.out.println("name = "+ name);
        return name;
    }
    public int getAge(int a)
    {
        return age;
    }
//    int age;
    public void speak()
    {
        System.out.println("name = " + name + ",,age = " + age);
        show();
    }
    public void show()
    {
        System.out.println("name = " + name);
    }
    public boolean compare(Person p)
    {
        return this.age == p.age;
    }
}
//class Car
//{
//    String color = "红色";
//    int wheelNum = 4;
//
//    void run()
//    {
//        System.out.println(color + "..." + wheelNum);
//    }
//}
class PersonStaticTest
{
    String name;
    static String country = "CN";//静态成员变量，类变量
    public void show()
    {
        System.out.println(name + "：：" + country);
    }
}
public class faceObject {
    public static void main(String[] args){

//        Student s = new Student();

//        single ss = single.getInstance();
//
//        staticCode s = new staticCode();
//        String[] arr = {"a","b","c","d"};
//        System.out.println(args.length);

//        System.out.println(PersonStaticTest.country);

//        PersonStaticTest p = new PersonStaticTest();
//        p.name = "zhangsan";
//        p.show();
//        Person p = new Person("zhangsan", 10);

//        Person p1 = new Person(20);
//        Person p2 = new Person(25);
//        boolean b = p1.compare(p2);
//        System.out.println(b);
//        Person p = new Person("lisi",20);

//        Person p1 = new Person();
//        Person p2 = new Person("lili");
//        p2.speak();
//        p2.cry();
//        Person p3 = new Person("zhangsan", 10);

//        Person p = new Person();
//        p.setAge(120);
//        p.speak();

//        Person p = new Person();
//        p.age = 20;
//        p.speak();

//        System.out.println("Hello World");
//        Car car = new Car();
//        car.wheelNum = 5;
//        car.run();
//        Car c = new Car();
//        c.run();

//        show(new Car());
    }

//    public static void show(Car c)
//    {
//        c.wheelNum = 3;
//        c.color = "blue";
//        c.run();
//    }
}
