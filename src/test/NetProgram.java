package test;

import java.net.*;

/**
 * Created by XCY on 2017/9/12.
 *  网络编程
 *  网络模型：OSI参考模型  TCP/IP参考模型
 */

class IPDemo1{
    public static void main(String[] args) throws Exception{

    }
    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}



class IPDemo{
    public static void main(String[] args) throws Exception{
//        InetAddress ia = InetAddress.getLocalHost();
//        myPrint(ia.toString());
//        myPrint("address:" + ia.getHostAddress());
//        myPrint("name:" + ia.getHostName());

//        InetAddress[] ia = InetAddress.getAllByName("www.baidu.com");
//        myPrint(ia.length);
//        myPrint(ia[0].toString());
//        myPrint(ia[1].toString());

        InetAddress ia = InetAddress.getByName("www.baidu.com");
        myPrint(ia.toString());
        myPrint("address:" + ia.getHostAddress());
        myPrint("name:" + ia.getHostName());
    }
    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}
public class NetProgram {
}
