package test;

import java.io.*;

/**
 * Created by XCY on 2017/9/4.
 *    字节流   字符流
 *    JB2312   JBK  汉字表
 *    字节流基类：InputStream，  OutputStream
 *    字符流基类：Reader，  Writer
 *
 */
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
            fw.write("abcd");//调用writer方法将数据写入流中

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
