package test;

import java.lang.ref.SoftReference;
import java.util.*;

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
 *      public static void printColl(ArrayList<? extends person10> a){//接受person10和其子类对象
 *       ? extends E:可以接受E类型或E的子类型 向上限定
 *       ? super   E:可以接受E类型或E的府类型 向下限定
 *
 *
 *      Map<K,V>:不包含重复键
 *      entrySet
 *      keySet:将map中所有的键存入到set中，因为set具备迭代器，所以可以迭代方式取出所有键，然后用get获取所有值
 *      Hashtable:底层哈希表结构  不可以使用null值和null键，线程同步
 *      HashMap:底层哈希表结构  可以使用null值和null键，该集合时不同步的
 *      TreeMap：底层二叉树结构。线程不同步，可以用于给map集合中的键进行排序，和set很像，其实set的底层用的就是map集合
 *      Collection<String> coll = map.values();
 *
 *      遍历map ：keySet，将map集合转成set集合，通过迭代器取出
 *                entrySet，
 *
 *
 *
 *
 *
 *
 */
/*
StaticImport 静态导入
import java.util.*
static import java.util.Arrays.*;//导入的是Arrays这个类中的所有静态成员
当方法重名时，需要指明具体所属的对象或类
 */

/*
可变参数
在使用时注意：可变参数一定要定义再参数列表最后面
 */
class setTest10 {
    public static void show(int... arr) {//可变参数
        System.out.println(arr.length);
    }

    public static void main(String[] args) {
        show(2,3,4,5);
    }
}

/*
Arrays
asList :将数组变成list集合   将数组变为list之后可以调用集合的方法来操作数组元素  contains
注意：将数组变成集合，不可以使用集合的增删方法，因为数组长度固定
如果数组中的元素都是对象，数组中的元素就直接转成集合张总的元素，
如果数组中的元素都是基本数据类型，那么会将该数组作为集合中的元素存在
int [] nums ={1,2,3};
List<int[]> li = Arrays.asList(nums);

Collection.toArray 将集合转为数组
ArrayList<String> li = new ArrayList<String>();
li.add("kkk");
li.add("kkk1");
li.add("kkk2");
String [] s = li.toArray(new String[0]);
指定类型的数字到底要定义多少？
当指定类型的数组的长度小于集合的size，那么该方法内部就会创建一个新的数组。长度为集合的size
当指定类型的数组的长度大于集合的size，就不会新创建数组，而是使用传进来的数组
为什么将集合转换为数组：为了限定对元素的操作，不需要进行增删了

 */


/*
collections  方法都是静态方法
sort 可以对list排序
max
binarrySearch
fill 将集合中所有元素替换成指定元素
replaceAll
reverse
reverseOrder 返回一个比较器
TreeSet<String> ts = new TreeSet(Collections.reverseOrder());
TreeSet<String> ts = new TreeSet(Collections.reverseOrder(new strLengthSort());//强行逆转自定义比较器

 */
class strLengthSort implements Comparator<String>{
    public int compare(String s1,String s2){
        if (s1.length() > s2.length()){
            return 1;
        }
        if (s1.length() < s2.length()){
            return -1;
        }
        return 0;
    }
}
class setTest9{
    public static void main(String[] args){
        List<String> list = new ArrayList<String>();
        list.add("aaaaaaaa");
        list.add("bbbbb");
        list.add("dddddd");
        list.add("jjccccccccc");
        list.add("ggggggggggggggggg");
//        int index = Collections.binarySearch(list,"bbbbb");
//        System.out.println(index);
        Collections.sort(list,new strLengthSort());
        int index1 = Collections.binarySearch(list,"bbbbb",new strLengthSort());
        System.out.println(index1);

//        String max = Collections.max(list);
//        String max1 = Collections.max(list,new strLengthSort());
//        System.out.println(max);
//        System.out.println(max1);

//        System.out.println(list);
////        Collections.sort(list);
//        Collections.sort(list,new strLengthSort());
//        System.out.println(list);




    }
    public static <T extends Comparable<? super T>> void sort(List<T> list){}

}


/*
map扩展：map集合被使用是因为具有映射关系
 */
class setTest8{
    public static void main(String[] args){
        HashMap<String,HashMap<String, String>> czbk = new HashMap<String,HashMap<String, String>>();
        HashMap<String, String> jiuye = new HashMap<String, String >();
        HashMap<String, String> yure = new HashMap<String, String >();
        czbk.put("jiuyeban",jiuye);
        czbk.put("yureban",yure);
        yure.put("01","zhangsan");
        yure.put("02","lisi");
        jiuye.put("01","zhaoliu");
        jiuye.put("02","wangwu");
        getStudentInfo1(czbk);
//        getStudentInfo2(jiuye);
//        getStudentInfo2(yure);
//        getStudentInfo3(jiuye.entrySet());


    }
    public static void getStudentInfo1(HashMap<String,HashMap<String, String>> room){
        Iterator<String> it = room.keySet().iterator();
        while (it.hasNext()){
            String roomName = it.next();
            System.out.println(roomName);
            getStudentInfo2(room.get(roomName));
        }
    }
    public static void getStudentInfo2(HashMap<String, String> room){
        Iterator<String> it = room.keySet().iterator();
        while (it.hasNext()){
            String id = it.next();
            String name = room.get(id);
            System.out.println(id + ":" + name);
        }
    }
    public static void getStudentInfo3(HashMap<String, String>  room){
        Iterator<Map.Entry<String, String>> it = room.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String, String> me = it.next();
            System.out.println(me.getKey() + ":" + me.getValue());
        }
    }
    public static void getStudentInfo4(Set<Map.Entry<String, String>> room){
        Iterator<Map.Entry<String, String>> it = room.iterator();
        while (it.hasNext()){
            Map.Entry<String, String> me = it.next();
            System.out.println(me.getKey() + ":" + me.getValue());
        }
    }
    }


/*
实例  treeSet  定义比较器改变原对象比较方式
 */
class cmpName implements Comparator<student21>{
    public int compare(student21 s1, student21 s2){
        int num = s1.getName().compareTo(s2.getName());
        if (num == 0){
            return new Integer(s1.getAge()).compareTo(new Integer(s2.getAge()));
        }
        return num;
    }
}
class setTest7{
    public static void main(String[] args){
        TreeMap<student21,String> map = new TreeMap<student21,String>();//student21中实现先按年龄再按名字排序
        map.put(new student21("lili5",20),"北京");
        map.put(new student21("lili2",21),"北京");
        map.put(new student21("lili3",22),"北京");
        map.put(new student21("lili4",21),"北京");

        //第一种遍历方法
        Set<student21> set1 = map.keySet();
        Iterator<student21> it1 = set1.iterator();
        while (it1.hasNext()){
            student21 s = (student21)it1.next();
            System.out.println("name:" + s.getName()+ "    age:" + s.getAge() + "---" + "value:" + map.get(s));
        }
        System.out.println("1111111111111111");
        printCol2(map.keySet(),map);

        System.out.println("2222222222222222");

        //第二张遍历方法
        Set<Map.Entry<student21,String>> set = map.entrySet();
        Iterator<Map.Entry<student21,String>> it = set.iterator();
        while (it.hasNext()){
            Map.Entry<student21,String> me = it.next();
            System.out.println("name:" + me.getKey().getName()+ "   age:" + me.getKey().getAge() + "---" + "value:" + me.getValue());
        }
        System.out.println("3333333333333333333");
        printColl(map.entrySet());
        System.out.println("4444444444444444");

        //改变student21比较规则先比较姓名，再比较年龄
        TreeMap<student21,String> map1 = new TreeMap<student21,String>(new cmpName());//student21中实现先按年龄再按名字排序
        map1.put(new student21("lili5",26),"北京");
        map1.put(new student21("lili5",20),"北京");
        map1.put(new student21("lili2",21),"北京");
        map1.put(new student21("lili3",22),"北京");
        map1.put(new student21("lili4",21),"北京");
        //第一种遍历方法
        Set<student21> set2 = map.keySet();
        Iterator<student21> it2 = set2.iterator();
        while (it2.hasNext()){
            student21 s = (student21)it2.next();
            System.out.println("name:" + s.getName()+ "    age:" + s.getAge() + "---" + "value:" + map1.get(s));
        }
        System.out.println("1111111111111111");
        printCol2(map1.keySet(),map1);

        System.out.println("2222222222222222");

        //第二张遍历方法
        Set<Map.Entry<student21,String>> set3 = map1.entrySet();
        Iterator<Map.Entry<student21,String>> it3 = set3.iterator();
        while (it3.hasNext()){
            Map.Entry<student21,String> me = it3.next();
            System.out.println("name:" + me.getKey().getName()+ "   age:" + me.getKey().getAge() + "---" + "value:" + me.getValue());
        }
        System.out.println("3333333333333333333");
        printColl(map1.entrySet());
        System.out.println("4444444444444444");

    }
    public static void printColl(Set<Map.Entry<student21,String>> a){//通配符，也可以不写，也可以定义为<T>,这样可以操作<T>元素，此时void前面也要加上<T>
        Iterator<Map.Entry<student21,String>> it = a.iterator();
        while (it.hasNext()){
            Map.Entry<student21,String> me = it.next();
            System.out.println("name:" + me.getKey().getName()+ "   age:" + me.getKey().getAge() + "---" + "value:" + me.getValue());
        }
    }
    public static void printCol2(Set<student21> a,Map<student21,String> map){//通配符，也可以不写，也可以定义为<T>,这样可以操作<T>元素，此时void前面也要加上<T>
        Iterator<student21> it = a.iterator();
        while (it.hasNext()){
            student21 s = (student21)it.next();
            System.out.println("name:" + s.getName()+ "   age:" + s.getAge() + "---" + "value:" + map.get(s));
        }
    }
}


/*
实例  HashMap
 */
class student21 implements Comparable<student21>{
    private String name;
    private int age;
    student21(String name, int age){
        this.name = name;
        this.age = age;
    }
    public int hashCode(){
        return name.hashCode() + age * 37;
    }
    public boolean equals(Object obj){//hashmap，hashset
        if (!(obj instanceof student21))
            throw new ClassCastException("类型不匹配");
        student21 s = (student21)obj;
        return this.name.equals(s.name) && this.age == s.age;
    }
    public int compareTo(student21 s){//具有比较性
        int num = new Integer(this.age).compareTo(new Integer(s.age));
        if (num == 0){
            return this.name.compareTo(s.name);
        }
        return num;
    }
    public String getName(){
//        System.out.println("getName" + name);
        return name;
    }
    public int getAge(){
//        System.out.println("getAge" + age);
        return age;
    }

}
class setTest6{
    public static void main(String[] args){
        HashMap<student21,String> map = new HashMap<student21,String>();
        map.put(new student21("lili1",20),"北京");
        map.put(new student21("lili2",21),"北京");
        map.put(new student21("lili3",22),"北京");
        map.put(new student21("lili4",21),"北京");
        //第一种遍历方法
        Set<student21> set1 = map.keySet();
        Iterator<student21> it1 = set1.iterator();
        while (it1.hasNext()){
            student21 s = (student21)it1.next();
            System.out.println("name:" + s.getName()+ "    age:" + s.getAge() + "---" + "value:" + map.get(s));
        }
        System.out.println("1111111111111111");
        printCol2(map.keySet(),map);
        System.out.println("2222222222222222");

        //第二张遍历方法
        Set<Map.Entry<student21,String>> set = map.entrySet();
        Iterator<Map.Entry<student21,String>> it = set.iterator();
        while (it.hasNext()){
            Map.Entry<student21,String> me = it.next();
            System.out.println("name:" + me.getKey().getName()+ "   age:" + me.getKey().getAge() + "---" + "value:" + me.getValue());
        }
        System.out.println("3333333333333333333");
        printColl(map.entrySet());
        System.out.println("4444444444444444");

    }
    public static void printColl(Set<Map.Entry<student21,String>> a){//通配符，也可以不写，也可以定义为<T>,这样可以操作<T>元素，此时void前面也要加上<T>
        Iterator<Map.Entry<student21,String>> it = a.iterator();
        while (it.hasNext()){
            Map.Entry<student21,String> me = it.next();
            System.out.println("name:" + me.getKey().getName()+ "   age:" + me.getKey().getAge() + "---" + "value:" + me.getValue());
        }
    }
    public static void printCol2(Set<student21> a,Map<student21,String> map){//通配符，也可以不写，也可以定义为<T>,这样可以操作<T>元素，此时void前面也要加上<T>
        Iterator<student21> it = a.iterator();
        while (it.hasNext()){
            student21 s = (student21)it.next();
            System.out.println("name:" + s.getName()+ "   age:" + s.getAge() + "---" + "value:" + map.get(s));
        }
    }
}


/*
遍历map      Set<Map.Entry>  entrySet:将map集合中的映射关系存入到set集合中，二这个关系的数据类型是：Map.Entry
将map集合转成set集合，通过迭代器取出
其实Entry也是一个接口，它是map接口中的一个内部接口
interface Map{
    interface{
        public abstract Object getKey();
        public abstract Object getValue();
    }
}
 */
class setTest5{
    public static void main(String[] args){
        Map<String,String> map = new HashMap<String,String>();
        map.put("lili1","01");
        map.put("lili2","02");
        map.put("lili3","03");
        map.put("lili4","04");
        Set<Map.Entry<String,String>> set = map.entrySet();
        Iterator<Map.Entry<String,String>> it = set.iterator();
        while (it.hasNext()){
            Map.Entry<String,String> me = it.next();
            System.out.println("key:" + me.getKey() + "---" + "value:" + me.getValue());
        }
        System.out.println("------------------");
        printColl(map.entrySet());

    }
    public static void printColl(Set<Map.Entry<String,String>> a){//通配符，也可以不写，也可以定义为<T>,这样可以操作<T>元素，此时void前面也要加上<T>
        Iterator<Map.Entry<String,String>> it = a.iterator();
        while (it.hasNext()){
            Map.Entry<String,String> me = it.next();
            System.out.println("key:" + me.getKey() + "---" + "value:" + me.getValue());
        }
    }
}




/*
遍历map    keySet
将map集合转成set集合，通过迭代器取出
 */
class setTest4{
    public static void main(String[] args){
        Map<String,String> map = new HashMap<String,String>();
        map.put("lili1","01");
        map.put("lili2","02");
        map.put("lili3","03");
        map.put("lili4","04");
        Set<String> set = map.keySet();
        Iterator<String> it = set.iterator();
        while (it.hasNext()){
            String key = it.next();
            System.out.println("key:" + key + "---" + "value:" + map.get(key));
        }
//        printColl(map.keySet(),map);

    }
    public static void printColl(Set<String> a, Map<String,String> map){//通配符，也可以不写，也可以定义为<T>,这样可以操作<T>元素，此时void前面也要加上<T>
        Iterator it = a.iterator();
        while (it.hasNext()){
            String key = (String)it.next();
            System.out.println("key:" + key + "---" + "value:" + map.get(key));
        }
    }
}



/*
特定类型比较器，假如定义为person，则可以传入
 */
class cmp implements Comparator<person10>{//这样，这个比较器可以传入person10的子类对象
    public int compare(person10 s1, person10 s2){
        return s1.getName().compareTo(s2.getName());
    }
}
class worker2 extends person10{
    worker2(String name, int age){
        super(name, age);
    }
}
class setTest3{
    public static void main(String[] args){
        TreeSet<student20> t1 = new TreeSet<student20>(new cmp());
        t1.add(new student20("lili1",20));
        t1.add(new student20("lili2",22));
        t1.add(new student20("lili3",23));
        t1.add(new student20("lili4",24));
        printColl(t1);

        TreeSet<worker2> t2 = new TreeSet<worker2>(new cmp());
        t2.add(new worker2("wwww1",20));
        t2.add(new worker2("wwww2",22));
        t2.add(new worker2("wwww3",23));
        t2.add(new worker2("wwww4",24));
        printColl(t2);
    }
    public static void printColl(TreeSet<? extends person10> a){//通配符，也可以不写，也可以定义为<T>,这样可以操作<T>元素，此时void前面也要加上<T>
        Iterator<? extends person10> it = a.iterator();
        while (it.hasNext()){
            System.out.println(it.next().getName());
        }
    }
}
/*
public static void printColl(ArrayList<? extends person10> a){//接受person10和其子类对象
? extends E:可以接受E类型或E的子类型 向上限定
? super   E:可以接受E类型或E的府类型 向下限定
 */
class person10{
    private String name;
    private int age;
//    person10(){}
    person10(String name, int age){
        this.name = name;
        this.age = age;
    }
    public String getName(){
//        System.out.println("getName" + name);
        return name;
    }
    public int getAge(){
//        System.out.println("getAge" + age);
        return age;
    }
}
class student20 extends person10{
    student20(String name, int age){
        super(name, age);
    }
}
class setTest1{
    public static void main(String[] args){
        ArrayList<person10> a1 = new ArrayList<person10>();
        a1.add(new person10("lili1",20));
        a1.add(new person10("lili2",22));
        a1.add(new person10("lili3",23));
        a1.add(new person10("lili4",24));
        printColl(a1);
        ArrayList<person10> a11 = new ArrayList<person10>();
        a11.add(new student20("sss1",20));
        a11.add(new student20("sss2",22));
        a11.add(new student20("sss3",23));
        a11.add(new student20("sss4",24));
        printColl(a11);

    }
    public static void printColl(ArrayList<? extends person10> a){//接受person10和其子类对象
//        System.out.println("llll");
        Iterator<? extends person10> it =a.iterator();
//        System.out.println("llll");
        while (it.hasNext()){
//            System.out.println("llll");
            System.out.println(it.next().getName());
        }
    }
}


/*
<?>通配符，当类型不确定时用通配符
 */

class demo15{
    public <T> void show(T t){
        System.out.println("show" + t);
    }
    public <Q> void print(Q q){
        System.out.println("print" + q);
    }
    public static <W> void method(W w){
        System.out.println("method" + w);
    }
    public static void printColl(ArrayList<?> a){//通配符，也可以不写，也可以定义为<T>,这样可以操作<T>元素，此时void前面也要加上<T>
        Iterator<?> it = a.iterator();
        while (it.hasNext()){
            System.out.println(it.next());
        }
    }

}

/*
泛型定义在接口
 */
interface Inter<T>{
    void show(T t);
}

class InterImpl1 implements Inter<String>{
    public void show(String t){
        System.out.println("show" + t);
    }

}

class InterImpl2<T> implements Inter<T>{
    public void show(T t){
        System.out.println("show" + t);
    }

}

/*
静态方法
静态方法不可以访问类上定义的泛型
如果静态方法操作的引用数据类型不确定可以将泛型定义在方法上
 */
class demo14{
    public <T> void show(T t){
        System.out.println("show" + t);
    }
    public <Q> void print(Q q){
        System.out.println("print" + q);
    }
    public static <W> void method(W w){
        System.out.println("method" + w);
    }
}

/*
泛型方法
泛型类定义的泛型，在整个类中有效如果方法被使用，那么泛型的对象明确要操作的具体类型后，所有的操作类型就已经固定了
为了让方法可以操作不同类型，而且类型还不确定，那么可以将泛型定义在方法上
 */
class demo13<T>{//也可以
    public  void show(T t){
        System.out.println("show" + t);
    }
    public <Q> void print(Q q){
        System.out.println("print" + q);
    }
}
//demo13<String> d = new demo13<String>();
//d.show("sff");
//d.print(134)

class demo12{
    public <T> void show(T t){
        System.out.println("show" + t);
    }
    public <Q> void print(Q q){
        System.out.println("print" + q);
    }
}

class demo11<T>{
    public void show(T t){
        System.out.println("show" + t);
    }
    public void print(T t){
        System.out.println("print" + t);
    }
}
class setTest{
    public static void main(String[] args){
        demo11 s = new demo11();
        s.show("dff");
        s.print(123);
//        demo11<String> s = new demo11<String>();
//        s.show("fonfod");
    }
}

class worker{
}
//当类中要操作的引用数据类型不确定时，定义泛型来完成扩展
class Tools<E>{//
    private E e;
    public void setObject(E e){
        this.e = e;
    }
    public Object getObject(){
        return e;
    }
}
//Tools<worker> t = new Tools<worker>();


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
        TreeSet<student11> ts = new TreeSet(new MyCompare());
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
