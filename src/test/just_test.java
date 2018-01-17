package test;




import jdk.nashorn.internal.objects.Global;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.lang.ref.SoftReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

/**
 * Created by XCY on 2017/9/24.
 */

class TestAuto{
    public static void main(String[] args){
        int i = 1;
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;
        Long g = 3L;
        System.out.println(i == a);//true
        System.out.println(c == d);//true
        System.out.println(e == f);//false
        System.out.println(c == (a + b));//true
        System.out.println(c.equals(a + b));//true
        System.out.println(g == (a + b));//true
        System.out.println(g.equals(a + b));//false
    }
}




//class GenericType{//编译不通过
//    public static void method(List<String> list){
//        System.out.println("method(List<String> list)");
//    }
//    public static void method(List<Integer> list){
//        System.out.println("method(List<Integer> list)");
//    }
//}
class GenericTypes{
//    public static String method(List<String> list){
//        System.out.println("method(List<String> list)");
//        return "";
//    }
    public static int method(List<Integer> list){
        System.out.println("method(List<Integer> list)");
        return 1;
    }
    public static void main(String[] args){
//        method(new ArrayList<String>());
        method(new ArrayList<Integer>());
    }
}



class IntAndBytes{
    public static int bytes2Int(byte[] b, int start, int len){
        int sum = 0;
        return sum;
    }
    public static void main(String[] args){
//        System.out.println((int)'3');
        byte[] b = "3c".getBytes();
        int ans = bytes2Int(b, 0, 2);
        System.out.println(ans);
    }
}




class DynamicProxyTest{
    interface IHello{
        void sayHello();
    }
    static class Hello implements IHello{
        @Override
        public void sayHello(){
            System.out.println("hello world");
        }
    }
    static class DynammicProxy implements InvocationHandler{
        Object originalObj;
        Object bind(Object originalObj){//返回一个实现了IHello的接口，并且代理了new Hello()实例行为的对象
            this.originalObj = originalObj;
            return Proxy.newProxyInstance(originalObj.getClass().getClassLoader(),
                    originalObj.getClass().getInterfaces(),this);
        }
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
            System.out.println("welcome");
            return method.invoke(originalObj, args);
        }
    }
    public static void main(String[] args){
        IHello hello = (IHello)new DynammicProxy().bind(new Hello());
        hello.sayHello();
    }
}




class DisPatch{
    static class QQ{}
    static class SonQQ extends QQ{}
    static class _360{}
    public static class Father{
        public void choice(QQ arg){
            System.out.println("F qq");
        }
        public void choice(SonQQ arg){
            System.out.println("F SonQQ");
        }
        public void choice(_360 arg){
            System.out.println("F 360");
        }
    }
    public static class Son extends Father{
        public void choice(QQ arg){
            System.out.println("Son qq");
        }
        public void choice(SonQQ arg){
            System.out.println("Son SonQQ");
        }
        public void choice(_360 arg){
            System.out.println("Son 360");
        }
    }
    public static void main(String[] args){
        Father f = new Father();
        Father son = new Son();
//        f.choice(new QQ());//F qq
//        son.choice(new QQ());//Son QQ
        QQ q = new SonQQ();
//        f.choice(q);//F qq
//        son.choice(q);//Son QQ
        f.choice(new SonQQ());//F SonQQ
        son.choice(new SonQQ());//Son SonQQ
    }
}



class t{
    public static int most(int[] arr){
        int ans = Integer.MIN_VALUE;
        int xor = 0;
        int[] mosts = new int [arr.length];
        HashMap<Integer,Integer> map = new HashMap<>();
        map.put(0,-1);
        for (int i = 0; i < arr.length; i++){
            xor^=arr[i];
            if (map.containsKey(xor)){
                int j = map.get(xor);
                mosts[i] = j == -1 ? 1 : (mosts[j] + 1);
            }
            mosts[i] = Math.max(mosts[i - 1], mosts[i]);
            map.put(xor,i);
            ans = Math.max(ans,mosts[i]);
        }
        return ans;
    }
}

public class just_test {
    public static void main(String[] args){
        int[][] arr = {{0,1,0,0},{1,1,1,1},{0,0,1,0}};
        int[] row = new int[arr.length];
        int[] col = new int[arr[0].length];
        for (int i = 0; i < arr.length; i++){
            int k = 0;
            for (int j = 0; j < arr[0].length; j++){
                row[i] += arr[i][j];
            }
        }
        for (int i = 0; i < arr[0].length; i++){
            int k = 0;
            for (int j = 0; j < arr.length; j++){
                col[i] += arr[j][i];
            }
        }
//        for (int i = 0; i < row.length; i++)
//            myPrint(row[i]);
//        for (int i = 0; i < col.length; i++)
//            myPrint(col[i]);

    }
//    public static int trace(int[] row, int rs, int re, int[] col, int cs, int ce, int k){
//        int rsum = Tsum(row,rs,re);
//        int csum = Tsum(col,cs,ce);
//        if (rsum < k)
//
//
//    }
    public static int Tsum(int[] a, int s, int e){
        int sum = 0;
        for (; s < e; s++){
            sum += a[s];
        }
        return sum;
    }
    public static void myPrint(Object obj) {
        System.out.println(obj);
    }
}

class MyLinkedStack<T> {
    private static class Node<U> {
        U item;
        Node<U> next;
        Node(){ item = null; next = null; }
        Node(U item, Node<U> next){
            this.item = item;
            this.next = next;
        }
        boolean end(){
            return item == null && next == null;
        }
    }
    private Node<T> top = new Node<T>();//end sentinel
    public void push(T item){
        top = new Node<T>(item, top);
    }
    public T pop(){
        T result = top.item;
        if (!top.end()){
            top = top.next;
        }
        return result;
    }
    public static void main(String[] args){
        MyLinkedStack<String> mls = new MyLinkedStack<String>();
        for (String s : "my first linkedstack test".split(" ")){
            mls.push(s);
        }
        String s;
        while ((s = mls.pop()) != null){
            System.out.println(s);
        }
    }
}

interface Generator<T>{
    T next();
}
class coffee{
    private static long count = 0;
    private final long id = count++;
    public String toString(){
        return getClass().getSimpleName() + " " + id;
    }
}

class Latte extends coffee{}
class Mocha extends coffee{}
class Cappuccino extends coffee{}
class Americano extends coffee{}
class Breve extends coffee{}
class CoffeeGenroator implements Generator<coffee>, Iterable<coffee>{
    private Class[] types = {Latte.class, Mocha.class, Cappuccino.class, Americano.class, Breve.class, };
    private static Random rand = new Random(47);
    public CoffeeGenroator(){}
    private int size = 0;
    public CoffeeGenroator(int sz){ size = sz; }
    public coffee next(){
        try{
            return (coffee) types[rand.nextInt(types.length)].newInstance();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    class CoffeeIterator implements Iterator<coffee>{
        int count = size;
        public boolean hasNext(){
            return count > 0;
        }
        public coffee next(){
            count--;
            return CoffeeGenroator.this.next();
        }
        public void remove(){
            throw new UnsupportedOperationException();
        }
    }
    public Iterator<coffee> iterator(){
        return new CoffeeIterator();
    }
//    public boolean hasNext(){}
    public static void main(String[] args){
        CoffeeGenroator gen = new CoffeeGenroator();

        for (int i = 0; i < 5; i++){
            System.out.println(gen.next());
        }
        for (coffee c : new CoffeeGenroator(5)){
            System.out.println(c);
        }
    }
}

class TestDouble{
    public static void main(String[] args){
        double p = 3.14;
        long longBits = Double.doubleToLongBits(p);
        System.out.println(longBits);
        int res = (int)(longBits ^ (longBits >>> 32));
        System.out.println(res);
    }
}


class AA{
    AA(){
        System.out.println("AAAA constructor");
    }
    public void fun(){
        System.out.println("AAAAAAA");
    }
}
class BB extends AA{
    BB(){
        System.out.println("BBBB constructor");
    }
    public void fun(){
        System.out.println("BBBBBBB");
    }

    public static void main(String[] args){
        BB b = new BB();
        b.fun();
    }
}
class ArrayTest{
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.addAll(Arrays.asList("asd","zxc","qwe"));
        System.out.println(list);
        for (String s : list)
            if ("asd".equals(s)){
                list.remove(s);
                break;
            }

        System.out.println(list);
    }

}

class testA{
    int x;
}
class _test{
    public static void main(String[] args){
//        testA a = new testA();
//        a.x = 4;
//        testA b = a;
//        b.x = 5;
//        System.out.println(b.x + ":" + a.x);
//        System.out.println(0x71);
        int[] a = {1,2,3};

        Object o = a;
        System.out.println(o);
        List<Integer> ajk = new ArrayList<Integer>();
    }
}
class VolatileFeatureExample{
    long vl = 0L;
    public synchronized void set(long L){
        vl = L;
    }
    public void getIncrement(){
        long temp = get();
        temp += 1L;
        set(temp);
    }
    public synchronized long get(){
        return vl;
    }
}

class DoubleCheckLocking{
    private DoubleCheckLocking() {}
    private volatile static DoubleCheckLocking instance;
    public static DoubleCheckLocking getInstance(){
        if (instance == null){
            synchronized (DoubleCheckLocking.class){
                if (instance == null)
                    instance = new DoubleCheckLocking();
            }
        }
        return instance;
    }
}

class InstanceFactory{
    private InstanceFactory(){}
    private static class InstanceHolder{
        public static InstanceFactory instance = new InstanceFactory();
    }
    public static InstanceFactory getInstance(){
        return InstanceHolder.instance;
    }
}

class MultiThread{
    public static void main(String[] args){
        //获取Java线程管理MXBean
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        //不需要获取同步的monitor和synchronizer信息，仅获取线程和线程堆栈信息
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false,false);
        //遍历线程信息，仅打印线程ID和名称信息
        for (ThreadInfo threadInfo : threadInfos)
            System.out.println("[ " + threadInfo.getThreadId()+ " ]" + threadInfo.getThreadName());
    }
}

class Priority{
    private static volatile boolean notStart = true;
    private static volatile boolean notEnd = true;
    public static void main(String[] args) throws Exception{
        List<Job> jobs = new ArrayList<Job>();
        for (int i = 0; i < 10; i++){
            int priority = i < 5 ? Thread.MIN_PRIORITY : Thread.MAX_PRIORITY;
            Job job = new Job(priority);
            jobs.add(job);
            Thread thread = new Thread(job, "Thread: " + i);
            thread.setPriority(priority);
            thread.start();
        }
        notStart = false;
        TimeUnit.SECONDS.sleep(10);
        notEnd = false;
        for (Job job : jobs){
            System.out.println("Job Priority : " + job.priority + ", count : " + job.jobCount);
        }
    }
    static class Job implements Runnable{
        private int priority;
        private long jobCount;
        public Job(int priority){
            this.priority = priority;
        }
        public void run(){
            System.out.println("Thread: " + Thread.currentThread().getName());
            while (notStart){
                Thread.yield();//线程让步，即该线程和其他已经准备好的线程重新一起抢夺CPU资源
            }
            while (notEnd){
                Thread.yield();
                jobCount++;
            }
        }
    }
}



class Interrupted{
    public static void main(String[] args) throws Exception{
        //sleepThread不停的尝试睡眠
        Thread sleepThread = new Thread(new SleepRunner(), "SleepThread");
        sleepThread.setDaemon(true);
        //busyThread不停的运行
        Thread busyThread = new Thread(new BusyRunner(), "BusyThread");
        busyThread.setDaemon(true);
        sleepThread.start();
        busyThread.start();//休眠5秒，让sleepThread和busyThread充分运行
        TimeUnit.SECONDS.sleep(5);
        sleepThread.interrupt();
        busyThread.interrupt();
        System.out.println("SleepThread interrupted is " + sleepThread.isInterrupted());
        System.out.println("BusyThread interrupted is " + busyThread.isInterrupted());
        //防止sleepThread和busyThread立刻退出
        SleepUtils.second(2);
    }

    static class SleepRunner implements Runnable{
        @Override
        public void run(){
            while (true){
                SleepUtils.second(10);
            }
        }
    }

    static class BusyRunner implements Runnable{
        @Override
        public void run(){
            while (true){}
        }
    }
}
class SleepUtils{
    public static final void second(long seconds){
        try{
            TimeUnit.SECONDS.sleep(seconds);
        }catch (InterruptedException e){
            System.out.printf(e.getMessage());
        }
    }
}


class Deprecate{
    public static void main(String[] args) throws Exception{
        DateFormat format = new SimpleDateFormat("HH:mm:ss");
        Thread printThread = new Thread(new Runner(), "printThread");
        printThread.setDaemon(true);
        printThread.start();
        TimeUnit.SECONDS.sleep(3);
        //将printThread进行暂停，输出内容工作停止
//        System.out.println("main suspend printThread at");
        printThread.suspend();
        System.out.println("main suspend printThread at "+ format.format(new Date()));
        //将printThread进行恢复，输出内容继续
        printThread.resume();
        System.out.println("main resume printThread at "+ format.format(new Date()));
        TimeUnit.SECONDS.sleep(3);
        //将printThread进行终止，输出内容停止
        printThread.stop();
        System.out.println("main stop printThread at "+ format.format(new Date()));
        TimeUnit.SECONDS.sleep(3);

    }
    static class Runner implements Runnable{
        @Override
        public void run(){
            DateFormat format = new SimpleDateFormat("HH:mm:ss");
            while (true){
                System.out.println(Thread.currentThread().getName() + "  Run at " + format.format(new Date()));
                SleepUtils.second(1);
            }
        }
    }
}

class Termination{
    public static void main(String[] args) throws Exception{
        Runner one = new Runner();
        Thread countThread = new Thread(one, "countThread");
        countThread.start();
        //睡眠1秒，main线程对countThread进行中断，使countThread能够感知中断结束
        TimeUnit.SECONDS.sleep(1);
        countThread.interrupt();
        Runner two = new Runner();
        countThread = new Thread(two, "countThread");
        countThread.start();
        //睡眠1秒，main线程对two进行取消，使countThread能够感知on为false而结束
        TimeUnit.SECONDS.sleep(1);
        two.cancel();
    }
    private static class Runner implements Runnable{
        private Long count = 0L;
        private volatile boolean on = true;
        @Override
        public void run(){
            while (on && !Thread.currentThread().isInterrupted()){
                count++;
            }
            System.out.println("count =  " + count);
        }
        public void cancel(){
            on = false;
        }
    }
}


class Piped{
    public static void main(String[] args) throws Exception{
        PipedWriter out = new PipedWriter();
        PipedReader in = new PipedReader();
        //将输出流和输入流进行连接，否则在使用时会抛出IOException
        out.connect(in);
        Thread printThread = new Thread(new Print(in), "printThread");
        printThread.start();
        System.out.println("Input: ");
        int receive = 0;
        try {
            while ((receive = System.in.read()) != -1){
                out.write(receive);
            }
        }finally {
           out.close();
        }
    }
    static class Print implements Runnable{
        private PipedReader in;
        public Print(PipedReader in){
            this.in = in;
        }
        public void run(){
            int receive = 0;
            try {
                while ((receive = in.read()) != -1){
                    System.out.print((char)receive);
                }
            }catch (IOException e){

            }
        }
    }
}

class Profiler{
    //第一次get()方法调用时会进行初始化（如果set方法没有调用），每个线程会调用一次
    private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<Long>(){
        protected Long initialValue(){
            return System.currentTimeMillis();
        }
    };
    public static final void begin(){
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }
    public static final long end(){
        return System.currentTimeMillis() - TIME_THREADLOCAL.get();
    }
    public static void main(String[] args) throws Exception{
        Profiler.begin();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Cost: " + Profiler.end() + " mills");
    }
}

class TestProfiler{
    public static void main(String[] args){
        Profiler.begin();
        SleepUtils.second(2);
        System.out.println("Cost: " + Profiler.end() + " mills");
    }

//    //对当前对象加锁
//    public synchronized Object get(long mills) throws InterruptedException{
//        long future = System.currentTimeMillis() + mills;
//        long remaining = mills;
//        //当超时大于0且result返回值不满足要求
//        while ((result == null) && remaining > 0){
//            wait(remaining);
//            remaining = future - System.currentTimeMillis();
//        }
//        return result;
//    }
}

/*
连接池的定义。通过构造函数初始化连接的最大上限，通过双向队列维护连接。调用方需要先调用fetchConnection（long）
方法来指定在多少毫秒内超时获取连接，当连接使用完时，需要调用releaseConnection（Connection）方法来将来凝结放回
线程池
 */
class ConnectionPool{
    private LinkedList<Connection> pool = new LinkedList<Connection>();
    public ConnectionPool(int initialSize){
        if (initialSize > 0){
            for (int i = 0; i < initialSize; i++){
                pool.addLast(ConnectionDriver.creatConnection());
            }
        }
    }
    public void releaseConnection(Connection connection){
        if(connection != null){
            synchronized (pool){
                //连接释放后需要进行通知，这样其他消费者能够感知到连接池中已经归还了一个连接
                pool.addLast(connection);///?
                pool.notifyAll();
            }
        }
    }

    //在mills内无法获得连接，将会返回null
    public Connection fetchConnection(long mills) throws InterruptedException{
        synchronized (pool){
            //完全超时
            if (mills <= 0){
                while (pool.isEmpty()){
                    pool.wait();
                }
                return pool.removeFirst();
            }else {
                long future = System.currentTimeMillis() + mills;
                long remaining = mills;
                while (pool.isEmpty() && remaining > 0){
                    pool.wait(remaining);
                    remaining = future - System.currentTimeMillis();
                }
                Connection result = null;
                if (!pool.isEmpty()){
                    result = pool.removeFirst();
                }
                return result;
            }
        }
    }
}
/*
由于java.sql.Connection是一个接口，最终实现是由数据库驱动提供方案来实现的，这里通过动态代理构造了一个Connection，
该Connection的代理实现仅仅实在commit方法调用时休眠100毫秒
 */
class ConnectionDriver{
    static class ConnectionHander implements InvocationHandler{
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
            if (method.getName().equals("commit")){
                TimeUnit.MILLISECONDS.sleep(100);
            }
            return null;
        }
    }
    //创建一个Connection的代理，在commit时休眠100毫秒
    public static final Connection creatConnection(){
        return (Connection) Proxy.newProxyInstance(ConnectionHander.class.getClassLoader(),
                new Class<?>[] {Connection.class},new ConnectionHander());
    }
}

/*
测试，客户端模拟ConnectionRunner获取、使用、释放连接的过程
 */
class ConnectionPoolTest{
    static ConnectionPool pool = new ConnectionPool(10);
    //保证所有的ConnectionRunner能同时开始
    static CountDownLatch start = new CountDownLatch(1);
    //main线程将会等待所有ConnectionRunner结束后才能继续执行
    static CountDownLatch end;
    public static void main(String[] args) throws Exception{
        //线程数量，可以修改线程数量进行观察
        int threadCount = 20;
        end = new CountDownLatch(threadCount);
        int count = 20;
        AtomicInteger got = new AtomicInteger();
        AtomicInteger notGot = new AtomicInteger();
        for (int i = 0; i < threadCount; i++){
            Thread thread = new Thread(new ConnectionRunner(count, got, notGot), "ConnectionRunnerThread");
            thread.start();
        }
        start.countDown();//只要计数器为0，那么线程就可以结束阻塞往下执行,进而保证线程同时开始
        end.await();
        System.out.println("total invoke: " + (threadCount * count));
        System.out.println("got connection: " + got);
        System.out.println("not got connection: " + notGot);
    }

    static class ConnectionRunner implements Runnable{
        int count;
        AtomicInteger got;
        AtomicInteger notGot;
        public ConnectionRunner(int count, AtomicInteger got, AtomicInteger notGot){
            this.count = count;
            this.got = got;
            this.notGot = notGot;
        }
        @Override
        public void run(){
            try{
                start.await();
            }catch (Exception ex){
            }
            while (count > 0){
                try {
                    //从线程池中获取连接，如果1000ms内无法获取到，将会返回null
                    //分别统计连接获取的数量got和未获取到的数量notGot
                    Connection connection = pool.fetchConnection(1000);
                    if (connection != null){
                        try {
                            connection.createStatement();//创建一个 Statement 对象来将 SQL 语句发送到数据库
                            connection.commit();// 使所有上一次提交/回滚后进行的更改成为持久更改，
                                                // 并释放此 Connection 对象当前持有的所有数据库锁。
                        }finally {
                            pool.releaseConnection(connection);
                            got.getAndIncrement();
                        }
                    }else {
                        notGot.getAndIncrement();
                    }
                }catch (Exception ex){
                }finally {
                    count--;
                }
            }
            end.countDown();
        }
    }
}




/*
线程池解决创建大量线程的问题
 */
interface ThreadPool<Job extends Runnable>{
    //执行一个Job，这个Job需要实现Runnable
    void execute(Job job);
    //关闭线程
    void shutDown();
    //增加工作线程
    void addWorkers(int num);
    //减少工作线程
    void removeWorker(int num);
    //得到正在等待执行的任务数量
    int getJobSize();
}

/*
客户端通过execute(Job)方法将Job提交入线程池执行，而客户端自身不用等待Job的执行。
除了该方法外，线程池接口提供了增大/减少工作线程以及关闭线程的方法。
这里的工作线程代表着一个重复执行的Job线程，而每个客户提交的Job都将进入到一个工作队列中等待工作者线程处理
 */
class DefaultThreadPoll<Job extends Runnable> implements ThreadPool<Job>{
    //线程池最大限制数
    private static final int MAX_WORKER_NUMBERS = 10;
    //线程池默认数量
    private static final int DEFAULT_WORKER_NUMBERS = 5;
    //线程池最小数量
    private static final int MIN_WORKER_NUMBERS = 1;
    //这是一个工作列表，将会向里面插入工作
    private final LinkedList<Job> jobs = new LinkedList<Job>();
    //工作者列表
    private final List<Worker> workers = Collections.synchronizedList(new ArrayList<Worker>());
    //工作者线程的数量
    private int workerNum = DEFAULT_WORKER_NUMBERS;
    //线程编号生成
    private AtomicLong threadNum = new AtomicLong();

    public DefaultThreadPoll(){
        initializeWorkers(DEFAULT_WORKER_NUMBERS);
    }
    public DefaultThreadPoll(int num){
        workerNum = num > MAX_WORKER_NUMBERS ? MAX_WORKER_NUMBERS : num < MIN_WORKER_NUMBERS ? MIN_WORKER_NUMBERS : num;
        initializeWorkers(workerNum);
    }
    public void execute(Job job){
        if (job != null){
            synchronized (jobs){
                jobs.addLast(job);
                jobs.notify();
            }
        }
    }
    public void shutDown(){
        for (Worker worker : workers){
            worker.shutDown();
        }
    }
    public void addWorkers(int num){
        synchronized (jobs){
            //限制新增的Worker数量不超过最大值
            if (num + this.workerNum > MAX_WORKER_NUMBERS){
                num = MAX_WORKER_NUMBERS - workerNum;
            }
            initializeWorkers(num);
            this.workerNum += num;
        }
    }
    public void removeWorker(int num){
        synchronized (jobs){
            if (num > this.workerNum){
                throw new IllegalArgumentException("beyong workNum");
            }
            //按照给定的数量停止Worker
            int count = 0;
            while (count < num){
                Worker worker = workers.get(count);
                if (workers.remove(worker)){
                    worker.shutDown();
                    count++;
                }
            }
            this.workerNum -= count;
        }
    }
    public int getJobSize(){
        return jobs.size();
    }

    //初始化线程工作者
    private void initializeWorkers(int num){
        for (int i = 0; i < num; i++){
            Worker worker = new Worker();
            workers.add(worker);
            Thread thread = new Thread(worker, "ThreadPool-Worker-" + threadNum.incrementAndGet());
            thread.start();
        }
    }

    class Worker implements Runnable{
        //是否工作
        private volatile boolean running = true;
        @Override
        public void run(){
            while (running){
                Job job = null;
                synchronized (jobs){
                    //如果工作列表是空的，那么就wait
                    while (jobs.isEmpty()){
                        try {
                            jobs.wait();
                        }catch (InterruptedException e){
                            //感知到外部对WorkerThread的中断操作，返回
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    //取出一个job
                    job = jobs.removeFirst();
                }
                if (job != null){
                    try {
                        job.run();
                    }catch (Exception e){
                        //忽略Job执行中的异常
                    }
                }
            }
        }
        public void shutDown(){
            running = false;
        }
    }
}


/*
一个基于线程池技术的简单Web服务器
 */
class SimpleHttpServer{
    //处理HttpRequest的线程池
    static ThreadPool<HttpRequestHandler> threadPool = new DefaultThreadPoll<HttpRequestHandler>(1);
    //SimpleHttpServer的根路径
    static String basePath;
    static ServerSocket serverSocket;
    //服务监听端口
    static int port = 8080;
    public static void setPort(int port){
        if (port > 0){
            SimpleHttpServer.port = port;
        }
    }
    public static void setBasePath(String basePath){
        if (basePath != null && new File(basePath).exists() && new File(basePath).isDirectory()){
            SimpleHttpServer.basePath = basePath;
        }
    }
    //启动SimpleHttpServer
    public static void start() throws Exception{
        serverSocket = new ServerSocket(port);
        Socket socket = null;
        while ((socket = serverSocket.accept()) != null){
            //接收一个客户端Socket，生成一个HttpRequestHandler，放入线程池执行
            threadPool.execute(new HttpRequestHandler(socket));
        }
        serverSocket.close();
    }


    static class HttpRequestHandler implements Runnable{
        private Socket socket;
        public HttpRequestHandler(Socket socket){
            this.socket = socket;
        }
        @Override
        public void run(){
            String line = null;
            BufferedReader br = null;
            BufferedReader reader = null;
            PrintWriter out = null;
            InputStream in = null;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String header = reader.readLine();
                //由相对路径计算出绝对路径
                String filePath = basePath + header.split(" ")[1];
                out = new PrintWriter(socket.getOutputStream());
                //如果请求资源的后缀为jpg或者ico，则读取资源并输出
                if (filePath.endsWith("jpg") || filePath.endsWith("ico")){
                    in = new FileInputStream(filePath);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    int i = 0;
                    while ((i = in.read()) != -1){
                        baos.write(i);
                    }
                    byte[] array = baos.toByteArray();
                    out.println("HTTP/1.1 200 OK");
                    out.println("Server: molly");
                    out.println("Content-Type: image/jpeg");
                    out.println("Content-Length: " + array.length);
                    out.println("");
                    socket.getOutputStream().write(array, 0, array.length);
                }else {
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
                    out = new PrintWriter(socket.getOutputStream());
                    out.println("HTTP/1.1 200 OK");
                    out.println("Server: molly");
                    out.println("Content-Type: text/html; charset=UTF-8");
                    out.println("");
                    while ((line = br.readLine()) != null){
                        out.println(line);
                    }
                }
                out.flush();
            }catch (Exception e){
                out.println("HTTP/1.1 500");
                out.println("");
                out.flush();
            }finally {

            }
        }
    }
    private static void close(Closeable...closeables){
        if (closeables != null){
            for (Closeable closeable : closeables){
                try {
                    closeable.close();
                }catch (Exception e){
                    //不做处理
                }
            }
        }
    }
}


class Lock_119{
    public static void main(String[] args){
        Lock lock = new ReentrantLock();
        lock.lock();
        try {
        }finally {
            lock.unlock();
        }
    }

}


class Mutex implements Lock{
    //静态内部类，自定义同步器
    private static class Sync extends AbstractQueuedSynchronizer{
        //是否处于占用状态
        protected boolean isHeldExclusively(){
            return getState() == 1;
        }
        //当状态为0的时候获取锁
        public boolean tryAcquire(int acquires){
            if (compareAndSetState(0,1)){
                setExclusiveOwnerThread(Thread.currentThread());//设置当前拥有独占访问的线程。null 参数表示没有线程拥有访问。
                return true;
            }
            return false;
        }
        //释放锁，将状态设置为0
        protected boolean tryRelease(int releases){
            if (getState() == 0) throw new IllegalMonitorStateException();
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }
        //返回一个Condition，每个condition都包含了一个condition队列
        Condition newCondition(){return new ConditionObject();}
    }
    //仅需要将操作代理到Sync上即可
    private final Sync sync = new Sync();
    public void lock(){sync.acquire(1);}// 以独占模式获取对象，忽略中断。
    public boolean tryLock(){return sync.tryAcquire(1);}
    public void unlock(){sync.release(1);}
    public Condition newCondition(){return sync.newCondition();}
    public boolean isLocked(){return sync.isHeldExclusively();}
    public boolean hasQueuedThreads(){return sync.hasQueuedThreads();}
    public void lockInterruptibly() throws InterruptedException{
        sync.acquireInterruptibly(1);
    }
    public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException{
        return sync.tryAcquireNanos(1,unit.toNanos(timeout));
    }
}


class TwinsLock implements Lock{
    private static final class Sync extends AbstractQueuedSynchronizer{
        Sync(int count){
            if (count <= 0){
                throw new IllegalArgumentException("count must large than zero");
            }
            setState(count);
        }
        public int tryAcquireShared(int reduceCount){
            for (;;){
                int current = getState();
                int newCount = current - reduceCount;
                if (newCount < 0 || compareAndSetState(current, newCount)){
                    return newCount;
                }
            }
        }
        public boolean tryReleaseShared(int returnCount){
            for (;;){
                int current = getState();
                int newCount = current + returnCount;
                if (compareAndSetState(current, newCount))
                    return true;
            }
        }
        Condition newCondition(){ return new ConditionObject();}
        public int getTwinsLockState(){
            return getState();
        }
    }
    private final Sync sync = new Sync(2);
    public void lock(){
        sync.acquireShared(1);
    }
    public void unlock(){
        sync.tryReleaseShared(1);
    }
    public boolean tryLock(){
        if (sync.getTwinsLockState() > 0 && sync.tryAcquireShared(1) >= 0){
            return true;
        }
        return false;
    }
    public Condition newCondition(){return sync.newCondition();}
    public void lockInterruptibly() throws InterruptedException{
        sync.acquireInterruptibly(1);
    }
    public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException{
        return sync.tryAcquireNanos(1,unit.toNanos(timeout));
    }
}

class TwinLockTest{
//    public static void main(String[] args){
//        final Lock lock = new TwinsLock();
//        class Worker extends Thread{
//            public void run(){
//                while (true){
//                    lock.lock();
//                    try{
//                        SleepUtils.second(1);
//                        System.out.println(Thread.currentThread().getName());
//                        SleepUtils.second(1);
//                    }finally {
//                        lock.unlock();
////                        System.out.println(System.nanoTime());
//                    }
//                }
//            }
//        }
//        //启动10个线程
//        for (int i = 0; i < 10; i++){
//            Worker w = new Worker();
//            w.setDaemon(true);
//            w.start();
//        }
//        //每隔1秒换行
//        for (int i = 0; i < 10; i++){
//            SleepUtils.second(1);
//            System.out.println();
//        }
//    }

    public static void main(String[] args){
        final Lock lock = new TwinsLock();
        class Worker implements Runnable{
//            int k = 1;
            public void run(){
                try {
                    SleepUtils.second(1);
                }catch (Exception e){

                }
                while (true){
                    System.out.println(Thread.currentThread().getName() + "kkkkkk");
                    lock.lock();
                    try{
                        SleepUtils.second(1);
                        System.out.println(Thread.currentThread().getName());
                        SleepUtils.second(1);
//                        k++;
                    }finally {
                        lock.unlock();
//                        System.out.println(System.nanoTime());
                    }
                }
            }
        }
        //启动10个线程
        for (int i = 0; i < 10; i++){
            Thread thread = new Thread(new Worker(), "Thread: " + i);
            thread.setDaemon(true);
            thread.start();
            System.out.println("i: " + i);
            System.out.println(thread.getName() + ": " + "start()");
        }
        //每隔1秒换行
        for (int i = 0; i < 10; i++){
            SleepUtils.second(1);
            System.out.println();
        }
    }
}



class Cache{
    static Map<String, Object> map = new HashMap<String, Object>();
    static ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    static Lock r = rwLock.readLock();
    static Lock w = rwLock.writeLock();
    //获取一个key对应的value
    public static final Object get(String key){
        r.lock();
        try {
            return map.get(key);
        }finally {
            r.unlock();
        }
    }
    //设置key对应的value值
    public static final Object put(String key, Object value){
        w.lock();
        try {
            return map.put(key, value);
        }finally {
            w.unlock();
        }
    }
    //清空所有内容
    public static final void clear(){
        w.lock();
        try {
            map.clear();
        }finally {
            w.unlock();
        }
    }
}

class LockDec{
    static ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    static Lock readLock = rwLock.readLock();
    static Lock writeLock = rwLock.writeLock();
    public volatile boolean update = false;
    public void processData(){
        readLock.lock();
        if (!update){
            //必须先释放读锁
            readLock.unlock();
            //锁降级从写锁获取到开始
            writeLock.lock();
            try {
                if (!update){
                    //准备数据的流程
                    update = true;
                }
                readLock.lock();
            }finally {
                writeLock.unlock();
            }
            //锁降级完成，写锁降级为读锁
        }
        try {
            //使用数据的流程
        }finally {
            readLock.unlock();
        }
    }
}


class BoundedQueue<T>{
    private Object[] items;
    //添加的下标、删除的下标、数组当前数量
    private int addIndex,removeIndex,count;
    private Lock lock = new ReentrantLock();
    private Condition conditionProducer= lock.newCondition();
    private Condition conditionConsumer = lock.newCondition();
    public BoundedQueue(int size){
        items = new Object[size];
    }
    //添加一个元素，如果数组满，则添加线程进入等待状态，直到有“空位”
    public void add(T t) throws InterruptedException{
        lock.lock();
        try {
            while (count == items.length)
                conditionProducer.await();
            items[addIndex] = t;
            if (++addIndex == items.length)
                addIndex = 0;
            ++count;
            conditionConsumer.signal();
        }finally {
            lock.unlock();
        }
    }
    //由头部删除一个元素，如果数组为空，则删除线程进入等待状态，直到有新添加元素
    @SuppressWarnings("uncheck")//告诉编译器忽略 unchecked 警告信息，如使用List，ArrayList等未进行参数化产生的警告信息
                                //可以标注在类、字段、方法、参数、构造方法，以及局部变量上。
    public T remove() throws InterruptedException{
        lock.lock();
        try {
            while (count == 0)
                conditionConsumer.await();
            Object obj = items[removeIndex];
            if (++removeIndex == items.length)
                removeIndex = 0;
            --count;
            conditionProducer.signal();
            return (T)obj;
        }finally {
            lock.unlock();
        }
    }
}



class CountTask extends RecursiveTask<Integer>{//因为是有返回，所以继承RecursiveTask
    private static final int THRESHOLD = 10;//阈值
    private int start;
    private int end;
    public CountTask(int start, int end){
        this.start = start;
        this.end = end;
    }
    @Override
    protected Integer compute(){
        int sum = 0;
        //如果任务足够小就计算任务
        boolean canCompute = (end - start) <= THRESHOLD;
        if (canCompute){
            for (int i = start; i <= end; i++){
                sum += i;
            }
        }else {
            //如果任务大于阈值，就分裂成两个子任务计算
            int middle = (start + end) / 2;
            CountTask leftTask = new CountTask(start, middle);
            CountTask rightTask = new CountTask(middle + 1, end);
            //执行子任务
            leftTask.fork();
            rightTask.fork();
            //等待子任务执行完，并得到计算结果
            int leftResult = leftTask.join();
            int rightResult = rightTask.join();
            //合并子任务
            sum = leftResult + rightResult;
        }
        return sum;
    }
    public static void main(String[] args){
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        //生成一个计算任务，负责计算1+2+3+4
        CountTask task = new CountTask(1, 100);
        //执行子任务
        Future<Integer> result = forkJoinPool.submit(task);
        try {
            System.out.println(result.get());
        }catch (InterruptedException e){
        }catch (ExecutionException e){
        }
    }
}

class AtomicIntegerTest{
    static AtomicInteger ai = new AtomicInteger(1);
    public static void main(String[] args){
        System.out.println(ai.getAndIncrement());
        System.out.println(ai.get());
        System.out.println(ai.compareAndSet(2,1));
        System.out.println(ai.get());
        System.out.println(ai.addAndGet(1));

    }
}

class AtomicIntegerArrayTest{
    static int[] value = new int[]{1,2};
    static AtomicIntegerArray ai = new AtomicIntegerArray(value);
    public static void main(String[] args){
        ai.getAndSet(0,3);
        System.out.println(ai.get(0));
        System.out.println(value[0]);
    }
}


class AtomicReferenceTest{
    public static AtomicReference<User> atomicUserReference = new AtomicReference<User>();
    public static void main(String[] args){
        User user = new User("conan",19);
        atomicUserReference.set(user);
        User updateUser = new User("smeth",20);
        atomicUserReference.compareAndSet(user, updateUser);
        System.out.println(atomicUserReference.get().getName());
        System.out.println(atomicUserReference.get().getOld());
    }
    static class User{
        private String name;
        private int old;
        public User(String name, int old){
            this.name = name;
            this.old = old;
        }
        public String getName(){
            return name;
        }
        public int getOld(){
            return old;
        }
    }
}


class AtomicIntegerFieldUpdaterTest{
    //创建原子更新器，并设置需要更新的对象类和对象的属性
    public static AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(User.class ,"old");
    //    @SuppressWarnings("Uncheck")
    public static void main(String[] args){
        //设置tom年龄19岁
        User tom = new User("tom",19);
        //更新年龄，getAndIncrement返回旧年龄
        System.out.println(atomicIntegerFieldUpdater.getAndIncrement(tom));
        //输出现在年龄
        System.out.println(atomicIntegerFieldUpdater.get(tom));
    }
    static class User{
        private String name;
        public volatile int old;
        public User(String name, int old){
            this.name = name;
            this.old = old;
        }
        public String getName(){
            return name;
        }
        public int getOld(){
            return old;
        }
    }
}


class CountDownLatchTest{
    static CountDownLatch c = new CountDownLatch(2);
    public static void main(String[] args) throws InterruptedException{
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(1);
                c.countDown();
                System.out.println(2);
                c.countDown();//计数器减一
            }
        }).start();
        c.await();////只要计数器为0，那么线程就可以结束阻塞往下执行,进而保证线程同时开始
        System.out.println("3");
    }
}



class CyclicBarrierTest{
    static CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
    public static void main(String[] args){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    cyclicBarrier.await();
                }catch (Exception e){

                }
                System.out.println(1);
            }
        }).start();
        try {
            cyclicBarrier.await();
        }catch (Exception e){

        }
        System.out.println(2);
    }
}

class CyclicBarrierTest2{
    static CyclicBarrier cyclicBarrier = new CyclicBarrier(2,new A());
    public static void main(String[] args){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    cyclicBarrier.await();
                }catch (Exception e){

                }
                System.out.println(1);
            }
        }).start();
        try {
            cyclicBarrier.await();
        }catch (Exception e){

        }
        System.out.println(2);
    }
    static class A implements Runnable{
        @Override
        public void run(){
            System.out.println(3);
        }
    }
}


class SemaphoreTest{
    private static final int THREAD_COUNT = 30;
    private static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);
    private static Semaphore semaphore = new Semaphore(10);
    public static void main(String[] args){
        for (int i = 0; i < THREAD_COUNT; i++){
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        System.out.println(Thread.currentThread() + ":Save data");
                        semaphore.release();
                    }catch (InterruptedException e){

                    }
                }
            });
        }
        threadPool.shutdown();
    }
}


class ExchangerTest{
    private static final Exchanger<String> exgr = new Exchanger<String>();
    private static ExecutorService threadPool = Executors.newFixedThreadPool(2);
    public static void main(String[] args){
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String A = "银行流水A"; //A录入银行流水数据
                    String c = exgr.exchange(A);
                }catch (InterruptedException e){

                }
            }
        });
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String B = "银行流水B";
                    String A = exgr.exchange(B);
                    System.out.println("A和B数据是否一致：" + A.equals(B) + ", A录入的是：" + A + "，B录入的是：" + B);
                }catch (InterruptedException e){

                }
            }
        });
        threadPool.shutdown();
    }
}






class FutureTaskForMultiCompute {

    public static void main(String[] args) {

        FutureTaskForMultiCompute inst=new FutureTaskForMultiCompute();
        // 创建任务集合
        List<FutureTask<Integer>> taskList = new ArrayList<FutureTask<Integer>>();
        // 创建线程池
        ExecutorService exec = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            // 传入Callable对象创建FutureTask对象
            FutureTask<Integer> ft = new FutureTask<Integer>(inst.new ComputeTask(i, ""+i));
            taskList.add(ft);
            // 提交给线程池执行任务，也可以通过exec.invokeAll(taskList)一次性提交所有任务;
            exec.submit(ft);
        }

        System.out.println(taskList.size());
        System.out.println("所有计算任务提交完毕, 主线程接着干其他事情！");

        // 开始统计各计算线程计算结果
        Integer totalResult = 0;
        for (FutureTask<Integer> ft : taskList) {
            try {
                //FutureTask的get方法会自动阻塞,直到获取计算结果为止
                totalResult = totalResult + ft.get();
                System.out.println("totalResult: " + totalResult);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        // 关闭线程池
        exec.shutdown();
        System.out.println("多任务计算后的总结果是:" + totalResult);

    }

    private class ComputeTask implements Callable<Integer> {

        private Integer result = 0;
        private String taskName = "";

        public ComputeTask(Integer iniResult, String taskName){
            result = iniResult;
            this.taskName = taskName;
            System.out.println("生成子线程计算任务: "+taskName);
        }

        public String getTaskName(){
            return this.taskName;
        }

        @Override
        public Integer call() throws Exception {
            // TODO Auto-generated method stub

            for (int i = 1; i <= 100; i++) {
                result += i;
            }
            // 休眠5秒钟，观察主线程行为，预期的结果是主线程会继续执行，到要取得FutureTask的结果是等待直至完成。
            Thread.sleep(5000);
            System.out.println("子线程计算任务: "+taskName+" 执行完成! " + result);
            return result;
        }
    }
}


//class KeywordEntity implements Serializable{
//    private static final long serialVersionUID = 1269373329410167403l;
//    private String name;
//    private Long id;
//
//    public KeywordEntity(String name, Long id) {
//        this.name = name;
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//}


//class fileTest{
//    public static void main(String[] args){
//        String filePath = "F:/keywordEntity.dat";
//        try {
//            FileInputStream fis = new FileInputStream(filePath);
//            ObjectInputStream ois = new ObjectInputStream(fis);
//            Object object = ois.readObject();
////            System.out.println(object);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//}






class LPA {

    public static float sigma = 1;
    public static int tag_num = 2;

    public static void main(String[] args) {

        float[][] data = {
                {1,1},
                {1,2},
                {2,1},
                {2,2},
                {4,4},
                {6,6},
                {6,7},
                {7,6},
                {7,7}
        };

        Map<Integer, Integer> tag_map = new HashMap<Integer, Integer>();
        tag_map.put(1, 1);
        tag_map.put(6, 0);

        float[][] weight = new float[data.length][data.length];

        for(int i = 0; i < weight.length; i++) {
            float sum = 0f;
            for(int j = 0; j < weight[i].length; j++) {
                weight[i][j] = (float) Math.exp( - distance(data[i], data[j]) / Math.pow(sigma, 2));
                sum += weight[i][j];
            }
            for(int j = 0; j < weight[i].length; j++) {
                weight[i][j] /= sum;
            }
        }

        System.out.println("weight ：=============");
        for(int i = 0; i < weight.length; i++) {
            System.out.println(Arrays.toString(weight[i]));
        }
        System.out.println("weight over： =============");

        float[][] tag_matrix = new float[data.length][tag_num];
        for(int i = 0; i < tag_matrix.length; i++) {
            if(tag_map.get(i) != null) {
                tag_matrix[i][tag_map.get(i)] = 1;
            } else {
                float sum = 0;
                for(int j = 0; j < tag_matrix[i].length; j++) {
                    tag_matrix[i][j] = (float) Math.random();
                    sum += tag_matrix[i][j];
                }
                for(int j = 0; j < tag_matrix[i].length; j++) {
                    tag_matrix[i][j] /= sum;
                }
            }
        }

        for(int it = 0; it < 100; it++) {
            for(int i = 0; i < tag_matrix.length; i++) {
                if(tag_map.get(i) != null) {
                    continue;
                }
                float all_sum = 0;
                for(int j = 0; j < tag_matrix[i].length; j++) {
                    float sum = 0;
                    for(int k = 0; k < weight.length; k++) {
                        sum += weight[i][k] * tag_matrix[k][j];
                    }
                    tag_matrix[i][j] = sum;
                    all_sum += sum;
                }
                for(int j = 0; j < tag_matrix[i].length; j++) {
                    tag_matrix[i][j] /= all_sum;
                }
            }
            System.out.println(it + "  tag start：=============");
            for(int i = 0; i < tag_matrix.length; i++) {
                System.out.println(Arrays.toString(tag_matrix[i]));
            }
            System.out.println(it + "   tag over ：=============");
        }
    }

    public static float distance(float[] a, float[] b) {

        float dis = 0;
        for(int i = 0; i < a.length; i++) {
            dis = (float) Math.pow(b[i] - a[i], 2);
        }
        return dis;
    }
}



class TestSomeArray{
    public static void main(String[] args){
        int num = 57;
        char c = '0';
        char s = (char)(num + '0');
        System.out.println("dfgs");
        System.out.println(s);
        System.out.println(c);
//        int[][] array = {{1,1,1,1,1,1,1,1},{1,3,4,5,6,7,8,9}};
//        System.out.println(Arrays.toString(array[1]));
//        boolean[][] hasVisit = new boolean[3][3];
//        System.out.println(hasVisit[0][0]);
    }
}


class KeywordEntity implements Serializable{
    private static final long serialVersionUID = 1269373329410167403l;
    private String name;
    private Long id;

    public KeywordEntity(String name, Long id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}


class WriteKeyword{
    public static void main(String[] args) throws Exception{
        FileInputStream fKeyword;
//        ByteArrayInputStream fKeyword;
        HashMap<String, KeywordEntity> keywords;
        keywords = new HashMap<String, KeywordEntity>();
        fKeyword = new FileInputStream("F:/keywordEntity.dat");
//        fKeyword = new ByteArrayInputStream("F:/keywordEntity.dat");
        int num = fKeyword.available();
        System.out.println(num);
        byte[]ctext=new byte[num];
        fKeyword.read(ctext);
        try {
            ObjectInputStream keywordInputStream = new ObjectInputStream(fKeyword);
            System.out.println("kkkkk");
            keywords = (HashMap<String, KeywordEntity>) keywordInputStream.readObject();
            System.out.println(keywords.size());
        }catch (Exception ex){
            System.out.println("异常： " + ex);
            ex.printStackTrace();
            fKeyword.close();
        }
    }
}


class TestSplit{
    public static void main(String[] args){
        String file = "E:\\PycharmCode\\Test\\paper.dat";
        try {
            List<String> list = new ArrayList<String>();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line = bufferedReader.readLine();
            System.out.println(line);
            String[] strs = line.trim().split(",");
            for (String str : strs)
                list.add(str);
            System.out.println(list);
            System.out.println(list.get(1));
            bufferedReader.close();
        }catch (IOException e){
            System.out.println(e);
        }
    }
}

class GolbalVer{
    private int index = 0;
    public void increase(){
        ++index;
    }
    public void print(){
        System.out.println("index = " + index);
    }
}

class TestGlobalVer{
    public static void main(String[] args){
        GolbalVer golbalVer = new GolbalVer();
        golbalVer.print();
        golbalVer.increase();
        golbalVer.print();
        golbalVer.increase();
        golbalVer.print();
        golbalVer.increase();
        golbalVer.print();
        golbalVer.increase();
        golbalVer.print();
    }
}


class ATest{
    ATest(){}
    ATest(int a){}
}
class BTest extends ATest{
//    BTest(){
//        super(7);
//    }
    BTest(int a){
    }
    public static void main(String[] args){
        StringBuilder stringBuilder = new StringBuilder("ajfjfpajf");
        String string = stringBuilder.toString();
        char[]chs = string.toCharArray();
        System.out.println(string.toCharArray());
        int[] arr = {1,2};
        System.out.println(arr);

    }
}