package test;

import java.io.*;
import java.net.*;
import java.util.Properties;

/**
 * Created by XCY on 2017/8/31.
 */

class UserThread1 implements Runnable{
    private Socket s;
    UserThread1(Socket s){
        this.s = s;
    }
    public void run(){
        String ip = s.getInetAddress().getHostAddress();
        myPrint(ip + "...connect");
        try {
            for (int i = 0; i < 3; i++){
                BufferedReader bufIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
                String name = bufIn.readLine();
                BufferedReader bufr = new BufferedReader(new FileReader("user.txt"));
                PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                String line = null;
                boolean flag = false;
                while ((line = bufr.readLine()) != null){
                    if (line.equals(name)){
                        flag = true;
                        break;
                    }
                }
                if (flag){
                    myPrint(name + "::" + "已登陆");
                    out.println(name + "，欢迎光临");
                    break;
                }else {
                    myPrint(name + "：尝试登录");
                    out.println(name + ",用户名不存在");
                }

            }
            s.close();

        }catch (Exception e){
            throw new RuntimeException(ip + "校验失败");
        }
    }
    public static void myPrint(Object obj) {
        System.out.println(obj);
    }
}

class LoginServe1{
    public static void main(String[] args) throws IOException{
        ServerSocket ss = new ServerSocket(10006);
        while (true){
            Socket s = ss.accept();
            new Thread(new UserThread1(s)).start();
        }
    }
}





class  TCPThreadPic1 implements Runnable {//可能线程不好
    private Socket s;
    TCPThreadPic1(Socket s){
        this.s = s;
    }
    public void run() {
        String ip = s.getInetAddress().getHostAddress();
        int count = 1;
        try {
            myPrint("ip: " + ip + "...connect");
            InputStream is = s.getInputStream();
            File file = new File(ip + "(" + count+")" +".jpg");
            while(file.exists())
                file = new File(ip + "(" + (count++)+")" +".jpg");
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = is.read(buf)) != -1){
                fos.write(buf, 0, len);
            }
            OutputStream out = s.getOutputStream();
            out.write("上传成功".getBytes());
//            out.flush();//因为关闭了s，所以关闭了out流，因此不用刷新
            fos.close();
            s.close();
        } catch (Exception e) {
            throw new RuntimeException(ip + "数据写入失败");
        }
    }
    public static void myPrint(Object obj) {
        System.out.println(obj);
    }
}
class PicServer1{//服务器得玩多线程
    public static void main(String[] args) throws IOException{
        ServerSocket ss = new ServerSocket(10005);
        while (true){
            Socket s = ss.accept();
            new Thread(new TCPThreadPic1(s)).start();
        }
    }
}






class TransTCPServe1{
    public static void main(String[] args) throws Exception{
        ServerSocket ss = new ServerSocket(10002);
        Socket s = ss.accept();
        String ip = s.getInetAddress().getHostAddress();
        myPrint("ip: " + ip + "...connect");
        BufferedReader bufIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
//        BufferedWriter bufOut = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream(),true);//简化上面书写
        String line = null;
        while ((line = bufIn.readLine()) != null){//readLine需要遇到回车才返回数据
            out.println(line);
//            bufOut.write(line.toUpperCase());
//            bufOut.newLine();
//            bufOut.flush();
        }
        s.close();
        ss.close();
    }
    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}



class TcpService1{
    public static void main(String[] args) throws IOException{
        ServerSocket ss = new ServerSocket(10003);
        Socket s = ss.accept();
        String ip = s.getInetAddress().getHostAddress();
        myPrint("ip:" + ip + "...connect");
        InputStream in = s.getInputStream();
        byte[] buf = new byte[1024];
        int len = in.read(buf);
        myPrint(new String(buf, 0 ,len));

        OutputStream out = s.getOutputStream();
        out.write("收到，你好".getBytes());
        s.close();
        ss.close();
    }
    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}




class UDPReceiveDemo1{
    public static void main(String[] args) throws Exception{
        udpReceive();
    }
    public static void udpReceive() throws Exception{
        //创建udp服务，通过DatagramSocket对象 建立端点
        DatagramSocket ds = new DatagramSocket(10001);//服务只需要创建一次就好
        while (true){
            long startTime = System.currentTimeMillis();
            //定义数据包，用于存储数据   DatagramPacket(byte[] buf, int length)
            byte[] buf = new byte[1024];
            DatagramPacket dp = new DatagramPacket(buf, buf.length);
            //通过socket服务的receive方法将收到的数据存入数据包中
            ds.receive(dp);//阻塞式方法
            long endTime = System.currentTimeMillis();
            //通过数据包的方法获取其中的数据
            String ip = dp.getAddress().getHostAddress();
            String d = new String(dp.getData(), 0, dp.getLength());
            int port = dp.getPort();
            myPrint("ip: " + ip);
            myPrint("port: " + port);
            myPrint("data: " + d);
//            if ("over".equals(d))
//                break;
        }
        //关闭资源
//        ds.close();//因为一直接收数据，所以不需要关闭资源
    }
    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}



class Outer1
{
    private int x = 3;
    private class Inner//内部类  可以被私有，私有不能
    {
        void function()
        {
            System.out.println("Inner :" + x);
        }
    }
    void method()
    {
//        System.out.println(x);
        Inner in = new Inner();
        in.function();

    }
}
public class faceObjectTest {
    public static void main(String[] args) throws Exception{
        Properties prop = System.getProperties();
        prop.list(System.out);

//        InetAddress ia = InetAddress.getLocalHost();
//        myPrint(ia.getHostAddress());
    }
    public static void myPrint(Object obj){//自定义输出位置
        System.out.println(obj);
//        try {
//            System.setOut(new PrintStream("sys.txt"));
//            System.out.println(obj);
//        }catch (IOException e){
//            throw new RuntimeException("jpjj");
//        }

    }
}
