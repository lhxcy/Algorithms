package test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Thread.currentThread;

/**
 * Created by XCY on 2017/9/14.
 * TCP 连接
 */
class TCPServer{
    public static void main(String[] args) throws Exception{
        ServerSocket ss = new ServerSocket(10001);
        Socket s= ss.accept();
        String ip = s.getInetAddress().getHostAddress();
        InputStream in = s.getInputStream();
        OutputStream out = s.getOutputStream();
        while (true){
            byte[] buf = new byte[1024];
            int len = in.read(buf);
            myPrint("ip: " + ip);
            myPrint(new String(buf, 0, len));
            out.write("收到了".getBytes());
        }

//        s.close();
//        ss.close();
    }
    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}
class  TCPClient{ //implements Runnable{//可能线程不好
//    public void run(){
//        try {
//            Socket s = new Socket(InetAddress.getByName("10.108.217.119"), 10001);//这句话放外面好
//            BufferedReader bufr = new BufferedReader(new InputStreamReader(System.in));
//            OutputStream out = s.getOutputStream();
//            InputStream in = s.getInputStream();
//            String line = null;
//            myPrint(currentThread().getName() +"请输入数据");
//            while ((line = bufr.readLine()) != null){
////            byte[] buf = line.getBytes();
////            out.write(buf, 0, buf.length);
//                out.write(line.getBytes());
//
//                byte[] buf = new byte[1024];
//                int len = in.read(buf);
//                myPrint(new String(buf, 0, len));
//                if ("over".equals(line))
//                    break;
//            }
//            s.close();
//        }catch (Exception e){
//            throw new RuntimeException("数据写入失败");
//        }
//    }
    public static void main(String[] args) throws Exception{
        try {
            Socket s = new Socket(InetAddress.getByName("10.108.217.119"), 10001);
            BufferedReader bufr = new BufferedReader(new InputStreamReader(System.in));
            OutputStream out = s.getOutputStream();
            String line = null;
            myPrint(currentThread().getName() +"请输入数据");
            while ((line = bufr.readLine()) != null){
//            byte[] buf = line.getBytes();
//            out.write(buf, 0, buf.length);
                out.write(line.getBytes());
                if ("over".equals(line))
                    break;
            }
            s.close();
        }catch (Exception e){
            throw new RuntimeException("数据写入失败");
        }
    }
    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}
public class TCPDemo {
    public static void main(String[] args){
//        new Thread(new TCPClient()).start();
//        new Thread(new TCPClient()).start();
    }

    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}
