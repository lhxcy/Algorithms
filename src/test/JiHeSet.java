package test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Created by XCY on 2017/9/4.
 *
 *      a1.retainAll(a2),取交集，a1变为a1与a2的交集
 *      迭代器iterator：
 *              Iterator it = a1.iterator();
 *              //System.out.println(it.next());
 *              while (it.hasNext()){//获取迭代器用于取出集合中元素
 *              System.out.println(it.next());}
 *
 *       List：元素有序，可以重复 线程不同步
 *              List特有迭代器，listIterator 在迭代时，不可以通过集合对象的方法操作集合中的元素，会抛出异常
 *              所以，在迭代器时，只能使用迭代器的方法操作元素，但是iterator方法较少，所以引出listIterator
 *       Set：元素无序，不可重复
 *
 *       Vector 线程同步（不建议用）
 *       HashSet：底层哈希表
 *                  用HashSet存储对象，需要覆盖hashcode方法和equals方法
 *
 *
 *       TreeSet：底层结构二叉树
 *       TreeSet排序第一种方式：让元素自身具备比较性，元素需要实现Comparable接口，覆盖compareTo方法
 *       TreeSet排序第二种方式：当元素自身不具备比较性，或者具备的比较性不是所需要的，这时就需要让集合自身具备毕竟性
 *          在集合一初始化时就有了比较方式
 *          当两种都存在时，以比较器为主
 *
 *          第二种方法相对较好
 *
 *    泛型：解决安全问题   ArrayList<String> a1 = new ArrayList<String>();
 *           此时需要改变迭代器，其他不变     Iterator<String> it = ts.iterator();
 *                                              while(it.hasNext()){
 *                                                  String s = it.next();
 *                                                  System.out.println();
 *                                              }
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

class strLength1 implements Comparator<String>{//避免强制转换
    public int compare(String str1, String str2){
        int num = new Integer(str1.length()).compareTo(new Integer(str2.length()));
        if (num == 0){
            return str1.compareTo(str2);
        }
        return num;
//        if (str1.length() > str2.length()){
//            return 1;
//        }
//        if (str1.length() == str2.length()){
//            return 0;
//        }
//        return -1;
    }
}





/*
按字符串长度排序
 */
class strLength implements Comparator{
    public int compare(Object obj1, Object obj2){
        String str1 = (String)obj1;
        String str2 = (String)obj2;
        int num = new Integer(str1.length()).compareTo(new Integer(str2.length()));
        if (num == 0){
            return str1.compareTo(str2);
        }
        return num;
//        if (str1.length() > str2.length()){
//            return 1;
//        }
//        if (str1.length() == str2.length()){
//            return 0;
//        }
//        return -1;
    }
}

/*
TreeSet排序第二种方式
当元素自身不具备比较性，或者具备的比较性不是所需要的，这时就需要让集合自身具备毕竟性
定义了比较器，将比较器对象作为参数传递给TreeSet集合的构造函数
当两种都存在时，以比较器为主
定义一个类实现Comparetor接口，覆盖compare方法
 */
class student11 implements Comparable{//TreeSet排序第一种方式
    private String name;
    private int age;
    student11(String name, int age){
        this.name = name;
        this.age = age;
    }
    public int compareTo(Object obj){//必须实现该函数，不然TreeSet不知道怎末比较
//        return 1;//假如想让怎末进入怎末取出就直接返回1就可以了
        if (!(obj instanceof student11)){
            throw new RuntimeException("不是学生对象");
        }
        student11 s = (student11)obj;
        System.out.println(this.getName() + "....." + s.getName());
        if (this.age > s.age){
            return 10;//只要返回正数就好
        }
        if (this.age == s.age){//排序时当只要条件满足时，判断次要条件
            return this.name.compareTo(s.name);//String默认实现compareTo
        }
        return -1;//负数就好
    }
    public String getName(){
        return name;
    }
    public int getAge(){
        return age;
    }
}
class MyCompare implements Comparator{
    public int compare(Object obj1, Object obj2){
        student11 s1 = (student11)obj1;
        student11 s2 = (student11)obj2;
        int num = s1.getName().compareTo(s2.getName());
        if (num == 0){
            return new Integer(s1.getAge()).compareTo(new Integer(s2.getAge()));
//            if (s1.getAge() > s2.getAge()){
//                return 1;
//            }
//            if (s1.getAge() == s2.getAge()){
//                return 0;
//            }
//            return -1;
        }
        return num;
    }
}
class test11 {
    public static void main(String[] args) {
        TreeSet ts = new TreeSet(new MyCompare());
        ts.add(new student11("lili0", 10));
        ts.add(new student11("lili1", 15));
        ts.add(new student11("lili2", 14));
        ts.add(new student11("lili2", 17));
        ts.add(new student11("lili3", 14));
        Iterator it = ts.iterator();
        while (it.hasNext()) {
            student11 s = (student11) it.next();
            System.out.println(s.getName() + "....." + s.getAge());
        }
    }
}

/*
TreeSet排序第一种方式
 */
class student10 implements Comparable{//TreeSet排序第一种方式
    private String name;
    private int age;
    student10(String name, int age){
        this.name = name;
        this.age = age;
    }
    public int compareTo(Object obj){//必须实现该函数，不然TreeSet不知道怎末比较
//        return 1;//假如想让怎末进入怎末取出就直接返回1就可以了
        if (!(obj instanceof student10)){
            throw new RuntimeException("不是学生对象");
        }
        student10 s = (student10)obj;
        System.out.println(this.getName() + "....." + s.getName());
        if (this.age > s.age){
            return 10;//只要返回正数就好
        }
        if (this.age == s.age){//排序时当只要条件满足时，判断次要条件
            return this.name.compareTo(s.name);//String默认实现compareTo
        }
        return -1;//负数就好
    }
    public String getName(){
        return name;
    }
    public int getAge(){
        return age;
    }
}
public class JiHeSet {

    public static void main(String[] args){
        TreeSet ts = new TreeSet();
        ts.add(new student10("lili0",10));
        ts.add(new student10("lili1",11));
        ts.add(new student10("lili2",14));
        ts.add(new student10("lili3",14));
        Iterator it = ts.iterator();
        while (it.hasNext()){
            student10 s = (student10)it.next();
            System.out.println(s.getName() + "....." + s.getAge());
        }





//        ArrayList a1 = new ArrayList();

//        a1.add("111111");
//        a1.add("222222");
//        a1.add("444444");
//        a1.add("555555");
//        Iterator it = a1.iterator();
////        System.out.println(it.next());
//        while (it.hasNext()){//获取迭代器用于取出集合中元素
//            System.out.println(it.next());
//        }

    }
}
