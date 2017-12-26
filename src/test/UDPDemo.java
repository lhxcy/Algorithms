package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

/**
 * Created by XCY on 2017/9/14.
 *  UDP 多线程 键盘读入数据 广播 端口 10000 目的IP  10.108.217.119      源IP  10.108.219.183
 */
class SendMessage implements Runnable{
    private DatagramSocket ds;
    SendMessage(DatagramSocket ds){
        this.ds = ds;
    }
    public void run(){
        try {
            BufferedReader bufr = new BufferedReader(new InputStreamReader(System.in));
            String line = null;
            myPrint("请输入数据：");
            while ((line = bufr.readLine()) != null){
                byte[] buf = line.getBytes();
                DatagramPacket dp = new DatagramPacket(buf, buf.length,
                                    InetAddress.getByName("10.108.217.119"), 10000);
                ds.send(dp);
                if ("over".equals(line))
                    break;
            }
            ds.close();
        }catch (Exception e){
            throw new RuntimeException("写入数据失败");
        }
    }
    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}

class ReceiveMessage implements Runnable{
    private DatagramSocket ds;
    ReceiveMessage(DatagramSocket ds){
        this.ds = ds;
    }
    public void run(){
        try {
            while (true){
                byte[] buf = new byte[1024];
                DatagramPacket dp = new DatagramPacket(buf, buf.length);
                ds.receive(dp);
                String ip = dp.getAddress().getHostAddress();
                String data = new String(dp.getData(), 0, dp.getLength());
                myPrint("ip: " + ip + "data: " + data);
            }
        }catch (Exception e){
            throw new RuntimeException("接收数据失败");
        }
    }
    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}

public class UDPDemo {
    public static void main(String[] args) throws Exception{
        DatagramSocket sendSocket = new DatagramSocket();
        DatagramSocket receiveSocket = new DatagramSocket(10000);
        new Thread(new SendMessage(sendSocket)).start();
        new Thread(new ReceiveMessage(receiveSocket)).start();
    }
}
class LoginServe13{
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(10006);
        while (true){
            Socket s = ss.accept();
            new Thread(new UserThread(s)).start();
        }
    }
}