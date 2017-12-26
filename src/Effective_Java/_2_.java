package Effective_Java;


import com.sun.xml.internal.ws.server.ServerRtException;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.io.*;
import java.lang.annotation.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by XCY on 2017/9/21.
 *  创建和销毁对象
 */

/*
第1条：考虑用静态工厂方法代替构造器
一个类只能有一个带有指定签名的构造器
类中有静态工厂方法的类被称为受控的类
编写受控的类：1：可以确保它是不可实例化的 2：使得不可变类可以确保不会存在两个相等的对象
接口不能有静态方法，因此按照惯例，接口Type的静态工厂方法被放在一个名为Types的不可实例化的类中
    创建一个类的实例：1：提供公有的构造函数 2：提供一个公有的静态工厂方法
    提供静态工厂方法优势：
        1：静态工厂方法于构造器不同的第一大优势在于，它们有名称
        2：静态工厂方法于构造器不同的第二大优势在于，不必每次调用它们的时候都创建新的对象
        3：静态工厂方法于构造器不同的第三大优势在于，它们可以返回 原返回类型 的任何 子类型 的对象
        4：静态工厂方法于构造器不同的第四大优势在于，在创建参数化类型实例的时候，它们使代码变得更加简洁。
    静态工厂方法的主要缺点在于，类如果不含公有的或者受保护的构造器，就不能被子类化
    静态工厂方法的第二个缺点在于，它们与其他的静态方法实际上没有任何区别
 */
//    public static Boolean valueOf(boolean b){//将boolean基本数据类型转换成了一个Boolean对象引用
//        return b ? Boolean.TRUE : Boolean.FALSE;
//    }
/*
第2条：遇到多个构造器参数时要考虑构建器
静态工厂和构造器有个共同的局限性：它们都不能很好地扩建到大量的可选参数。
如果类的构造器或者静态工厂中具有多个参数，设计这种类时，Builder模式就是种不错的选择
 */
///Builder Pattern
class NutritionFacts{
    private final int servingSize;
    private final int servings;
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbohydrate;
    public static class Builder{
        //Required parameters
        private final int servingSize;
        private final int servings;

        //Optional parameters  -  initialized 头default values
        private int calories = 0;
        private int fat = 0;
        private int carbohydrate = 0;
        private int sodium = 0;

        public Builder(int servingSize, int servings){
            this.servingSize = servingSize;
            this.servings = servings;
        }

        public Builder calories(int val){
            calories = val;
            return this;
        }

        public Builder fat(int val){
            fat = val;
            return this;
        }

        public Builder carbohydrate(int val){
            carbohydrate = val;
            return this;
        }

        public Builder sodium(int val){
            sodium = val;
            return this;
        }

        public NutritionFacts builder(){
            return new NutritionFacts(this);
        }
    }
    private NutritionFacts(Builder builder){
        servingSize = builder.servingSize;
        servings = builder.servings;
        calories = builder.calories;
        fat = builder.fat;
        carbohydrate = builder.carbohydrate;
        sodium = builder.sodium;
    }

    public static void main(String[] args){
        NutritionFacts cocaCola = new NutritionFacts.Builder(240,8).
                                    calories(100).sodium(35).carbohydrate(27).builder();
    }
}

/*
第3条：用私有构造器或者枚举类型强化Singleton属性
Singleton指仅仅被实例化一次的类，Singleton通常被用来代表本质上唯一的系统组件
使类称为Singleton会使它的客户端测试变得十分困难，因为无法给Singleton替换模拟实现，除非它实现一个充当其类型的接口

 */
//下面的2种方法可能会被通过反射机制调用私有构造器创建对象，
// 若需要抵御这种攻击，可以修改构造器，当他在被要求创建第2个对象时，抛出异常
//第一种方法
class Elvis{
    public static final Elvis INSTANCE = new Elvis();
    private Elvis(){}
    public void leaveTheBuilding(){}
}
//第二种方法
class Elvis2{//饿汉式
    private static final Elvis2 INSTANCE = new Elvis2();
    private Elvis2(){}
    public static Elvis2 getInstance(){ return INSTANCE; }
    public void leaveTheBuilding(){}
}
//为了使利用这其中一种方法实现的Singleton类变成是可序列化的，仅仅在声明中加上“implements Serializable”是不够的。
//为了保证Singleton，必须声明所有的实例域都是瞬时（transient）的，并提供一个readResolve方法，否则每次反序列化的实例时，
// 都会创建一个新的实例。为了防止这种情况，要在Elvis类中加入下面这个readResolve方法：
//readResolve method to preserve singleton property
//private Object readResolve(){
//    //return the one true Elvis and let the garbage collector
//    //take care of the Elvis imperdonstor
//    return INSTANCE;
//}
//第三种实现方法 只需编写一个包含单个元素的枚举类型：
enum Elvis3{//无偿提供了序列化机制，可以抵御反射攻击
    INSTANCE;
    public void leaveTheBuilding(){}
}
//单元素的枚举类型已经称为实现Singleton的最佳方法

/*
第4条：通过私有构造器强化不可实例化的能力
工具类（只包含静态方法和静态域）不希望被实例化，实例对它没有任何意义
因此只要让这个类包含私有构造器，它就不能被实例化了
 */
class UtilityClass{
    //Suppress default constructor for noninstantiability
    private UtilityClass(){
        throw new AssertionError();//不是必须的，但是它可以避免不小心在类的内部调用构造器，它保证该类在任何情况下都不会被实例化
    }
}
//该方法副作用，它使得一个类不能被子类化。
// 所有的构造器都必须显式或隐式地调用超类的构造器，在这种情形下，子类就没有可访问的超类构造器可以调用了

/*
第5条：避免创建不必要的对象
一般来说最好能重用对象
 */
//String s = new String("gfgsfg");//反例
//String s = "gfgsfg";//正例

//class _5_Person{//反例
//    private final Date birthDate; //= new Date();//初始化再说，只是演示
//
//    //Other fileds , method, and constructor omitted
//    //Do not do this!
//    public boolean isBabyBoomer(){//每次调用该方法都会新创建一个Calendar、一个TImeZone和两个Date实例
//        //Unnecessary alloction of expensive object
//        Calendar gmtCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
//        gmtCal.set(1946,Calendar.JANUARY,1,0,0,0);
//        Date boomStart = gmtCal.getTime();
//        gmtCal.set(1964,Calendar.JANUARY,1,0,0,0);
//        Date boomEnd = gmtCal.getTime();
//        return birthDate.compareTo(boomStart) >= 0 &&
//                birthDate.compareTo(boomEnd) < 0;
//    }
//}

//上面例子的改进
class _5_Person2{
    private final Date birthDate;
    {
        Calendar birth = Calendar.getInstance(TimeZone.getTimeZone("GMT"));//这个必须卸载构造代码块或方法区才行（main函数也可以）
        birth.set(1955,Calendar.OCTOBER,1,0,0,0);
        birthDate = birth.getTime();
    }
    //Other fileds , method, and constructor omitted

    //the starting and ending dates of the baby boom
    private static final Date BOOM_START;
    private static final Date BOOM_END;
    static {
        Calendar gmtCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        gmtCal.set(1946,Calendar.JANUARY,1,0,0,0);
        BOOM_START = gmtCal.getTime();
        gmtCal.set(1964,Calendar.JANUARY,1,0,0,0);
        BOOM_END = gmtCal.getTime();
    }
    public boolean isBabyBoom(){
        return birthDate.compareTo(BOOM_START) >= 0 && birthDate.compareTo(BOOM_END) < 0;
    }
}
//改进后只在初始化时创建一次一个Calendar、一个TImeZone和一个Date实例

/*
适配器是指这样一个对象：它把功能委托给一个后备对象，从而为后备对象提供一个可以替代的接口。
由于适配器除了后备对象之外，没有其他的状态信息，所以针对某个给定对象的特定适配器而言，它不需要创建多个适配器实例
适配器模式把一个类的接口变换成客户端所期待的另一种接口，从而使原本因接口不匹配而无法在一起工作的两个类能够在一起工作
 */

/*
第6条：消除过期的对象引用
 */
//can you spot the "memory leakk"?
class _6_Stack{
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    public _6_Stack(){
        elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }
    public void push(Object e){
        ensureCapacity();
        elements[size++] = e;
    }
    public Object pop(){
        if (size == 0)
            throw new EmptyStackException();
        return elements[--size];
    }
    public Object pop_modify(){//修改版
        if (size == 0)
            throw new EmptyStackException();
        Object result = elements[--size];
        elements[size] = null;//消除过期引用
        //清空对象引用应该是一种例外，而不是一种规范行为
        return result;
    }
    /*
    ensure space for at least one more element,roughly doubling the capacity each time the array needs to grow
     */
    private void ensureCapacity(){
        if (elements.length == size)
            elements = Arrays.copyOf(elements,2 * size + 1);
    }
}
//上面的pop方法会造成“内存泄露问题”。从占中弹出来的对象将不会被当作垃圾回收，即使使用栈的程序不再引用这些对象，它们也不会被回收
//因为栈内部维护着对这些对象的过期引用（指永远不会被解除的引用），上面修改版pop_modify可以解决这个问题
//一般而言，只要类是自己管理内存，程序员就应该警惕内存泄漏问题
//内存泄露的另一个常见的来源是缓存
//内存泄露的第三个常见来源是监听器和其他回调

/*
第7条：避免使用终结方法
终结方法（finalizer）通常是不可预测的，也是很危险的，一般情况下是不必要的。
终结方法的缺点在于不能保证会被及时地执行
从一个对象变得不可达开始，到它的终结方法被执行，所花费的这段时间是任意长的，这意味着，注重时间的任务不应该由终结方法来完成
不应该依赖终结方法来更新重要的持久状态
使用终结方法由一个非常严重的性能损失
显式的终止方法通常与try-finally结构结合起来使用，以确保及时终止
对于每一个带有终结方法的非final公有类，应考虑使用 终结方法守卫者 这种方法
 */

//第3章：对所有对象都通用的方法
/*
第8条：覆盖equals时请遵守通用约定
不覆盖equals方法，在这种情况下，类的每个实例都只与它自身相等，如果满足了一下任何一个条件，这就正是所期望的结果
类的每个实例本质上都是唯一的
不关心类是否提供了“逻辑相等”的测试功能
超类已经覆盖了equals，从超类继承过来的行为对于子类也是合适的。
类是私有的或是包级私有的，可以确定他的equals方法永远不会被调用。这时，应该覆盖equals方法，以防它被意外调用
@override public boolean equals(Object o){
    throw new AssertionError();//Mehtod is never called
}

如果类具有自己特有的“逻辑相等”概念（不同于对象等同的概念），而且超类还没有覆盖equals以实现期望的行为，这时就需要覆盖equals方法。
这通常属于“值类”的情形。有一种”值类“不需要覆盖equals方法，即用实例受控确保“每个值至多只存在一个对象”的类。
对于这样的类，逻辑相同与对象等同是一回事。因此Object的equals方法等同于逻辑意义上的equals方法  枚举就属于这种类

在覆盖equals方法时，必须遵守下面的约定：
    equals方法实现了等价关系：
        1：自反性 对于任何非null的引用值x，x.equals(x)必须返回true
        2：对称性
        3：传递性
        4：一致性 未修改对象的情况下，多次调用返回结果一样
    对于任何非null的引用值x，x.equals(null)必须返回false

 */

class Point{
    private final int x;
    private final int y;
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
    @Override public boolean equals(Object o){
        if (!(o instanceof Point))
            return false;
        Point p = (Point)o;
        return p.x == this.x && p.y == this.y;
    }
    //Remainder omitted
}
//假设需要扩展这个类，为一个点添加颜色信息：
class ColorPoint extends Point{
    private final Color color;
    public ColorPoint(int x, int y, Color color){
        super(x,y);
        this.color = color;
    }
//    @Override
//    public boolean equals(Object o) {//这样会失去对称性
//        if (!(o instanceof ColorPoint))
//            return false;
//        return super.equals(o) && ((ColorPoint)o).color == color;
//    }
    //Point p = new Point(1,2);
    //ColorPoint cp = new ColorPoint(1,2,Color.RED);
    //p.equals(cp); true   cp.equals(p);  false
    @Override
    public boolean equals(Object o) {//这样会失去传递性
        if (!(o instanceof Point))
            return false;
        if (!(o instanceof ColorPoint))
            return false;
        return super.equals(o) && ((ColorPoint)o).color == color;
    }
    //ColorPoint p1 = new ColorPoint(1,2,Color.RED);
    //Point p2 = new Point(1,2);
    //ColorPoint p3 = new ColorPoint(1,2,Color.BLUE);
    //p1.equals(p2); true   p2.equals(p3); true    p1.equals(p3) false
    //Remainder  omitted
}
//上面例子结论：我门无法在扩展可实例化的类的同时，既增加新的值组件，同时又保留equals约定，除非愿意放弃面向对象的抽象所带来的优势
//里氏替换原则认为，一个类型的任何重要属性也将适用于他的子类型，因为该类型编写的任何方法，在它的子类型上也应该同样运行的很好
//虽然没有一种令人满意的方法既可以扩展可实例化类，又增加组件，但还是有一种不错的权宜之计。根据第16条建议：复合优先于继承。
//我们不再让ColorPoint扩展Point，而是在ColorPoint中加入一个私有的Point域，以及一个共有的视图方法，
// 此方法返回一个于该有色点处在相同位置的普通Point对象
class ColorPoint_Change{
    private final Point point;
    private final Color color;
    public ColorPoint_Change(int x, int y, Color color){
        if (color == null)
            throw new NullPointerException();
        point = new Point(x, y);
        this.color = color;
    }

    /*
    returns the point-view of this color point
     */
    public Point asPoint(){
        return point;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ColorPoint_Change))
            return false;
        ColorPoint_Change cp = (ColorPoint_Change)obj;
        return cp.point.equals(point) && cp.color.equals(color);
    }
}
//注意：可以在一个抽象类的子类中增加新的值组件，而不会违反equals约定
//域的比较顺序可能会影响到equals方法的性能。为了获得最佳的性能，应该最先比较最有可能不一致的域，或者开销最低的域，
// 最理想的情况是两个条件同时满足的域。
//覆盖equals方法时总要覆盖hashCode
//不要企图让equals方法过于智能
//不要将equals声明中的Object对象替换为其他的类型，@Override注解的用法可以防止犯这种错误


/*
第9条：覆盖equals时总要覆盖hashCode
在每个覆盖了equals方法的类中，也必须覆盖hashCoe方法。如果不这样做的话，就会违反Object.hasnCode的通用约定，
从而导致该类无法结合所有基于散列的集合一起正常运作。
Object.hasnCode的通用约定：
    1：在程序执行期间，只要对象的equals方法的比较操作所用道德信息没有被修改，那么对这同一对象调用多次，
    hashCode方法都必须始终如一地返回同一分整数。在同一个英哟个程序的多次执行过程中，每次执行所返回的整数可以不一致
    2：如果两个对象根据equals方法比较是相等的，那么调用这两个对象中任意一个对象的hashCode方法必须产生同样的整数结果
    3：如果两个对象根据equals方法比较是不相等的，那么调用这两个对象中任意一个对象的hashCode方法，则不一定要产生不同的整数结果
    但是程序员知道，给不相等的对象产生不同的整数结果，有可能提升散列表的性能。
    p41中介绍了一种如何编写好的hashCode方法的方法
 */

/*
第10条：始终覆盖toString
 */

/*
第11条：谨慎地覆盖clone
Cloneable接口的目的是作为对象的一个mixin接口，表明这样的对象允许克隆，遗憾的是，它没有成功的达到这个目的
本条目将告诉你如何实现一个良好的clone方法，并讨论何时适合这样做，同时也简单地讨论了其他的可替换做法。
 */
class PhonrNumber{
    private final short areaCode;
    private final short prefix;
    private final short lineNumber;
    public PhonrNumber(int areaCode, int prefix, int lineNumber){
        rangeCheck(areaCode, 999, "area code");
        rangeCheck(prefix, 999, "prefix");
        rangeCheck(lineNumber, 9999,"line number");
        this.areaCode = (short)areaCode;
        this.prefix = (short)prefix;
        this.lineNumber = (short)lineNumber;
    }
    public void rangeCheck(int arg, int max, String name){
        if (arg < 0 || arg > max)
            throw new IllegalArgumentException(name + " " + arg);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof PhonrNumber))
            return false;
        PhonrNumber p = (PhonrNumber)obj;
        return p.lineNumber == lineNumber
                && p.prefix == prefix
                && p.areaCode == areaCode;
    }

    @Override
    public int hashCode(){
        int result = 17;
        result = 31 + result +areaCode;
        result = 31 + result +prefix;
        result = 31 + result +lineNumber;
        return result;
    }
//    //Lazily initialized, cached hashCode
//    private volatile int hashCode;
//    @Override
//    public int hashCode(){//懒惰初始化哈希值，只初始化一次
//        int result = hashCode;
//        if (result == 0){
//            result = 17;
//            result = 31 + result +areaCode;
//            result = 31 + result +prefix;
//            result = 31 + result +lineNumber;
//            hashCode = result;
//        }
//        return result;
//    }
    @Override
    public String toString(){
        return String.format("(%03d) %03d-%04d",areaCode,prefix,lineNumber);
    }
    @Override
    public PhonrNumber clone(){//使用到了协变返回类型
        try{
            return (PhonrNumber)super.clone();
        }catch (CloneNotSupportedException e){
            throw new AssertionError();
        }
    }
    //因为所需复制的数据都是不可变的，所以可以用上述方法覆盖clone，若是可变的，将会产生灾难性后果
    //下面这个类不能使用上述clone方法
}

/*
若该类使用上面的clone方法，size会得到正确的值，但是他的elements域将引用与原始Stack实例相同的数组
如果调用Stack中唯一的构造器，这种情况将永远不会发生。
实际上，clone方法就是另一个构造器；你必须确保它不会伤害到原始的对象，并确保正确的创建被克隆对象中的约束条件
为了使_11_Stack类中的clone方法正常地工作，它必须要拷贝栈的内部信息。最容易的做法是，在elements数组中递归的调用clone
 */
class _11_Stack{
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    public _11_Stack(){
        elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }
    public void push(Object e){
        ensureCapacity();
        elements[size++] = e;
    }
    public Object pop(){
        if (size == 0)
            throw new EmptyStackException();
        return elements[--size];
    }
    public Object pop_modify(){//修改版
        if (size == 0)
            throw new EmptyStackException();
        Object result = elements[--size];
        elements[size] = null;//消除过期引用
        //清空对象引用应该是一种例外，而不是一种规范行为
        return result;
    }
    /*
 ensure space for at least one more element,roughly doubling the capacity each time the array needs to grow
  */
    private void ensureCapacity(){
        if (elements.length == size)
            elements = Arrays.copyOf(elements,2 * size + 1);
    }
    @Override
    public _11_Stack clone(){//重写clone，如果elements域是final的，上述方案就不能正常工作，因为clone方法是被禁止给elements域赋新值的
        //这是个根本问题：clone架构与引用可变对象的final域的正常用法是不相兼容的
        try {
            _11_Stack result = (_11_Stack) super.clone();
            result.elements = this.elements.clone();
            return result;
        }catch (CloneNotSupportedException e){
            throw new AssertionError();
        }
    }
}
/*
递归地调用clone有时还不够，例如下面的例子
 */
//class _HashTable implements Cloneable{
//    private _Entry[] bukets = ...
//    private static class _Entry{
//        final Object key;
//        Object value;
//        _Entry next;
//        _Entry(Object key, Object value, _Entry next){
//            this.key = key;
//            this.value = value;
//            this.next = next;
//        }
//    }
//    //Remainder omitted
//    //Broken - result in shared internal state
//    @Override
//    public _HashTable clone(){//虽然被克隆对象有它自己的散列桶数组，
//        // 但是，这个数组的引用的链表与原始对象是一样的，从而会引起克隆对象和原始对象中的不确定的行为
//        try {
//            _HashTable result = (_HashTable) super.clone();
//            result.bukets = bukets.clone();
//            return result;
//        }catch (CloneNotSupportedException e){
//            throw new AssertionError();
//        }
//    }
//}

/*
为了修正上面的例子的问题，必须单独地拷贝并组成每个桶的链表
 */
//class _HashTable1 implements Cloneable{
//    private _Entry[] bukets = ...
//    private static class _Entry{
//        final Object key;
//        Object value;
//        _Entry next;
//        _Entry(Object key, Object value, _Entry next){
//            this.key = key;
//            this.value = value;
//            this.next = next;
//        }
//        //Recursively copy the linked list headed by this _Entry
////        _Entry deepCopy(){//这样克隆链表并不是一种很好的方法，因为针对列表中的每个元素，它都要消耗一段栈空间，
////            // 如果链表比较长，容易导致栈溢出。为了避免这种情况，可以在deepCopy中用迭代代替递归
////            return new _Entry(key,value,next == null ? null : next.deepCopy());
////        }
//        _Entry deepCopy(){
//            _Entry result = new _Entry(key,value,next);
//            for (_Entry p = result; p.next != null; p = p.next)
//                p.next = new _Entry(p.next.key, p.next.value, p.next.next);
//            return result;
//        }
//    }
//    //Remainder omitted
//    //Broken - result in shared internal state
//    @Override
//    public _HashTable1 clone(){//虽然被克隆对象有它自己的散列桶数组，
//        // 但是，这个数组的引用的链表与原始对象是一样的，从而会引起克隆对象和原始对象中的不确定的行为
//        try {
//            _HashTable1 result = (_HashTable1) super.clone();
//            result.bukets = new _Entry[bukets.length];
//            for (int i = 0; i < bukets.length; i++){
//                if (bukets[i] != null)
//                    result.bukets[i] = bukets[i].deepCopy();
//            }
//            return result;
//        }catch (CloneNotSupportedException e){
//            throw new AssertionError();
//        }
//    }
//}
/*
另一个实现对象拷贝的好办法是提供一个拷贝构造器或拷贝工厂
拷贝构造器只是一个构造器，它唯一的参数类型是包含该构造器的类
例如 public Yum(Yum yum)
拷贝工厂是类似于拷贝构造器的静态工厂方法
public static Yum newInstances(Yum yum)
拷贝构造器或拷贝工厂都比Cloneable/clone方法具有更多的优势P52
 */


/*
第12条：考虑实现Comparable接口
compareTo是Comparable接口中唯一的方法
compareTo方法不但允许进行简单的等同行比较，而且允许执行顺序比较
类实现Comparable接口，就表明它的实例具有内在的排序关系
 */
//该类依赖于String实现了Comparable接口，去掉了命令行参数列表中的重复参数,并按字母顺序打印出来
class WordList{
    public static void main(String[] args){
        Set<String> s = new TreeSet<String>();
        Collections.addAll(s,args);
        System.out.println(s);
    }
}
//无法在用新的值组件扩展可实例化的类时，同时保持compareTO约定，除非愿意放弃面向对象的抽象优势。
// 针对equals的权宜之计也同样适用于compareTo方法。如果你想为一个实现了Comparable接口的类增加值组件，
// 请不要扩展这个类，而是要编写一个不相关的类，其中包含第一个类的一个实例，然后提供一个 视图 方法返回这个实例
//这样既可以让你自由地在第二个类上实现compareTo方法，同时也允许它的客户端在i要的时候，把第二个类的实例视同第一个实例

/*
第4章：类和接口
 */

/*
第13条：使类和成员的可访问性最小化
设计良好的模块会隐藏所有的实现细节，把他的API与它的实现清晰的隔离开来
信息隐藏（具体实现细节隐藏）可以有效的解除组成系统的各模块之间的耦合关系
第一个规则：尽可能地使每个类或者成员不被外界访问
如果方法覆盖了超类中的一个方法，子类中的访问级别就不允许低于超类中的访问级别
如果一个类实现了一个接口，那么接口中所有的类方法在这个类中都必须被声明为公有的
实例域决不能公有的     包含公有可变域的类并不是线程安全的
注意，长度非0的数组总是可变的，所以，类具有公有的静态final数组域，或者返回这种域的访问方法，这几乎总是错误的。如果类具有这样的域或者
访问方法客户端将能修改数组中的内容。
//protential security hole
public static final Thing[] VALUE = {...};
两种修改方法：
//1：将公有数组变为私有，并增加一个公有的不可变列表
private static final Thing[] PRIVATE_VALUE = {...};
public static final List<Thing> VALUE = Collections.unmodifiableList(Arrays.asList(PRIVATE_VALUE));
//2：将数组变为私有的，并添加一个公有方法，它返回私有数组的一个备份
private static final Thing[] PRIVATE_VALUE = {...};
public static final  Thing[] VALUE(){
    return PRIVATE_VALUE.clone();
}
 */

/*
第14条：在公有类中使用访问方法而非公有域
 */
//退化类(Degenerate classes)，没什么作用，只是用来集中实例域
//Degenerate classes like this should not be public
class Point3{
    public double x;
    public double y;
}

//上面类应该用下面写法代替
class Point4{
    private double x;
    private double y;
    public Point4(double x, double y){
        this.x = x;
        this.y = y;
    }
    public double getX(){ return x;}
    public double getY(){ return y;}
    public void setX(double x){this.x = x;}
    public void setY(double y){this.y = y;}
}
//如果类可以在它所在的包的外部进行访问，就提供访问方法，以保留将来改变类的内部表示法的灵活性
//如果类使包级私有的，或者是私有的嵌套类，直接暴露它的数据域没有本质的错误。
//让公有类直接暴露虽然从来都不是种好方法，但是如果域是不可变的，这种做法的危害就比较小一些。如果不改变类的API，就无法改变这种类的表示法，
//当域被读取的时候，你也无法采取辅助的行动，但是可以加强约束条件
//例如，这个类确保了每个实例都表示一个有效的时间

//Pubic class with exposed immutable field  -questionable
final class _Time{//这里应该是public
    private static final int HOUR_PER_DAY = 24;
    private static final int MINUTES_PER_HOUR = 60;
    public final int hour;
    public final int minute;
    public _Time(int hour, int minute){
        if (hour < 0 || hour >= HOUR_PER_DAY)
            throw new IllegalArgumentException("Hour: " + hour);
        if (minute < 0 || minute >= MINUTES_PER_HOUR)
            throw new IllegalArgumentException("Min: " + minute);
        this.hour = hour;
        this.minute = minute;
    }
}
//总之，公有类永远都不应该暴露可变的域

/*
第15条：使可变性最小化
不可变类只是其实例不能被修改的类。每个实例中包含的所有信息都必须在创建该实例的时候就提供，并在对象的整个生命周期内固定不变。
不可变的类：String、基本类型的包装类、BigInteger和BigDecimal。

为了使类成为u不可变，需要遵守下面5条规则：
    1：不要提供任何会修改对象状态的方法。
    2：保证类不会被扩展。  为了防止子类化，一般做法是使中国类称为final的
    3：使所有的域都是final的。
    4：使所有的域都成为私有的。
    5：确保对于任何可变组件的互斥访问。  如果类具有指向可变对象的域，则必须保证该类的客户端无法获得指向这些对象的引用。
 */

final class _Complex{//复数的类
    public static final _Complex ZERO = new _Complex(0,0);
    //可以使用静态工厂的方法共享已有的实例，而不必为创建新的实例，从而降低内存占用和垃圾回收的成本
    public static final _Complex ONE = new _Complex(1,0);
    public static final _Complex I = new _Complex(0,1);
    public static _Complex getZero(){//静态工厂方法
        return ZERO;
    }
    private final double re;
    private final double im;
    public _Complex(double re, double im){
        this.re = re;
        this.im =im;
    }
    //Accessors（访问器） with no corresponding mutators(修改器)
    public double realPart(){return re;}
    public double imaginaryPart(){return im;}
    //注意下面这些算术运算是如何创建并返回新的_Complex实例，而不是修改这个实例。
    //大多数不可变类都使用了这种模式。它被称为  函数的做法，因为这些方法返回了一个函数的结果，
    // 这些函数对操作数进行运算但并不修改它
    //与之相对应的更为常见的是 过程的或者 命令式的做法，使用这些方法使将一个过程作用在它们的操作数上，会导致它的状态发生改变
    public _Complex add(_Complex c){
        return new _Complex(re + c.re, im +c.im);
    }
    public _Complex subtract(_Complex c){
        return new _Complex(re - c.re, im - c.im);
    }
    public _Complex multiply(_Complex c){
        return new _Complex(re * c.re - im * c.im, im * c.re + re * c.im);
    }
    public _Complex divide(_Complex c){
        double temp = c.re * c.re +c.im * c.im;
        return new _Complex((re *c.re + im * c.im)/temp, (im * c.re - re * c.im)/temp);
    }
    @Override
    public boolean equals(Object obj){
        if (obj == this)
            return true;
        if (!(obj instanceof _Complex))
            return false;
        _Complex c = (_Complex)obj;
        return Double.compare(re, c.re) == 0 && //因为为double类型，所以用compare方法
                Double.compare(im, c.im) == 0;
    }
    @Override
    public int hashCode(){
        int result = 17 + hashDouble(re);
        result = 31 + result + hashDouble(im);
        return result;
    }
    private int hashDouble(double val){
        long longBits = Double.doubleToLongBits(re);//根据 IEEE 754 浮点双精度格式 ("double format") 位布局，返回指定浮点值的表示形式。
        return (int)(longBits ^ (longBits >>> 32));//  无符号右移，忽略符号位，空位都以0补齐
    }
    @Override
    public String toString(){
        return "(" + re + " + " + im + "i)";
    }
}

//不可变对象比较简单
//不可变对象本质上是线程安全的，它们不要求同步。所以不可变对象可以被自由地共享
//不可变对象可以被自由地共享导致的结果是，永远不需要进行 保护性拷贝，因此你不需要，也不应该为不可变的类提供clone方法或拷贝构造器
//不仅可以共享不可变对象，甚至也可以共享它们的内部信息
//不可变对象为其他对象提供了大量的构件，无论是可变的还是不可变的对象。
//不可变类真正唯一的缺点是，对于每个不同的值都需要一个单独的对象。
//让不可变的类变成final的另一种方法就是，让类的所有构造器都变成私有的或者包级私有的，并添加公有的静态工厂来代替公有的构造器
//这样使类不能被子类化

//Immutable class with static factories instead of constructor
class _2Complex{
    private final double re;
    private final double im;
    private _2Complex(double re, double im){
        this.re = re;
        this.im = im;
    }
    public static _2Complex valueOf(double re, double im){
        return new _2Complex(re, im);
    }
    public static _2Complex valueOfPolar(double r, double theta){//极坐标
        return new _2Complex(r * Math.cos(theta), r * Math.sin(theta));
    }
    //....

}
//BigInteger、BigDecimal是不可变类，但是它可以子类化，若你在编写一个类，它的安全性依赖于BigInteger、BigDecimal参数的不可变性，
// 就必须就行检查，以确定这个参数是“真正的”BigInteger、BigDecimal，而不是不可信任子类的实例，如果是后者，
// 就必须在假设它可能是可变的前提下对它进行保护性拷贝：
//    public static BigInteger safeInstance(BigInteger val){
//        if (val.getClass() != BigInteger.class)
//            return new BigInteger(val.toByteArray());
//        return val;
//    }

//如果类不能被做成是不可变的，仍然应该尽可能地限制它的可变性。


/*
第16条：复合优于继承
与方法调用不同，继承打破了封装性。
 */
//Broken - Inappropriate use of inheritance
class _InstrumentedHashSet<E> extends HashSet<E>{//继承HashSet，并计算曾经加入的所有的元素的个数
    //the number of attepted element insertions
    private int addCount = 0;
    public _InstrumentedHashSet(){}
    public _InstrumentedHashSet(int initCap, float loadFactor){
        super(initCap, loadFactor);
    }

    @Override
    public boolean add(E e) {
        addCount++;
        return super.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return super.addAll(c);
    }
    public int getAddCount(){
        return addCount;
    }
}
//HashSet 的addAll方法是在它的add方法上实现的，因此上面的例子调用addAll方法时，返回结果不对，只需要去掉addAll方法的覆盖即可
//新类中的每个实例方法都可以调用被包含的现有类实例中对应的方法，并返回它的结果，这被称为转发(forwarding)。
// 新类中的方法被称为转发方法(forwarding method)

//Reusable forwarding class(可重用转发类)
class ForwardingSet<E> implements Set<E>{
    private final Set<E> s;
    public ForwardingSet(Set<E> set){this.s = set;}
    public void clear(){s.clear();}
    public boolean contains(Object o){return s.contains(o);}
    public boolean isEmpty(){return s.isEmpty();}
    public int size(){return s.size();}
    public Iterator<E> iterator(){return s.iterator();}
    public boolean add(E e){return s.add(e);}
    public boolean remove(Object o){return s.remove(o);}
    public boolean containsAll(Collection<?> c){return s.containsAll(c);}
    public boolean addAll(Collection<? extends E> c){return s.addAll(c);}
    public boolean removeAll(Collection<?> c){return s.removeAll(c);}
    public boolean retainAll(Collection<?> c){return s.retainAll(c);}
    public Object[] toArray(){return s.toArray();}
    public <T> T[] toArray(T[] a){return s.toArray(a);}

    @Override
    public boolean equals(Object obj) {
        return s.equals(obj);
    }

    @Override
    public int hashCode() {
        return s.hashCode();
    }

    @Override
    public String toString() {
        return s.toString();
    }
}
//Wrapper class - uses composition in place of ineritance
class _2InstrumentedSet<E> extends ForwardingSet<E>{
    private int addCount = 0;
    public _2InstrumentedSet(Set<E> s){super(s);}

    @Override
    public boolean add(E e) {
        addCount++;
        return super.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return super.addAll(c);
    }
    public int getAddCount(){
        return addCount;
    }

    public static void main(String[] args){
        _2InstrumentedSet<String> s = new _2InstrumentedSet<String>(new HashSet<String>());
        s.addAll(Arrays.asList("snap", "Crackle", "Pop"));
        System.out.println(s.getAddCount());
    }
}
//Set接口的存在使得_2InstrumentedSet类的设计称为可能，因为Set几口保存了HashSet类的功能特性
//从本质上讲，这个类把一个Set转变成了另一个Set，同时增加了计数的功能
//前面提到的基于继承的方法只适用于单个具体的类，并且对于超类中所支持的每个构造器都要求有一个单独的构造器，与此不同的是，
//这里的包装类（wrapper class）可以被用来包装任何Set实现，并且可以结合任何先前存在的构造器一起工作
//包装类不适合用在回调框架中。在回调框架中，对象把自身的引用传递给其他对象，用于后续的调用。


/*
第17条：要么为继承而设计，并提供文档说明，要么就禁止继承。
对于为了继承而设计的类，唯一的测试方法就是编写子类。
为了继承，类还必须遵守其他一些约束。构造器绝不能调用可被覆盖的方法，无论是直接调用还是间接调用。
无论是clone还是readObject，都不可以调用可覆盖的方法，不管是以直接还是间接的方式。
为了继承而设计的类，对这个类会有一些实质性的限制。
对于那些并非为了安全地进行子类化而设计和编写文档的类，要禁止子类化。
有两种方法可以禁止子类化：比较容易的是把这个类声明为final的。另一种方法是把所有的构造器都变成私有的，或者包级私有的，
并增加一些公有的静态工厂来代替构造器。
如果具体的类没有实现标准的接口，那么禁止继承可能会给有些程序员带来不便。
你可以机械地消除类中可覆盖方法的自用特性，而不改变它的行为。将每个可覆盖方法的代码体移到一个私有的“辅助方法”中，
并且让每个可覆盖的方法调用私有辅助方法。然后，用“直接调用可覆盖方法的私有辅助方法”来代替“可覆盖方法的每个自用调用”。
 */

/*
第18条：接口优于抽象类
接口和抽象之间最明显的区别在于：抽象类允许包含某些方法的实现，但是接口则不允许。一个更明显的区别在于，为了实现由抽象类定义的类型，
类必须成为抽象类的一个子类。任何一个类，只要它定义类所有的方法，并遵守通用约定，它就被允许实现一个接口，而不管这个类是出于类层次的那个位置。
现有的类可以很容易被更新，已实现新的接口。
接口是定义mixin（混合类型）的理想选择。
mixin是指这样的类型：类除了实现它的“基本类型”之外，还可以实现这个mixin类型，以表明它提供了某些可选择的行为。
抽象类不能被用于定义mixin
接口允许我们构造非层次结构的类型框架。
接口使得安全地增强类的功能成为可能。
通过对你导出的每个重要接口都提供一个抽象的骨架实现类，把接口和抽象类的优点结合起来。接口的作用仍然是定义类型，
但是骨架实现类接管了所有与接口实现相关的工作
按照惯例，骨架实现被称为AbstractInterface，这里的Interface是指所实现的接口的名字
 */

//Conserte implementation built atop skeletal implementation
//完整的静态工厂方法，它包含一个完整的、功能全面的List实现
class _18_AbstractInterface{
    static List<Integer> intArrayAsList(final int[] a){
        if (a == null)
            throw new NullPointerException();
        return new AbstractList<Integer>() {

            public Integer get(int index) {
                return a[index];
            }

            @Override
            public Integer set(int i, Integer val){
                int oldVal = a[i];
                a[i] = val;
                return oldVal;
            }
            @Override
            public int size() {
                return a.length;
            }
        };
    }
}
/*
骨架实现的美妙之处在于，它们为抽象类提供了实现上的帮助，但是又不强加“抽象类被用作类型定义时”所特有的严格限制。
对于接口的大多数实现来讲，扩展骨架实现类是个很显然的选择，但并不是必需的。如果预置的类无法扩展骨架实现类，这个
类始终可以手工实现这个接口。此外，骨架实现类仍然有助于接口的实现。实现了这个接口的了可以把对于接口方法的调用转发
到内部私有类的实例上，这个内部私有类扩展了骨架实现类/这中方法被称为  模拟多重继承。
 */
//Skeletal Implementation骨架实现
//Map.Entry接口的骨架实现
//找出基本方法，其他方法则可以根据它们来实现，这些基本方法将成为骨架实现类中的抽象方法
abstract class AbstractMapEntry<K, V> implements Map.Entry<K, V>{
    //primitive operations
    public abstract K getKey();
    public abstract V getValue();

    //Entries in modifiable maps must override this method
    public V setValue(V value){
        throw new UnsupportedOperationException();
    }

    //Implements the general contract of Map.Entry.equals
    @Override
    public boolean equals(Object obj){
        if (obj == this)
            return true;
        if (!(obj instanceof Map.Entry))
            return false;
        Map.Entry<?, ?> arg = (Map.Entry)obj;
        return equals(getKey(), arg.getKey()) &&
                equals(getValue(),arg.getValue());
    }
    private static boolean equals(Object obj1, Object obj2){
        return obj1 == null ? obj2 == null : obj1.equals(obj2);
    }

    //Implements the general contract of Map.Entry.hashCode
    @Override
    public int hashCode(){
        return hashCode(getKey()) ^ hashCode(getValue());
    }
    public static int hashCode(Object obj){
        return obj == null ? 0 : obj.hashCode();
    }
}
//抽象类的演变比接口的演变要容易得多，如果在后续的发行版本中，虚妄在抽象类中增加新的方法，始终可以增加具体方法，然后该
//抽象类的所有实现都将体统这个新方法。对于接口这样做是行不通的。
//一般来说要想在公有接口中增加方法，而不破坏实现这个接口的所有现有的类，这是不可能的。
//接口一旦被公开发行，并且已被广泛实现，再想改变这个接口几乎是不可能的。
//当演变的容易性比灵活性和功能更为重要的时候，应该使用抽象类。

/*
第19条：接口只用于定义类型
当类实现接口时，接口就充当可以引用这个类的实例的类型
常量接口中没有任何方法，只包含静态final域
接口应该只被用来定义类型，它们不应该被用来导出常量。
 */

/*
第20条：类层次优于标签类
 */
//标签类 tagged class 标签类过于冗长、容易出错，并且效率低下
class Figure{
    enum Shape{RECTANGLE, CIRCLE}
    //Tag field  -- the shape of this figure
    final Shape shape;

    //These fields are used only if shape if RECTANGLE
    double length;
    double width;
    //These fields are used only if shape if CIRCLE
    double radius;
    Figure(double radius){
        shape = Shape.CIRCLE;
        this.radius = radius;
    }
    Figure(double length, double width){
        shape = Shape.RECTANGLE;
        this.length = length;
        this.width = width;
    }
    double area(){
        switch (shape){
            case RECTANGLE:
                return length * width;
            case CIRCLE:
                return Math.PI * radius *radius;
            default:
                throw new AssertionError();
        }
    }
}
//标签类正是类层次的一种简单的效仿
//class hierarchy replacement for a tagged class
abstract class Figure_hier{//可以定义为接口
    abstract double area();
}
class Circle extends Figure_hier{
    final double radius;
    Circle(double radius){
        this.radius = radius;
    }
    double area(){
        return Math.PI * radius * radius;
    }
}
class Rectangle extends Figure_hier{
    final double lenggh;
    final double width;
    Rectangle(double lenggh, double width){
        this.lenggh = lenggh;
        this.width = width;
    }
    double area(){
        return lenggh * width;
    }
}

class Square extends Rectangle{
    Square(double side){
        super(side,side);
    }
}
//类层次可以用来反映类型之的本质上的层次关系


/*
第21条：用函数对象表示策略
Java没有提供函数指针，但是可以用对象引用实现同样的功能
调用对象上的方法通常是执行该对象上的某项操作。然而，我们也可能定义这样一种对象，它的方法执行其他对象（这些对象被显示传递给这些方法）
上的操作。如果一个类仅仅导出这样的一个方法，它的实例实际上就等同于一个指向该方法的指针。这样的实例被称为 函数对象
 */
//考虑下面例子
class StringLengthComparator{
    public int compare(String s1, String s2){
        return s1.length() - s2.length();
    }
}
/*
指向StringLengthComparator对象的引用可以被当作是一个指向该比较器的“函数指针”，可以在任意对字符串上被调用。换句话说，
StringLengthComparator实例就是用于字符串比较操作的 具体策略。
作为典型的具体策略，StringLengthComparator类是无状态的：它没有域，所以这个类的所有实例在功能上都是相互等价的。因此，
Singleton是非常合适的，可以节省不必要的对象创建开销
 */
class _2StringLengthComparator{
    private _2StringLengthComparator(){}
    public static final _2StringLengthComparator INSTANCE = new _2StringLengthComparator();
    public int compare(String s1, String s2){
        return s1.length() - s2.length();
    }
}
/*
为了把_2StringLengthComparator实例传递给方法，需要适当的参数类型。使用_2StringLengthComparator并不好，因为客户端将无法
传递任何其他的比较策略。相反我们需要定义一个_Comparator接口，并修改_2StringLengthComparator来实现这个接口。换句话说，
我们在设计具体的策略类时，还需要定义一个策略接口，如下
 */
//strategy interface
interface _Comparator<T> {
    public int compare(T t1, T t2);
}
class _3StringLengthComparator implements _Comparator<String>{
    private _3StringLengthComparator(){}
    public static final _3StringLengthComparator INSTANCE = new _3StringLengthComparator();
    public int compare(String s1, String s2){
        return s1.length() - s2.length();
    }
}
//具体策略往往使用匿名类声明
class test_3StringLengthComparator{
    public static void main(String[] args){
        String[] stringArray = {"aff","fagfaga","AGgaga","agfgagas"};

        Arrays.sort(stringArray, new java.util.Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
               return o1.length() - o2.length();
            }
        });
    }
}
/*
但是注意，以这种方式使用匿名类时，将会在每次执行调用的时候创建一个新的实例。如果它被多次重复执行，
考虑函数对象存储到一个私有的静态final域里，并重用它。这样做的另一个好处是，可以为这个函数对象取一个有意义的域名称。
因为策略接口被用作所有具体策略实例的类型，所以我们并不需要为了导出具体策略，而把具体策略类做成公有的。相反，“宿主类”
还可以导出公有的静态域（或者静态工长方法），器类型为策略接口，具体的策略类可以是宿主类的私有嵌套类。
 */
//exporting a conceret strategy
class Host{
    private static class StrlenCmp implements _Comparator<String>,Serializable{
        public int compare(String s1, String s2){
            return s1.length() - s2.length();
        }
    }
    //returned comparator is serializable
    public static final _Comparator<String> STRING_LENGTH_COMPARATOR = new StrlenCmp();

    //bulk of class omitted
}
/*
简而言之，函数指针的主要用途就是实现策略模式。为了在Java中实现这种模式，要声明一个接口来表示该策略，并且为每个具体策略
声明一个实现了该接口的类。当一个具体策略只被调用一次时，通常使用匿名类来声明和实例化这个具体策略类。当一个具体策略是
设计用来重复使用的时候，它的类通常就要被实现为私有的静态成员类，并通过公有的静态final域被导出，其类型为该策略接口。
 */


/*
第22条：优先考虑静态成员类
嵌套类是指被定义在另一个类内部的类。嵌套类存在的目的应该只是为他的外部类提供服务。如果嵌套类将来可能会用于其他的某个环境中，
它就应该是顶层类。
嵌套类又四种：静态成员类，非静态成员类，匿名类和局部类。 除了第一种之外，其他三种都被称为内部类。
本条目将告诉声明时候应该使用那种嵌套类，以及这样做的原因。
静态成员类是最简单的一种嵌套类。最好把它看作是普通的类，只是碰巧被声明在另一个类内部而已，它可以访问外部类的所有成员，
包括那些声明为私有的成员。静态成员类是外围类的一个静态成员，与其他静态成员一样，也遵守同样的可访问性规则。如果被声明为私有的，
它就只能在外围类的内部才可以被访问，等等。
静态成员类的一种常用用法是作为公有的辅助类，仅当与它的外部类一起使用时才有意义。

非静态成员类的每个实例都隐含着与外围类的一个外围实例相关联。在非静态成员类的实例方法内部，可以调用外围实例上的方法，
或者利用修饰过的this构造获得外围实例的引用。[outerClass].this      outerClass 为外部类的类名。
如果嵌套类的实例可以在它外围类的实例之外独立存在，这个嵌套类就必须是静态成员类。在没有外围实例的情况下，要创建非静态成员类的实例是不可能的

非静态成员类的一种常见用法是定义一个Adapter，它允许外部类的实例被看作是类一个不相关的类的实例。
如果声明成员类不要求访问外部实例，就要始终把static修饰符放在它的声明中，使它称为静态成员类，而不是非静态成员类。如果省略了static修饰符，
则每个实例都将包含一个额外的指向外围对象的引用。
如果没有外围实例的情况下，也需要分配实例，就不能使用非静态成员类，因为非静态成员类的实例必须要有一个外围实例。

私有静态成员类的一种常见用法是用来代表外围类所代表的对象的组件。

匿名类的一种常见用法是动态地创建函数对象。
 */



/*
第5章：泛型
 */
/*
第23条：请不要在新代码中使用原生态类型
每个泛型都定义一个原生态类型，即不带任何实际类型参数的泛型名称
List<String>是原生态类型List的一个子类型，而不是参数化类型List<Object>的子类型。因此如果使用像List这样的原生态类型，
就会石雕类型安全性，但是如果使用像List<Object>这样的参数化类型，则不会。
在类文字中必须使用原生态类型
 */
/*
static int numElementsInCommon(Set s1,Set s2){//原生态
    int result = 0;
    for(Object o1:s1）
        if(s2.contains(o1))
            result++；
     return result;
}
 */
/*
static int numElementsInCommon(Set<?> s1,Set<?> s2){//泛型
    int result = 0;
    for(Object o1:s1）
        if(s2.contains(o1))
            result++；
     return result;
}
 */
/*
if(o instanceof Set){
    Set<?> m = (Set<?>)o;
    ...
}
 */

/*
第24条：消除非受检警告
用泛型编程时，会遇到许多编译期警告：非受检强制转化警告、非受检方法调用警告、非受检普通数组创建警告，以及非受检转换警告
要尽可能地消除每一个非受检警告
如股票无法消除警告，同时可以证明引起警告的代码是类型安全的，可以用@SuppressWarnings("ubchecked")注解来禁止这条警告
SuppressWarnings注解可以用在任何力度的级别中。应该始终在尽可能小的范围中使用SuppressWarnings注解。
永远不要在整个类上使用SuppressWarnings
每当使用SuppressWarnings("ubchecked")注解时，都要添加一条注释，说明为什么这么做是安全的。P104
 */


/*
第25条：列表优于数组
数组与泛型相比，有两个重要的不同点。第一，数组是协变的。即如果Sub为Super的子类型，匿名数组类型Sub[]就是Super[]的子类型。
相反，泛型则是不可变的：对于任意两个不同类型的Type1和Type2，List<Type1>既不是List<Type2>的子类型，也不是List<Type2>的超类型。
数组与泛型之间的第二大区别在于，数组是具体化的，因此数组会在运行时才会知道并检查它们的元素类型约束。
相比之下，泛型则是通过擦除来实现的。因此泛型只在编译时强化它们的类型信息，并在运行时丢弃（或者擦除）它们的元素类型信息。
擦除就是使泛型可以与没有使用泛型的代码随意进行互用。
因为上述2个原因，数组和泛型不能很好地混合使用。创建泛型数组是非法的，因为它不是类型安全的。
从技术的角度来说，像E，List<E>和List<String>这样的类型应称作不可具体化的类型。
直观地说，不可具体化类型是指其运行时表示法包含的信息比它的编译时表示法包含的信息更少的类型。
唯一可具体化的参数化类型是无限制的通配符类型，如List<?>和Map<?,?>.

虽然不常用，但是创建无限制通配符类型的数组是合法的

当你得到泛型数组创建错误时，最好的解决方法通常是优于使用集合类型List<E>,而不是数组类型E[]/
 */

//编写reduce方法，使用函数apply来处理这个列表
//reduction without generics, and with concurrency flaw 没有泛型时的代码
//static Object reduce(List list, Fuction f, Object initial){
//    synchronized (list){
//        Object result = initial;//list为空时返回initial
//        for (Object o : list)
//            result = f.apply(result, o);//一般不要在同步区域调用“外来的方法”
//        return result;
//    }
//}
//interface Fuction{
//    Object apply(Object arg1, Object arg2);
//}

//Java1.5发行版本之前，要这么做一般是利用List的toArray方法
//reduction without generics or concurrency flaw
//static Object reduce(List list, Fuction f, Object initVal){
//    Object[] snapshot = list.toArray();//Lock list internally
//    Object result = initVal;
//    for (Object e : snapshot)
//        result = f.apply(result, e);
//    return result;
//}

//如果试图通过泛型完成这一点，就会遇到泛型数组的麻烦
//接口泛型版
//interface Function<T>{
//    T apply(T arg1, T arg2);
//}
//native generic version of reduction - won't compile
//static <E> E reduce(List<E> list, Fuction f, E initVal){
////    E[] snapshot = list.toArray();//Lock list
//    E[] snapshot = (E[]) list.toArray();//Lock list
//    E result = initVal;
//    for (E e : snapshot)
//        result = f.apply(result, e);
//    return result;
//}
//虽然强制转换后消除了编译错误，但是它无法在运行时检查转换的安全性，很危险。
//下面是对上面方法的正确改写
//List-besed generic reduction
//static <E> E reduce(List<E> list, Fuction f, E initVal){
//    List<E> snapshot;
//    synchronized (list){
//        snapshot = new ArrayList<E>(list);
//    }
//    E result = initVal;
//    for (E e : snapshot)
//        result = f.apply(result, e);
//    return result;
//}
//虽然冗长，但是没有问题了。


/*
第26条：优先考虑泛型
 */
//考虑这个堆栈实现
class _26_Stack{
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    public _26_Stack(){
        elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }
    public void push(Object e){
        ensureCapacity();
        elements[size++] = e;
    }
    public Object pop(){
        if (size == 0)
            throw new EmptyStackException();
        Object result = elements[--size];
        elements[size] = null;
        return result;
    }
    public Object pop_modify(){//修改版
        if (size == 0)
            throw new EmptyStackException();
        Object result = elements[--size];
        elements[size] = null;//消除过期引用
        //清空对象引用应该是一种例外，而不是一种规范行为
        return result;
    }
    public boolean isEmpty(){
        return size == 0;
    }
    /*
    ensure space for at least one more element,roughly doubling the capacity each time the array needs to grow
     */
    private void ensureCapacity(){
        if (elements.length == size)
            elements = Arrays.copyOf(elements,2 * size + 1);
    }
}
//转换为泛型版本
class _26_FanXingStack<E>{
    private E[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
//    public _26_FanXingStack(){
//        elements = new E[DEFAULT_INITIAL_CAPACITY];
//    }//报错 不能创建不可具体化的类型的数组
    /*
    解决不能创建不可具体化的类型的数组问题有两种方法：第一种直接绕过创建泛型数组的禁令，创建一个Object的数组，
    并将他转换成泛型数组类型。这样错误会消除，但是会产生一条警告，这种用法是合法的，但是不是类型安全的。
    编译器不会证明程序的安全性，你自诩必须确保未受检的转换不会危及到程序的安全性。


     */
    //第一种方法
    //The elements array will contain onle E instaces from push(E),
    //this is sufficient to ensure type safety,but the runtime
    // type of the array won't be E[];it will always be Object[]
    @SuppressWarnings("unchecked")
    public _26_FanXingStack(){
        elements = (E[])new Object[DEFAULT_INITIAL_CAPACITY];
    }//报错 不能创建不可具体化的类型的数组
    public void push(E e){
        ensureCapacity();
        elements[size++] = e;
    }

    public Object pop(){
        if (size == 0)
            throw new EmptyStackException();
        E result = elements[--size];
        elements[size] = null;
        return result;
    }
    public E pop_modify(){//修改版
        if (size == 0)
            throw new EmptyStackException();
        E result = elements[--size];
        elements[size] = null;//消除过期引用
        //清空对象引用应该是一种例外，而不是一种规范行为
        return result;
    }
    public boolean isEmpty(){
        return size == 0;
    }
    /*
    ensure space for at least one more element,roughly doubling the capacity each time the array needs to grow
     */
    private void ensureCapacity(){
        if (elements.length == size)
            elements = Arrays.copyOf(elements,2 * size + 1);
    }
}

//  第二种方法就是，将elements域的类型从E[]改为Object[]
class _26_FanXingStack2<E>{
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
        public _26_FanXingStack2(){
        elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }//报错 不能创建不可具体化的类型的数组
    //第一种方法
    //The elements array will contain onle E instaces from push(E),
    //this is sufficient to ensure type safety,but the runtime
    // type of the array won't be E[];it will always be Object[]
//    @SuppressWarnings("unchecked")
//    public _26_FanXingStack2(){
//        elements = (E[])new Object[DEFAULT_INITIAL_CAPACITY];
//    }//报错 不能创建不可具体化的类型的数组
    public void push(E e){
        ensureCapacity();
        elements[size++] = e;
    }
    public Object pop(){
        if (size == 0)
            throw new EmptyStackException();
//        E result = /elements[--size];//这时这里会报错
        @SuppressWarnings("unchecked")
        E result = (E) elements[--size];//强制转换后会变成一条警告,和上面一样，需要自己确保正确性
        elements[size] = null;
        return result;
    }
    public E pop_modify(){//修改版
        if (size == 0)
            throw new EmptyStackException();
        @SuppressWarnings("unchecked")
        E result = (E)elements[--size];
        elements[size] = null;//消除过期引用
        //清空对象引用应该是一种例外，而不是一种规范行为
        return result;
    }
    public boolean isEmpty(){
        return size == 0;
    }
    /*
    ensure space for at least one more element,roughly doubling the capacity each time the array needs to grow
     */
    private void ensureCapacity(){
        if (elements.length == size)
            elements = Arrays.copyOf(elements,2 * size + 1);
    }
}

/*
第27条：优先考虑泛型方法
静态工具方法尤其适合泛型化。
 */
//返回两个集合的联合
class _27_FanXing{
    public static <E> Set<E> union(Set<E> s1, Set<E> s2){
        //该方法存在限制，三个集合的类型不许全部相同，利用有限制的通配符类型，可以使这个方法变得更加灵活。
        Set<E> result = new HashSet<E>(s1);
        result.addAll(s2);
        return result;
    }
//    public static <K,V> HashMap<K,V> newHashMap(){
//        return new HashMap<K, V>();
//    }
//    Map<String, List<String>> anagrams = newHashMap();
}

//类型参数出现在了变量声明的左右两边，显得有些冗余
//Map<String, List<String>> anagrams = new HashMap<String, List<String>>();
//为了消除这种冗余，可以编写一个泛型静态工厂方法，与想要使用的每个构造器相对应
//public static <K,V> HashMap<K,V> newHashMap(){
//    return new HashMap<K, V>();
//}
//Map<String, List<String>> anagrams = newHashMap();
//虽然很少见，但是，通过某个包含该类型参数本身的表达式来限制类型参数是允许的。
//Using a recursive type bound to express mutual comparability
//public static <T extends Comparable<T>> T max(List<T> list){
//    Iterator<T> i = list.iterator();
//    T result = i.next();
//    while (i.hasNext()){
//        T t = i.next();
//        if (t.compareTo(result) > 0)
//            result = t;
//    }
//    return result;
// }
//<T extends Comparable<T>> 读作 “针对可以与自身进行比较的每个类型T”


/*
第28条：利用有限通配符来提升API的灵活性
有时候我们需要的灵活性要比不可变类型所提供的更多。
确定了子类型后，每个类型便都是自身的子类型，即便它没有将自身扩展  <? extends E>      <? super E>
为了获取最大限度的灵活性，要在表示生产者或消费者的输入参数上使用通配符类型。如果某个输入参数既是生产者又是消费者，
那么通配符类型就没有好处了：因为需要的是严格的类型匹配。
PECS表示producer-extends  ,  consumer-super
换句话说，如果参数化类型表示一个T生产者，就用<? extends T>;如果他表示一个T消费者，就用<? super T> eg:
static <E> E reduce(List<E> list, Function<E> f, E initVal)应该为
static <E> E reduce(List<? extends E> list, Function<E> f, E initVal)

不要用通配符类型作为返回类型

如果类的用户必须考虑通配符类型，类的API或许就会出错。

Set<Integer> integers = ...
Set<Double> doubles = ...
Set<Number> numbers = union(integers,doubles);//该句会报错，编译器不能推断出你希望它拥有的类型
//解决办法
Set<Number> numbers = Union.<Number>union(integers,doubles);//添加显式的类型参数

public static <T extends Comparable<T>> T max(List<T> list)修改为
public static <T extends Comparable<? super T>> T max(List<? extends T> list){
//    Iterator<T> i = list.iterator();//修改为这个后，这句话会报错，因为list不是List<T>,
                                      //因此它的iterator方法没有返回Iterator<T>,它返回T的某个子类型的一个iterator
    Iterator<? extends T> i = list.iterator();//这样就没问题了
//    T result = i.next();
//    while (i.hasNext()){
//        T t = i.next();
//        if (t.compareTo(result) > 0)
//            result = t;
//    }
//    return result;
// }
//因为T的comparable消费T实例，因此Comparable<T>被Comparable<? super T>代替
comparable始终是消费者，因此使用时始终应该是Comparable<? super T>优于Comparable<T>，对于comparator也一样，
因此使用时始终是Comparator<? super T>优于Comparator<T>

一般来说，如果类型参数只在方法声明中出现一次，就可以用无限通配符取代它。
public static boid swap(List<?> list, int i, int j){
    list.set(j, list.set(j,list.get(i)));//有错误，不能将元素放回到刚刚从中取出的列表中
}
s=上面的实现方法有错误，不能将元素放回到刚刚从中取出的列表中，这似乎不太对劲。问题在于list的类型为List<?>,
你不能把null之外的任何值放到List<?>中。幸运的是，有一种方法可以实现这个方法，无需求助于不安宁全的转换或者原生类型。
这种想法就是编写一个私有的辅助方法来捕捉通用符类型。为了捕捉类型，辅助方法必须是泛型方法，像下面这样：
 public static boid swap(List<?> list, int i, int j){
    swapHelper(list,i.j);
}
//private helper method for wildcard capture
private static <E> void swapHelper(List<E> list, int i , int j){
    list.set(j, list.set(j,list.get(i)));
}
swapHelper知道list是一个List<E>.因此，它知道从这个列表中取出的任何值均为E类型，并且知道将E类型的任何值放进列表都是安全的。
 */


/*
第29条：优先考虑类型安全的异构容器
有时候你会需要更多的灵活性。例如，数据库行可以有任意多的列，如果能以任意类型安全的方式访问所有列就好了。
幸运的是，有一种方法可以很容易地做到这一点。这种想法就是将键进行参数化而不是将容器参数化。然后将参数化的键提交给容器，
来插入或者获取值。用泛型来确保值的类型与它的键相符。

当一个类的字面文字被用在方法中，来传达编译时和运行时的类型信息时，就被称作type token.
 */
//示例：考虑Favorites类，它允许其客户端从任意数量的其他类中，保存并获取一个“最喜爱”的实例。
class Favorites{
    private Map<Class<?>, Object> favorites = new HashMap<Class<?>, Object>();
    public <T> void putFavorites(Class<T> type, T instance){
        if (type == null)
            throw new NullPointerException("type is null");
//        favorites.put(type,instance);
        favorites.put(type,type.cast(instance));//让putFavorites方法检验instance是否真的是type所表示的类型的实例。
    }
    public <T> T getFavorites(Class<T> type){
        return type.cast(favorites.get(type));
        /*
        源码
        public T cast(Object obj) {
        if (obj != null && !isInstance(obj))
            throw new ClassCastException(cannotCastMsg(obj));
        return (T) obj;
    }
        //核心为：a = A.class.cast(b1); 把a转化为了B类型，此处容易产生把b1转成A类型误解
         */
    }

    public static void main(String[] args){
        Favorites f = new Favorites();
        f.putFavorites(String.class,"java");
        f.putFavorites(Integer.class,0xcafebabe);
        f.putFavorites(Class.class, Favorites.class);
        String favoriteString = f.getFavorites(String.class);
        Integer favoriteInteger = f.getFavorites(Integer.class);
        Class<?> favoriteClass = f.getFavorites(Class.class);
        System.out.printf("%s, %x , %s%n", favoriteString, favoriteInteger, favoriteClass.getSimpleName());
    }
}
/*
Favorites实例是类型安全的，同时它也是异构的：不像普通的map，它的所有键都不是同类型的。因此我们将Favoritrs称为类型安全的异构容器。
要注意的是通配符类型是嵌套的：它不是属于通配符类型的Map的类型，而是它的键的类型。
第二件要注意的事情是，favorites Map的值类型只是Object。
换句话说，Map并不能保证键和值之间的类型关系，即不能保证每个值的类型都与键的类型相同

cast方法是Java的cast操作符的动态模拟。它只检验它的参数是否为Class对象所表示的类型的实例。如果是，就返回参数。
Favorites的第一个局限是不能保证键和值之间的类型关系，不过有解决办法。
favorites.put(type,type.cast(instance));//让putFavorites方法检验instance是否真的是type所表示的类型的实例。
Favorites类的第二种局限性在于它不能用在不可具体化的类型中。换句话说，你可以保存最喜爱的String或者String[],
但是不能保存最喜爱的List<String>.还没有令人满意的解决办法。有一种方法称作super type token

Favorites使用的类型令牌（type token）是无限制的：getFavorites和putFavorites接受任何Class对象。有时候，可能需要限制那些
可以传递给方法的类型。这可以通过有限制的的类型令牌（bounded type token）来实现，它只是一个类型令牌，
利用有限制类型参数或者有限制通配符，来限制可以表示的类型。
 */


/*
第6章：枚举和注解
 */

/*
第30条：用enum代替int常量
枚举类型是指有一组固定的常量组成合法值的类型。
Java的枚举本质上是int值
Java枚举类型背后的基本想法非常简单：它们就是通过公有的静态final域为每个枚举常量导出实例的类。因为没有可以访问的构造器，
枚举类型是真正的final。
因为客户端既不能创建枚举类型的实例，也不能对它进行扩展，因此很可能没有实例，而只有声明过的枚举常量。换句话说，枚举类型是实例受控空的。
枚举类型为类型安全的枚举类型。
枚举提供了编译时的类型安全。
你可以增加或者重新排列枚举类型中的常量，而无需重新编译它的客户端代码，因为导出常量的域在枚举类型和它的客户端之间提供了一个隔离层：
常量值并没有被编译到客户端代码中，而是在int枚举模式之中。
枚举类型还允许添加任意的方法和域，并实现任意的接口。
为了将数据与枚举类型常量关联起来，得声明实例域，并编写一个带有数据并将数据保存在域中的构造器。
枚举类型天生就是不可变的，因此所有的域都是final的
 */
enum Apple {FUJI, PIPPIN, GRANNY_SMITH}
enum Orange {NAVEL, TEMRLE, BLOOD}

//enum type with data and behavior
enum Planet{
    MERCURY(3.302e+23,2.439e6),
    VENUS(4.869e+24, 6.052e6),
    EARTH(5.975e24, 6.378e6);
    private final double mass;
    private final double radius;
    private final double surfaceGravity;

    //Universal gravitational constant in m^3/kg s^2
    private static final double G = 6.67300E-11;
    Planet(double mass, double radius){
        this.mass = mass;
        this.radius = radius;
        this.surfaceGravity = G * mass / (radius * radius);
    }
    public double getMass(){return mass;}
    public double getRadius(){return radius;}
    public double getSurfaceGravity(){return surfaceGravity;}
    public double surfaceWeight(double mass){
        return mass * surfaceGravity;
    }
}
/*
如果一个枚举具有普遍适用性，它就应该成为一个顶层类。
 */
//enum type with constant-specific method implementions这种方法称作特定于常量的方法实现
enum Operation{
    PLUS{double apply(double x, double y){return x + y;}},
    MINUS{double apply(double x, double y){return x - y;}},
    TIMES{double apply(double x, double y){return x * y;}},
    DIVIDE{double apply(double x, double y){return x / y;}};
    abstract double apply(double x, double y);
}
//这时如果给Operation增加新的常量，就必须实现apply方法。因为枚举类型中的抽象方法必须被它所有常量中的具体方法所覆盖。

//特定于常量的方法实现可以于特定于常量的具体数据结合起来，例如下面的Operation_change覆盖了toString来返回通常于该操作关联的符号。
enum Operation_change{
    PLUS("+"){double apply(double x, double y){return x + y;}},
    MINUS("-"){double apply(double x, double y){return x - y;}},
    TIMES("*"){double apply(double x, double y){return x * y;}},
    DIVIDE("/"){double apply(double x, double y){return x / y;}};
    private final String symbol;
    Operation_change(String symbol){this.symbol = symbol;}
    @Override public String toString(){return symbol;}
    abstract double apply(double x, double y);
}
//枚举类型有一个自动产生的valueOf（String）方法，它将常量的名字转换成常量本身。如果在枚举类型中国覆盖toString，要考虑
//编写一个fromString方法，经定制的字符串表示法便会相应的枚举
class Operation_change_test{
    //Implementing a fromStrinh method on an enum type
    private static final Map<String, Operation_change> stringToEnum
            = new HashMap<String, Operation_change>();
    static {//Initialize map from constant name to enum constant
        for (Operation_change op : Operation_change.values()){
            stringToEnum.put(op.toString(),op);
        }
    }
    //return Operation_change for string , or null if string is invalid
    public static Operation_change formString(String symbol){
        return stringToEnum.get(symbol);
    }
}
//特定于常量的方法实现有一个美中不足的地方。它使得在枚举常量中共享代码变得更加困难了。
//例如考虑用一个枚举表示薪资包中的工作天数。有一个枚举方法，根据某工人的基本工资以及当天的工作时间，计算他的当天报酬。
//enum that switches on its value to share code  - questionable
enum PayrollDay{
    MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY,SUNDAY;
    private static final int HOUR_PRE_SHIFT = 8;
    double pay(double hoursWorked,double payRate){//注意double并不适合薪资应用程序。
        double basePay = hoursWorked * payRate;
        double overtimePay;//calculate overtime pay
        switch (this){
            case SATURDAY: case SUNDAY:
                overtimePay = hoursWorked * payRate / 2;
            default://weekdays
                 overtimePay = hoursWorked <= HOUR_PRE_SHIFT ? 0 : (hoursWorked - HOUR_PRE_SHIFT) * payRate / 2;
                break;
        }
        return basePay + overtimePay;
    }
}
/*
代码虽然简单，但是非常危险。假设将一个元素添加到该枚举中，或许是一个表示假期天数的特殊值，但是忘记给switch语句添加相应的case。
程序依然可以编译，但pay方法会将假期的工资计算成与正常工作日的相同。为了利用特定于常量的方法实现安全地执行工资计算，可能
必须重复计算每个常量的加班工资，或者将计算移到两个辅助方法中（一个计算工作日，一个计算双休日），并从每个常量调用相应的辅助方法。
这种方法代码太多，降低可读性，并增加了出错几率。

目的是：每当添加一个枚举常量时，就强制选择一种加班报酬策略。幸运的是，有一种很好的方法可以实现这一点。
这种想法就是将加班工资计算移到一个私有的嵌套枚举中，将这个策略枚举的实例传到PayrollDay，枚举的构造器中。之后PayrollDay枚举
将加班工资计算委托给策略枚举，PayrollDay中就不需要switch语句或者特定于常量的方法实现了。
 */
//the strategy enum pattern
enum PayrollDay_change{
    MONDAY(PayType.WEEKDAY),
    TUESDAY(PayType.WEEKDAY),
    WEDNESDAY(PayType.WEEKDAY),
    THURSDAY(PayType.WEEKDAY),
    FRIDAY(PayType.WEEKDAY),
    SATURDAY(PayType.WEEKEBD),
    SUNDAY(PayType.WEEKEBD);
    private final PayType payType;
    PayrollDay_change(PayType payType){this.payType = payType;}
    double pay(double hoursWorked, double payRate){
        return payType.pay(hoursWorked,payRate);
    }
    //the strategy enum type
    private enum PayType{
        WEEKDAY{
            double overtimePay(double hours, double payRate){
                return hours <= HOUR_PRE_SHIFT ? 0 : (hours - HOUR_PRE_SHIFT) / 2;
            }
        },
        WEEKEBD{
            double overtimePay(double hours, double payRate){
                return hours * payRate / 2;
            }
        };
        private static final int HOUR_PRE_SHIFT = 8;
        abstract double overtimePay(double hrs, double payRate);
        double pay(double hoursWorked, double payRate){
            double basePay = hoursWorked * payRate;
            return basePay + overtimePay(hoursWorked, payRate);
        }
    }
}
//枚举中的switch语句适合于给外部的枚举类型增加特定于常量的行为。
//如果多个枚举常量同时共享相同的行为，则考虑枚举策略。


/*
第31条：用实例域代替序数
许多枚举天生就与一个单独的int值相关联。所有的枚举都有一个ordinal方法，它返回每个枚举常量在类型中的数字位置。
大多数程序员都不需要这个方法。它是设计成用于像EnumSet和EnumMap这种基于枚举的通用数据结构的。
永远不要根据枚举的序数导出与它关联的值，而是要将它保存在一个实例域中
 */
//永远不要根据枚举的序数导出与它关联的值，而是要将它保存在一个实例域中：
enum Ensemble{
    SOLO(1),DUET(2),TRIO(3),DECTET(10);
    private final int numberOfMusicians;
    Ensemble(int size){this.numberOfMusicians = size;}
    public int getNumberOfMusicians(){return numberOfMusicians;}
}



/*
第32条：用EnumSet代替位域
如果一个枚举类型的元素主要用在集合中，一般就使用int枚举模式（直接列出来）
 */
//bit field enumeration constants
class Text{
    public static final int STYLE_BOLD = 1 << 0;
    public static final int STYLE_ITALIC = 1 << 1;
    public static final int STYLE_UNDERLINE = 1 << 2;
    public static final int STYLE_STRIKETHROUGH = 1 << 3;
    //parameter is bitwise OR of zero or more STYKE_ constants
    public void  applyStyles(int style){}
}
//这种表示法让你用OR位运算将几个常量合并到一个集合中，称作位域：
//text.applyStyles(STYLE_BOLD|STYLE_ITALIC);
//有些程序员在需要传递多组常量集时，仍然倾向于使用位域。其实并没有理由那么做，因为还有更好的替代方法。
//Java.util包提供了EnumSet类来有效地表示从单个枚举类型中提取的多个值的多个集合。这个类实现Set接口。但是在内部具体的实现上，
//EnumSet内容都表示为位矢量。
//将前一个范例改成用枚举代替位域后的代码
//EnumSet - a modern replacement for bit fields
class Text_change{
    public enum Style{BOLD, ITALIC, UNDERLINE, STRIKETHROUGH}
    //any set could be passed in, but EnumSet is clearly best
    public void applyStyles(Set<Style> styles){}
}
//text.spplyStyles(ENumSet.of(Style.BOLD,Style.ITALIC));
//正是因为枚举类型要用在集合中，所以没有理由用位域来表示它。
//EnumSet有个缺点：无法创建不可变的EnumSet


/*
第33条：用EnumMap代替序数索引
 */
//有时候，你可能会见到利用ordinal方法来索引数组的代码。例如：
class Herb{//表示一种烹饪用的香草
    public enum Type{ ANNUAL, PERENNIAL, BIENNIAL}
    private final String name;
    private final Type type;
    Herb(String name, Type type){
        this.name = name;
        this.type = type;
    }
    @Override public String toString(){return name;}
    public static void main(String[] args){
        System.out.println(Herb.Type.class);
    }
}
/*
假设有一个香草数组，表示一座花园中的植物，你想要按照类型（一年生，多年生，两年生植物）进行组织侯将这些植物列出来。
如果这么做，构建三个集合，遍历花园，将每种香草放入相应集合
 */
//Using ordinal() to index an array  -- DON'T DO THIS
//Herb[] garden = ...;
//Set<Herb> [] herbsByType = (Set<Herb>[]) new Set[Herb.Type.values().length];//Indexed by Herb.Type.ordinal()
//for(int i = 0; i < herbsByType.length; i++)
//    herbsByType[i] = new HashSet<Herb>();
//for(Herb h : garden)
//    herbsByType[h.Type.ordinal()].add(h);
//
////print the result
//for(int i = 0; i < herbsByType.length; i++)
//    System.out.printf("%s: %s%n",Herb.Type.values()[i],herbsByType[i]);
//虽然这种方法可行，但是有很多问题。
//因为数组不能与泛型兼容，程序需要进行未受检的住那还，并且不能正确无误地进行编译。因为数组不知道它的索引代表这什么，
// 你必须手工标注这些索引的输出。但是这种方法最严重的问题在于，当你访问一个按照枚举的序数进行索引的数组时，使用正确的
//int值就是你的职责了，int不能提供枚举的类型安全。
//幸运的是，有以中国更好的方法可以达到同样的效果。数组实际上充当着从枚举到值的映射，因此可能还要用到Map。
//更具体地说，有一种非常快速的Map实现专门用于枚举键，称作java.util.EnumMap.以下是用EnumMap改写后的代码
//Using an EnumMap to associate data with an enum
//EnumMap(Class<K> keyType)创建一个具有指定键类型的空枚举映射
//Map<Herb.Type, Set<Herb>> herbsByType = new EnumMap<Herb.Type, Set<Herb>>(Herb.Type.class);
//for(Herp.Type t : Herb.Type.values())
//    herbsByType.put(t, new HashSet<Herb>());
//for(Herb h : garden)
//    herbsByType.get(h.type).add(h);
//System.out.println(herbsByType);

//
//Using a nested EnumMap to associate data with enum pairs
enum Phase{
    SOLID, LIQUID, GAS;
    public enum Transition{
        MELT(SOLID,LIQUID),FREEZE(LIQUID,SOLID),
        BOIL(LIQUID,GAS),CONDENSE(GAS,LIQUID),
        SUBLIME(SOLID,GAS),DEPOSIT(GAS,SOLID);
        private final Phase src;
        private final Phase dst;
        Transition(Phase src, Phase dst){
            this.src = src;
            this.dst = dst;
        }
        //Initialize the Phase transition map
        private static final Map<Phase, Map<Phase,Transition>> m =
                new EnumMap<Phase, Map<Phase,Transition>>(Phase.class);
        static {
            for (Phase p : Phase.values())
                m.put(p, new EnumMap<Phase,Transition>(Phase.class));
            for (Transition trans : Transition.values())
                m.get(trans.src).put(trans.dst,trans);
        }
        public static Transition from(Phase src, Phase dst){
            return m.get(src).get(dst);
        }
    }
}
//总而言之，最好不要用序数索来索引数组，而要使用EnumMap。


/*
第34条：用接口模拟可伸缩的枚举
枚举的可伸缩性（用一个枚举去扩展另一个枚举）最后证明基本上都不是什么好点子。
操作码是指这样的枚举类型：它的元素表示在某种机器上的那些操作。例如第30条中的Operation类型，它表示一个简单的计算器中的某些函数。
有时候，要尽可能地让API的用户提供它们自己的操作，这样可以有效地扩展API所提供的操作集
幸运的是，有一种很好的方法可以利用枚举类型来实现这种效果。由于枚举类型可以通过给操作码类型和枚举定义接口，基本想法就是利用这一事实。
 */
//以下是第30条中Operation的扩展版本
//Emulated extensible enum using an interface
interface _34_Operation{
    double apply(double x, double y);
}
enum BasicOperation implements _34_Operation{
    PlUS("+"){
        public double apply(double x, double y){ return x + y; }
    },
    MINUS("-"){
        public double apply(double x, double y){ return x - y; }
    },
    TIMES("*"){
        public double apply(double x, double y){ return x * y; }
    },
    DEVIDE("/"){
        public double apply(double x, double y){ return x / y; }
    };
    private final String symbol;
    BasicOperation(String symbol){
        this.symbol = symbol;
    }
    @Override public String toString(){
        return symbol;
    }
}
/*
虽然枚举类型不是可扩展的，但是接口类型则是可扩展的。它是用来表示API中的操作的接口。你可以定义另一个枚举类型，它实现这个接口，
并用这个新类型的实例代替基本类型。例如，假设要定义一个上述操作类型的扩展，由求幂和求余操作组成。所要做的就是编写一个枚举类型，实现_34_Operation接口
 */
//Emulated extension enum
enum ExtendedOperation implements _34_Operation{
    EXP("^"){
        public double apply(double x, double y){ return Math.pow(x, y); }
    },
    REMAINDER("%"){
        public double apply(double x, double y){ return x % y; }
    };
    private final String symbol;
    ExtendedOperation(String symbol){
        this.symbol = symbol;
    }
    @Override public String toString(){
        return symbol;
    }
}
class _34_test{
    public static void main(String[] args){
        double x = 2.0;
        double y = 3.0;
        test(BasicOperation.class,x,y);
        test(ExtendedOperation.class,x,y);
    }
    private static <T extends Enum<T> & _34_Operation> void test(Class<T> opSet, double x, double y){
        for (_34_Operation op : opSet.getEnumConstants())
            System.out.printf("%f %s %f = %f%n",x,op,y,op.apply(x,y));
    }
}
/*
用接口模拟可伸缩枚举有个小小的不足，即无法将实现从一个枚举类型继承到另一个枚举类型。
如果共享的功能比较多，则可以将它封装在一个辅助类或者静态辅助方法中，来避免代码的重复工作。
总之，虽然无法编写可扩展的枚举类型，却可以通过编写接口以及实现该接口的基础枚举类型，对它进行模拟。
 */


/*
第35条：注解由于命名模式
Java1.5之前，一般使用 命名模式 表明有些程序元素需要通过某种工具或者框架进行特殊处理。
命名模式缺点：1：文字拼写错误会导致失败，且没有任何提示；
              2：无法确保它们只用于相应的程序元素上；
              3：它们没有提供将参数值与程序元素联合起来的好方法
注解解决了所有这些问题。
假设想要定义一个注解类型来指定简单的测试，它们自动运行，并在抛出异常时失败。以下就是这样一种注解类型，命名未Test
 */
//Marker annotation type declaration

/**
 * Indicates that the annotataion method is a test method.
 * Use only on parameterless static methods.（只用于无参的静态方法）
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Test{}
/*
Test注解类型的声明就是自身通过Retention和Target注解进行了注解。注解类型声明中的这种注解被称为元注解。
@Retention(RetentionPolicy.RUNTIME)元注解表明，Test注解应该在运行时保留。如果没有保留，测试工具就无法知道Test注解
@Target(ElementType.METHOD)元注解表明，Test注解只在方法声明中才是合法的：它不能运用到类声明、域声明或者其他程序元素上。

下面就是现实应用中的Test注解，称作标记注解，因为它没有参数，只是“标记”被注解的元素。如果拼错Test，
或者将Test注解应用到程序元素而非方法声明，程序就无法编译：
 */
//Program containing marker annotations
class _35_Simple{
    @Test public static void m1(){ }//Test should pass
    public static void m2(){}
    @Test public static void m3(){//Test should fail
        throw new RuntimeException("Boom");
    }
    public static void m4(){}
    @Test public void m5(){}//Incalid use: nonstatic method
    public static void m6(){}
    @Test public static void m7(){//Test should fail
        throw new RuntimeException("Crash");
    }
    public static void m8(){}
}
/*
_35_Simple类中有8个方法，其中4个被注解为测试。4个中有2个抛出异常：m3, m7。另外两个没有：m1, m5。
但是其中一个没有抛出异常的被注解方法：m5，是一个实例方法，因此不属于注解有效使用。
总之，_35_Simple包含4项测试：一项会通过，两项失败，另一项无效。没有用Test注解进行标注的4个方法会被测试工具忽略。

Test注解对_35_Simple类的语义没有直接的影响，它们只是负责提供相关的程序使用。更一般地讲，注解永远不会改变被注解代码的语义，
但是使它可以通过工具及逆行特殊的处理。例如下面的简单的测试运行类：
 */
//Program to process marker annotations
class _35_RunTest{
    public static void main(String[] args) throws Exception{
        int tests = 0;
        int passed = 0;
//        Class testClass = Class.forName(args[0]);
//        Class.forName(String className) 返回与带有给定字符串名的类或接口相关联的 Class 对象
        Class testClass = _35_Simple.class;
        for (Method m : testClass.getDeclaredMethods()){
            if (m.isAnnotationPresent(Test.class)){
//                System.out.println("method: " + m);
                tests++;
                try{
                    m.invoke(null);
                    passed++;
                }catch (InvocationTargetException wrappeExc){
                    Throwable exc = wrappeExc.getCause();
                    System.out.println(m + "  failed:" + exc);
                }catch (Exception e){
                    System.out.println("INNVALID @Test :" + m);
                }
            }
        }
        System.out.printf("Passed: %d, Failed: %d%n ", passed, tests - passed);
    }
}
/*
测试运行工具通过调用Method.invoke反射地运行类中所有标注了Test的方法。
isAnnotationPresent方法告知该工具要运行哪些方法。
如果测试抛出异常，反射机制就会将他封装在InvocationTargetException中。该工具捕捉到了这个异常，并打印失败报告，
包含测试方法抛出的原始异常，这些信息是通过getCause方法从InvocationTargetException中提取出来的。

现在我们要针对只在抛出特殊异常时才成功的测试添加支持。为此我们需要一个新的注解类型：
 */
//Annotation type with a parameter

/**
 * Indicates that the annotation method is a test method that
 * must throw the designated exception to succeed.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface _35_ExceptionTest{//这个注解的参数类型是： Class<? extends Exception>
    Class<? extends Exception> value();
}
//实际应用中的这个注解。注意类名城被用作了注解的参数值
//Program containing annotation with a parameter
class _35_Simple2{
    @_35_ExceptionTest(ArithmeticException.class)//主要是看异常类型是否为ArithmeticException
    public static void m1(){//Test should pass
        int i = 0;
        i = i / i;
    }
    @_35_ExceptionTest(ArithmeticException.class)
    public static void m2(){//should fail(wrong exception)
        int[] a = new int[0];
        int i = a[1];
    }
    @_35_ExceptionTest(ArithmeticException.class)
    public static void m3(){}//should fail (no exception)
}

class _35_RunTest2{
    public static void main(String[] args) throws Exception{
        int tests = 0;
        int passed = 0;
//        Class testClass = Class.forName(args[0]);
//        Class.forName(String className) 返回与带有给定字符串名的类或接口相关联的 Class 对象
        Class testClass = _35_Simple2.class;
        for (Method m : testClass.getDeclaredMethods()){
            if (m.isAnnotationPresent(_35_ExceptionTest.class)){
//                System.out.println("method: " + m);
                tests++;
                try{
                    m.invoke(null);
                    System.out.printf("Test %s failed: no exception%n", m);
                }catch (InvocationTargetException wrappeExc){
                    Throwable exc = wrappeExc.getCause();
                    Class<? extends Exception> excType = m.getAnnotation(_35_ExceptionTest.class).value();
                    if (excType.isInstance(exc)){
                        passed++;
                    }else {
                        System.out.printf("Test %s failed: expected %s, get %s%n",m,excType.getName(),exc);
                    }

                }catch (Exception e){
                    System.out.println("INNVALID @Test :" + m);
                }
            }
        }
        System.out.printf("Passed: %d, Failed: %d%n ", passed, tests - passed);
    }
}
/*
将上面的异常测试实例再深入一下，想象测试可以再抛出任何一种指定异常时都能得到通过。
注解机制是一种工具，使得支持这种用法变得十分容易。假设将_35_ExceptionTest的参数类型改成Class对象的一个数组：
 */
//Annotation type with an array parameter
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface _35_ExceptionTest2{//这个注解的参数类型是： Class<? extends Exception>
    Class<? extends Exception>[] value();
}
//使用了_35_ExceptionTest新版的数组参数之后，之前所有的_35_ExceptionTest注解仍然有效，并产生单元素的数组。
//为了指定多元素数组，要用花括号将元素包围起来，并用“，”将它们隔开：
class _35_Simple3{
    @_35_ExceptionTest2(ArithmeticException.class)//主要是看异常类型是否为ArithmeticException
    public static void m1(){//Test should pass
        int i = 0;
        i = i / i;
    }
    @_35_ExceptionTest2(ArithmeticException.class)
    public static void m2(){//should fail(wrong exception)
        int[] a = new int[0];
        int i = a[1];
    }
    @_35_ExceptionTest2(ArithmeticException.class)
    public static void m3(){}//should fail (no exception)
    @_35_ExceptionTest2({IndexOutOfBoundsException.class,NullPointerException.class})
    public static void doublyBad(){
        List<String> list = new ArrayList<String>();
        //the spec permitd this method to throw either
        //IndexOutOfBoundsException or NullPointerException
        list.addAll(5,null);
    }
}

class _35_RunTest3{
    public static void main(String[] args) throws Exception{
        int tests = 0;
        int passed = 0;
//        Class testClass = Class.forName(args[0]);
//        Class.forName(String className) 返回与带有给定字符串名的类或接口相关联的 Class 对象
        Class testClass = _35_Simple3.class;
        for (Method m : testClass.getDeclaredMethods()){
            if (m.isAnnotationPresent(_35_ExceptionTest2.class)){
//                System.out.println("method: " + m);
                tests++;
                try{
                    m.invoke(null);
                    System.out.printf("Test %s failed: no exception%n", m);
                }catch (Throwable wrappeExc) {
                    Throwable exc = wrappeExc.getCause();
                    Class<? extends Exception>[] excTypes = m.getAnnotation(_35_ExceptionTest2.class).value();
                    int oldPassed = passed;
                    for (Class<? extends Exception> excType : excTypes) {
                        if (excType.isInstance(exc)) {
                            passed++;
                            break;
                        }
                    }
                    if (passed == oldPassed)
                        System.out.printf("Test %s failed: expected %s, get %s%n",m,excTypes[0].getName(),exc);
//                        System.out.printf("Test %s failed: %s %n",m,exc);
                }
            }
        }
        System.out.printf("Passed: %d, Failed: %d%n ", passed, tests - passed);
    }
}


/*
第36条：坚持使用Override注解
在想要覆盖超类声明的每个方法声明中使用Override注解
 */

/*
第37条：用标记接口定义类型
标记接口是没有包含方法声明的接口，而只是指明（或标明）一个类实现了具有某种属性的接口。
例如Serializable接口。通过实现这个接口，类表明它的实例可以被写到ObjectOutputStream。
标记接口有2点胜过标记注解。1：标记接口定义的类型是由被标记类的实例实现的；标记注解则没有定义这样的类型。
（这个类型允许你在编译时捕捉在使用标记注解情况下要等到运行时才能捕捉到的错误）
                           2：标记接口可以被更加精确地进行锁定
如果注解类型利用@Target(ElementType.TYPE)声明，它就可以被应用到任何类或者接口。
标记注解胜过标记接口的最大优点在于，它可以通过默认的方法添加一个或者多个注解类型元素，给已被使用的注解类型添加更多的信息。
标记注解的另一个优点在于，它们是更大的注解机制的一部分。

那么什么时候应该使用标记注解，什么时候应该使用标记接口呢？
很显然，如果标记是应用到任何程序元素而不是类或者接口，就必须使用注解，因为只有类和接口可以用来实现或者扩展接口。
如果标记只应用给类和接口，就要考虑以下：要编写一个还是多个只接受有这种标记的方法呢？如果是这种情况，就应该优先使用标记接口而非注解。
这样就可以用接口作为相关方法的参数类型，它真正可以为你提供编译时进行类型检查的好处。如果对问题的回答是否定的，就需再考虑以下：
是否要永远限制这个标记只用于特殊接口的元素？如果是，最好将标记定义成该接口的子接口。如果否，就用注解。

总之，如果想要定义一个任何新方法都不会与之关联的类型，标记接口就是最好的选择。
如果想要标记程序元素而非类和接口，考虑未来可能要给标记添加更多的信息，或者标记要适合于已经广泛使用了注解类型的框架，
那么标记注解就是正确的选择。
 */


/*
第7章：方法
讨论方法设计的几个方面：如何处理参数和返回值，如何设计方法签名，如何为方法编写文档
 */
/*
第38条：检查参数的有效性
对于公有的方法，要用Javadoc的@throws标签在文档中说明违反参数值限制时会抛出的异常。
一旦在文档中记录了对于方法参数的限制，并且记录了一但违反这些限制将要抛出的异常，强加这些现限制就是非常简单的事情了，eg：
 */
class _38_test {
/**
 * Return a BigInteger whose value is(this mod m ).This method
 * differs from the remainder method in that it always returns a
 * non-negative BigInteger
 *
 * @param m the modulus, which must be positive
 * @return this mod m
 * @throws ArithmeticException if m is less than or equal to 0
 */
    public BigInteger mod(BigInteger m){
        if (m.signum() <= 0)
            throw new ArithmeticException("Modulus <= 0" + m);
        return m;//...其他操作
    }
    //非公有的方法通常应该使用断言来检查它们的参数，eg：
    //Private helper function for a recursive sort
    private static void sort(long a[], int offset, int length){
        assert a != null;
        assert offset >= 0 && offset <= a.length;
        assert length >= 0 && length <= a.length - offset;
        //其他操作
    }
}
/*
非公有的方法通常应该使用断言来检查它们的参数。断言如果直白，将会抛出AssertionError。不同于一般的有效性检查，
如果它们没有起到作用，本质上也不会有成本开销，除非通过将 -ea（或者-enableassertions）标记传递给Java解释器，来启用它们。

对于有些参数，方法本身没有用到，却被保存起来供以后使用，检验这类参数的有效性尤为重要。构造器正是这种情况的一种特殊的情形。
 */


/*
第39条：必要时进行保护性拷贝

没有对象的帮助时，虽然另一个类不可能修改对象的内部状态，但是对象很容易在无意识的情况下提供这种帮助。
例如下面的类，它声称可以表示一段不可变的时间周期：
 */
//Broken "immutable" time period class
final class Period{
    private final Date start;
    private final Date end;
    /**
     * @param start the begining of period
     * @param end  the end of the period; must bot precede start
     * @throws IllegalArgumentException if start is after end
     * @throws NullPointerException if start or end is null
     */
    public Period(Date start, Date end){
        if (start.compareTo(end) > 0)
            throw new IllegalArgumentException(start + " after " + end);
        this.start = start;
        this.end = end;
    }
    public Date getStart(){ return start; }
    public Date getEnd(){ return end; }
    //remainder omitted
}
/*
乍一看，这个类似乎是不可变的，并且加了约束条件：周期的起始时间不能在结束时间之后。然而，因为Date类本身是可变的，因此
很容易违反这个约束条件：
//Attack the internals of a Period instance
//Data start = new Date();
//Data end = new Date();
//Period p = new Period(start,end);
//end.setYear(78);//Modifies internals of p

为了白虎Period实例的内部信息避免受到这种攻击， 对于构造器的每个可变参数进行保护性拷贝是必要的，
并且使用备份对象作为Period实例的组件，而不使用原始的对象
//Repaired constructor - makes defensive copies of parameters
public Period(Date start, Date end){
        this.start = new Date(start.getTime());
        this.end = new Date(end.getTime());
        if (this.start.compareTo(this.end) > 0)
            throw new IllegalArgumentException(start + " after " + end);
    }

用了这个构造器之后，上述攻击对于Period实例不在有效。
注意，保护性拷贝是在检查参数的有效性之前进行的，并且有效性检查是针对拷贝之后的对象，而不是针对原始的对象。
虽然这样做看起来有点不太自然，却是必要的。这样做可以避免在“危险阶段”期间从另一个线程改变类的参数，这里的危险阶段是指
从检查参数开始，知道拷贝参数之间的时间段。
注意，我们没有用Date的clone方法来进行保护性拷贝。因为Date是非final的，不能保证clone方法一定返回类为java.util.Date对象：
它有可能返回专门处于恶意的目的而设计的不可信子类的实例。例如，这样的子类可以在每个实例被创建的时候，把指向该实例的引用
记录到一个私有的静态列表中，并且允许攻击者访问这个立标。这样使得攻击者可以自由地控制所有的实例。为了阻止这种攻击，
对于参数类型可以被不可信任方子类化的参数，请不要使用clone方法进行保护性拷贝。

虽然替换构造器可以成功地避免上述的攻击，但是改变Period实例仍然是有可能的，因为它的访问方法提供了对其可变内部成员的访问能力：
//second attack on the internals of a Period instance
//Data start = new Date();
//Data end = new Date();
//Period p = new Period(start,end);
//p.getEnd().setYear(78);//Modifies internals of p

为了防御这第二种攻击，只需修改这两个访问方法， 使它返回可变内部域的保护性拷贝即可：
public Date getStart(){ return new Date(start.getTime()); }
public Date getEnd(){ return new Date(end.getTime()); }
//这时Period真正不可变了，这里的访问方法可以使用clone方法，因为我们知道Period内部的Date对象的类是java.util.Date,而不是其他
某个潜在的不可信子类
 */


/*
第40条：谨慎设计方法签名
谨慎地选择方法的名称。
不要过于追求提供遍历的方法
避免过长的参数列表 目标是四个参数，或者更少。
缩短长参数列表 1：把方法分解成多个方法
               2：创建辅助类，用来保存参数的分组（P163）（这些辅助类一般为静态成员类。
               如果一个频繁出现的参数序列可以被看作是代表了每个独特的实体，则建议使用这种方法。）
结合了前两种方法特征的第三种方法是，从对象构建到方法调用都采用Builder模式。
对于参数类型，要优先使用接口而不是类。
对于boolean参数，要优先使用两个元素的枚举类型。
 */

/*
第41条：慎用重载
要调用哪个重载方法是在编译时做出决定的。
对于重载方法的选择是静态的，而对于被覆盖的方法的选择是动态的。
安全而保守的策略是，永远不要导出两个具有相同参数数目的重载方法。
一个类的多个构造器总是重载的。
 */
class SetList{
    public static void main(String[] args){
        Set<Integer> set = new TreeSet<Integer>();
        List<Integer> list = new ArrayList<Integer>();
        for (int i = -3; i < 3; i++){
            set.add(i);
            list.add(i);
        }
//        for (int i = 0; i < 3; i++){//此时打印 [-3, -2, -1] [-2, 0, 2]，
                                        // 因为 list.remove(i);调用的是 list.remove(int i)方法，它从立标的指定位置上去除元素
//            set.remove(i);
//            list.remove(i);
//        }
        for (int i = 0; i < 3; i++){//此时打印 [-3, -2, -1] [-3, -2, -1]
            set.remove(i);
            list.remove((Integer)i);//因为list.remove((Integer)i)调用list.remove(Object o)方法，去除对象
        }
        System.out.println(set + " " + list);
    }
}


/*
第42条：慎用可变参数
可变参数方法，一般称作可匹配不同长度的变量的方法
可变参数方法接受0个或者多个指定类型的参数
可变参数机制通过先创建一个数组，数组的大小为在调用位置所传递的参数数量，然后将参数值传到数组中，最后将数组传递给方法。
 */
class _42_test{
    static int sum(int... numbers){
        int sum = 0;
        if (numbers.length == 0)
            throw new IllegalArgumentException("too few arguments");
        for (int num : numbers)
            sum += num;
        return sum;
    }
    static int min(int firstNum, int... remainingNum){
        int min = firstNum;
        for (int num : remainingNum)
            if (min < num)
                min = num;
        return min;
    }
}
/*
不必改造具有final数组参数的每个方法；只当确定是在数量不定的值上执行调用时才使用可变参数。
 */



/*
第43条：返回零长度的数组或者集合，而不是null
 */
class Cheese{}
class _43_Test{
    //像下面这样的方法并不少见
    private final List<Cheese> cheeseInStock = new ArrayList<Cheese>();
    /**
     * @return an array containing all of the cheeses in the shop,
     * or null if o cheeses are available for purchase
     */
//    private Cheese[] getCheeses(){
//        if (cheeseInStock.size() == 0)
//            return null;
//        ...
//    }
    /*
    把没有奶酪（cheese）可买的情况当作是一种特例，这是不合常理的，这样会要求客户端中必须有额外的代码类处理null返回值，例如：
    Cheese[] cheeses = shop.getCheeses();
    if(cheeses != null && Arrays.asList(cheeses).contains(Cheese.STILTON))
        System.out.println(:jolly good,just the thinf.")
    而不是下面这段代码：
    if(Arrays.asList(shop.getCheeses()).contains(Cheese.STILTON))
        System.out.println(:jolly good,just the thinf.")
     */

    //The right way to return an array from a collection
//    private final List<Cheese> getCheeseInStock = ...;
//    private static final Cheese[] EMPTY_CHEESE_ARRAY = new Cheese[0];
//    /**
//     * @return an array containing all of the cheeses in the shop
//     */
//    public Cheese[] getCheeses(){
//        return cheeseInStock.toArray(EMPTY_CHEESE_ARRAY);
//    }
    /*
    在这种习惯用法中，零长度数组常量被传递给toArray方法，以指明所期望的返回类型。正常情况下，toArray方法分配了返回的数组，
    但是，如果集合是空的，它将使用零长度的输入数组，Collection.toArray(T[])的规范保证：如果输入数组大到足够容纳这个集合，
    它就将返回这个输入数组。因此，这种做法永远不会非陪零长度的数组。

    同样di地，集合值的方法也可以做成在每当需要返回空集合时都返回同一个不可变的空集合。
    Collection.emptySet、emptyList和emptyMap方法提供的正是你所需要的，如下所示：
    //The right way to return a copy of a collection
    public List<Cheese> getCheeseList(){
        if(cheesesInStock.isEmpty())
            return Collections.emptyList();//Alway returns same list
        elae
            return new ArrayList<Cheese>(cheesesInStock);
    }
   */
}
/*
简而言之，返回类型为数组或集合的方法没理由返回null，而不是返回一个零长度的数组或者集合。
 */


/*
第44条：为所有导出的API元素编写文档注释 P177
 */


/*
第8章：通用程序设计
 */
/*
第45条：将局部变量的作用域最小化
本条目与第13条（使类和成员的访问性最小化）本质上是类似的。
Java允许你在任何可以出现语句的地方声明变量。
要使局部变量的作用域最小化，最有力的方法就是在第一次使用它的地方声明。
几乎每个局部变量的声明都应该包含一个初始化表达式。
 */


/*
第46条：for-each虚幻由于传统的for循环
有三种常见的情况无法使用for-each循环：
    1：过滤---如果需要遍历集合，并删除选定的元素，就需要使用显式的迭代器，以便可以调用他额remove方法。
    2：转换---如果需要遍历列表或者数组，并取代它的部分或全部的元素值，就需要列表迭代器或者数组索引，以便设定元素的值
    3：平行迭代---如果需要并行地遍历多个集合，就需要显式的控制迭代器或者索引变量，以便所有迭代器或者索引变量都可以得到同步前移。
 */

/*
第47条：了解和使用类库
 */
//产生位于0和某个上界之间的随机整数
class _47_Test{
    private static final Random rnd = new Random();
    //Common but deeply flawed
    static int random(int n){
        return Math.abs(rnd.nextInt()) % n;
    }
    /*
    上面方法看起来不错，但是却有3个缺点：
        1：如果n使一个比较小的2的乘方，经过一段相当短的周期之后，它产生的随机数序列将会重复
        2：如果n不是2的乘方，那么平均起来，有些数会比其他的数出现得更为频繁，如果n比较大，这个缺点就会非常明显。
        （可以通过下面的test方法直观的体现出来，它会产生一百万经过细心指定范围的随机数，并打印有多少个数字落在随机数取值范围的前半部分）
        3：在极少数情况下，它的失败是灾难性的，返回一个落在指定范围之外的数。（之所以如此，是因为这个方法试图通过调用Math.abs，
        将rnd.nextInt()返回的值映射为一个非负整数int。如果nextInt()返回Integer.MIN_VALUE，那么Math.abs也会返回Integer.MIN_VALUE，
        假设n不是2的乘方，那么取模操作符将返回一个负数，这会使程序失败）
     为了解决上面3个缺点的random方法，幸运的是，已经有现成的成果可以为你所用。它被称为Random.nextInt(int)
     */
    public static void test(){
        int n = 2 * (Integer.MAX_VALUE / 3);
        int low = 0;
        for (int i = 0; i < 1000000; i++)
            if (random(n) < n/2)
                low++;
        System.out.println(low);
    }
}


/*
第48条：如果需要精确的答案，请避免使用float和double
float和double类型尤其不适合用于货币计算
 */
/*
假设有1元，货架上货物价格分别为0.1,0.2,0.3,...1.0。从标价为0.1开始，每种买一个，一直到不能支付货架上下一种价格的货物为止，
那么你能买多上糖果？还会找回多上零头？
 */
class _48_Test{
    public static void main(String[] args){
        double funds = 1.00;
        int itemsBought = 0;
        for (double price = .10; funds >= price; price += .10){
            funds -= price;
            itemsBought++;
        }
        System.out.println(itemsBought + " items bought");
        System.out.printf("Change: " + funds);
        //3 items bought
        //Change: 0.3999999999999999
    }
}
/*
运行发现上面程序有问题。解决这个问题的正确方法是使用BigDecimal、int或者long进行货币计算。
下面程序是对上面程序的简单翻版，它使用BigDecimal类型代替double
 */
class _48_Test2{
    public static void main(String[] args){
        final BigDecimal TEN_CENTS = new BigDecimal(".10");
        BigDecimal funds = new BigDecimal("1.00");
        int itemsBought = 0;
        for (BigDecimal price = TEN_CENTS; funds.compareTo(price) >= 0; price = price.add(TEN_CENTS)){
            funds = funds.subtract(price);
            itemsBought++;
        }
        System.out.println(itemsBought + " items bought");
        System.out.printf("Change: " + funds);
//        4 items bought
//        Change: 0.00
    }
}
/*
上面程序正确。但是，使用BigDecimal有两个缺点：与使用基本运算类相比，这样做很不方便，而且很慢。
除了使用BigDeciamal之外，还有一种方法是使用int或long，到底使用int还是long要取决于所涉及数值的大小，同时要自己处理十进制小数点。
下面示例中，以分为单位
 */
class _48_Test3{
    public static void main(String[] args){
        int itemsBought = 0;
        int funds = 100;
        for (int price = 10; funds >= price; price += 10){
            itemsBought++;
            funds -= price;
        }
        System.out.println(itemsBought + " items bought");
        System.out.printf("Change: " + funds);
//        4 items bought
//        Change: 0
    }
}
/*
如果十足范围没有超过9位十进制数字，就可以使用int，如果不超过18位数字就可以使用long，如果数值超过18位数字，就必须使用BigDecimal
 */

/*
第49条：基本数据类型优于装箱基本类型
对装箱基本类型运用==操作符几乎总是错误的。
 */

/*
第50条：如果其他类型更合适，则尽量避免使用字符串
字符串不适合代替其他的值类型。
字符串不适合代替枚举类型
字符串不适合代替聚集类型
字符串不适合代替能力表（有时候，字符串被用于对某种功能进行授权访问）
 */


/*
第51条：当心字符串连接的性能
为连接n个字符串而重复地使用字符串连接操作符，需要n的平方级的时间。
这是由于字符串不可变而导致的不幸结果。当两个字符串被连接在一起时，它们的内容都要被拷贝。
为了获得可以接受的性能，请使用StringBuilder代替String。
 */

/*
第52条：通过接口引用对象
回忆第40条的建议：应该使用接口而不是用类作为参数类型。更一般地讲，应该优先使用接口而不是类来引用对象。
如果有合适的接口类型存在，那么对于参数、返回值、变量和域来说，就都应该使用接口类型进行声明。
有一点值得注意：如果原来的实现提供了某种特殊的功能，而这种功能并不是这个接口的通用约定所要求的，并且周玮的代码又依赖于这个功能，
那么关键的一点是，新的实现也要提供同样的功能。
 */

/*
第53条：接口优先于反射机制
核心反射机制java.lang.reflect,提供了“通过程序来访问关于已装载的类的信息”的能力。
给定一个Class实例，你可以获得Constructor、Method和Field实例，分别代表了该Class实例所表示的类的Constructor、Method和Field。
这些对象提供了“通过程序来访问类的成员名称、域类型、方法签名的信息”的能力。而且Constructor、Method和Field实例使你能过
通过反射机制操作它们的底层对等体：通过调用Constructor、Method和Field实例上的方法，可以构造底层类的实例，调用底层类的方法，
并访问底层类中的域。例如，Method.invoke是你可以调用任何类的任何对象上的任何方法。反射机制允许一个类使用另一个类，
即使当前者被编译的时候后者还根本不存在。然而这种能力要付出代价：
    1：丧失了编译时类型检查的好处，包括异常检查。
    2：执行反射访问所需的代码非常笨拙和冗长
    3：性能损失。
核心反射机制最初是为了基于组件的应用创建工具而设计的。
通常，普通应用程序在运行时不应该以反射方式访问对象。
如果只是以非常有限的形式使用反射机制，虽然也要付出少许代价，但是可以获得许多好处。
对于有些程序，它们必须用到在编译时无法获取的类，但是在编译时存在适当的接口或者超类，通过它们可以引用这个类。如果是这种情况，
就可以以反射方式创建实例，然后通过它们的接口或者超类，以正常的方式访问这些实例。
 */
class _53_Test{
    public static void main(String[] args){//java.util.TreeSet bbbbb ccccc ddddd aaaaa
        //Translate the class name into a Class.object
        Class<?> c1 = null;
        try {
            c1 = Class.forName(args[0]);
        }catch (ClassNotFoundException e){
            System.err.println("Class not found");
            System.exit(1);
        }
        //Instantiate the class
        Set<String> s = null;
        try {
            s = (Set<String>)c1.newInstance();
        }catch (IllegalAccessException e){
            System.err.println("Class not accessible");
            System.exit(1);
        }catch (InstantiationException e){
            System.err.println("Class not instantiable");
            System.exit(1);
        }

        //Exercise the set
        s.addAll(Arrays.asList(args).subList(1,args.length));
        System.out.println(s);//[aaaaa, bbbbb, ccccc, ddddd]
    }
}
/*
上面这个例子演示了反射机制的2个缺点。
    1：这个例子会产生3个运行时错误，如果不使用反射方式的实例化，这3个错误都会成为编译时错误
    2：根据类名生成它的实例需要20行冗长的代码，而调用一个构造器可以非常简洁地只使用一行代码
另一个值得注意的附带问题是，这个程序使用了System.exit。很少有需要调用这个方法的时候，
它会终止整个虚拟机。但是，他对于命令行有效性的非法终止是很合适的
 */


/*
第54条：谨慎地使用本地非法
Java Native Interface（JNI）允许Java应用程序可以调用本地非法，所谓本地方法是指用本地程序设计语言（比如c或者c++）来编写的特殊方法。
使用本地方法来提高性能的做法不值得提倡。
使用本地方法有一些严重的缺点。因为本地语言不是安全的。因为本地语言是与平台相关的，使用本地方法的应用程序也不再是可自由移植的。
 */


/*
第55条：谨慎地进行优化
优化的弊大于利，特别是不成熟的优化。
要努力编写好的程序而不是快的程序。
努力避免那些限制性能的设计决策
要考虑API设计决策的性能后果。
为了获得好的性能而对API进行包装，这是一种非常不好的想法。
 */

/*
第56条：遵守普遍接受的命名惯例
 */


/*
第9章：异常
 */
/*
第57条：只针对异常的情况才使用异常
因为异常机制的设计初衷是用于不正常的情形，所以很少会有JVM实现试图对它们进行优化，使得与显式的测试一样快速
把代码放在try-catch块中反而阻止了现代JVM实现本来可能要执行的某些特定优化
对数组进行遍历的标准模式并不会导致冗余的检查。有些现代的JVM实现会将它们优化掉。
异常应该只用于异常的情况下：它们永远不应该用于正常的控制流。
设计良好的API不应该强迫它的客户端为了正常的控制流而使用异常。
 */


/*
第58条：对可恢复的情况使用受检异常，对编程错误使用运行时异常
Java程序设计语言提供了三种可抛出结构（throwable）：受检的异常（checked exception）、运行时异常（run-time exception）和错误（error）
在决定使用受检的异常还是未受检的异常时，主要的原则是：如果期望调用者能够适当地恢复，对于这种情况就应该使用受检的异常。
有两种未受检的可抛出结构：运行时异常和错误。在行为上两者是等同的：它们都是不需要也不应该被捕获的可抛出结构。
如果程序抛出未受检的异常或错误，往往就属于不可恢复的情形，就绪执行下去有害无益。
如果程序没有捕获这样的可抛出结构，将会导致当前的线程停止，并出现适当的错误消息。
运行时异常来表明编程错误
 */


/*
第59条：避免不必要地使用受检的异常
 */


/*
第60条：优先使用标准的异常
专家级程序员与缺乏经验的程序员一个最主要的区别在于，专家追求并且通常也能够实现高度的代码重用。
最经常被重用的异常是IllegalArgumentException。当调用者传递的参数不合适的时候，往往就会抛出整个异常。
另一个经常被重用的异常是IllegalStateException。如果因为接收对象的状态而使调用非法，通常就会抛出这个异常。
另一个值得了解的通用异常是ConcurrentModificationException。如果对象被设计为专用于单线程或者与外部同步机制配合使用，
一旦发现它正在被并发地修改，就应该抛出这个异常。
最后一个值得注意的异常是UnsupportedOperationException。如果对象不支持所请求的操作，就会抛出这个异常。
 */


/*
第61条：抛出与抽象相对应的异常
 更高层的实现应该捕获低层的异常，同时抛出可以按照高层抽象进行解释的异常。这种做法被称为异常转译。。
 //Exception Translation
 tru{
    //use low-level abstraction to do our bidding
    ...
    }catch(lowerLevelException e){
        throw new HigherLevelException(...);
        }

一种特殊的异常转译形式称为异常链，如果底层的异常对于调试导致高层异常的问题非常有帮助，使用异常链就很合适。
低层的异常（原因）被传递到了高层的异常高层的异常提供访问非法来获取低层的异常：
//Exception Chaining
try{
    ...//use low-level abstraction to do our bidding
    }catch(lowerLevelException cause){
        throw new HigherLevelException(cause);
        }

高层异常的构造器将原因传到支持链(chaining-aware)的超级构造器，因此它最终将被传给Throwable的其中一个运行异常链的构造器，例如Throwable:
//Exception with chaining-aware constructor
class HigherLevelException extends Exception{
    HigherLevelException（Throwable cause){
        super(cause);
        }
}
 */


/*
第62条：每个方法抛出的异常都要有文档
始终要单独地声明受检地异常，并利用Javadoc地@throws标记，准确地记录下抛出每个异常地条件
使用Javadoc地@throws标签记录下一个方法可能排除地每个受检异常，但是不要使用throws关键字将未受检地异常包含在方法地声明中。
如果一个类中的许多方法出于同样的原因而抛出同一个异常，在该类的文档注释中对这个异常建立文档，
这是可以接受的，而不是为每个方法单独建立文档，
 */


/*
第63条：在细节消息中包含能捕获失败的信息
当程序由于未被捕获的异常而失败的时候，系统会子哦那个地打印出该异常的堆栈轨迹。在堆栈轨迹中包含该异常的字符串表示法，
即它的toString方法的调用结果。它通常包含该异常的类名，紧随其后的是细节消息。
为了捕获失败，异常的细节信息应该包含所有“对该异常有贡献”的参数和域的值。
为了确保在异常的细节消息中包含足够的能捕获失败的信息，一种方法是在异常的构造器而不是字符串细节消息中引入这些信息。然后，
娱乐这些信息，只要把它们放到消息描述中，就可以自动产生细节消息。例如IndexOutOfBoundsException并不是有个String构造器，
而是有个这样的构造器：
/**
 * Construct an IndexOutOfBoundsException
 *
 * @param lowerBound the lowest legal index value
 * @param upperBound the heighest legal indec value plus one
 * @param index the actual index value
 *
//public IndexOutOfBoundsException(int lowerBound, int upperBound, int index){
//    //Generate a detail message that captures the failure
//    super("lower bound:" + lowerBound +
//            ",upper boundL" + upperBound +
//            ",index:" + index);
//    //Save failure information for programmatic access
//    this.lowerBound = lowerBound;
//    this.upperBound = upperBound;
//    this.index = index;
}
 */


/*
第64条：努力使失败保持原子性
一般而言，失败的方法调用应该使对象保持在被调用之前的状态。具有这种属性的方法被称为具有失败的原子性。
有几种途径可以实现这种效果。最简单的办法莫过于设计一个不可变得的对象
对于可变对象上执行操作的方法，获取失败原子性最常见的方法是，在执行操作之前检查参数的有效性。
第三种获得失败原子性的办法远远没有那么常用，做法是编写一段恢复代码，
由它来拦截操作过程中发生的失败，以及使对象回滚到操作之前的状态上。
最后一种获得失败原子性的办法是，在对象的一份临时拷贝上执行操作，当操作完成之后再有临时拷贝中的结果代替对象的内容。
 */


/*
第65条：不要忽略异常
空的catch块会使异常达不到应有的目的
catch块也应该包含一条说明，解释为什么可以忽略这个异常。
 */



/*
第10章：并发 P229
 */
/*
第66条：同步访问共享的可变数据
 */
/*
关键字synchronized可以保证在同一时刻，只有一个线程可以执行某一个方法，或者某一个代码块。
同步不仅可以阻止一个线程看到对象处于不一致的状态之中，它还可以保证进入同步方法或者同步代码块的每个线程，
都看到有同一个锁保护的之前所有的修改效果。
为了在线程之间进行通信，也为了互斥范文，同步是必要的。
不要使用Thread.stop
要阻止一个线程妨碍另一个线程，建议做法是让第一个线程轮询（poll）一个boolean域，这个域一开始为false，但是可以通过第二个线程设置为true，
以表示第一个线程将终止自己。boolean域的读写操作都是原子的。
 */
//Broken -How long would you expect this program to run
class _66_StopThread{
    private static boolean stopRequested;
    public static void main(String[] args) throws InterruptedException{
        Thread backgroundThread = new Thread(new Runnable(){
            public void run(){
                int i = 0;
                while (!stopRequested)
                    i++;
            }
        });
        backgroundThread.start();
        TimeUnit.SECONDS.sleep(1);
        stopRequested = true;
    }
}
/*
上面这个程序永远不会终止：因为后台线程永远在循环。
问题在于，由于没有同步，就不能保证后台线程何时“看到主线程对stopRequested的值所做的改变。没有同步，虚拟机将这个代码：
while(!done)
    i++;
转变成这样：
if(!done)
    while(true)
        i++;
修正这个问题的一种方式是同步访问stopRequested域。
 */
//Properly synchronized cooperative thread termination
class _66_StopThread_change{
    private static boolean stopRequested;
    private static synchronized void requestStop(){
        stopRequested = true;
    }
    private static synchronized boolean isStopRequested(){
        return stopRequested;
    }
    public static void main(String[] args) throws InterruptedException{
        Thread backgroundThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (!isStopRequested())
                    i++;
            }
        });
        backgroundThread.start();;
        TimeUnit.SECONDS.sleep(1);
        requestStop();
    }
}
/*
注意写方法和都方法都被同步了。只同步写方法还不够。实际上，读和写操作没有都被同步，同步就不会起作用。
_66_StopThread_change中被同步方法的动作即使没有同步也是原子的。换句话说，这些方法的同步只是为了它的通信效果，而不是为了互斥访问。
如果stopRequested被声明为volatile，_66_StopThread_change中的锁就可以省略。
虽然volatile修饰符不执行互斥访问，但是它可以保证任何一个线程在读取该域的时候都能看到最近刚刚被写入的值：
 */

//Cooperation thread termination with a volatile field
class _66_StopThread_change2{
    private static volatile boolean stopRequested;
    public static void main(String[] args) throws InterruptedException{
        Thread backgroundThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (!stopRequested)
                    i++;
            }
        });
        backgroundThread.start();;
        TimeUnit.SECONDS.sleep(1);
        stopRequested = true;
    }
}
/*
在使用volatile的时候务必要小心。考虑下面的方法，假设它要产生序列号：
//Broken - requires synchronization
private static volatile int nextSerialNumber = 0;
public static int generateSerialNumber(){
    return nextSerialNumber++;
}
如果没有同步，这个方法没法正常工作。问题在于增量操作符（++）不是原子的。因此这个程序会计算出错误的结果。
修改generateSerialNumber方法的一种是在它的声明中增加synchronized修饰符。
最好还是遵循第47条中的建议，使用AtomicLong。它是java.util.concurrent.atomic的一部分。它所做的工作正是你想要的，并且
可能比同步版的generateSerialNumber执行得更好：
private static final Atomiclong nextSerialNum = new Atomiclong();
public static int generateSerialNumber(){
    return nextSerialNum.getAndIncrement();
}
当多个线程共享可变数据得时候，每个读或者写数据得线程都必须执行同步。
 */


/*
第67条：避免过度同步
过度同步可能会导致性能降低、死锁，甚至不确定得行为。
为了避免活性失败和安全性失败，在一个被同步得方法或者代码块中，永远不要放弃对客户端得控制。换句话说，在一个被同步得区域
内部，不要调用设计成要被覆盖的方法，或者是由客户端以函数的形式提供的方法。
考虑下面的类，它实现了一个可以观察到的集合包装。该类允许客户端在将元素添加到集合中时预定通知。这就是观察者模式。
为了简单起见，类在从集合中删除元素时，没有提供通知，但要提供也是件很容易的事情。
这个类是在16条中可重用的FrowardingSet上实现的：
 */
interface SetObserver<E>{
    //Invoke when an element is add to the observable set
    void added(ObservableSet<E> set, E element);
}
//Broken - invokes alien method from synchronized block
class ObservableSet<E> extends ForwardingSet<E>{
    public ObservableSet(Set<E> set){ super(set); }
    private final List<SetObserver<E>> observers = new ArrayList<SetObserver<E>>();
    public void addObserver(SetObserver<E> observer){
        synchronized (observers){
            observers.add(observer);
        }
    }
    public boolean removeObserver(SetObserver<E> observer){
        synchronized (observers){
            return observers.remove(observer);
        }
    }
//    public void notifyElementAdded(E element){//旧
//        synchronized (observers){
//            for(SetObserver<E> observer : observers)
//                observer.added(this, element);//同步区域调用外来方法
//        }
//    }
    private void notifyElementAdded(E element){//修改版本
        List<SetObserver<E>> snapshot = null;
        synchronized (observers){
            snapshot = new ArrayList<SetObserver<E>>(observers);
        }
        for (SetObserver<E> observer : snapshot)
            observer.added(this,element);
    }
    @Override public boolean add(E element){
        boolean added = super.add(element);
        if (added)//如果加入成功调用 预定通知
            notifyElementAdded(element);
        return added;
    }
    @Override public boolean addAll(Collection<? extends E> c){
        boolean result = false;
        for(E element : c)
            result |= this.add(element);//calls notifyElementAdded  |= 或运算  相当于只要一个加入成功就返回true
        return result;
    }
}
/*
Oberver通过调用addObserver方法预定通知，通过调用removeObserver方法取消预定。在这两种情况下，这个回调接口的实例都会被传递给方法：
interface SetObserver<E>{
    //Invoke when an element is add to the observable set
    void added(ObservableSet<E> set, E element);
}
 */
class _67_Test{
    //如果只是粗略地检验一下，ObervableSet会显得很正常。例如，下面程序打印出0--99地数字：
//    public static void main(String[] args){
//        ObservableSet<Integer> set = new ObservableSet<Integer>(new HashSet<Integer>());
//        set.addObserver(new SetObserver<Integer>() {
//            @Override
//            public void added(ObservableSet<Integer> set, Integer element) {
//                System.out.println("Add:" + element);
//            }
//        });
//        for (int i = 0; i < 100; i++)
//            set.add(i);
//    }
    /*
    尝试一些更复杂地例子。假设我们用一个addObserver调用来代替上面地调用，用来替换地哪个assObserver调用传递了一个打印Integer值
    的观察者，这个值被添加到该集合中，如果值为23，这个观察者要将自身删除：
     */
        public static void main(String[] args) {
            ObservableSet<Integer> set = new ObservableSet<Integer>(new HashSet<Integer>());
            set.addObserver(new SetObserver<Integer>() {
                @Override
                public void added(ObservableSet<Integer> set, Integer element) {
                    System.out.println("Add:" + element);
                    if (element == 23)
                        set.removeObserver(this);
                }
            });
            for (int i = 0; i < 100; i++)
                set.add(i);
            System.out.println(set);
        }
        /*
        上面这个程序在打印出0--23的数字后抛出ConcurrentModificationException。问题在于当notifyElementAdded调用观察者的added方法时，
        它正处于遍历observers立标的过程中。added方法调用可观察集合的removeObserver方法，从而调用observers.remove。
        现在，有麻烦了。我们正在企图在遍历列表的过程中，将一个元素从列表中删除，这是非法的。
        notifyElementAdded非法中的迭代是在同一个同步的块中，可以防止并发的修改，但是无法防止迭代线程本身回调到可观察的集合中，
        也无法防止修改它的observers列表。
         */
        /*
        现在尝试一些比较奇特的例子：我们来编写一个试图取消预订的观察者，但是不直接调用removeObserve，它用另一个线程的服务来完成。
        这个观察者使用了一个executor server（见68条）：
         */
//    public static void main(String[] args) {
//        ObservableSet<Integer> set = new ObservableSet<Integer>(new HashSet<Integer>());
//        //Observer that uses a background thread needlessly
//        set.addObserver(new SetObserver<Integer>() {
//            @Override
//            public void added(ObservableSet<Integer> set, Integer element) {
//                System.out.println("Add:" + element);
//                if (element == 23){
//                    ExecutorService executorService = Executors.newSingleThreadExecutor();
//                    final SetObserver<Integer> observer = this;
//                    try{
//                        executorService.submit(new Runnable() {
//                            @Override
//                            public void run() {
//                                set.removeObserver(observer);
//                            }
//                        }).get();
//                    }catch (ExecutionException ex){
//                        throw new AssertionError(ex.getCause());
//                    }catch (InterruptedException ex){
//                        throw new AssertionError(ex.getCause());
//                    }finally {
//                        executorService.shutdown();
//                    }
//                }
//            }
//        });
//        for (int i = 0; i < 100; i++)
//            set.add(i);
//    }
    /*
    这一次没有遇到异常，而是遇到了死锁。后台线程调用set.removeObserver，它企图锁定observers，但它无法获得该锁，
    因为主线程已经有锁了。在这期间，主线程一直在等待后台线程来完成对观察者的删除，这正是造成死锁的原因。
     */
    /*
    幸运的是，通过将外来方法的调用移出同步代码块来解决这个问题通常并不太困难。对于notifyElementAdded方法，这还涉及给
    observers列表拍张“快照”，然后没有锁也可以安全地遍历这个列表了。经过这一修改，前两个例子运行起来便再也不会出现异常或者死锁了：
     */
    //Alien method move outside of synchronized block - open calls
//    private void nonotifyElementAdded(E element){
//        List<SetObserver<E>> snapshot = null;
//        synchronized (observers){
//            snapshot = new ArrayList<SetObserver<E>>(observers);
//        }
//        for (SetObserver<E> observer : snapshot)
//            observer.added(this,element);
//    }
}
/*
事实上，要将外来方法的调用移出同步的代码块，好于一种更好的方法。
Java类库提供了一个并发集合，称作CopyOnWriteArrayList，这是专门为此定制的。这是ArrayList的一种变体，通过重新拷贝整个底层数组，
在这里实现所有的写操作。由于内部数组永远不改动，因袭迭代不需要锁定，速度也非常快。如果大量使用，
CopyOnWriteArrayList的性能将大受影响，但是对于观察者列表来说确是很好的，因为它们几乎不改动，并且经常被遍历。
如果这个列表改成使用CopyOnWriteArrayList，就不必改动ObservableSet的add和addAll方法。下面是这个类的其余代码。
注意其中并没有任何显式的同步。
private final List<SetObserver<E>> observers = new CopyOnWriteArrayList<SetObserver<E>>();
    public void addObserver(SetObserver<E> observer){
        observers.add(observer);
    }
    public boolean removeObserver(SetObserver<E> observer){
        return observers.remove(observer);
    }
    private void notifyElementAdded(E element){
        for (SetObserver<E> observer : observers)
            observer.added(this, element);
    }
 */
/*
在同步区域之外被调用的外来方法被称作“开放调用”。除了可以避免死锁外，开放调用还可以极大地增加并发性。
通常。应该在同步区域做尽可能少地工作。
为了避免死锁和数据破坏，千万不要从同步区域调用外来方法。
StringBuffer 线程安全， StringBuilder线程不安全
 */


/*
第68条：exectuor和task优先于线程
 */
/*
Java 1.5发行版本中，Java平台中增加了java.util.concurrent。这个包中包含了一个 Executor Framework，
这是一个很灵活的基于接口的任务执行工具。它创建了一个更好的工作队列，只需要一行代码：
ExecutorService executor = Executors.newSingleThreadExecutor();
下面是为执行提交一个runnable的方法：
executor.ececute(runnable);
下面告诉executor如何优雅地终止（如果做不到这一点，虚拟机可能将不会退出）：
exector.shutdown();

可以利用executor service完成更多地事情。例如，可以等待完成一项特殊地任务，你可以等待一个任务集合中地任何任务或所有任务完成，
你可以等待executor service优雅地完成终止，等等。
 */


/*
第69条：并发工具由于wait和notify
既然正确地使用wait和notify比较困难，就应该用更高级地并发工具来代替。
java.util.concurrent中更高级地工具分为3类：Executor Framework、并发集合（Concurrent Collection）以及同步器（Synchronizer）。
并发集合 为标准的集合接口（如List、Queue和Map）提供了高性能的并发实现。为了提高并发性，这些实现在内部自己管理同步。
因此，并发集合中不可能排除并发活动；将它锁定没有上面作用，只会使程序的速度变慢。
这意味着客户无法原子地对并发集合进行方法调用。因此有些集合接口已通过依赖状态的修改操作进行扩展，
它将几个基本操作合并到了单个原子操作中。
除非不得已，否则应该优先使用ConcurrentHashMap，而不是Collections.synchronizedMap或者HashTable.
有些接口已经通过阻塞操作进行了扩展，它们会一直等待（或者阻塞）到可以成功执行为止。例如，BlockingQueue扩展了Queue接口，
并添加了包括take在内的几个方法，它从队列删除并返回了头元素，如果队列为空，就等待。这样就允许将阻塞队列用于工作队列，
也称作生产者-消费者队列，一个或者多个生产者线程在工作队列中添加项目，并且当工作项目可用时，一个或多个消费者线程则从
工作队列中取出队列并处理工作项目。大多数ExecutorService实现都是用BlockingQueue。

同步器 是一些使线程能等待另一个线程的对象，允许它们协调动作。
最常用的同步器是CountDownLatch和Semaphore。比较不常用的是CyclicBarrier和Exchanger。
倒计数锁存器 （CountDown Latch）是一次性的障碍，允许一个或者多个线程等待一个或者多个其它线程来做某些事情。
CountDownLatch的唯一构造器带有一个int类型的参数，这个int参数是指允许所有在等待的线程被处理之前，必须在锁存器上调用countDown方法的次数。

要在这个简单的基本类型之上构建一些有用的东西，做起来是相当的容易。例如，假设想要构建一个简单的框架，用来给一个动作的并发执行定时。
这个框架中包含单个方法，这个方法带有一个执行该动作的executor，一个并发级别（表示要并发执行该动作的次数），以及表示该动作的runnable。
所有的工作线程自身都准备好，要在timer线程启动时钟之前运行该动作（为了实现准确的定时，这是必须的）。
当最后一个工作线程准备好运行该动作时，timer线程就“发起头炮”，同时允许工作线程执行该动作。一旦最后一个工作线程执行完该动作，
timer线程就立即停止计时。直接在wait和notify之上实现这个逻辑至少来说会很混乱，而在CountDownLatch之上实现则相当简单：
 */
//Simple framwork for timing concurrent execution
class _69_Test{
    public static long time(Executor executor, int concurrency, final Runnable action) throws InterruptedException{
        final CountDownLatch ready = new CountDownLatch(concurrency);
        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch done = new CountDownLatch(concurrency);
        //CountDownLatch(int count)构造一个用给定计数初始化的 CountDownLatch。
        for (int i = 0; i < concurrency; i++){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    ready.countDown();//tell timer we're ready
                    // public void countDown()递减锁存器的计数，如果计数到达零，则释放所有等待的线程。
                    try {
                        start.await();
                        action.run();
                    }catch (InterruptedException e){
                        Thread.currentThread().interrupt();
                    }finally {
                        done.countDown();//tell timer we're done
                    }
                }
            });
        }
        ready.await();//wait for all workers to be ready
        long startNano = System.nanoTime();
        start.countDown();//and they are off
        done.await();//wait for all workers to finish
        return System.nanoTime() - startNano;
    }
}
/*
注意这个方法使用了三个倒计数锁存器。第一个是ready，工作线程用它来告诉timer线程它们已经准备好了。然后工作线程在第二个锁存器上
等待，也就是start。当最后一个工作线程调用ready.countDown时，timer线程记录下起始时间，并调用start.countDown,允许所有的工作
线程继续进行。然后timer线程在第三个锁存器（done）上等待，直到最后一个工作线程运行完该动作，并调用done.countDown。一旦调用
这个，timer线程救护苏醒过来，并记录下结束的时间。
还有，传递给time方法的executor必须允许创建至少与指定并发级别一样多的线程，否则这个测试就永远不会结束。这就是线程饿死死锁。
如果工作线程捕捉到InterruptedException，就会利用习惯用法 Thread.currentThread().interrupt();重新断言中断，并从它的run方法中
返回。这样就允许executor在必要的时候处理中断，事实上也理当如此。最后，注意利用System.nanoTIme来给后动定时，
而不是利用System.currentTimeMillis。对于间歇式的定时，始终应该优先使用System.nanoTIme，而不是System.currentTimeMillis。
System.nanoTIme更加准确也更加精确，它不受系统的实时时钟的调整所影响。

始终应该使用wait循环模式来调用wait方法；永远不要在循环之外调用wait方法。
 */


/*
第70条：线程安全性的文档化
在一个方法声明中出现synchronized修饰符，这是个实现细节，并不是导出的API的一部分。
一个类为了可被多个线程安全地使用，必须在文档中清除地说明它所支持地线程安全性级别。
线程安全性地几种级别：
    1：不可变的---这个类的实例是不可变的。所以，不需要外部的同步。String、Long、BigInteger。
    2：无条件的线程安全---这个类的实例是可变的，但是这个类有着足够的内部同步。所以它的实例可以被并发使用，无需任何外部的同步。
    3：有条件的线程安全---除了有些方法为进行安全的并发使用而需要外部同步之外，这种线程安全级别与无条件的线程安全相同。
    4：非线程安全
    5：线程对立的---这个类不能安全地被多个线程并发使用，即使所有地方法调用都被外部同步包围。
 */


/*
第71条：慎用延迟初始化
延迟初始化是延迟到需要域地值时才将它初始化地这种行为。如果永远不需要这个值，这个域就永远不会被初始化。
可以用于静态域也可以用于实例域。
延迟初始化降低了初始化类或者创建实例地开销，却增加了访问被延迟初始化地域地开销。
延迟初始化有它的好处，如果域只在类的实例部分被访问，并且初始化这个域的开销很高，很可能就值得延迟初始化。
大多数情况下，正常的初始化要优先于延迟初始化。
如果处于性能的考虑而需要对静态域使用延迟初始化，就使用lazy initialization holder class模式。如下：
//lazy initialization holder class idiom for static fields
private static class FieldHolder{
    static final FieldType field = computeFieldValue();
}
static FieldType getField(){return FieldHolder.field;}
双重检查模式就是懒汉式的同步形式。
 */


/*y
第72条：不要依赖于线程调度器
当有多个线程可以运行时，有线程调度器决定哪些线程将会运行，以及运行多长时间。任何一个合理的操作系统在做出这样的决定时，都会努力
做到公正，但是所采用的策略却大相径庭。因此编写良好的程序不应该依赖于这种策略细节。
任何依赖于线程调度器来达到正确性或者性能要求的程序，很可能都是不可移植的。
要编写健壮的、响应良好的、可移植的多线程应用程序，最好的办法是确保可运行线程的平均数量不明显多余处理器数量。
这使得线程调度器没有更多的选择：它只需要运行这些可运行的线程，直到它们不再可运行为止。
注意可运行的线程的数量并不等于线程的总数量。
保持可运行线程数量尽可能少的主要方法是，让每个线程做些有意义的工作，然后等待更多有意义的工作。如果线程没有做有意义的工作，
就不应该运行。
Thread.yield没有可测试的语义。
线程优先级是Java平台上罪不可一直的特征。
简而言之，不要让程序的正确性依赖域线程调度器。推论，不要依赖Thread.yield或者线程优先级。
 */


/*
第73条：避免使用线程组
除了线程、锁和监视器之外，线程系统还提供了一个基本的抽象，即线程组。
线程组的初衷是作为一种隔离applet（小程序）的机制，当然是出于安全的考虑。但是它们从来没有真正履行这个承诺
线程组已经过时了。
总之，线程组没有提供太多有用的功能，而且它们提供的许多功能还都是有缺陷的。
 */


/*
第11章：序列化
将一个对象编码成一个字节流，称作该对象的序列化，相反的过程称作反序列化。
 */
/*
第74条：谨慎地实现Serializable接口
要想使一个类地实例可被序列化，只要在它地声明中加入“implements Serializable”字样即可。
实现Serializable接口而付出地最大代价是，一旦一个类被发布，就大大降低了“改变这个类地实现”的灵活性。
实现Serializable的第二个代价是，它增加了出现Bug和安全漏洞的可能性。
实现Serializable视为第三个代价是，随着类发行新的版本，相关的测试负担也增加了。
实现Serializable接口并不是一颗很轻松就可以做出的决定。它提供了一些实在的益处：如果一个类将要加入到某个框架中，
并且该框架依赖于序列化来实现对象传输或者持久化，对于这个类来说，实现Serializable接口就非常有必要。
为了继承而设计的类，应该尽可能少地取实现Serializable接口，用火地接口也应该尽可能少地继承Serializable接口。
对于为继承而设计的不可序列化的类，你应该考虑提供一个无参构造器。
有一种办法可以给“不可序列化但可扩展的类”增加无参构造器。假设该类有这样一个构造器：
public AbstractFoo(int x, int y){...}
下面的转换增加了一个受保护的无参构造器，和一个初始化方法。初始化方法域正常的构造器具有相同的参数。
并且也建立起同样的约束关系。注意保存对象状态（x，y）的遍历不能是final的，因为它们是由initialize方法设置的：
 */
//Nonserializable sateful class allowing serializable subclass
abstract class AbstractFoo{
    private int x, y;//Our state
    //This enum and field are used to track initialization
    private enum State {NEW, INITIALIZING, INITIALIZED};
    private final AtomicReference<State> init = new AtomicReference<State>(State.NEW);
    public AbstractFoo(int x, int y){ initialize(x,y); }
    //This constructor and the flolowing method allow
    //subclass's readObject method to initialize our state
    protected AbstractFoo(){}
    protected final void initialize(int x, int y){
        if (!init.compareAndSet(State.NEW, State.INITIALIZING))
            throw new IllegalStateException("Already initialized");
        this.x = x;
        this.y = y;
        //do something else the original constructor did
        init.set(State.INITIALIZED);
    }
    protected final int getX(){ checkInit(); return x; }
    protected final int getY(){ checkInit(); return y; }
    private void checkInit(){
        if (init.get() != State.INITIALIZED)
            throw new IllegalStateException("Uninitialized");
    }
    //Remainder omitted
}
/*
有了上面程序对于对象初始化的保证，实现一个可序列化子了就很简单了：
 */
//Serializable subclass of onoserializable stateful class
class Foo extends AbstractFoo implements Serializable{
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException{
        s.defaultReadObject();
        //Manually deserialize and initialize superclass state
        int x = s.readInt();
        int y = s.readInt();
        initialize(x,y);
    }
    private void writeObject(ObjectOutputStream s) throws IOException{
        s.defaultWriteObject();
        //Manually seriaize superclass state
        s.writeInt(getX());
        s.writeInt(getY());
    }
    //Constructor does not use the fancy mrchanism
    public Foo(int x, int y){ super(x,y);}
    private static final long serialVersionUID = 1856835860954L;
}
/*
内部类不应该实现Serializable。
 */


/*
第75条：考虑使用自定义的序列化形式
如果没有先认真考虑默认的序列化形式是否合适，则不要贸然接受。
默认的序列化形式描述了该对象内部所包含的数据，以及每一个可以从这个对象到达的其他对象的内部数据。它描述了所有这些对象被
链接起来后的拓扑结构。对于一个对象而言，理想的序列化形式以你改只包含该对象所表示的逻辑数据，而逻辑数据域物理表示法应该
是各自独立的。
如果一个对象的物理表示法等同于它的逻辑内容，可能就适合于使用默认的序列化形式。
即使你确定了默认的序列化形式是合适的，通常还必须提供一个readObject方法以保证约束关系和安全性。
当一个对象的物理表示法与它的逻辑数据内容有实质性的区别时，使用默认序列化形式会有以下4个缺点：
    1：它使这个类的导出API永远束缚在该类的内部表示法上。
    2：它会消耗过多的空间。
    3：它会消耗过多的时间。
    4：它会引起栈溢出。
如果所有的实例域都是瞬时的，从技术角度而言，不调用defaultReadObject()和defaultWriteObject()也是允许的，但是不建议这样做。
对于散列表而言，接受默认的速猎虎啊形式将会构成一个严重的Bug。
在决定将一个域做成非transient的之前，请一定要确信它的值将是这个对象逻辑状态的一部分。
如果你正在使用默认的序列化形式，并且把一个或者多个域标记为transient，则要记住，当一个实例被反序列化的时候，这些域
将被初始化为它们的默认值：对于对象引用域，默认值为null。。。如果这些值不能被任何transient域多接受，
你就必须提供一个readObject方法，它首先调用defaultReadObject()，然后把这些transient域恢复成可接受的值。
无论是否使用默认的序列化形式，如果在读取整个对象状态的任何其他方法上强制任何同步，则必须在对象序列化上强制这种同步。
不管选择了哪种序列化形式，都要为自己编写的每个可序列化的类声明一个显式的序列化版本UID。
要声明一个序列版本UID非常简单：
private static final long serialVersionUID = 1856835860954L;
 */


/*
第76条：保护性地编写readObject方法
 */
final class _76_Period implements Serializable{
    private final Date start;
    private final Date end;
    /**
     * @param start the begining of period
     * @param end  the end of the period; must bot precede start
     * @throws IllegalArgumentException if start is after end
     * @throws NullPointerException if start or end is null
     */
    public _76_Period(Date start, Date end){
        this.start = new Date(start.getTime());
        this.end = new Date(end.getTime());
        if (this.start.compareTo(this.end) > 0)
            throw new IllegalArgumentException(start + " after " + end);
    }
    public Date getStart(){ return new Date(start.getTime()); }
    public Date getEnd(){ return new Date(end.getTime()); }
    public String toString(){ return start + ".-" + end; }
    //remainder omitted
}
/*
假设决定把这个类做成可序列化的。因为_76_Period对象的物理表示法正好反映它的逻辑数据内容，所以使用默认序列化形式并没有声明不合理。
为了能使这个类可序列化，似乎只需要在类的声明中增加“implements Serializable”字样。然而，如果真这样做，那么这个类将不在保证
它的关键性约束了。
问题在于，readObject方法实际上相当于另一个公有的构造器，如同其他构造器一样，它也要求注意同样的所有注意事项。
构造器必须检查其参数的有效性，并且在必要的时候对参数进行保护性拷贝，同样地，readObject方法也需要这样做。
如果readObject方法无法做到这两者之一，对于攻击者来说，要违反这个类地约束条件相对就比较简单了。
不严格地说，readObject是一个“用字节流作为唯一参数”地构造器。在正常情况下，对一个正常构造的实例进行序列化可以产生字节流。
但是当面对一个人工仿造地字节流时，readObject产生的对象会违反它的类的约束条件，这时问题就产生了。
为了修正这个问题，你可以为_76_Period提供一个readObject方法，该方法首先调用defaultReadObject，然后检查被反序列化之后的对象的
有效性。如果有效性检查失败，readObject方法就抛出一个InvalidObjectException异常，时反序列化不能成功完成：
//readObject method with validity checking
private void readObject(ObjectInputStream s) throws IOException , ClassNotFoundException{
    s.defaultReadObject();
    //Check that our invariants are satisfied
    if(start.comapreTo(end) > 0)
        throw now InvalidObjectException(start + "after" + end);
}
尽管这样的修正避免了攻击者创建无效的_76_Period实例，但是这里仍然隐藏着一个更为微妙的问题。通过伪造字节流，要想创建可变的_76_Period
实例仍然是有可能的，做法是：字节流以一个有效的_76_Period实例开头，然后附加上两个额外的引用，指向_76_Period实例中的两个私有的Date域。
攻击者从ObjectInputStream中地区_76_Period实例，然后读取附加在其后面的“恶意编制的对象引用”。这些对象引用使得攻击者能够访问到_76_Period
对象内部的私有Date域所引用的对象。通过改变这些Date实例，攻击者可以改变_76_Period实例。下面的类演示了这种攻击：
 */
class MutablePeriod{
    //A period instance
    public final _76_Period period;
    //period's start field, to which we shouldn't have access
    public final Date start;
    //period's end field, to which we shouldn't have access
    public final Date end;
    public MutablePeriod(){
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            //Serialize a valid _76_Period instance
            out.writeObject(new _76_Period(new Date(), new Date()));
            /**
             * Append rogue "previous object refs" for internal
             * Date field in _76_Period.
             */
            byte[] ref = {0x71, 0, 0x7e, 0, 5};//ref #5
            bos.write(ref);//the start field
            // bos.write(byte[] b, int off, int len)将指定 byte 数组中从偏移量 off 开始的 len 个字节写入此 byte 数组输出流
            ref[4] = 4;//ref #4
            bos.write(ref);//the end field
            //Deserialize _76_Period and "stolen" Date referennces
            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));//可使用 toByteArray() 和 toString() 获取数据
            period = (_76_Period) in.readObject();
            start = (Date)in.readObject();
            end = (Date)in.readObject();
        }catch (Exception e){
            throw new AssertionError(e);
        }
    }
    public static void main(String[] args){
        MutablePeriod mp = new MutablePeriod();
        _76_Period p = mp.period;
        Date pEnd = mp.end;
        //Let's turn back the clock
        pEnd.setYear(78);
        System.out.println(p);
        pEnd.setYear(69);
        System.out.println(p);
    }
}
/*
问题的根源在于，_76_Period的readObject方法并没有完成足够的保护性拷贝。
当一个对象被反序列化的时候，对于客户端不应该拥有的对象引用，如果哪个域包含了这样的对象引用，就必须要做保护性拷贝，这时非常重要的。
下面的readObject方法可以确保_76_Period的约束条件不会遭到破坏，以保持它的不变性：
//readObject method with defensive copying and validity checking
private void readObject(ObjectInputStream s) throws IOException , ClassNotFoundException{
    s.defaultReadObject();
    //defensively copy our mutable components
    start = new Date(start.getTime());
    end = new Date(end.getTime());
    //Check that our invariants are satisfied
    if(start.comapreTo(end) > 0)
        throw now InvalidObjectException(start + "after" + end);
}
readObject方法不可以调用可覆盖的方法，无论是直接还是间接调用。
*/


/*
第77条：对于实例控制，枚举类型由于readResolve
如果一个Singleton类在声明中加上了“implements Serializable”的字样，它就不是一个Singleton。
readResolve特性允许你用readObject创建的实例代替另一个实例。
对于一个正在被反序列化的对象，如果它的类定义了一个readResolve方法，并且具备真确的声明，那么在反序列化之后，新建对象
上的readResolve方法就会被调用。然后该方法返回的对象引用将被返回，取代新建的对象。
在这个特性的绝大多数用法中，指向新建对象的引用不需要在被保留，因此立即成为垃圾回收的对象。
class _77_Elvis{
    public static final _77_Elvis INSTANCE = new _77_Elvis();
    private _77_Elvis(){...}
    public void leaveTheBuilding(){...}
}
如果_77_Elvis类要实现Serializable接口，下面的readResolve方法就足以保证它的Singleton属性：
//readResolve for instance control - you can do better
private Object readResolve(){
    //return the true _77_Elvis and let the garbage collector
    //take care of the _77_Elvis impersonator.
    return INSTANCE;
}
该方法忽略了被反序列化的对象，只返回该类初始化时创建的那个特殊的_77_Elvis实例。因此，_77_Elvis实例的序列化形式并不需要包含
任何实际的数据；所有的实例域都应该被声明为transient的。事实上，如果依赖readResolve进行实例控制，带有对象引用类型的所有
实例域则都必须被声明为transient的。否则，那种破釜沉舟式的攻击者，就有可能在readResolve方法被运行之前，保护指向反序列化对象的引用，
采用的方法类似有76条中提到的MutablePeriod攻击。

如果Singleton包含一个非transient的对象引用域，这个域的内容就可以在Singleton的readResolve方法运行之前被反序列化。
当对象引用的内容被反序列化时，它就允许一个精心制作的流“盗用”指向最初被反序列化的Singleton的引用。
具体详细的工作原理：首先编写一个“盗用者”类，它既有readResolve方法，又有实例域，实例域指向被序列化的Singleton的引用，
“盗用者”类就“潜伏”在其中。在序列化流中，用“盗用者”类的实例代替Singleton的非transient时域。你修现在就有了一个循环：
Singleton包含“盗用者”类，“盗用者”类则引用该Singleton。
由于Singleton包含“盗用者”类，当这个Singleton被反序列化时，“盗用者”类的readResolve方法先运行。因此，当“盗用者”的readResolve
方法运行时，它的实例域仍然引用被部分序列化（并且还没有被解析）的Singleton。
“盗用者”的readResolve方法从它的实例有中将引用复制到静态域中，以便改引用可以在readResolve方法运行之后被访问到。然后这个方法为它
所藏身的那个域返回正确的类型值。如果没有这么做，当序列化系统试着将“盗用者”引用保存到这个域中时，JVM就会抛出ClassCastException。
为了更具体说明这一点，考虑下面有问题的SIngleton：
 */
//Broken singleton - has nontransient object reference field
class _77_Elvis implements Serializable{
    public static final _77_Elvis INSTANCE = new _77_Elvis();
    private _77_Elvis(){}
    private String[] favoriteSongs = {"Hound Dog", "Heartbreak Hotel"};
    public void printFavorites(){
        System.out.println(Arrays.toString(favoriteSongs));
    }
    private Object readResolve(){
        return INSTANCE;
    }
}
//下面这个是“盗用者”类，是根据上面的描述构造的：
class _77_ElvisStealer implements Serializable{
    static _77_Elvis impersonator;
    private _77_Elvis payload;
    private Object readResolve(){
        //save a reference to the "unresolved" _77_Elvis instance
        impersonator = payload;
        //return an object of correct type for favorites field
        return new String[] {"a Fool Such as he"};
    }
    private static final long serialVersionUID = 0;
}
/*
下面代码见P273  最终这个代码可以创建2个戒烟不同的_77_Elvis实例。通过将favorites域声明为transient，可以修正这个问题。
但是最好把_77_Elvis做成一个单元素的枚举类型进修正。

如果反过来，将一个可序列化的实例受控的类编写成枚举，就可以绝对保证除了所声明的常量外，不会有别的实例。
以下是把_77_Elvis改写成枚举的例子：
 */
//Eunm singleton - the preferred approach
enum _77_ElvisEnum{
    INSTANCE;
    private String[] favoriteSongs = {"Hound Dog", "Heartbreak Hotel"};
    public void printFavorites(){
        System.out.println(Arrays.toString(favoriteSongs));
    }
}

/*
readResolve的可访问性很重要。如果把readResolve方法放在一个final类上，它就应该是私有的。如果把readResolve方法放在一个非final的类上，
就必须认真考虑它的可访问性。P274

总而言之，你应该尽可能地使用枚举类型来实施实例控制地约束条件。如果做不到，同时又需要一个既可序列化又是实例受控地类，就必须
提供一个readResolve方法，并确保该类地所有实例域都为基本类型，或者都是transient的。
 */


/*
第78条：考虑用序列化代理代替序列化实例
如上条所说，决定实现Serializable接口，会增加出错和出现安全问题的可能性，因为它导致实例要利用语言之外的机制来创建，而不是用不同的构造器。
然而，有一种方法可以极大地减少这些风险。这种方法就是序列化代理模式。
序列化代理模式相当简单。首先，为可序列化地类设计一个私有的静态嵌套类，精确地表示外围类的逻辑状态。这个嵌套类被称作序列化代理，
它应该有一个单独的构造器，其参数类型就是那个外围类。这个构造器只从它的参数中复制数据：它不需要任何一致性检查或者保护性拷贝。
从设计的角度看，序列化代理的默认序列化形式是外围类的最好的序列化形式。外围类及其序列化代理都必须声明实现Serializable接口。
考虑在76（39）条中做成可序列化的Period类，以下是这个类的一个序列化代理。Period简单，以致它的序列化代理有着与类完全形同的域：

//Serialization proxy for class
private static class SerializationProxy implements Serializable{
    private final Date start;
    private final Date end;
    SerializationProxy(_39_Period p){
        this.start = p.start;
        this.end = p.end;
    }
    private static final long serialVersionUID = 23405485048L;//Any number do (item 75)
}
接下来，将下面的writeReplace方法添加到外围类中。通过序列化代理，这个方法可以被逐字地复制到任何类中：
writeReplace method for the Serialization proxy pattern
private Object writeReplace(){
    return new SerializationProxy(this);
}
这个方法地存在导致序列化系统产生一个SerializationProxy实例，代替外围类地实例。换句话说，writeReplace方法在序列化之前
将外围类地实例转变成了它的序列化代理。
有了这个writeReplace方法后，序列化系统永远不会产生外围类的序列化实例，但是攻击者有可能伪造，企图违反该类的约束条件。
为了确保这种攻击无法得逞，只要在外围类中添加这个readObject方法即可：
//readObject method for the serialization pattern
private void readObject(ObjectInputStream stream) throws InvalidObjectException{
    throw new InvalidObjectException("Proxy required");
}
最后在SerializationProxy类中提供一个readResolve方法，它返回一个逻辑上相当的外围的实例。这个方法的出现，
导致序列化系统在反序列化时将序列化代理转变回外围类实例。
//readResolve method for Period.SerializationProxy
private Object readResolve(){
    return new Period(start,end);//use public constructor
}
序列化代理方法可以阻止伪字符流的攻击以及内部域的盗用攻击。
*/





public class _2_ {

}
