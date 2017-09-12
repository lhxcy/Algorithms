package test;

import java.io.*;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 该电脑默认使用 UTF-8编码表
 * Created by XCY on 2017/9/4.
 *    字节流   字符流
 *    JB2312   JBK  汉字表
 *    字节流基类：InputStream，  OutputStream  BufferedInputStream  BufferedOutputStream
 *    字符流基类：Reader，  Writer     BufferedReader  BufferedWriter
 *

    流操作基本规律：
    最痛苦的是流对象有很多，不知道该用哪个
    1：明确源和目的
        源：输入流  InputStream  Reader
        目的：输出流  OutputStream  Writer
    2：操作数据是否是纯文本
        是：字符流
        不是：字节流

    3：当体系明确后，再明确要使用哪个具体的对象，通过设备来区分
        源设备：内存 硬盘 键盘
        目的设备：内存 硬盘 控制台


    需求1：将一个文件数据存入另一个文件中
           源：因为是源，所以使用读取流    InputStream  Reader
               是不是才做文件：是，选择Reader
               接下来确定要使用该体系中的哪个对象
               明确设备：硬盘上一个文件，Reader体系中可以操作文件的对象FileReader
               FileReader fr = new FileReader("b.txt");
               是否需要提高效率：是。加入Reader体系中的BufferedFileReader
               BufferedReader bufr = new BufferedReader(fr);
           目的：OutputStream  Writer
                 纯文本：Writer
                 硬盘上文件：选择FileWriter
                 是否需要提高效率：是。加入Writer体系中的BufferedFileWriter

    需求2：将键盘录入的数据存储到另一个文件中
        源：InputStream Reader
        是不是纯文本？是  Reader
        设备：键盘  对象应该是System.in
        不是选择Reader吗？System.in对用的不是字节流吗？
        为了操作键盘的文本数据方便，转成字符流按照字符串操作最方便
        所以既然明确了使用Reader，那么就将System.in转换成Reader
        用了Reader中的转换流  InputStreamReader
        InputStreamReader isr = new InputStreamReader(System.in);
        是否需要提高效率？是用BufferedReader
        BufferedReader bufr = new BufferedReader(isr);



        扩展：想要把录入的数据按照指定的编码表(utf-8)，将数据存入到文件中

        目的：OutputStream Writer
        是否是纯文本  是 Writer
        设备：硬盘   一个文件  使用FileWriter
        但是FieWriter使用的是默认编码表  GBK

        但是存储时，需要加入指定编码表(utf-8)，而指定的编码表只有转换流可以指定，
        所以要使用的对象时OutputStreadWriter
        而该转换流对象要接收一个字节输出流，而且还可以操作文件的字节输出流  FileOutputStream

        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("d.txt"), "UTF-8");
        需要高效吗？需要
        BufferedWriter bufw = new BufferedWriter(osw)
        所以记住转换流什么时候使用：字符和字节直接按的桥梁，通常涉及到字符编码转换时，需要用到转换流
 */
/*
字符编码
通过子类转换流完成：InputStreamReader   OutputStreamWriter
在两个对象进行构造时可以加入字符集
 */



/*
操作基本数据类型  DataInputStream和DataOutputStream
操作字节数组 ByteArrayInputStream  ByteArrayOutputStream   关不关对象都一样，不涉及底层资源调用
操作字符数组 CharArrayReader  CharArrayWriter
操作字符串 StringReader StringWriter

ByteArrayInputStream  ：在构造的时候需要接收数据源，而且数据源是一个字节数组
ByteArrayOutputStream ：在构造的时候，不用定义数据目的，因为该对象中已经内部封装了可变长度的字节数组，这就是数据目的地
因为这两个流对象都操作的是数组，不是系统资源，所以不用close关闭
用流的读写方法操作数组
 */
class ByteArrayInputStreamDemo{
    public static void main(String[] args){
        //数据源
        ByteArrayInputStream bais = new ByteArrayInputStream("ABCDEFD".getBytes());

        //数据目的
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int by = 0;
        while ((by = bais.read()) != -1){
            baos.write((char)by);
        }
        myPrint(baos.size());
        try {
            baos.writeTo(System.out);
        }catch (IOException e){
            throw new RuntimeException("打印出错");
        }
    }
    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}


/*
DataInputStream和DataOutputStream
可以用于操作基本数据类型的数据的流对象
 */
class dataStreamDemo{
    public static void main(String[] args) throws IOException{
//        writeData();
//        readData();
//        writeUTFDemo();
        readUTFDemo();


//        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("utf_data.txt"),"utf-8");//6字节 utf-8
//        osw.write("你好");
//        osw.close();
    }
    public static void writeData() throws IOException{
        DataOutputStream dos = new DataOutputStream(new FileOutputStream("data.txt"));
        dos.writeInt(98);//4字节
        dos.writeBoolean(true);//1字节
        dos.writeDouble(3583.242);//8字节
        dos.close();
    }
    public static void writeUTFDemo() throws IOException{//8字节  utf-8修改版
        DataOutputStream dos = new DataOutputStream(new FileOutputStream("utfdata.txt"));
        dos.writeUTF("你好");
        dos.close();
    }
    public static void readUTFDemo() throws IOException{
        DataInputStream dis = new DataInputStream(new FileInputStream("utfdata.txt"));
        String s = dis.readUTF();
        myPrint(s);
        dis.close();
    }
    public static void readData() throws IOException{
        DataInputStream dis = new DataInputStream(new FileInputStream("data.txt"));
        int num = dis.readInt();
        boolean b = dis.readBoolean();
        double d = dis.readDouble();
        myPrint("num: " + num + "  bool: " + b + "  double: " + d );
        dis.close();

    }
    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}



/*
RandomAccseeFile ：支持对随机访问文件的读取和写入，该类不是IO体系的子类，直接继承Object
但是它是IO包中的成员，因为它具备读和写功能，内部封装了一个数组，可以通过指针对数组进行操作
可以通过getFilePointer获取指针位置，同时可以通过seek方法改变指针位置
其实完成读写的原理就是内部封装了字节输入流和输出流
通过构造函数可以看出，该类只能操作文件，而且操作文件还有模式

如果模式为 r 的话，不会去创建文件，会去读一个已存在文件，如果该文件不存在，则会出现异常
如果模式为 rw ，操作的文件不存在时，会自动创建，如果存在，则不会覆盖
RandomAccseeFile对象的构造函数要操作的文件不存在时，会自动创建，如果存在，则不会覆盖
 */
class RandomAccseeFileDemo{
    public static void main(String[] args) throws IOException{
//        writeFile();
        writeFile_2();
//        myPrint("李四".getBytes().length);//返回李四的字节数
//        readFile();
    }
    public static void writeFile() throws IOException{
        //方法一。
//        File f = new File("ran.txt");
//        if (!f.exists())
//            f.createNewFile();
//        RandomAccessFile raf = new RandomAccessFile(f, "rw");

        //方法二
        RandomAccessFile raf = new RandomAccessFile("ran.txt", "rw");
//        raf.write("lisi".getBytes());
        raf.write("李四".getBytes());
//        raf.write(97);//写入一个字节
        raf.writeInt(97);//一次写入四个字节
        raf.write("王五".getBytes());
        raf.writeInt(99);//一次写入四个字节
        raf.close();
    }
    public static void readFile() throws IOException{
        RandomAccessFile raf = new RandomAccessFile("ran.txt", "r");
//        raf.seek(10);//设置指针位置
        raf.skipBytes(10);//跳过指定的字节数,只能往后跳
        byte[] buf = new byte[6];//utf-8中一个中文3个字节
        raf.read(buf);
        String name = new String(buf);
        int age = raf.readInt();//假如李四写中文，byte定义还是4的时候，导致age会读取有误，因为在utf-8中一个汉子3分字节
        myPrint(name);
        myPrint(age);
        raf.close();
    }
    public static void writeFile_2() throws IOException{
        RandomAccessFile raf = new RandomAccessFile("ran.txt", "rw");
        raf.seek(0);
        raf.write("周期".getBytes());
        raf.writeInt(103);
        raf.close();
    }
    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}




/*
管道流：输入输出中科院直接进行连接，通过结合线程使用
 */
class Read implements Runnable{
    private PipedInputStream in;
    Read(PipedInputStream in){
        this.in = in;
    }
    public void run(){
        try {
            byte[] buf = new byte[1024];
            myPrint("读取前，没有数据阻塞");
            int len = in.read(buf);
            myPrint("读到数据，阻塞结束");
            String s = new String(buf, 0, len);
            myPrint(s);
            in.close();
        }catch (IOException e){
            throw new RuntimeException("管道读取流失败");
        }
    }
    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}
class Write implements Runnable{
    private PipedOutputStream out;
    Write(PipedOutputStream out){
        this.out = out;
    }
    public void run(){
        try {
            myPrint("开始写入数据，等待6秒后");
            Thread.sleep(6000);
            out.write("Pip is coming".getBytes());
            out.close();
        }catch (Exception e){
            throw new RuntimeException("管道输出流失败");
        }
    }
    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}
class PipStreamDemo{
    public static void main(String[] args) throws IOException{
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream();
        in.connect(out);
        Read r = new Read(in);
        Write w = new Write(out);
        new Thread(r).start();
        new Thread(w).start();

    }
}




/*
对象的序列化
对象存在堆内存中
静态不能被序列化
 */
class Person1 implements Serializable{//没有方法的接口通常成为标记接口，只有实现这个接口对象才能序列化
    private static final long serialVersionUID = 42L;
    //所谓的序列化就是使类产生一个序列化ID，加入不加上这句话，只要类中的数据改变一点，一起存储的对象就不能使用了，
    //加上之后，可以照常使用，这个ID可以不为42L
    //如果对于非静态成员也不想被序列化，可以加上transient关键字
    String name;
    transient int age;//保证其值在堆内存中存在，不在文件中存在
    Person1(String name, int age){
        this.name = name;
        this.age = age;
    }
    public String toString(){
        return name + " : " + age;
    }
}
class ObjectStreamDemo{
    public static void main(String[] args) throws IOException, ClassNotFoundException{
//        writeObj();
        readObj();
    }
    public static void readObj()throws IOException, ClassNotFoundException{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Obj.txt"));
        Person1 p = (Person1)ois.readObject();
        myPrint(p);
        ois.close();
    }
    public static void writeObj() throws IOException{
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Obj.txt"));
        oos.writeObject(new Person1("lisi",38));
        oos.close();
    }
    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}



/*
分割文件
    可以分割合并图像音频，视频等
 */
class SplitFile{
    public static void main(String[] args) throws IOException{
//        splitFIle();
        merge();
    }
    public static void merge() throws IOException{
        ArrayList<FileInputStream> al = new ArrayList<FileInputStream>();
        for (int i = 1; i <= 35; i++){
            al.add(new FileInputStream("SplitAndMerge\\" + i + ".part"));
        }
        final Iterator<FileInputStream> it = al.iterator();//因为匿名内部类访问了it
        Enumeration<FileInputStream> en = new Enumeration<FileInputStream>() {
            @Override
            public boolean hasMoreElements() {
                return it.hasNext();
            }

            @Override
            public FileInputStream nextElement() {
                return it.next();
            }
        };
        SequenceInputStream sis = new SequenceInputStream(en);
        FileOutputStream fos = new FileOutputStream("SplitAndMerge\\1.jpg");
        byte[] by = new byte[1024];
        int len = 0;
        while ((len = sis.read(by)) != -1){
            fos.write(by, 0, len);
        }
        fos.close();
        sis.close();
    }
    public static void splitFIle() throws IOException{
        FileInputStream fis = new FileInputStream("1.jpg");
        FileOutputStream fos = null;
        byte[] by = new byte[1024];
        int len = 0;
        int count = 1;
        while ((len = fis.read(by)) != -1){
            //数据太大时，可以定义一个计数器，当计数器达到10的倍数时，再创建新的文件，相当于一个文件存10k数据
            fos = new FileOutputStream("SplitAndMerge\\" + (count++) +".part");
            fos.write(by, 0 ,len);
            fos.close();
        }
        if (fos != null){
            fos.close();
        }
        fis.close();
    }
}





/*
将多个流合为一个流
 */
class SequenceDemo{
    public static void main(String[] args) throws IOException{
        Vector<FileInputStream> v = new Vector<FileInputStream>();
        v.add(new FileInputStream("1.txt"));
        v.add(new FileInputStream("2.txt"));
        v.add(new FileInputStream("3.txt"));
        Enumeration<FileInputStream> en = v.elements();
        SequenceInputStream sis = new SequenceInputStream(en);
        FileOutputStream fos = new FileOutputStream("4.txt");
        byte[] by = new byte[1024];
        int len = 0;
        while ((len = sis.read(by)) != -1){
            fos.write(by,0,len);
        }
        sis.close();
        fos.close();
    }
}



/*
打印流：该流提供了打印方法，可以将各种数据类型的数据都原样打印
字节打印流
PrintStream
构造函数可以接收参数类型：
    1：file对象 File
    2：字符串路径 String
    3：字节输出流 OutputStream

字符打印流
PrintWriter
构造函数可以接收参数类型：
    1：file对象 File
    2：字符串路径 String
    3：字节输出流 OutputStream
    4：字符输出流 Writer
 */
class PrintStreamDemo{
    public static void main(String[] args) throws IOException{
        BufferedReader bufr = new BufferedReader(new InputStreamReader(System.in));
//        PrintWriter out = new PrintWriter(System.out,true);
//        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("a.txt")),true);
        PrintWriter out = new PrintWriter(new FileWriter("a.txt"),true);
        String line = null;
        while ((line = bufr.readLine()) !=null){
            if ("over".equals(line))
                break;
            out.println(line.toUpperCase());
//            out.write(line.toUpperCase());
            out.flush();
        }
        out.close();
        bufr.close();
    }
}





/*
用于记录应用程序运行次数
如果使用次数已到，那么给出注册提示
很容易想到：计数器，课时该计数器定义在程序中，随着程序的运行而在内存中存在，并运行而自增，
可是随着该应用程序的退出，该计数器也在内存中消失了，下一次再启动该程序，又重新从0开始
要求程序即使结束，该计数器值也存在
下次程序启动时会先加载该计数器的值并加1后重新存储起来
所以要建立一个配置文件，用于记录软件使用次数
该配置文件使用键值对的形式
键值对数据时map集合，数据是以文件存储，使用io技术
那么map + io--->Properties
配置文件可以实现应用程序数据共享
 */
class RunCount{
    public static void main(String[] args) throws IOException{
        Properties prop = new Properties();

        File file = new File("count.ini");
        if (!file.exists()){
            file.createNewFile();
        }
        int count = 0;
        FileInputStream fis = new FileInputStream(file);
        prop.load(fis);
        String value = prop.getProperty("time");
        if (value != null){
            count = Integer.parseInt(value);
            if (count > 5){
                myPrint("免费使用次数已到");
                return;
            }
        }
        count++;
        prop.setProperty("time", count + "");
        FileOutputStream fos = new FileOutputStream(file);
        prop.store(fos,"change");
        fis.close();
        fos.close();
    }
    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}




/*
演示：如何将流中的数据存储到集合中
想要将info.txt中键值数据存储到集合中进行操作
    1：用一个流和info.txt文件关联
    2：读取一行的数据，将该行数据用“=”进行分割
    3：等号左变作为键，右边作为值，存入到Properties集合中

那么在加载数据时，需要数据有固定格式：键=值
 */
class PropertiesDemo1{
    public static void main(String[] args) throws IOException{
        method();
        loadDeno();
    }
    public static void loadDeno() throws IOException{
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("info.txt");

        prop.load(fis);
        prop.setProperty("wangwu", "39");//改变的是内存中的结果
        FileOutputStream fos = new FileOutputStream("info.txt");
        prop.store(fos,"change wangwu");
        myPrint(prop);
        prop.list(System.out);
    }
    public static void method(){
        BufferedReader bufr = null;
        try {
//            bufr = new BufferedReader(new FileReader("info.txt"));
            bufr =  new BufferedReader(new InputStreamReader(new FileInputStream("info.txt"),"GBK"));
            String line = null;
            Properties prop = new Properties();
            while ((line = bufr.readLine()) != null){
                String[] arr = line.split("=");
//                myPrint(arr[0] + "::" + arr[1]);
                prop.setProperty(arr[0], arr[1]);
//                myPrint(line);
            }
            myPrint(prop);
        }catch (Exception e){
            throw new RuntimeException("创建文件连接流失败");
        }finally {
            try {
                if (bufr != null)
                    bufr.close();
            }catch (Exception e){
                throw new RuntimeException("文件关闭失败");
            }
        }
    }
    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}

/*
Properties是hashtable的子类
也就是说它具备map集合的特点，而且它里面存储的键值对都是字符串
是集合和IO技术相结合的集合容器
该对象特点：可以用于键值对形式的配置文件
 */
class PropertiesDemo{
    public static void main(String[] args){
        setAndGet();
    }
    public static void setAndGet(){//设置和获取元素
        Properties prop = new Properties();
        prop.setProperty("zhangsan", "30");
        prop.setProperty("lisi", "40");

        prop.setProperty("lisi","89");

//        myPrint(prop);

//        String value = prop.getProperty("zhangsan");
//        myPrint(value);

        Set<String> names = prop.stringPropertyNames();
        for (String name : names){
            myPrint(name + "::" + prop.getProperty(name));
        }
    }
    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}


/*
练习：将一个指定目录下的Java文件的绝对路径，存储到一个文本文件中
建立一个Java文件列表文件
思路：
    1：对指定目录进行递归
    2：获取递归过程中所有Java文件路径
    3：将这些路径存储到集合中
    4：将集合中的数据写入到一个文件中
 */
class JavaFileList{
    public static void main(String[] args){
        File dir = new File("E:\\chapter1");
        List<File> list = new ArrayList<File>();
        fileToList(dir, list);
        myPrint(list.size());
        File file = new File(dir, "JavaName.txt");
        writeToFile(list, file.toString());
    }
    public static void fileToList(File dir, List<File> list){
        File[] files = dir.listFiles();
        for (File file : files){
            if (file.isDirectory())
                fileToList(file, list);
            else {
                if (file.getName().endsWith(".java"))
                    list.add(file);
            }
        }
    }
    public static void writeToFile(List<File> list, String javaListFile){
        BufferedWriter bufw = null;
        try {
            bufw = new BufferedWriter(new FileWriter(javaListFile));
            for (File file : list){
                String path = file.getAbsolutePath();
                bufw.write(path);
                bufw.newLine();
                bufw.flush();
            }
        }catch (Exception e){
            throw new RuntimeException("文件写入失败");
        }finally {
            try {
                if (bufw != null)
                    bufw.close();
            }catch (Exception e){
                throw new RuntimeException("文件关闭失败");
            }
        }
    }
    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}




/*
删除一个带内容的目录
 */
class RemoveDir{
    public static void main(String[] args){
        File dir = new File("E:\\chapter1\\abc");
        removeDir(dir);
    }
    public static void removeDir(File dir){
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++){
            if (!files[i].isHidden() && files[i].isDirectory())
                removeDir(files[i]);
            else myPrint(files[i].toString() + "::" + files[i].delete());
        }
        myPrint(dir.toString() + "::" + dir.delete());
    }
    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}



/*
列出目录下的所有内容  带层次
 */
class FileDemo4{
    public static void main(String[] args){
        File dir = new File("E:\\chapter1");
        showDir(dir, 0);
    }
    public static String getLevel(int level){
        StringBuilder sb = new StringBuilder();
        sb.append("|--");
        for (int i = 0; i < level; i++){
            sb.insert(0, "|  ");
        }
        return sb.toString();
    }
    public static void showDir(File dir,int lever){
        lever++;
        myPrint(getLevel(lever) + dir);
        File[] files = dir.listFiles();
        for (int x = 0; x < files.length; x++){
            if (files[x].isDirectory()){
                showDir(files[x], lever);
            }else
                myPrint(files[x]);
        }
    }
    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}

/*
列出指定目录下的文件或者文件夹，包含子目录的内容，也就是列出指定目录下所有内容
因为目录中含有目录，只要使用同一个列出目录功能的函数完成即可
 */
class FileDemo3{
    public static void main(String[] args){
        File dir = new File("E:\\chapter1");
        showDir(dir);
    }
    public static void showDir(File dir){
        myPrint(dir);
        File[] files = dir.listFiles();
        for (int x = 0; x < files.length; x++){
            if (files[x].isDirectory()){
                showDir(files[x]);
            }else
                myPrint(files[x]);
        }
    }
    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}


/*
文件筛选
 */

class FileDemo2{
    public static void main(String[] args) throws IOException{
//        Method();
//        Method1();

        File dir = new File("E:\\chapter1");
//        String[] arr = dir.list();

        String[] arr = dir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
//                myPrint("dir:" + dir + "...name..." + name);
                return name.endsWith(".txt");//返回以 .txt 结尾的文件
            }
        });
        myPrint(arr.length);
        for (String name : arr){
            myPrint(name);
        }
    }
    public static void Method(){
        File[] files = File.listRoots();
        for (File f : files){
            myPrint(f);
        }
    }
    public static void Method1() throws IOException{
        File files =new  File("e:\\");//调用list方法的file必须是封装了一个目录。该目录必须存在
        String[] names = files.list();//返回e盘一级目录东西
        for (String name : names){
            myPrint(name);
        }
    }
    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}

/*
File类的常见方法：
    1：创建
        mkdir() 创建目录 创建文件夹  只能创建一级目录
        mkdirs  创建多级目录
        boolean CreatNewFile():在指定位置创建文件，如果文件存在，则不创建，返回false
                                和输出流不一样，输出流对象一定建立创建文件，而且文件已经存在会覆盖
    2：删除
        boolean delete
        void deleteOnExit  //在程序退出时删除指定文件
    3：判断
        canExecute 是否能执行
        boolean exists()  判断文件是否存在
        isHidden   是否隐藏
        isAbsolute() 是否是绝对路径
    4：获取信息
        getName()
        getPath()
        getParent()
        getAbsolutePath()
        lastModified()
        length()
 */
class FileDemo1{
    public static void main(String[] args) throws IOException{
//        Method();
//        Method1();
//        Method3();
//        Method4();
//        Method5();
//        MethodTest();
        Method6();
    }
    public static void MethodTest() throws IOException{
        //在判断文件对象是否是文件或目录时，必须要先判断该文件对象封装的内容是否存在
        //通过exists判断
        File f = new File("file.txt");
//        f.createNewFile();
        myPrint("exists:" + f.exists());
        myPrint("dir:" + f.isDirectory());
        myPrint("file:" + f.isFile());
    }
    public static void Method() throws IOException{
        File f1 = new File("a.txt");
//        f1.createNewFile();
        myPrint("creat:" + f1.createNewFile());
    }
    public static void Method1() throws IOException {
        File f1 = new File("abc");

        myPrint("delete:" + f1.delete());
    }
    public static void Method2() throws IOException {
        File f1 = new File("a.txt");
        f1.deleteOnExit();//在程序退出时删除指定文件
    }
    public static void Method3() throws IOException {
        File f1 = new File("a.txt");
        myPrint("execute: " + f1.canExecute());
    }
    public static void Method4() throws IOException {
        File f1 = new File("a.txt");
        myPrint("exists: " + f1.exists());
    }
    public static void Method5() throws IOException {
        File f1 = new File("a.txt");
        File dir = new File("abc");
        myPrint("mkdir:" + dir.mkdir());//dir.mkdir()创建目录 只能创建一级目录
    }
    public static void Method6() throws IOException {
//        File f1 = new File("a.txt");
        File f1 = new File("e:\\a.txt");
//        f1.createNewFile();
        myPrint(f1.getPath());
        myPrint(f1.getAbsolutePath());
        myPrint(f1.getParent());//该方法返回的是绝对路径中的父目录，如果获取的是相对目录，返回null
                                //如果相对路径中有上一层目录，那么该目录就是返回结果
    }
    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}



/*
流只能操作数据
File:用来将文件或文件夹封装成对象
 */
class FileDemo{
    public static void main(String[] args){
        consMethod();
    }
    public static void consMethod(){
        //将a.txt 封装成File最小。可以将已有和未出现的文件或文件夹封装成对象
        File f1 = new File("a.txt");

        File f2 = new File("e:\\abc","b.txt");

        File d = new File("e:\\abc");
        File f3 = new File(d,"b.txt");
        File f4 = new File("e:" + File.separator + "abc" + File.separator + "a.txt");//实现跨平台
        myPrint("f1: " + f1);
        myPrint("f2: " + f2);
        myPrint("f3: " + f3);
        myPrint("f4: " + f4);
    }
    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}



/*
获取系统信息
 */
class SystemInfo{
    public static void main(String[] args){
        Properties prop = System.getProperties();
//        System.out.println(prop);
        System.out.println(prop.getClass().getName());//对象所属类的类名
        prop.list(System.out);//可以改目的
    }
}


/*
log4j专门建立Java日志信息
 */
class ExceptionInfo{
    public static void main(String[] args) throws IOException{
        try{
            int[] arr = new int[2];
            System.out.println(arr[3]);
        }catch (Exception e){
            try {
//                e.printStackTrace();//默认是控制台
//                e.printStackTrace(new PrintStream("a.txt"));
                Date d = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");//格式化时间
                String s = sdf.format(d);
                PrintStream ps = new PrintStream("exception.log");
//                ps.println(d.toString());
                ps.println(s);
                System.setOut(ps);
            }catch (Exception e1){
                throw new RuntimeException("日志文件创建失败");
            }
            e.printStackTrace(System.out);
        }
    }
}

/*
改变标准输入输出设备
 */
class WriteOut3{
    public static void main(String[] args) throws IOException{
//        System.setIn(new FileInputStream("jianpan.txt"));//更改标准输入
//        System.setOut(new PrintStream("zz.txt"));//更改标准输出
      /*字节转字符*/
        //使用GBK编码读表
        BufferedReader bufr = new BufferedReader(new InputStreamReader(new FileInputStream("jianpan.txt"),"GBK"));
        /*字符转换为字节流 */
        //输出写法
        BufferedWriter bufw = new BufferedWriter(new OutputStreamWriter(System.out));
        String line = null;
        while ((line = bufr.readLine()) != null){
//            if ("over".equals(line))
//                break;
            bufw.write(line.toLowerCase());
            bufw.newLine();//添加换行
            bufw.flush();
        }
        bufr.close();
    }
}



/*
需求：讲一个文件的内容打印在控制台上
 */
class WriteOut2{
    public static void main(String[] args) throws IOException{
      /*字节转字符*/
      //使用GBK编码读表
        BufferedReader bufr = new BufferedReader(new InputStreamReader(new FileInputStream("jianpan.txt"),"GBK"));
        /*字符转换为字节流 */
        //输出写法
        BufferedWriter bufw = new BufferedWriter(new OutputStreamWriter(System.out));
        String line = null;
        while ((line = bufr.readLine()) != null){
//            if ("over".equals(line))
//                break;
            bufw.write(line.toLowerCase());
            bufw.newLine();//添加换行
            bufw.flush();
        }
        bufr.close();
    }
}



/*
需求：
源：键盘录入
目的：文件
 */
class WriteOut1{
    public static void main(String[] args) throws IOException{
        /*字节转字符*/
        BufferedReader bufr = new BufferedReader(new InputStreamReader(System.in));
        /*字符转换为字节流 */
        //输出写法
        BufferedWriter bufw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("jianpan.txt"),"GBK"));
        String line = null;
        while ((line = bufr.readLine()) != null){
            if ("over".equals(line))
                break;
            bufw.write(line.toUpperCase());
            bufw.newLine();//添加换行
            bufw.flush();
        }
        bufr.close();
        bufw.close();
    }
}



/*
字符流通向字节流的桥梁  OutputStreamWriter
源：键盘录入
目的：控制台
 */
class WriteOut{
    public static void main(String[] args) throws IOException{
        /*
        字节转字符
         */
//        InputStream in = System.in;//获取键盘录入对象
//        //将字节流对象转换成字符流对象，使用转换流InputStreamReader
//        InputStreamReader isr = new InputStreamReader(in);
//        //为了提高效率，将字符串进行缓冲区技术高效操作，使用BufferedReader
//        BufferedReader bufr = new BufferedReader(isr);
        //键盘录入最常见写法
        BufferedReader bufr = new BufferedReader(new InputStreamReader(System.in));


        /*
        字符转换为字节流
         */
//        OutputStream out = System.out;
//        OutputStreamWriter osw = new OutputStreamWriter(out);
//        BufferedWriter bufw = new BufferedWriter(osw);

        //输出写法
        BufferedWriter bufw = new BufferedWriter(new OutputStreamWriter(System.out));

        String line = null;
        while ((line = bufr.readLine()) != null){
            if ("over".equals(line))
                break;
            bufw.write(line.toUpperCase());
            bufw.newLine();//添加换行
            bufw.flush();
        }
        bufr.close();
    }
}





/*
InputStreamReader  字节流通向字符流的桥梁
 */

class ReadIn1{
    public static void main(String[] args) throws IOException{
        InputStream in = System.in;//获取键盘录入对象
        //将字节流对象转换成字符流对象，使用转换流InputStreamReader
        InputStreamReader isr = new InputStreamReader(in);
        //为了提高效率，将字符串进行缓冲区技术高效操作，使用BufferedReader
        BufferedReader bufr = new BufferedReader(isr);
        String line = null;
        while ((line = bufr.readLine()) != null){
            if ("over".equals(line))
                break;
            System.out.println(line.toUpperCase());
        }
        bufr.close();
    }
}


/*
读取键盘录入
System.out:对应标准输出设备  控制台
System.in ：标准输入设备

需求：通过键盘录入数据，当录入1行数据后，就将该行数据进行打印，当录入over时，停止录入
通过刚才的键盘录入数据并打印其大写，发现其实就是读一行的原理，也就是readdline方法
能不能直接使用readLine方法来完成键盘录入的一行数据的读取呢：
readLine方法是字符流的BufferedRead方法，而键盘录入的read方法是字节流的InputStream的方法，
那么能不能将字节流转换成字符流再使用字符流缓冲区的readLine方法呢？  InputStreamReader
 */
class ReadIn{
    public static void main(String[] args) throws IOException{
//        InputStream in = System.in;
//        int by = in.read();
//        System.out.println(by);
        InputStream in = System.in;
//        int ch = 0;
//        while ((ch = in.read()) != -1){
//            System.out.println((char)ch);
//        }
        StringBuilder sb = new StringBuilder();
        while (true){
            int ch = in.read();
            if (ch == '\r')
                continue;
            if (ch == '\n'){
                String s = sb.toString();
                if ("over".equals(s)){
                    break;
                }
                System.out.println(s.toUpperCase());
                sb.delete(0,sb.length());//清空缓冲区
            }else {
                sb.append((char)ch);
            }
        }
        in.close();
    }
}





/*
自定义BufferInputStream

//当数据中出现 1111-1111 时，会被识别为-1，与返回标志一样，所以会出现问题
//1111-1111 --》提升了一个int类型，还是-1，是-1的原因是因为在8个1前面补的是1导致的
//那么只要在前面补0，既可以保留原数据不变，又可以避免-1的出现
//只需要与255（1111-1111）相与就好
 */
class MyBufferedInputStream{
    private InputStream in;
    private byte[] buf = new byte[1024];
    private int pos = 0, count = 0;
    MyBufferedInputStream(InputStream in){
        this.in = in;
    }
    public int myRead() throws IOException{//一次都一个字节，从缓冲区（字节数组）获取，
        if (count == 0){
            count = in.read(buf);
            if(count < 0) {
                return -1;
            }
            pos = 0;
            byte b = buf[pos];
            count--;
            pos++;
            return b&255;
            //当数据中出现 1111-1111 时，会被识别为-1，与返回标志一样，所以会出现问题
            //1111-1111 --》提升了一个int类型，还是-1，是-1的原因是因为在8个1前面补的是1导致的
            //那么只要在前面补0，既可以保留原数据不变，又可以避免-1的出现
            //只需要与255（1111-1111）相与就好
        }else if (count > 0){
            byte b = buf[pos];
            count--;
            pos++;
            return b&255;
        }
        return -1;
    }
    public void myClose() throws IOException{
        in.close();
    }
}




/*
演示mp3文件的复制，通过缓冲区
BufferInputStream，  BufferOutputStream
 */
class CopyMp3{
    public static void main(String[] args) throws IOException{
//        copyMp3_1();
        long start = System.currentTimeMillis();
//        copyMp3_2();
//        System.out.println("11111t");
        copyMp3_3();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    public static void copyMp3_1(){
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream("1.mp3");
            fos = new FileOutputStream("11.mp3");
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = fis.read(buf)) != -1){
                fos.write(buf, 0, len);
            }
        }catch (IOException e){
            throw new RuntimeException("复制文件失败");
        }finally {
            try{
                if (fis !=null)
                    fis.close();
            }catch (IOException e){
//                System.out.println("读异常");
                throw new RuntimeException("读取关闭失败");
            }
            try{
                if (fos !=null)
                    fos.close();
            }catch (IOException e){
//                System.out.println("写异常");
                throw new RuntimeException("写入关闭失败");
            }
        }
    }
    public static void copyMp3_2() throws IOException{//通过字节流的缓冲区完成复制
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream("1.mp3"));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("111.mp3"));
        int by = 0;
        while ((by = bis.read()) != -1){
            bos.write(by);
        }
        bis.close();
        bos.close();
    }
    public static void copyMp3_3() throws IOException{//有点小问题
        MyBufferedInputStream bis = new MyBufferedInputStream(new FileInputStream("1.mp3"));//自定义缓冲区
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("1111.mp3"));
        int by = 0;
        while ((by = bis.myRead()) != -1){//read()方法把byte转为int，write把int强转为byte再写入
            bos.write(by);
        }
        bis.myClose();
        bos.close();
    }
}




/*
复制图片
用字节流复制
1：用字节读取流和对象相关联
2：用字节写入流对象创建一个图片文件，用于存储获取到的图片数据
3：通过循环读写，完成数据的存储
4：关闭资源
 */
class CopyPic{
    public static void main(String[] args){
        copyPic();
    }
    public static void copyPic(){
        FileOutputStream fos = null;
        FileInputStream fis = null;
        try{
            fis = new FileInputStream("1.jpg");//1
            fos = new FileOutputStream("11.jpg");//2
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = fis.read(buf)) != -1){
                fos.write(buf,0,len);
            }
        }catch (IOException e){
//            System.out.println("读写异常");
            throw new RuntimeException("复制文件失败");
        }finally {
            try{
                if (fis !=null)
                    fis.close();
            }catch (IOException e){
//                System.out.println("读异常");
                throw new RuntimeException("读取关闭失败");
            }
            try{
                if (fos !=null)
                    fos.close();
            }catch (IOException e){
//                System.out.println("写异常");
                throw new RuntimeException("写入关闭失败");
            }
        }
    }
}




/*
字节流：InputStream，  OutputStream
需求，操作图片数据，这时需要用到字节流
 */
class FileStream{
    public static void main(String[] agrs) throws IOException{
//        writeFile();
//        readFile_2();
        readFile_3();

    }
    public static void writeFile() throws IOException{
        FileOutputStream fos = new FileOutputStream("fos.txt");
        fos.write("abcdef".getBytes());
        fos.close();
    }
    public static void readFile_1() throws IOException{
        FileInputStream fis = new FileInputStream("fos.txt");
        int ch = 0;
        while ((ch = fis.read()) != -1){
            System.out.println((char)ch);
        }
        fis.close();
    }
    public static void readFile_2() throws IOException{//建议用这个方法
        FileInputStream fis = new FileInputStream("fos.txt");
        byte[] buf = new byte[1024];//字节流
        int len = 0;
        while ((len = fis.read(buf)) != -1){
            System.out.println(new String(buf,0,len));
        }
        fis.close();
    }
    public static void readFile_3() throws IOException{//数据太大不太合适
        FileInputStream fis = new FileInputStream("fos.txt");
//        int num = fis.available();//返回文档字节个数
        byte[] buf = new byte[fis.available()];//定义一个刚刚好的缓冲区
        fis.read(buf);
        System.out.println(new String(buf));
        fis.close();
    }
}



/*
优化下面小程序
 */
class MyLineNumberReader1 extends MyBufferReader{
    private Reader r;
    private int lineNumber;
    MyLineNumberReader1(Reader r){
        super(r);
    }
    public void setLineNumber(int lineNumber){
        this.lineNumber = lineNumber;
    }
    public int getLineNumber(){
        return lineNumber;
    }
    public String myReadLine() throws IOException{
        lineNumber++;
        return super.myReadLine();
    }
}


/*
实现类似LineNumberReader的geiLineNumber方法
 */
class MyLineNumberReader{
    private Reader r;
    private int lineNumber;
    MyLineNumberReader(Reader r){
        this.r = r;
    }
    public void setLineNumber(int lineNumber){
        this.lineNumber = lineNumber;
    }
    public int getLineNumber(){
        return lineNumber;
    }
    public String myReadLine() throws IOException{
        lineNumber++;
        StringBuilder sbu = new StringBuilder();
        int ch = 0;
        while ((ch = r.read()) != -1){
            if (ch == '\r')
                continue;
            if (ch == '\n'){
                return sbu.toString();
            }
            sbu.append((char)ch);
        }
        if (sbu.length() != 0)
            return sbu.toString();
        return null;
    }
    public void myclose() throws IOException{
        r.close();
    }
}
class MyLineNumberReaderDemo {
    public static void main(String[] args) throws IOException{
        FileReader fr = new FileReader("demo.txt");
        MyLineNumberReader mlnr = new MyLineNumberReader(fr);
        String line = null;
//        mlnr.setLineNumber(100);
        while ((line = mlnr.myReadLine()) != null){
//            System.out.println(line);
            System.out.println(mlnr.getLineNumber() + ":" +line);//从1开始
        }
        mlnr.myclose();
    }

}

/*
LineNumberReader
 */
class LineNumberReaderDemo {
    public static void main(String[] args) throws IOException{
        FileReader fr = new FileReader("demo.txt");
        LineNumberReader lnr = new LineNumberReader(fr);
        String line = null;
        lnr.setLineNumber(100);
        while ((line = lnr.readLine()) != null){
//            System.out.println(line);
            System.out.println(lnr.getLineNumber() + ":" +line);//从1开始
        }
        lnr.close();
    }

}


/*
自定义装饰类
 */
class MyBufferReaderZhuangShi extends Reader {
    private Reader r;

    MyBufferReaderZhuangShi(Reader r) {
        this.r = r;
    }
    public String myReadLine() throws IOException {
        StringBuilder sbu = new StringBuilder();
        int ch = 0;
        while ((ch = r.read()) != -1) {
            if (ch == '\r')
                continue;
            if (ch == '\n')
                return sbu.toString();
            else
                sbu.append((char)ch);
        }
        if (sbu.length() != 0)
            return sbu.toString();
        return null;
    }

    //覆盖
    public void close() throws IOException {
        r.close();
    }

    public int read(char[] cbuf, int off, int len) throws IOException {
        return r.read(cbuf, off, len);
    }
}


/*
原体系
MyReader//专门用于读取数据的类
     |---MyTextReader
            |---MyBufferTextReader
     |---MyMediaReader
            |---MyBufferMediaReader
     |---MyDataReader
            |---MyBufferDataReader

class MyBufferReader{
    MyBufferReader(MyTextReader mtr){};
    MyBufferReader(MyMediaReader mmr){};
}
上面这个类扩展性很差，找到其参数的共同类型，通过多态的形式，可以提高扩展性
class MyBufferReader extends MyReader{
    private MyReader mr;
    MyBufferReader(MyReader mr){};
}

优化后的体系
MyReader//专门用于读取数据的类
     |---MyTextReader
     |---MyMediaReader
     |---MyDataReader
     |---MyBufferReader

装饰模式比继承要灵活，避免了继承体系的臃肿，而且降低了类与类之间的关系

装饰类因为增强已有对象，几倍的功能和已有的时相同的，只不过提供了更强的功能，
所以装饰类和被装饰类通常是属于一个体系中的
 */



/*
装饰设计模式：
        当想要对已有的对象进行功能增强时，可以定义类，将已有对象传入，基于已有的功能，并提供加强功能
        那么自定义的该类成为装饰类
        装饰类通常会通过构造方法接收被装饰的对象，并基于被装饰的对象提供更强的功能
 */
class personZhuangshi{
    public void Eat(){
        System.out.println("吃饭");
    }
}
class SuperPerson{//一般情况下两个类实现同一接口
    private personZhuangshi p;
    SuperPerson(personZhuangshi p){
        this.p = p;
    }
    public void SuperEat(){
        System.out.println("开胃酒");
        p.Eat();
        System.out.println("甜点");
        System.out.println("smoke");
    }
}



/*
明白了BufferReader中的readline原理，自己定义一个myReadLine方法和readline功能一样
MyBufferReader
 */
class MyBufferReader{
    private Reader r;
    MyBufferReader(Reader r){
        this.r = r;
    }
    public String myReadLine() throws IOException{
        StringBuilder sbu = new StringBuilder();
        int ch = 0;
        while ((ch = r.read()) != -1){
            if (ch == '\r')
                continue;
            if (ch == '\n')
                return sbu.toString();
            else
                sbu.append(ch);
        }
        if (sbu.length() != 0)
            return sbu.toString();
        return null;
    }
    public void close() throws IOException{
        r.close();
    }
}


/*
FileBufferedReader
提供了一个一次读一行的方法 readline，如果已到达流末尾，则返回 null，该方法返回的是一行中的有效数据，不包括换行符
 */
class FileBufferedReaderDemo{
    public static void bufw_reader(){
        FileReader fr = null;
        try{
            fr = new FileReader("buf.txt");//创建目的地
            BufferedReader bfr = new BufferedReader(fr);//创建缓冲区
            String line = null;
            while ((line = bfr.readLine()) != null){
                System.out.println(line);
            }
            bfr.close();//其实关闭缓冲区就是关闭缓冲区中的流对象所以fw.close()可以不用写
        }catch (IOException e){
            throw new RuntimeException("读写失败");
        }
    }

    public static void main(String[] args){
        bufw_reader();
    }
}


/*
BufferedWriter
缓冲区的出现是为了提高流的操作效率而出现的，所以说在创建缓冲区之前，必须先有流对象
g该缓冲区提供了一个跨平台的换行方法newline
 */
class FileBufferedWriterDemo{
    public static void bufw_writer(){
        FileWriter fw = null;
        try{
            fw = new FileWriter("buf.txt");//创建目的地
            BufferedWriter bfw = new BufferedWriter(fw);//创建缓冲区
            bfw.write("abcd");
            bfw.newLine();//换行，在linux和window里都是换行
            bfw.write("abcd");
            bfw.newLine();//换行，在linux和window里都是换行
            bfw.flush();
            bfw.close();//其实关闭缓冲区就是关闭缓冲区中的流对象所以fw.close()可以不用写
        }catch (IOException e){
            throw new RuntimeException("读写失败");
        }
//        finally {
//            try{
//                if (fw !=null) {
//                    fw.close();
//                }
//            }catch (IOException e){
//                System.out.println(e.toString());
//            }
//        }
    }

    public static void main(String[] args){
        bufw_writer();
    }
}




/*
将c盘一个文本文件复制到d盘
原理： 1：在d盘定义一个文件，用于存储c盘文件中的数据
       2：定义读取流和c盘文件的关联
       3：通过不断的读写完成数据存储
       4：关闭资源
 */
class FileReaderDemo2{
    public static void copy(){
        FileReader fr = null;
        FileWriter fw = null;
        try{
            fw = new FileWriter("RuntimeDemo_copy.txt");//创建目的地
            fr = new FileReader("RuntimeDemo.txt");//与已有文件关联
            char[] buf = new char[1024];//定义一个字符数组。用于存储读到的字符，该read(char[])返回的是读到的字符的个数
            //一般定义1024
            int len = 0;
            while((len = fr.read(buf)) != -1){
                fw.write(buf,0,len);
            }
        }catch (IOException e){
            throw new RuntimeException("读写失败");
        }finally {
            try {
                if (fr != null)
                    fr.close();
            }catch (IOException e){
                System.out.println(e.toString());
            }
            try{
                if (fw !=null) {
                    fw.close();
                }
            }catch (IOException e){
                System.out.println(e.toString());
            }
        }
    }

    public static void main(String[] args){
        copy();
    }
}


/*
读 第二种方式
 */
class FileReaderDemo1{
    public static void main(String[] args){
//        FileReader fr =new FileReader("demo.txt");//创建一个读取流对象，和指定名称的文件相关联，要保证文件已存在，否则抛出异常
        FileReader fr = null;
        try{
            fr = new FileReader("demo.txt");
            char[] buf = new char[1024];//定义一个字符数组。用于存储读到的字符，该read(char[])返回的是读到的字符的个数
            //一般定义1024
            int num = 0;
            while((num = fr.read(buf)) != -1){
                System.out.println("num:" + num + "..." + new String(buf, 0, num));
            }
        }catch (IOException e){
            System.out.println(e.toString());
        }finally {
            try {
                if (fr != null)
                    fr.close();
            }catch (IOException e){
                System.out.println(e.toString());
            }
        }
    }
}


/*
读 第一种
 */
class FileReaderDemo{
    public static void main(String[] args){
//        FileReader fr =new FileReader("demo.txt");//创建一个读取流对象，和指定名称的文件相关联，要保证文件已存在，否则抛出异常
        FileReader fr = null;
        try{
            fr = new FileReader("demo.txt");
//            int ch = fr.read();//一次都一个字符，会继续往下读，如果读到末尾，返回-1
//            System.out.println((char)ch);
//            int ch1 = fr.read();//一次都一个字符，会继续往下读
//            System.out.println((char)ch1);

            int ch = 0;
            while ((ch = fr.read()) != -1){
                System.out.println((char)ch);
            }

//            while (true){
//                int ch = fr.read();
//                if (ch == -1){
//                    break;
//                }
//            System.out.println((char)ch);
//            }
        }catch (IOException e){
            System.out.println(e.toString());
        }finally {
            try {
                if (fr != null)
                    fr.close();
            }catch (IOException e){
                System.out.println(e.toString());
            }
        }
    }
}


/*
续写
 */
class FileWriterDemo3{
    public static void main(String[] args){
        FileWriter fw = null;
        try{
            fw = new FileWriter("demo.txt",true);//文件续写
            fw.write("数据\r\n哈哈");//只是写\n在txt中不会显示换行  \r\n可以

        }catch (IOException e){
            System.out.println(e.toString());
        }finally {
            try {
                if (fw != null)
                    fw.close();
            }catch (IOException e){
                System.out.println(e.toString());
            }

        }


    }
}


/*
IO异常处理
 */
class FileWriterDemo2{
    public static void main(String[] args){
        FileWriter fw = null;
        try{
            fw = new FileWriter("demo.txt");//当前目录下创建demo.txt 文件存放在项目下  该句抛出IOException
            fw.write("abcd\r\n");//调用writer方法将数据写入流中
            fw.write("abcd\r\n");//调用writer方法将数据写入流中
            fw.write("abcd\r\n");//调用writer方法将数据写入流中

        }catch (IOException e){
            System.out.println(e.toString());
        }finally {
            try {
                if (fw != null)
                    fw.close();
            }catch (IOException e){
                System.out.println(e.toString());
            }

        }


    }
}


/*
字节流基类：InputStream，  OutputStream
字符流基类：Reader，  Writer

字符流
数据的最常见体现形式：文件
需求：创建文件并写入数据
专门用于操作文件的Writer子类对象 FileWriter
1:创建FileWriter对象,该对象一被初始化就必须要明确被操作的文件
//而且该文件会被创建到指定目录下,如果该目录下已有同名文件，则被覆盖，其实该步就是明确数据要存放的目的地
 */

public class IOLiu {
    public static void main(String[] args) throws IOException{
        FileWriter fw = new FileWriter("demo.txt");//当前目录下创建demo.txt   该句抛出IOException
        fw.write("数据");//调用writer方法将数据写入流中
        fw.flush();//刷新流对象中的缓冲中的数据，将该数据刷到目的地中

        fw.write("哈哈");//调用writer方法将数据写入流中
        fw.flush();//刷新流对象中的缓冲中的数据，将该数据刷到目的地中

        fw.write("数据");//调用writer方法将数据写入流中
        fw.close();//关闭流资源，关闭之前刷新一次内部缓冲中的数据，将数据刷到目的地中，
                    // 和flush区别，flush刷新后，流还可以继续使用，close刷新后，会将流关闭
    }
}
