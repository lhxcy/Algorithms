package test;

import com.sun.corba.se.spi.activation.Server;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.*;

import static java.lang.Thread.currentThread;

/**
 * Created by XCY on 2017/9/12.
 *  网络编程
 *  网络模型：OSI参考模型  TCP/IP参考模型
 *  192.168.1.0（网络地址）  192.168.1.255（广播地址）
 *
 *      TCP
 *       Socket   ServerSocket
 *
 *
 *
 *
 *
 */

/*

 */
class URLConnectDemp{//应用层
    public static void main(String[] args) throws Exception{
        URL url = new URL("http://192.168.1.254:8080/myweb/demo.html");
        URLConnection conn = url.openConnection();//连接目标主机
        InputStream  in = conn.getInputStream();
        byte[] buf = new byte[1024];
        int len = in.read(buf);
        myPrint(new String(buf, 0, len));//打印出来的不包括响应头
    }
    public static void myPrint(Object obj) {
        System.out.println(obj);
    }
 }


/*
URL类
 String getFile()
          获取此 URL 的文件名。
 String getHost()
          获取此 URL 的主机名（如果适用）。
 String getPath()
          获取此 URL 的路径部分。
 int getPort()
          获取此 URL 的端口号。
 String getProtocol()
          获取此 URL 的协议名称。
 String getQuery()
          获取此 URL 的查询部分。
 String getRef()
          获取此 URL 的锚点（也称为“引用”）。
 String getUserInfo()
          获取此 URL 的 userInfo 部分。

 */
class URLDemo{
    public static void main(String[] args) throws Exception{
        //当不写端口号时，默认-1
        URL url = new URL("http://192.168.1.254:8080/myweb/demo.html?name=haha&age=30");
        myPrint("getProtocol():" + url.getProtocol());
        myPrint("getHost():" + url.getHost());
        myPrint("getPort():" + url.getPort());
        myPrint("getPath():" + url.getPath());
        myPrint("getFile():" + url.getFile());
        myPrint("getQuery():" + url.getQuery());
//        int port = url.getPort();
//        if (port == -1){
//            port = 80;
//        }

    }
    public static void myPrint(Object obj) {
        System.out.println(obj);
    }
}






/*
演示客户端和服务端
1：客户端：浏览器，服务端：自定义
2：客户端：浏览器，服务端：Tomcat服务器
2：客户端：自定义，服务端：Tomcat服务器
 */
class MyServe{//1
    public static void main(String[] args) throws Exception{
        ServerSocket ss = new ServerSocket(11000);
        Socket s = ss.accept();
        myPrint("ip:" + s.getInetAddress().getHostAddress());
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
//        out.println("客户端你好");
        out.println("<font color='red' size='7'>hello</font");
        Thread.sleep(10000);
        s.close();
        ss.close();
    }
    public static void myPrint(Object obj) {
        System.out.println(obj);
    }
}
class MyServe3{//3
    public static void main(String[] args) throws Exception{
        ServerSocket ss = new ServerSocket(11000);
        Socket s = ss.accept();
        myPrint("ip:" + s.getInetAddress().getHostAddress());
        InputStream in = s.getInputStream();
        byte[] buf = new byte[1024];
        int len = in.read(buf);
        myPrint(new String(buf, 0, len));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
//        out.println("客户端你好");
        out.println("<font color='red' size='7'>hello</font");
        Thread.sleep(10000);
        s.close();
        ss.close();
    }
    public static void myPrint(Object obj) {
        System.out.println(obj);
    }
}
/*
ip:10.210.101.79
GET / HTTP/1.1
//Accept: text/html, application/xhtml+xml, image/jxr,
//        Accept-Language: zh-CN
//        User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36 Edge/15.15063
//        Accept-Encoding: gzip, deflate
//        Host: 10.210.101.79:11000
//        Connection: Keep-Alive
// */


class MyIE{//需要下载Tomcat服务器并运行
    public static void main(String[] args) throws IOException{//上面那些消息不一定都要写
        Socket s = new Socket("10.210.101.79", 10006);
        PrintWriter out = new PrintWriter(s.getOutputStream(),true);
        out.println("GET / HTTP/1.1");
        out.println("Accept: text/html, application/xhtml+xml, image/jxr,*/*");//可以直接写*/*
        out.println("Accept-Language: zh-CN");
        out.println("Host: 10.210.101.79:11000");
        out.println("Connection: Keep-Alive");
        out.println();
        out.println();
        BufferedReader bufr = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String line = null;
        while ((line = bufr.readLine()) != null){
            myPrint(line);
        }
        s.close();
    }
    public static void myPrint(Object obj) {
        System.out.println(obj);
    }
}




/*
客户端通过键盘录入用户名，服务端对这个用户名进行校验
如果用户存在，在服务端显示   xxx，已登陆 ，并在客户端显示 xxx，欢迎光临
如果用户不存在，在服务器端显示   xxx，尝试登陆  ，并在客户端显示xxx，，该用户不存在
最多登陆3次
 */
class LoginClient{
    public static void main(String[] args) throws IOException{
        Socket s = new Socket("10.210.101.79", 10006);
        BufferedReader bufr = new BufferedReader(new InputStreamReader(System.in));//系统输入
        PrintWriter out = new PrintWriter(s.getOutputStream(),true);//套接字的输出流
        BufferedReader bufIn = new BufferedReader(new InputStreamReader(s.getInputStream()));//套接字的输入流
        for (int i = 0; i < 3; i++){
            String line = bufr.readLine();
            if (line == "")//若Ctrl+C停止客户端程序，则line会是 空 此时需要跳出循环
                break;
//            myPrint(line);
            out.println(line);
            String info = bufIn.readLine();
            myPrint("服务端：" + info);
            if (info.contains("欢迎"))
                break;
        }
        bufr.close();
        s.close();
    }
    public static void myPrint(Object obj) {
        System.out.println(obj);
    }
}

class UserThread implements Runnable{
    private Socket s;
    UserThread(Socket s){
        this.s = s;
    }
    public void run(){
        String ip = s.getInetAddress().getHostAddress();
        myPrint(ip + "...connect");
        try {
            for (int i = 0; i < 3; i++){
                BufferedReader bufIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
                String name = bufIn.readLine();
                if (name == "")//若Ctrl+C停止客户端程序，则line会是 空 此时需要跳出循环
                    break;
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

class LoginServe{
    public static void main(String[] args) throws IOException{
        ServerSocket ss = new ServerSocket(10006);
        while (true){
            Socket s = ss.accept();
            new Thread(new UserThread(s)).start();
        }
    }
}





/*
TCP 客户端并发上传图片
 */
class  TCPThreadPic implements Runnable {//可能线程不好
    private Socket s;
    TCPThreadPic(Socket s){
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
            out.flush();
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
class PicServer{//服务器得玩多线程
    public static void main(String[] args) throws IOException{
        ServerSocket ss = new ServerSocket(10005);
        while (true){
            Socket s = ss.accept();
            new Thread(new TCPThreadPic(s)).start();
        }
    }
}
class PicClient{
    public static void main(String[] args) throws IOException{
        if (args.length != 1){
            myPrint("请选择一个jpg格式文件");
            return;
        }
        File file = new File(args[0]);
        if (!(file.exists() && file.isFile())){
            myPrint("该文件有问题，要么不存在，要么不是文件");
            return;
        }
        if (!file.getName().endsWith(".jpg")){
            myPrint("图片格式不对，请重新选择");
            return;
        }
        if (file.length() > 1024 * 1024 *20){
            myPrint("文件过大");
            return;
        }
        Socket s = new Socket("10.210.101.79", 10005);
        FileInputStream fis = new FileInputStream(file);
        OutputStream out = s.getOutputStream();
        int len = 0;
        byte[] buf = new byte[1024];
        while ((len = fis.read(buf)) != -1){
            out.write(buf,0, len);
        }
        s.shutdownOutput();////
        InputStream is = s.getInputStream();
        byte[] bufIn = new byte[1024];
        int num = is.read(bufIn);
//        myPrint(num);
        myPrint(new String(bufIn, 0, num));
        fis.close();
        s.close();
    }
    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}



/*
需求：上传图片
 */
/*
客户端
    1：服务端点
    2：读取客户端已有的图片数据
    3：通过Socket输出流将数据发送给服务端
    4：读取服务端返回信息
    5：关闭
 */
class UPClient{
    public static void main(String[] args) throws IOException{
        Socket s = new Socket("10.210.101.82", 10004);
        FileInputStream fis = new FileInputStream("1.jpg");
        OutputStream out = s.getOutputStream();
        int len = 0;
        byte[] buf = new byte[1024];
        while ((len = fis.read(buf)) != -1){
            out.write(buf,0, len);
        }
        s.shutdownOutput();////禁用此套接字输出流
        InputStream is = s.getInputStream();
        byte[] bufIn = new byte[1024];
        int num = is.read(buf);
        myPrint(new String(bufIn, 0, num));
        fis.close();
        s.close();
    }
    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}
/*
服务端
 */
class UPserver{
    public static void main(String[] args) throws IOException{
        ServerSocket ss = new ServerSocket(10004);
        Socket s = ss.accept();
        String ip = s.getInetAddress().getHostAddress();
        myPrint("ip: " + ip + "...connect");
        InputStream is = s.getInputStream();
        FileOutputStream fos = new FileOutputStream("server.jpg");
        byte[] buf = new byte[1024];
        int len = 0;
        while ((len = is.read(buf)) != -1){
            fos.write(buf, 0, len);
        }
        OutputStream out = s.getOutputStream();
        out.write("上传成功".getBytes());
        fos.close();
        s.close();
        ss.close();
    }
    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}



/*
tcp 复制文件
 */
class TCPCopyClient{
    public static void main(String[] args) throws IOException{
        Socket s = new Socket("10.210.101.82", 10003);
        BufferedReader bufr = new BufferedReader(new FileReader("IPDemo.txt"));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
//        DataOutputStream dos = new DataOutputStream(s.getOutputStream());//操作基本数据类型的流
//        long time = System.currentTimeMillis();
//        out.println(time);////
//        dos.writeLong(time);///第二种方式
        String line = null;
        while ((line = bufr.readLine()) != null){
            out.println(line);
        }
//        out.println("over");//为了使服务端可以正常结束   第一种方式
//        dos.writeLong(time);//第二种方式  时间戳   流对象用的过多
        s.shutdownOutput();//关闭客户端输出流，相当于给流中加入一个结束标记 -1，此时服务器端读取流可以正常关闭
        BufferedReader bufIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String str = bufIn.readLine();
        myPrint(str);
        bufr.close();
        s.close();
    }
    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}
class TCPCopyServer{
    public static void main(String[] args)throws IOException{
        ServerSocket ss = new ServerSocket(10003);
        Socket s = ss.accept();
        String ip = s.getInetAddress().getHostAddress();
        myPrint("ip: " + ip + "...connect");
//        DataInputStream dis = new DataInputStream(s.getInputStream());//第二种方式
//        long time = dis.readLong();
        BufferedReader bufIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(new FileWriter("server.txt"),true);
        String line = null;
        while ((line = bufIn.readLine()) != null){
//            if ("over".equals(line)) //第一种结束方式
//                break;
//            if (time ==dis.readLong())//第二种方式
//                break;
            out.println(line);
        }
        PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
        pw.println("上传成功");
        bufIn.close();
        ss.close();
        s.close();
    }
    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}


/*
需求：建立一个文本转换服务
客户端给服务端发送文本，服务端将文本转换成大写在返回给客户端
而且客户端可以不断进行文本的转换，当客户端输入over是，转换结束

分析：
    客户端：
            既然是操作设备上数据，那么就可以使用io技术，并按照io的擦做规律来思考
            源：键盘
            目的：网络设备，网络输出流，操作文本数据，可以选择字符流
        步骤：
            1：建立服务
            2：获取键盘录入
            3：发送数据
            4：接收服务端返回大写数据
            5：关闭资源
         都是文本数据，可以使用字符流进行操作，同时提高效率，加入缓冲


 */
class TransTCPClient{
    public static void main(String[] args) throws Exception{

        Socket s = new Socket(InetAddress.getByName("10.210.101.82"), 10002);
        //定义键盘读取
        BufferedReader bufr = new BufferedReader(new InputStreamReader(System.in));
        //定义目的
//        BufferedWriter bufOut = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream(),true);
        //定义一个Socket读取流，读取服务端返回信息
        BufferedReader bufIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String line = null;
        while ((line = bufr.readLine()) != null){
            myPrint("请输入数据：");
            if ("over".equals(line)) {
                break;
            }
            out.println(line.toUpperCase());
//            bufOut.write(line);
//            bufOut.newLine();//因为服务端readLine需要遇到换行符才返回数据，所以需要加入这行代码
//            bufOut.flush();//需要刷新
            String str = bufIn.readLine();
            myPrint("服务器：" + str);
        }
        bufr.close();
        s.close();
    }
    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}
/*
服务端：
    源：socket读取流
    目的：socket输出流
 */
class TransTCPServe{
    public static void main(String[] args) throws Exception{
        ServerSocket ss = new ServerSocket(10002);
        Socket s = ss.accept();
        String ip = s.getInetAddress().getHostAddress();
        myPrint("ip: " + ip + "...connect");
        BufferedReader bufIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
//        BufferedWriter bufOut = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream(),true);//简化上面书写,既能接收字符流，又能接收字节流
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




/*
演示tcp的传输的客户端和服务端的互访
需求：客户端给服务端发送数据，服务端收到数据后，给客户端反馈信息

客户端：
    1：建立socket服务，指定要连接主机和端口
    2：获取socket流中的输出流，将数据写入该流中，通过网络发送给服务端
    3：获取socket流的输入流，将服务端反馈的数据获取到，并打印
    4：关闭资源
 */
class TcpClient2{
    public static void main(String[] args) throws IOException{
        //创建socket服务，并指定要连接的主机和端口
        Socket s = new Socket("10.168.103.10",10003);//"10.168.103.10"
        //为了发送数据，应该获取socket流中的输出流
        OutputStream out = s.getOutputStream();
        out.write("服务端，你好".getBytes());

        InputStream in = s.getInputStream();
        byte[] buf = new byte[1024];
        int len = in.read(buf);
//        myPrint(len);
        myPrint(new String(buf, 0, len));
//        myPrint("kkkkkkkk");
        s.close();
    }
    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}
class TcpService2{
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



/*
 演示TCP传输
    1：TCP分为客户端和服务端
    2：客户端对于Socket , 服务端对应ServerSocket

  客户端，
    通过查询Socket对象，发现该对象建立时，就可以连接指定主机
    因为tcp时面向连接的所以建立socket服务时，就要有服务端存在，并连接成功，形成通路后，在该通道进行数据传输

    需求：给服务端发送一个文本数据
    思路：
        1：创建socket服务，并指定要连接的主机和端口
        2:


 */
class TcpClient{
    public static void main(String[] args) throws IOException{
        //创建socket服务，并指定要连接的主机和端口
        Socket s = new Socket("10.168.103.15",10003);//"10.168.103.10"
        //为了发送数据，应该获取socket流中的输出流
        OutputStream out = s.getOutputStream();

        out.write("tcp is coming".getBytes());

        out.close();
    }
    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}
/*
tcp 服务端
需求：定义端点接收数据并打印到控制台
服务端：
    1：建立服务端的socket对象  ServerSocket，并监听一个端口
    2：获取连接过来的客户端对象，通过ServerSocket的accept方法，这个方法是阻塞式的
    3：客户端如果发过来数据，那么服务端要使用对应的客户端对象，并获取该客户端的对象的读取流来读取发送过来的数据并打印
    4关闭资源(可选)
 */
class TcpService{
    public static void main(String[] args) throws IOException{
        ServerSocket ss = new ServerSocket(10003);
        Socket s = ss.accept();
        String ip = s.getInetAddress().getHostAddress();
        myPrint("ip:" + ip + "...connect");
        InputStream in = s.getInputStream();
        byte[] buf = new byte[1024];
        int len = in.read(buf);
        myPrint(new String(buf, 0 ,len));
        s.close();
        ss.close();
    }
    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}


/*
udp 聊天程序
需求：
    编写一个聊天程序，有收数据部分和发送数据部分
    这两部分同时执行，那就需要用到多线程技术
    一个线程控制接收，一个线程控制发送

    因为收和发动作是不一致的，那么需要定义两个run方法
    而且这两个方法要封装到不同的类中
 */
class Send implements Runnable{
    private DatagramSocket ds;
    Send(DatagramSocket ds){
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
                                    InetAddress.getByName("10.168.103.10"), 10002);//10.108.219.183"10.108.219.255
                ds.send(dp);
                if ("over".equals(line))
                    break;
            }
//            ds.close();
        }catch (Exception e){
            throw new RuntimeException("发送数据失败");
        }
    }
    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}
class Receive implements Runnable{
    private DatagramSocket ds;
    Receive(DatagramSocket ds){
        this.ds = ds;
    }
    public void run(){
        try {
            while (true){
                byte[] buf = new byte[1024];
                DatagramPacket dp = new DatagramPacket(buf, buf.length);
                ds.receive(dp);
                String ip = dp.getAddress().getHostAddress();
                String data = new String(dp.getData(),0, dp.getLength());
//                int port = dp.getPort();
                myPrint("ip: " + ip + " data: " + data);
//                myPrint("port: " + port);
//                myPrint("data: " + data);
            }
        }catch (Exception e){
            throw new RuntimeException("接收数据失败");
        }
    }
    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}
class Chat{
    public static void main(String[] args) throws Exception{
        DatagramSocket sendSocket = new DatagramSocket();//这个port可以不设置
        DatagramSocket receiveSocket = new DatagramSocket(10002);
        new Thread(new Send(sendSocket)).start();
        new Thread(new Receive(receiveSocket)).start();
    }
}





/*
键盘录入方式发送数据
 */
class UDPSendDemo2{
    public static void main(String[] args) throws Exception{
        udpSent();
    }
    public static void udpSent() throws Exception{
        //创建udp服务，通过DatagramSocket对象
        DatagramSocket ds = new DatagramSocket(9999);//这个端口可以不写，默认使用
        BufferedReader bufr = new BufferedReader(new InputStreamReader(System.in));
        String line = null;
        myPrint("请输入数据：");
        while ((line = bufr.readLine()) != null){
//            if ("over".equals(line))//加入放后面，则把数据发送过去在关闭
//                break;
            //确定数据，并封装成数据包   DatagramPacket(byte[] buf, int length, InetAddress address, int port)
            byte[] buf = line.getBytes();
            DatagramPacket dp = new DatagramPacket(buf, buf.length, InetAddress.getByName("DESKTOP-QVBGNSP"), 10001);

            //通过socket服务将已有的数据包发送出去
            ds.send(dp);
            if ("over".equals(line))
                break;
        }

        //关闭资源
        ds.close();
    }
    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}

class UDPReceiveDemo2{
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


/*
Socket 就是为网络服务提供的一种机制
DatagramSocket
需求：通过udp传输方式，讲一段文字数据发送出去
    1：建立udpsocket服务
    2：提供数据，并将数据封装到数据包中
    3：通过socket服务的发送功能，将数据包发出去
    4：关闭资源

 */
class UDPSendDemo{
    public static void main(String[] args) throws Exception{
        udpSent();
    }
    public static void udpSent() throws Exception{
        //创建udp服务，通过DatagramSocket对象
        DatagramSocket ds = new DatagramSocket(9999);//这个端口可以不写，默认使用

        //确定数据，并封装成数据包   DatagramPacket(byte[] buf, int length, InetAddress address, int port)
        byte[] buf = "udp shu ju is coming".getBytes();
        DatagramPacket dp = new DatagramPacket(buf, buf.length, InetAddress.getByName("DESKTOP-QVBGNSP"), 10000);

        //通过socket服务将已有的数据包发送出去
        ds.send(dp);

        //关闭资源
        ds.close();
    }
    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}
/*
用于接收数据
需求：定义一个应用程序，用于接收udp协议传输的数据并处理
思路：
    1：定义udpsocket服务,通常会监听一个端口。其实就是给这个接受网络应用程序定义数字标识
        方便于明确哪些数据过来该应用程序可以处理
    2：定义一个数据包，因为要存储接收到的数据，因为数据包中有更多功能可以提取字节数据中的不同数据信息
    3：通过socket服务的receive方法将收到的数据存入定义好的数据包中
    4：通过数据包对象的特有功能，将这些不同的数据取出，打印在控制台上
    5：关闭资源
 */
class UDPReceiveDemo{
    public static void main(String[] args) throws Exception{
        udpReceive();
    }
    public static void udpReceive() throws Exception{
        //创建udp服务，通过DatagramSocket对象 建立端点
        DatagramSocket ds = new DatagramSocket(10000);

        //定义数据包，用于存储数据   DatagramPacket(byte[] buf, int length)
        byte[] buf = new byte[1024];
        DatagramPacket dp = new DatagramPacket(buf, buf.length);

        //通过socket服务的receive方法将收到的数据存入数据包中
        ds.receive(dp);

        //通过数据包的方法获取其中的数据
        String ip = dp.getAddress().getHostAddress();
        String d = new String(dp.getData(), 0, dp.getLength());
        int port = dp.getPort();
        myPrint("ip: " + ip);
        myPrint("port: " + port);
        myPrint("data: " + d);

        //关闭资源
        ds.close();
    }
    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}

/*
获取ip
InetAddress
 */


class IPDemo{
    public static void main(String[] args) throws Exception{
        InetAddress ia = InetAddress.getLocalHost();
        myPrint(ia.toString());
        myPrint("address:" + ia.getHostAddress());
        myPrint("name:" + ia.getHostName());

//        InetAddress[] ia = InetAddress.getAllByName("www.baidu.com");
//        myPrint(ia.length);
//        myPrint(ia[0].toString());
//        myPrint(ia[1].toString());
//
//        InetAddress ia = InetAddress.getByName("www.baidu.com");
//        myPrint(ia.toString());
//        myPrint("address:" + ia.getHostAddress());
//        myPrint("name:" + ia.getHostName());
    }
    public static void myPrint(Object obj){
        System.out.println(obj);
    }
}
public class NetProgram {
}
