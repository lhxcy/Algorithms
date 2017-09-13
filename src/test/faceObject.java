package test;

import org.jcp.xml.dsig.internal.MacOutputStream;
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
 *  子父类中的函数
 *      当子类出现和父类一样的函数时，当子类对象调用该函数，会运行子类函数的内容，如同父类函数被覆盖一样
 *      这种情况是函数的另一个特性 ： 重写（覆盖）
 *
 *      当子类继承父类，沿袭了父类的功能到子类中，但是子类虽具有该功能，但功能的内容和父类不一致，
 *      这时没必要定义新功能，而是使用覆盖特性，保留父类功能定义并重写功能内容
 *      覆盖：1：子类覆盖父类，必须保证子类权限大于等于父类权限，才可以覆盖，否则编译失败
 *            2：静态只能覆盖静态
 *
 *      重载：只看同名函数的参数列表
 *            方法重载是让类以统一的方式处理不同类型数据的一种手段。
 *            多个同名函数同时存在，具有不同的参数个数/类型。
 *      重写：子父类方法要一摸一样
 *
 *   子父类中的构造函数
 *          在对子类对象初始化时父类构造函数也会运行，因为子类的构造函数默认第一行有一条隐式语句super()
 *          super():会访问父类中控参数的构造函数，而且子类中所有的构造函数默认第一行都是super()
 *          super()必须写在子类构造函数第一行
 *       为什么子类一定要访问父类构造函数？因为父类中的数据子类可以直接获取，所以子类对象在建立时，
 *       需要先查看父类是如何对这些数据进行初始化的。所以子类在对象初始化时，
 *       需要访问一下父类中的构造函数。如果要访问父类中指定的构造函数，可以通过手动定义super语句的方式指定
 *
 *     子类的所有构造函数默认都会访问父类中空参数的构造函数，因为子类的构造函数默认第一行有一条隐式语句super()
 *     当父类中没有空参数的构造函数，子类的构造函数必须手动通过super语句的形式来访问父类中的构造函数
 *     当然子类的构造函数第一行也可以使用this来访问本类中的构造函数，子类中至少会有一个构造函数访问父类中的构造函数
 *
 *   final：
 *          1：可以修饰类，函数，变量
 *          2：被final修饰的类不能被继承，为了避免被继承，被子类复写功能
 *          3：被final修饰的功能不能被复写
 *          4：被final修饰的变量是一个常量只能赋值一次，既可以修饰成员变量又可以修饰局部变量
 *              当在描述事物时，一些数据的出现值是固定的，所以定义为final  比如PI
 *              常量书写规范 所有字母都大写，单词之间用下划线连接
 *              public static final double PI = 3.14
 *          5：内部类定义在类中的局部位置上时，只能访问该局部被final修饰的局部变量
 *
 *   抽象类：
 *          抽象类和一般类没有太大不同，只不过出现了一些抽象接口，这些接口也是事物的功能，
 *          需要明确指出，但无法定义主题，通过抽象方法表示
 *          抽象类比一般类多了抽象函数，就是在类中可以定义抽象方法，抽象类不可以实例化
 *          抽象类中可以不定义抽象方法，这样做仅仅是不让该类创建对象
 *
 *          当多个类中出现了相同功能，但功能主体不同，这时可以进行向上抽取，
 *          这时，只抽取功能定义，而不抽取功能主体
 *
 *       特点：1：抽象方法只能存在抽象类中
 *             2：抽象方法和抽象类必须被abstract关键字修饰
 *             3：抽象类不可以用new创建对象
 *             4：抽象类中的方法要被使用，必须由子类复写所有抽象方法后，建立子类对象调用
 *                如果子类只是覆盖了部分抽象方法，那么该子类还是一个抽象类
 *   接口：初期可以认为是一个特殊的抽象类 like a
 *          当一个类中的方法都是抽象的，那么该类可以通过接口的形式表示
 *
*          接口格式特点：
*              1：接口中常见定义：常量，抽象方法
*              2：接口中的成员都有固定修饰符
*                  常量：public static final
*                  方法：public abstract
 *          接口中的成员都是public的
 *          接口不可以创建对象，因为有抽象方法
 *          需要被子类实现，子类对接口中的抽象方法全部覆盖后，子类才可以实例化，否则子类是一个抽象类
 *          接口可以被类多实现，也是对多继承不支持的转换形式
 *
 *   多态：可以理解为事物存在的多种体现形态 eg：人：男，女
 *          猫   x = new 猫()
 *          动物 x = new 动物()
 *          对象的多态性
 *
 *      1：多态的体现
 *          父类的引用指向自己的子类对象
 *          父类的引用也可以接受自己的子类对象
 *      2：多态的前提
 *          必须是类与类之间有关系，要么继承要么实现
 *          通常还有一个前提：存在覆盖
 *      3：多态的好处
 *          多态的出现提高了程序的扩展性
 *      4：多态的弊端
 *          提高了扩展性，但是只能使用父类的引用访问父类中的成员
 *       public static void function(Animal a)
 *        {
 *          a.eat();
 *           if(a instanceof Cat)//一般不这么用，扩展性差
 *           {
 *               Cat c = (Cat)a;
 *               c.catchMouse();
 *           }
 *           else if(a instanceof Cat)
 *           {
 *               Dog d = (Dog)a;
 *               d.kanJia();
 *           }
 *       }
 *      在多态中成员函数的特点：
 *          在编译时期：参阅引用型变量所属的类中是否有调用的方法如果有，编译通过，如果没有编译失败
 *          在运行时期：参阅对象所属的类中是否有调用的方法
 *          简单总结：成员函数在动态调用时，编译看左边，运行看右边
 *
 *      在多态中成员变量的特点：
 *          无论编译还是运行都参考左边（引用型变量所属的类）
 *
 *      在多态中静态成员函数的特点：无论编译还是运行都参考左边
 *
 *   Object :所有对象的直接或者间接父类
 *           该类中定义的肯定是所有对象都具备的功能
 *
 *   内部类：
 *      内部类访问规则：
 *           1：内部类可以直接访问外部类成员，包括私有
 *              之所以能直接访问外部类成员，是因为内部类中持有一个外部类的引用，格式 外部类名称.this
 *           2：外部类要访问内部类，必须建立内部类对象
 *      访问格式：
 *          当内部类定义在外部类的成员位置上，而且非私有，可以在外部其他类中，可以直接建立内部类对象
 *          格式 外部类命.内部类名 变量名 = new 外部类命.内部类名（）；
 *          当内部类在成员位置上，就可以被成员修饰符修饰
 *          比如 private，将内部类在外部类进行封装
 *               static：内部类具有static的特性，当内部类被static修饰后，只能访问外部类的static成员，出现访问局限
 *
*            在外部其他类中，如何访问static内部类
*                  new Outer.Inner().function()
 *           在外部其他类中，如何访问static内部类的static方法
 *                  Outer.Inner().function()
 *            注意：当内部类定义了静态成员，该内部类必须是静态的
 *                  当外部类的静态方法访问内部类时，内部类也必须是静态的
 *      当描述事物时，事物的内部还有事物，该事务用内部类描述
 *          因为内部事物在使用外部事物的内容
 *          局部内部类不能调用成员方法eg:static
 *          内部类定义在局部时：
 *                1：不可以被成员修饰符修饰
 *               2：可以直接访问外部类的成员，因为还持有外部类中的引用，
 *                  但是不可以访问他所在的局部中的变量，只能访问被final修饰的变量
 *   匿名内部类：
         1：就是内部类的简写格式
         2:定义内部类的前提 : 内部类必须继承一个类或接口
         3：匿名内部类格式： new 父类或接口（）{定义子类的接口}
         4：其实匿名内部类就是一个匿名子类对象，而且这个对象有点胖，可以理解为带内容的对象
         5：匿名内部类中定义的方法最好不超过3个

    class Outer{
        private int m;
        private class Inner{
            private int n;
            private int k;
     }
 你想不通的肯定是指内部类的私有变量怎么可以被外部类访问吧，按常规，私有变量m只能在InnerClass里被访问，
 但你要注意，内部类就相当于一个外部类的成员变量，举个例子。m和类Inner都是成员变量，他们之间是平等的，
 唯一不同的就是Inner它是包装了几个成员变量比如n,k，也就是说m n k是平等的，区别在于访问n k要通过Inner，
 就是要建立Inner实例访问nk，这样解释够明白了吧

 *
 *   异常：程序运行时出现不正常的情况
 *          异常由来：问题也是显示生活中的一个具体事物，也可以通过Java的类的形式进行描述，并封装成对象
 *                  其实就是Java对不正常情况进行描述后的对象体现
*           对于问题的划分：严重，非严重
*           严重：Java通过Error类进行描述
 *                  对于Error，一般不编写针对性代码进行处理
 *          非严重：Java通过Exception类进行描述
 *                  对于Exception可以使用针对性的处理方式进行处理
 *          无论error还是Exception都具有一些共性，比如不正常情况的信息，引发原因等
 *          Throwable  含有Error,Exception两个子类
 *    异常处理： Java提供特有的语句进行处理
 *               try{需要检测的代码}catch{处理异常的代码，处理方式}finally{一定执行的语句}
 *         对捕获到的异常对象进行常见方法操作:  String getMessage()，toString，printStackTrace
 *         其实jvm默认的异常处理机制就是调用printStackTrace（）方法，打印异常的堆栈的跟踪信息
 *    对多异常的处理：
 *          1：声明异常时，建议声明更为具体的异常，这样处理的可以更具体
 *          2：对方声明多个异常，就对于有几个catch块，不要定义多个catch块
 *              如果多个catch块2中的异常出现继承关系，父类异常放在最下面
 *           建议在进行catch处理时，catch中一定要定义具体处理方式
 *           不要简单定义一句e.printStackTrace()
 *           也不要简单的就写一条输出语句
 *
 *    自定义异常：
 *          因为项目中会出现特有的问题，而这些问题并未被Java所描述并封装对象，
 *          所以对于这些特有的问题可以按照Java的对问题封装的思想，将特有的问题进行自定义的异常封装
 *
 *          当在函数内部出现了throw抛出异常对象，那么就必须要给出对应的处理动作
 *          要么在内部try catch处理，要么在函数上声明让调用者处理
 *          一般情况下，函数内出现异常，函数上要声明
 *
 *          如何定义异常信息呢：因为父类已经把异常信息的操作都完成了，所以子类只需要在构造时，
 *          将异常信息通过super语句就可以，通过getMessage（）方法获取异常信息
 *
 *          自定义异常必须：必须是自定义类继承Exception
 *          继承Exception原因：异常体系有一个特点，因为异常类和异常对象都被抛出，他们都具有可抛性，这个
 *              可抛性是Throwable这个体系的独有特点，只有这个体系中的对象才可以被throws和throw操作
 *
 *          throws和throw 区别：
 *              1：throws使用在函数上，throw使用在函数内
 *              2：throws后面跟的异常类，可以跟多个，用逗号隔开，throw跟的是异常对象
 *
 *          Exception中有一个特殊的子类异常，RuntimeException 运行时异常
 *              如果在函数内容抛出该异常，函数上可以不声明，编译一样通过，
 *              如果在函数上声明了该异常，调用者可以不用处理，编译一样通过
 *
 *              之所以不用在函数上声明，是因为不需要让调用者处理，当该异常发生时，希望程序停止，
 *              因为在运行时，出现无法运算的情况，希望程序停止后，对代码进行修正
 *
 *              自定义异常时：如果该异常发生，无法继续进行运算，就让该自定义异常继承RuntimeException
 *
 *              异常分两种：1：编译时被检测的异常
 *                          2：编译时不被检测的异常，运行时异常RuntimeException及其子类
 *
 *           finally中存放的是一定会被执行的代码，通常用于关闭资源，例如数据库
 *           try catch,  try catch finally , try finally,三种格式
 *           catch用于处理异常，如果没有catch就代表异常没有被处理过，如果该异常是检测时异常，必须被声明
 *
 *       异常在子父类覆盖中的体现：
 *          1：子类在覆盖父类时，如果父类的方法抛出异常，那么子类的覆盖方法，只能抛出父类的异常或这该异常的子类
 *          2：如果父类抛出多个异常，那么子类覆盖方法时，只能抛出父类一次的子集
 *          3：如果父类或者接口的方法中没有异常抛出，那么子类在覆盖方法时也不可以抛出异常，如果子类方法发生了异常，
 *              就必须要进行try处理，绝对不能抛
 *
 *         异常：对问题的描述，将问题进行对象封装
 *         -----------------
 *               异常体系：Throeable
 *                              |--Error
 *                              |--Exception
 *                                   |--RuntimeException
 *          ----------------------
 *               异常体系特点：异常体系中的所有类以及建立的对象都具有可抛性
 *                              也就是说可以被throw或throws关键字操作
 *                              只有异常体系具备这个特点
 *            -------------------------
 *               throw和throws区别：
 *                      throw由于函数内，用于抛出异常对象
 *                      throws定义在函数上，用于抛出异常类，可以抛出多个，用逗号隔开
 *              ---------------
 *               当函数内容有throw抛出异常对象，并未进行try处理，必须要在函数上声明，否则编译失败
 *               注意：RuntimeException除外，也就是说如果函数内部抛出RuntimeException异常，函数可以不声明
 *
 *               如果函数声明了异常，调用者需要进行处理，处理方法可以throw可以try
 *              ------------------------
 *               两种异常：
 *                      编译时被检测异常：
 *                              该异常在编译时，如果没有处理，编译失败
 *                              该异常被标识，代表可以被处理
 *                      运行时异常：
 *                              在编译时，不需要处理，编译器不检查
 *                              该异常的发生，建议不处理，让程序停止，需要对代码进行修改
*              ---------------------------------
 *                注意：finally通常定义关闭资源的代码
*
*                finally只有一种情况不会执行，当执行到System.exit(0)时，finally不会执行
 *          -----------------------------
*                自定义异常：定义类继承Exception或RuntimeException
 *                      1：为了让改自定义类具有可抛性
 *                      2：让该类具备操作异常的共性方法
 *                      注意当要自定义异常额度信息时，可以使用父类定义好的功能
 *                      class MyException extends Exception
 *                      {
 *                          MyException(String msg)
 *                          {
 *                              super(msg);
 *                          }
 *                      }
 *
 *                 自定义异常：按照Java的面向对象思想，将程序中出现的特有问题进行封装
 *
 *                 异常好处：
 *                          1：将问题进行封装
 *                          2：将正常流代码和问题处理代码分离，方便阅读
 *
 *                 异常的处理原则：
 *                          1：处理异常有两种  try throw
 *                          2：调用到抛出异常的功能时，抛出几个就处理几个，一个try对应多个catch
 *                          3：多catch父类的catch放最下面
 *                          4：catch内，需要定义针对性的处理方式，不要简单的打印printStackTrace，输出语句，也不要不写
 *                                  当捕获的异常，本功能处理不了时，可以在catch中继续抛出
 *                                  try
 *                                  {
 *                                      throw new AException();
 *                                  }catch(AException e)
 *                                  {
 *                                      throw e;
 *                                  }
 *                          如果该异常处理不了，但并不属于该功能出现的异常，可以将异常转换后，再抛出该功能相关的异常
 *                          或者异常可以处理，当需要将异常产生的和本功能相关的问题提供出去
 *                          让调用者知道。并处理，也可以将捕获的异常处理后，转换新的异常
 *                                  try
 *                                  {
 *                                      throw new AException();
 *                                  }catch(AException e)
 *                                  {
 *                                      //对AException处理
 *                                      throw new BException();
 *                                  }
 *
 *                           注意事项：
 *                              在子父类覆盖时
 *                                  1：子类抛出的异常必须是父类的异常的子类或子集
 *                                  2：如果父类或者接口没有异常抛出时，子类覆盖出现异常，只能try不能抛
 *
 *
 *    包：
 *          package pack；放在程序最上面
 *          class packageDemo{}
 *          Javac -d . packageDemo.java    -d 表示目录， . 当前目录
 *
 *       不同包中的类的访问：
 *              1：包名.类名 对象 = new 包名.类名 （）；、被访问的类以及类中的成员需要public修饰
 *              不同包之间类可以继承，需要  包名.类名
 *              2：不同包中的子类还可以直接访问父类中被protected修饰的成员
 *        包与包直接可以使用的权限只有两种：public protected
 *                  public      protected       default         private
 *        同一个类    OK            OK             OK              OK
 *        同一个包    OK            OK             OK
 *        子类        OK            OK
 *        不同包      OK
 *
 *         为了简化类名书写，使用一个关键字import
 *              import packb.hh.hehe.heihei.DemoC//导入DemoC类
 *              import packb.hh.hehe.heihei.* //导入所有类
 *
 *        建议定义包名不要重复，可以使用url来完成定义，url是唯一的
 *
 *      Jar包：
 *            Java的 压缩包
 *            jar -cf haha.jar packa pack//将packa和oack两个包压缩到haha.jar
 *            -c 创建新jar文件， -f 指定文件名
 *            jar -t haha.jar  //查看haha.jar的文件
 *            jar -tf haha.jar >c:\1.txt //将haha.jar显示的信息写入C盘下的1.txt文件中
 *
 *            dir >c:\2.txt  //将dir显示的信息存入2.txt    所谓的数据重定向
 *
 *
 *
 *
 *
 *
 *
 *
 */

/**注释模板
 * 文档注释：@author  @version v1.1
 * 获取。。。
 * @param
 *@return
 */
//类公有后被访问的成员也要共有才可以被访问

//非静态类中不可以定义静态成员，内部类中如果定义了静态成员，该内部类必须被静态修饰
// 静态只能覆盖静态
/*
子父类出现后，类成员的特点
类中成员
1：变量 如果子类中出现非私有的同名成员变量时，子类要想访问本类中的变量，用this。访问父类中的同名变量，用super
2：函数
3：构造函数
 */

//package test;
//public class DemoA
//{
//    public void show()
//    {
//        System.out.println("DemoA");
//    }
//}




/*
有一个圆形和长方形
都可以获取面积，对于面积如果出现非法数值，视为获取面积出现问题
 */
interface MianJi
{
    void getArea();
}
class NoValueException extends RuntimeException
{
    NoValueException(String msg)
    {
        super(msg);
    }
}
class Rec implements MianJi
{
    private int len,wid;
    Rec(int len, int wid) //throws NoValueException
    {
        if (len <= 0 || wid <= 0)
            throw new NoValueException("出现非法值");//一旦出错程序终止
        this.len = len;
        this.wid = wid;
    }
    public void getArea()
    {
        System.out.println(len*wid);
    }
}
class Circle implements MianJi
{
    private double radius;
    public static final double PI = 3.14;//全局常量，不需要改变
    Circle(double radius)
    {
        if (radius <= 0)
            throw new NoValueException("非法");
        this.radius = radius;
    }
    public void getArea()
    {
        System.out.println(PI * radius * radius);
    }
}
//
//      Rec r = new Rec(-2,4);
//      r.getArea()
//System.out.println("over");

/*
异常在子父类覆盖中的体现：
        1：子类在覆盖父类时，如果父类的方法抛出异常，那么子类的覆盖方法，只能抛出父类的异常或这该异常的子类
 */
class AException extends Exception
{}
class BException extends AException
{}
class fu6
{
    void show() throws AException {}
}
class zi6 extends fu6
{
    void show() throws AException{}//这里能抛AException或BException
}

/*
需求：毕老师用电脑上课
开始思考上课中出现的问题：电脑蓝屏，冒烟
要对问题描述，封装对象
 当冒烟发生时，出现讲课无法继续，出现讲师问题，课时计划无法完成
 */
class NoPlanException extends Exception
{
    NoPlanException(String msg)
    {
        super(msg);
    }
}
class MaoYanException extends Exception
{
    MaoYanException(String msg)
    {
        super(msg);
    }
}
class LanPingException extends Exception
{
    LanPingException(String msg)
    {
        super(msg);
    }
}
class computer
{
    private int state = 1;
    public void run() throws LanPingException,MaoYanException
    {
        if (state == 2)//lan ping
            throw new LanPingException("lan ping le");
        if (state == 3)//mao yan
            throw new MaoYanException("mao yan le");
        System.out.println("computer run");
    }
    public void reset()
    {
        state = 1;
        System.out.println("computer reset");
//        run();
    }
}
class Teacher
{
    private String name;
    private computer cmp;
    Teacher(String name)
    {
        this.name = name;
        cmp = new computer();
    }
    public void prelect() throws NoPlanException
    {
        try {
            cmp.run();
        }catch (LanPingException e)
        {
            cmp.reset();
        }catch (MaoYanException e)//冒烟处理不了
        {
            test();
            throw new NoPlanException("课时无法继续" + e.getMessage());
//            test();//此异常抛出，该语句不会执行，所以test();写上面
        }

        System.out.println("jiang ke");
    }
    public void test()
    {
        System.out.println("做练习");
    }
}
//Teacher t = new Teacher("bilaoshi");
//try{
//  t.prelect();
// }catch(NoPlanException e)
// {
//        System.out.println(e.toString());
//        System.out.println("换老师或放假");
// }
//


class person6
{
    public void checkName(String name)
    {
//        if (name.equals("lisi"))//NullPointerException
        if ("lisi".equals(name))//if ("lisi"!=null&&name.equals("lisi))//这样解决传空出现NullPointerException
        {
            System.out.println("YES");
        }else {
            System.out.println("No");
        }
    }
}
//person6 p = new person6();
//p.checkName(null);//NullPointerException



/*
需求：在本程序中，对于除数是-1，也视为错误的，是无法进行运算的
 */
class Demo6
{
    int div(int a, int b) throws FuShuException
    {
        if (b == 0)
            throw new ArithmeticException("被零除");//这里若抛出Exception则报错，此时函数上可以不用声明ArithmeticException异常
        if (b < 0)
            throw new FuShuException("出现了除数是负数的信息", b);//手动通过throw抛出一个自定义异常
        return a/b;
    }
}
class FuShuException extends Exception//getMessage()
{
    private int value;
    FuShuException(String msg,int value)
    {
        super(msg);
        this.value = value;
    }
    public int getValue()
    {
        return this.value;
    }
//    private String msg;//父类已经定义了这些操作的函数，因此不需要在子类中覆盖了，直接用上面的方法
//    FuShuException(String msg)
//    {
//        this.msg = msg;
//    }
//    public String getMessage()
//    {
//        return msg;
//    }
}
class ExceptionTest2
{
    public static void main(String[] args) //throws Exception//不处理div的异常，直接抛出，交给jvm处理
    {
        Demo6 d  = new Demo6();
        try {
            int x = d.div(4,-1);
            System.out.println(x);
        }catch (FuShuException e)
        {
            System.out.println(e.toString());//若FuShuException为空，此时发现打印的结果中只有异常的名称，却没有异常的信息，因为自定义异常并未定义信息
            System.out.println("除数出现负数"+e.getValue());
        }
    }
}



class Demo5
{
    //throws ArithmeticException
    int div(int a, int b) throws ArithmeticException,ArrayIndexOutOfBoundsException//throws Exception//在功能上通过throws的关键字声明了该功能可能会出现问题
    {
        return a/b;
    }
}

class exceptionTest1
{
    public static void main(String[] args) //throws Exception//不处理div的异常，直接抛出，交给jvm处理
    {
        Demo5 d = new Demo5();
//        int x = d.div(4,0);
//        System.out.println(x);
        try {//用了try就不用主函数抛出异常了，也可以使用上面注释的句子，主函数需要抛出异常
            int x = d.div(4,0);
            System.out.println(x);
        }catch (ArithmeticException e)
        {
            System.out.println("除零");
            System.out.println(e.getMessage());//by zero
            System.out.println(e.toString());//异常名称：异常信息
            e.printStackTrace();//异常名称，异常信息，异常出现的位置
            //其实jvm默认的异常处理机制就是调用printStackTrace（）方法，打印异常的堆栈的跟踪信息
        }catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("越界");
        }catch (Exception e)
        {
            System.out.println(e.toString());
        }
    }
}



interface Inner3//接口类型都是固定的
{
    void method();
}
class Test3
{
    //补足代码，通过匿名内部类
//    static class Inner implements Inner3
//    {
//        public void method(){
//            System.out.println("method run");
//        }
//    }
//    static Inner3 function()
//    {
//        return new Inner();
//    }
    static Inner3 function()
    {
        return new Inner3() {
            @Override
            public void method() {
                System.out.println("method run");
            }
        };
    }
}
//Test3.function():Test3类中有一个静态方法function
//.method()： function返回的结果是一个Inner3的对象，因为只有Inner3的对象有.method()方法
//    Test3.function().method();



/*
匿名内部类：
    1：就是内部类的简写格式
    2:定义内部类的前提 : 内部类必须继承一个类或接口
    3：匿名内部类格式： new 父类或接口（）{定义子类的接口}
    4：其实匿名内部类就是一个匿名子类对象，而且这个对象有点胖，可以理解为带内容的对象
    5：匿名内部类中定义的方法最好不超过3个
 */;
abstract class AbsDemo
{
    abstract void show();
}
class Outer4
{
    int x = 3;
//    class Inner extends AbsDemo
//    {
//        void show()
//        {
//            System.out.println("method");
//        }
//    }
//    public void function()
//    {
//        new Inner().show();
//    }
    public void function()
    {
//        AbsDemo d = new AbsDemo()//上面内部类的简写
//        {
//            void show()
//            {
//                System.out.println("x=" + x);
//            }
//            void abc()
//            {
//                System.out.println("abc");
//            }
//        };
//        d.show();
//        d.abc();//错误，编译失败，多态编译看左
        new AbsDemo()//上面内部类的简写
        {
            void show()
            {
                System.out.println("x=" + x);
            }
            void abc()
            {
                System.out.println("abc");
            }
        }.show();//也可以通过.abc()访问abc
//        new AbsDemo()//上面内部类的简写
//        {
//            void show()
//            {
//                System.out.println("x=" + x);
//            }
//            void abc()
//            {
//                System.out.println("abc");
//            }
//        }.abc();//也可以通过.abc()访问abc
    }
}


/*
内部类定义在局部时：
    1：不可以被成员修饰符修饰
    2：可以直接访问外部类的成员，因为还持有外部类中的引用，
        但是不可以访问他所在的局部中的变量,只能访问被final修饰的变量
 */
class Outer3
{
    int x = 3;
    void method(final int a)
    {
        final int y = 4;
        class Inner//局部内部类不能调用成员方法eg:static
        {
            void function()
            {
                System.out.println(Outer3.this.x);
                System.out.println(y);
                System.out.println(a);
            }
        }
        new Inner().function();
    }
}
//new Outer3.method();
//Outer3 out = new Outer3();
//out.method(7);//输出7，该语句执行完，出栈，释放
//out.method(8);//8


//当描述事物时，事物的内部还有事物，该事务用内部类描述
//         因为内部事物在使用外部事物的内容
class Body
{
    private class XinZang{}
    public void show()
    {
        new XinZang();
    }
}


/*
内部类访问规则：
    1：内部类可以直接访问外部类成员，包括私有
        之所以能直接访问外部类成员，是因为内部类中持有一个外部类的引用，格式 外部类名称.this
    2：外部类要访问内部类，必须建立内部类对象
 */

class Outer2
{
    private int x = 3;
    static class Inner//内部类  可以被私有，私有不能使用Outer.Inner in = new Outer.Inner();创建对象
    {
        void function()
        {
//            System.out.println("Inner :" + x);//报错，当内部类被static修饰后，只能访问外部类的static成员
        }
    }
    static class Inner2
    {
        void show()
        {
            System.out.println("Inner2 : show" );
        }
    }
    void method()
    {
//        System.out.println(x);
        Inner in = new Inner();
        in.function();
    }
    public static void method1()
    {
        new Inner2().show();
    }
}

class Outer
{
    private int x = 3;
    private class Inner//内部类  可以被私有，私有不能使用Outer.Inner in = new Outer.Inner();创建对象
    {
        int x = 4;
        void function()
        {
            int x = 6;
            System.out.println("Inner :" + x);//打印6
            System.out.println("Inner :" + this.x);//打印 4
            System.out.println("Inner :" + Outer.this.x);//打印3
        }
    }
    void method()
    {
//        System.out.println(x);
        Inner in = new Inner();
        in.function();

    }
}
//Outer out = new Outer();
//out.method();

//直接访问内部类中的成员
//Outer.Inner in = new Outer.Inner();
//in.function();


class Demo2
{
    private int num;
    Demo2(int num)
    {
        this.num = num;
    }
    //       重载：只看同名函数的参数列表
     //        方法重载是让类以统一的方式处理不同类型数据的一种手段。
    //                多个同名函数同时存在，具有不同的参数个数/类型。
    //          重写：子父类方法要一摸一样
    public boolean equals(Object obj)//参数相同覆盖（重写），不同重载
    {
        if (!(obj instanceof Demo))//也可以抛出异常
        {
            return false;
        }
        Demo2 d = (Demo2)obj;
        return this.num == d.num;
    }
//    public boolean compare(Demo2 d)//父类中有比较函数equals，直接覆盖
//    {
//        return this.num == d.num;
//    }
    public String toString()
    {
        return "Demo2:" + num;
    }
}
//Demo2 d = new Demo2();
//System.out.println(d.toString())


class Demo1
{}
//Demo1 d = new Demo1();
//Demo1 d1 = new Demo1();
//System.out.println(d == d1)//比较地址



/*
需求：
数据是： 用户信息
1：连接数据库  JDBC  Hibernate
2：操作数据库
    c creat r read u update d delete
3：关闭数据库
 */
//Dao （data access operate）
interface UserInfoDao
{
//    public void add(User user);
//    public void delete(User user);
}
class UserInfoByJDBC implements UserInfoDao
{
//    public void add(User user)//User类没有定义
    {
//        1：JDBC连接数据库
//        2：使用sql添加语句添加数据
//        3：关闭连接
    }
//    public void delete(User user)
    {
//        1：JDBC连接数据库
//        2：使用sql删除语句删除数据
//        3：关闭连接
    }
}
class UserInfoByHibernate implements UserInfoDao
{
//    public void add(User user)//User类没有定义
    {
//        1：Hibernate连接数据库
//        2：使用sql添加语句添加数据
//        3：关闭连接
    }
//    public void delete(User user)
    {
//        1：Hibernate连接数据库
//        2：使用sql删除语句删除数据
//        3：关闭连接
    }
}

//UserInfoDao ui = new UserInfoByJDBC();
//ui.add(user);
//ui.delete(user);

/*
class UserInfoByJDBC
{
    public void add(User user)//User类没有定义
    {
//        1：JDBC连接数据库
//        2：使用sql添加语句添加数据
//        3：关闭连接
    }
    public void delete(User user)
    {
//        1：JDBC连接数据库
//        2：使用sql删除语句删除数据
//        3：关闭连接
    }
}
class UserInfoByHibernate
{
    public void add(User user)//User类没有定义
    {
//        1：Hibernate连接数据库
//        2：使用sql添加语句添加数据
//        3：关闭连接
    }
    public void delete(User user)
    {
//        1：Hibernate连接数据库
//        2：使用sql删除语句删除数据
//        3：关闭连接
    }
}
//UserInfoByJDBC ui = new UserInfoByJDBC();
//ui.add(user);
//ui.delete(user);

//UserInfoByHibernate ui = new UserInfoByHibernate();
//ui.add(user);
//ui.delete(user);
*/


/*
需求：电脑运行实例
电脑运行基于主板
 */

interface PCI
{
    public void open();
    public void close();
}
class MainBoard
{
    public void run()
    {
        System.out.println("MainBoard run");
    }
    public void usePCI(PCI p)//PCI p = new NetCard();//接口型引用指向自己的子类对象。
    {
        if (p != null)
        {
            p.open();
            p.close();
        }
    }
}
class NetCard implements PCI
{
    public void open()
    {
        System.out.println("NetCard open");
    }
    public void close()
    {
        System.out.println("NetCard close");
    }
}
class SoundCard implements PCI
{
    public void open()
    {
        System.out.println("SoundCard open");
    }
    public void close()
    {
        System.out.println("SoundCard close");
    }
}
//MainBoard m = new MainBoard();
//m.run();
//m.usePCI(new NetCard());
//m.usePCI(new SoundCard());


/*class MainBoard
{
    public void run()
    {
        System.out.println("MainBoard run");
    }
    public void useNetCard(NetCard c)
    {
       c.open();
       c.close();
    }
}
class NetCard
{
    public void open()
    {
        System.out.println("NetCard open");
    }
    public void close()
    {
        System.out.println("NetCard close");
    }
}
//MainBoard m = new MainBoard();
//m.run();
//m.useNetCard(new NetCard());
*/



class fu3
{
    void method1()
    {
        System.out.println("fu method1");
    }
    void method2()
    {
        System.out.println("fu method2");
    }
    static void method4()//静态
    {
        System.out.println("fu method4");
    }
}
class zi3 extends fu3
{
    void method1()
    {
        System.out.println("zi method1");
    }
    void method3()
    {
        System.out.println("zi method3");
    }
    static void method4()//一般没人覆盖静态
    {
        System.out.println("zi method4");
    }
}
//fu3 f = new zi3();
//f.method4();//打印 fu method4
//在多态中静态成员函数的特点：无论编译还是运行都参考左边

/*
zi3 z = new zi3();
z.method1();
z.method2();
z.method3();
//打印 zi method1 fu method2 zi method3
fu3 f = new zi3();
f.method1();
f.method2();
f.method3();
//报错 f 中没有method3()，编译不通过
//在多态中成员函数的特点：
    在编译时期：参阅引用型变量所属的类中是否有调用的方法如果有，编译通过，如果没有编译失败
    在运行时期：参阅对象所属的类中是否有调用的方法
    简单总结：成员函数在动态调用时，编译看左边，运行看右边
fu3 f = new zi3();
f.method1();
f.method2();
//打印  zi method1 fu method2
*/


/*
基础班学生： 学习，睡觉
高级班学生： 学习，睡觉
 */
abstract class student3
{
    public abstract void study();
    public void sleep()
    {
        System.out.println("躺着睡");
    }
}
class BaseStudent1 extends student3
{
    public void study()
    {
        System.out.println("base study");
    }
    public void sleep()
    {
        System.out.println("坐着睡");
    }
}
class AdvStudent1 extends student3
{
    public void study()
    {
        System.out.println("Adv study");
    }
}
/*
BaseStudent1 bs = new BaseStudent1();
bs.study();
bs.sleep();
AdvStudent1 as = new AdvStudent1();
as.study();
as.sleep();
public void doSome(BaseStudent1 bs){}
public void doSome(AdvStudent1 as){}
public void doSome(Student3 stu)//也可以构建在一个类中，如下：
{
    stu.study();
    stu.sleep();
}
class DoStudent
{
    public void doSome(Student3 stu)
    {
        stu.study();
        stu.sleep();
    }
}
DoStudent ds = new DoStudent();
ds.doSome(new BaseStudent1())
*/


/*
动物：猫，狗
 */
abstract class Animal
{
    abstract void eat();
}
class Cat extends Animal
{
    public void eat()
    {
        System.out.println("Cat eat fish");
    }
    public void catchMouse()
    {
        System.out.println("catch mouse");
    }
}
class Dog extends Animal
{
    public void eat()
    {
        System.out.println("Dog eat bone");
    }
    public void kanJia()
    {
        System.out.println("kan jia");
    }
}
class Pig extends Animal
{
    public void eat()
    {
        System.out.println("Pig eat siliao");
    }
    public void gongDi()
    {
        System.out.println("gong di");
    }
}
/*
//Cat c = new Cat();
//c.eat();
//Cat c1 = new Cat();
//c1.eat();
public static void function(Cat c)
{
    c.eat();
}
public static void function(Dog d)
{
    d.eat();
}
public static void function(Pig p)
{
    p.eat();
}
public static void function(Animal a)
{
    a.eat();
    if(a instanceof Cat)
    {
        Cat c = (Cat)a;
        c.catchMouse();
    }
    else if(a instanceof Cat)
    {
        Dog d = (Dog)a;
        d.kanJia();
    }
}
//多态的体现
Animal a = new Cat();//类型提升，也成为向上转型
a.eat();
    //如果想要调用猫的特有方法，如何操作：
    //强制将父类的引用，转换成子类类型
Cat c = (Cat)a;
c.catchMouse(); 这样可以
    //千万不要将父类对象转换为子类类型
    //我们能转换的是父类引用指向了自己的对象时，改引用可以提升也可以被强制转换
    //多态自始至终都是子类对象在做着对象变化
//Animal  a = new Animal();
//Cat c = (Cat)a;//错误
*/

abstract class student1
{
    abstract void study();
    void sleep()
    {
        System.out.println("sleep");
    }
}
interface Smoking//用接口扩展功能
{
    void smoke();
}
class ZhangSan extends student1 implements Smoking
{
    void study() {}
    public void smoke(){}
}

interface InnerA
{
    public abstract void method();
}
interface Inner
{
    public static final int NUM = 3;
    public abstract void show();
}
class Demo
{
    public void function(){}
}
class Test extends Demo implements Inner, InnerA
{
    public void show(){}
    public void method(){}
}
interface A
{
    void methodA();
}
interface B //extends A
{
    void methodB();
}
interface C extends B,A
{
    void methodC();
}
class D implements C
{
    public void methodA(){}
    public void methodB(){}
    public void methodC(){}
}
//Test t = new Test();
// System.out.println(t.NUM);
//System.out.println(Test.NUM);
// System.out.println(Inner.NUM);

/*
获取一段程序的运行时间
原理：获取开始，结束时间，然后相减
获取时间：System.currentTimeMillis()
这种方式，模板方法设计模式
什么是模板方法设计模式？
在定义功能时，功能一部分时确定的，但是由一部分是不确定的，而确定的部分在使用不确定的部分
那么这时就将不确定部分暴露出去，由该类子类去完成
 */
abstract class GetTime
{
    public final void getTime()
    {
        long start = System.currentTimeMillis();
        runCode();
        long end = System.currentTimeMillis();
        System.out.println("time: " + (end - start));
    }
    public abstract void runCode();
}
class SubTime extends GetTime
{
    public void runCode()
    {
        for (int i = 0; i < 4000; i++)
        {
            System.out.println(i);
        }
    }
}


//员工类 name id pay
//经理类  继承了员工 并有自己特有的bonus
abstract class Employee
{
    private String name;
    private String id;
    private double pay;
    Employee(String name, String id, double pay)
    {
        this.name = name;
        this.id = id;
        this.pay = pay;
    }
    abstract public void work();
}
class Manager extends Employee
{
    private int bonus;
    Manager(String name, String id, double pay, int bonus)
    {
        super(name, id, pay);
        this.bonus = bonus;
    }
    public void work()
    {
        System.out.println("manager work");
    }
}
class pro extends Employee
{
    pro(String name, String id, double pay)
    {
        super(name, id, pay);
    }
    public void work()
    {
        System.out.println("pro work");
    }
}


abstract class student
{
    abstract void study();
    abstract void study1();
    void sleep()
    {
        System.out.println("sleep on the bed");
    }
}
class BaseStudent extends student
{
    void study()
    {
        System.out.println("base study");
    }
    void study1(){}
}
class AdvStudent extends student
{
    void study()
    {
        System.out.println("adv study");
    }
    void study1(){}
}

class person2
{
    private String name;
    person2(String name)
    {
        this.name = name;
    }
}
class student2 extends person2
{
    student2(String name)
    {
        super(name);
    }
}


class fu2
{
    fu2()
    {
        System.out.println("fu2 run");
    }
    fu2(int x)//即使有这个函数   zi2 z = new zi2(4);//输出 fu2 run  zi2。。 4
    {
        //super()
        System.out.println("fu2 .." + x);
    }
}
class zi2 extends fu2
{
    zi2()
    {
        //super();//隐式调用父类
        //super(4);  加入把fu2(){}去掉，必须显示访问父类构造函数
        System.out.println("zi2 run");
    }
    zi2(int x)
    {
        //super()
        System.out.println("zi2 .." + x);
    }
}
//zi2 z = new zi2();  输出 fu2 run  zi2 run
//zi2 z = new zi2(4);//输出 fu2 run  zi2。。 4
class Tel
{
    void show()//当该函数定义为private时，子类可以定义show函数，但不能称之为覆盖，因为该函数对子类隐形
    {
        System.out.println("number");
    }
}
class newTel extends Tel
{
    void show()
    {
        super.show();//也可以使用下面一句，不过麻烦
//        System.out.println("number");
        System.out.println("name");
        System.out.println("picture");
    }
    void lingsheng(){}
}

class fu1
{
    void show()
    {
        System.out.println("fu");//打印子类   this 打印子类
    }
    void speak()
    {
        System.out.println("vb");
    }
}
class zi1 extends fu1
{

    void show()
    {
        System.out.println("zi");//打印子类   this 打印子类
    }
    void speak()
    {
        System.out.println("java");
    }
}
//zi2 z = new zi2();
//z.show(); 打印子类 zi


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
class zi0 extends fu
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
        SubTime s = new SubTime();
        s.getTime();

//        GetTime g = new GetTime();
//        g.getTime();

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
