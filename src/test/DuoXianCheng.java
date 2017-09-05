package test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.currentThread;

/**
 * Created by XCY on 2017/9/2.
 *  多线程：
 *          多线程一个特性：随机性
 *          进程：是一个执行中的程序，每一个进程执行都有一个执行顺序，该顺序是一个执行路径，或者叫一个控制单元
 *          线程：进程中的一个独立的控制单元，线程控制这进程的执行
 *          一个进程中至少有一个线程
 *          Java虚拟机启动时会有一个进行叫java.exe
 *          该进程中至少有一个线程负责Java程序的执行，而且该线程的代码存在main方法中，该线程称之为主线程
 *
 *
 *          1：如何在自定义代码中，自定义一个线程
 *              通过对api查找，Java已经提供了对线程这类事物的描述，Thread类
 *              （1）创建线程的一第种方法：继承Thread类
 *                  步骤：1：定义类继承Thread
 *                        2：复写Thread的run方法
 *                              目的：将自定义的代码存储在run方法中，让线程运行
 *                        3：调用该线程的start方法
 *                              该方法两个作用：1：启动线程，
 *                                              2：调用run方法
 *               （2）创建线程的第二种方式：实现Runnable接口
 *                   步骤：
 *                      1：定义类实现Runnable接口
 *                      2：覆盖Runnable接口中的run方法
 *                              将程序要运行的代码存放在该run方法中
 *                      3：通过Thread类建立线程对象
 *                      4：将Runnable接口的子类对象作为实际参数传递给Thread类的构造函数
 *                              为什么要将Runnable接口的子类对象传递给Thread的构造函数
 *                                  因为，自定义的run方法所属的对象是Runnable接口的子类对象，
 *                                  所以要让线程去执行指定对象的run方法，就必须明确该run方法所属的对象
 *                      5：调用Thread类的start方法开启线程并调用Runnable接口子类的run方法
 *
 *                  实现方式和继承方式有什么区别呢？
 *                          实现方式好处：避免了单继承的局限性
 *                          继承Thread：线程代码存放在Thread子类的run方法中
 *                          实现Runnable：线程代码存放在Runnable接口子类的run方法中
 *
 *                  在定义线程时，建议使用实现方式
 *
 *              class Demo extends Thread
 *              {
 *                  public void run()
 *                  {
 *                      System.out.println("run");
 *                  }
 *              }
 *           为什么要覆盖run方法？
 *                  Thread用于描述线程，该类就定义了一个功能，用于存储线程要运行的代码，该存储功能就是run方法，
 *                  也就是说Thread类中的run方法用于存储线程要运行带代码
 *
 *            创建线程----（start（））--->运行----（sleep(time),wait()）---->冻结------（sleep时间到，notify()）--->运行(有可能到阻塞状态)
 *
 *            ----（stop(),run结束）---->消亡
 *
 *            static Thread.currentThread() ：获取dan当前线程对象。
 *            getName() ：获取线程名称
 *            设置线程名称：setName()  或 构造函数
 *
 *        多线程的运行出现了安全问题
 *              问题原因：当多条语句在操作同一个线程共享数据时，一个线程对多条语句只执行了一部分，还没有执行完，
 *              另一个线程参与进来执行，导致共享数据的错误
 *              解决办法：对多条操作共享数据的语句，只能让一个线程执行完。在执行过程中，其他线程不可以参与执行
 *
 *              Java对于多线程的安全问题提供了专业的解决方案，就是同步代码块
 *              synchronized（对象）
 *              {
 *               需要被同步的代码
 *               }
 *               对象如同锁，持有锁的的线程可以在同步中执行
 *               没有锁的线程即使获得cpu的执行权也进不去，因为没有获取锁
 *
 *               同步前提：
 *                      1：必须拥有两个或两个以上的线程
 *                      2：必须时多个线程使用同一个锁
 *
 *                必须保证同步中只能有一个线程在运行
 *
 *                同步好处：解决多线程安全问题
 *                同步弊端：多个线程都需要判断锁，较为消耗资源
 *
 *                如何找问题：
 *                      1：明确哪些代码是多线程运行代码
 *                      2：明确共享数据
 *                      3：明确多线程运行代码中哪些语句是操作共享数据
 *
 *                 同步函数用的哪一个锁？
 *                      函数需要被对象调用，那么函数都有一个所属对象的索引。就是this，所以同步函数使用的锁是this
 *
 *                  如果同步函数被静态修饰后，使用的锁是什么？
 *                          通过验证发现不是this，因为静态方法中不可以定义this
 *
 *                   静态进内存时，内存中没有本类的对象，
 *                      但是一定有该类对应的字节码文件对象   类名.class       ,该对象的类型是Class
 *
 *                   静态的同步方法使用的同步锁是该方法的字节码文件对象  类名.class
 *
 *                   面试一般面懒汉式
 *                         有什么不同？懒汉式特点，实例的延时加载
 *                         懒汉式延时加载有什么问题？如果多线程访问会出现安全问题，加同步解决，用同步函数或同步代码块可以解决，稍微低效，
 *                         用双重判断可以稍微优化，加同步使用的锁是该类所属的字节码文件对象 类名.class
 *
 *                    死锁：同步中嵌套同步
 *
 *        线程间通讯：
 *                  其实就是多个线程在操作同一资源，但是操作的动作不同
 *                  wait(),notify(),notifyAll()都使用在同步中，
 *                  因为要对持有监视器（锁）的线程操作。所以要使用在同步中，因为只有同步才具有锁
 *
 *              为什么这些操作线程的方法要定义在Object类中呢？
 *                  因为这些方法在操作同步中线程时，都必须要标识它们所操作线程持有的锁，
 *                  只有同一个锁上的等待线程，可以被同一个锁上的notify唤醒，不可以对不同锁中的线程进行唤醒
 *                  也就是说，等待和唤醒必须是同一个锁，而锁可以是任意对象，而可以被任意对象调用的方法定义在Object类中
 *
 *                  jdk1.5提供了多线程的升级解决方案。将同步synchronized替换成lock操作，
 *                  将Object中的wait，notify, notifyAll替换成了Condition对象，该对象可以通过Lock锁对象进行获取
 *
 *                 stop已经过时，
 *                    如何停止线程？
 *                    只有一种方法，run方法结束，开启多线程运行，运行代码结构通常是循环结构，
 *                    只要控制循环，就可以让run方法结束，也就是线程结束
 *
 *                    特殊情况：当线程处于冻结状态，就不会读取到标记，那么线程就不会结束
 *                    当没有指定的方式让冻结的线程回复到运行状态时，这时需要对线程进行清楚
 *                    强制让线程恢复到运行状态中来，这样就可以操作标记让线程结束，
 *                    Thread类中提供了该方法，   interrupt方法
 *
 *             守护线程：
 *                      //设置为守护线程，设置在start之前，主线程结束，守护线程自动结束
 *                      //当正在运行的线程都是守护线程时，Java虚拟机自动退出
 *
 *              join：当A线程执行到了B线程的.join方法时，A就会等待，等B线程执行完，A才会执行
 *              join可以用来临时加入线程执行
 *
 *
 *
 *
 *
 *
 */
/*
所有线程默认优先级5
t1.setPriority(Thread.MAX_PRIORITY);//将t1设为最高优先级，一共10级
 */
class Demo11 implements Runnable{
    public void run(){
        for (int i = 0; i < 70; i++){
            System.out.println(Thread.currentThread().toString() + "....." + i);
            Thread.yield();//稍微延缓线程执行的频率，相当于使当前线程释放cpu资源
        }
    }
}
class TestT15{
    public static void main(String[] args) throws InterruptedException{
        Demo11 d = new Demo11();
        Thread t1 = new Thread(d);
        Thread t2 = new Thread(d);
        t1.start();
        t1.setPriority(Thread.MAX_PRIORITY);//将t1设为最高优先级
        t2.start();

        for (int i = 0; i < 70; i++){
            System.out.println(Thread.currentThread().getName() + "....." + i);
        }
        System.out.println("over");
    }
}

/*
join：当A线程执行到了B线程的.join方法时，A就会等待，等B线程执行完，A才会执行
        join可以用来临时加入线程执行
 */

class Demo10 implements Runnable{
    public void run(){
        for (int i = 0; i < 70; i++){
            System.out.println(Thread.currentThread().getName() + "....." + i);
        }
    }
}
class TestT14{
    public static void main(String[] args) throws InterruptedException{
       Demo10 d = new Demo10();
       Thread t1 = new Thread(d);
       Thread t2 = new Thread(d);
       t1.start();
       t1.join();//这时主线程处于冻结状态，当t1结束后主函数活过来
       t2.start();
//       t1.join();//加入放这里，主线程冻结，t1和t2抢夺CPU资源
        for (int i = 0; i < 70; i++){
            System.out.println(Thread.currentThread().getName() + "....." + i);
        }
        System.out.println("over");
    }
}

/*
解决下面代码问题
stop已经过时，
如何停止线程？
只有一种方法，run方法结束，开启多线程运行，运行代码结构通常是循环结构，
只要控制循环，就可以让run方法结束，也就是线程结束

特殊情况：当线程处于冻结状态，就不会读取到标记，那么线程就不会结束
当没有指定的方式让冻结的线程回复到运行状态时，这时需要对线程进行清楚
强制让线程恢复到运行状态中来，这样就可以操作标记让线程结束，
Thread类中提供了该方法，   interrupt方法
 */

class StopThread1 implements Runnable{
    private boolean flag = true;
    public void changeFlag(){
        flag = false;
    }
    public synchronized void run(){
        while (flag){
            try {
                wait();
            }catch (InterruptedException e){
                System.out.println(Thread.currentThread().getName() + "Exception");
                flag = false;
            }
            System.out.println(Thread.currentThread().getName() + "...run");
        }
    }
}
class TestT13{
    public static void main(String[] args){
        StopThread1 st = new StopThread1();
        Thread t1 = new Thread(st);
        Thread t2 = new Thread(st);
//        t1.setDaemon(true);//设置为守护线程，设置在start之前，主线程结束，守护线程自动结束
//        t2.setDaemon(true);//当正在运行的线程都是守护线程时，Java虚拟机自动退出
        t1.start();
        t2.start();
        int num = 0;
        while (true){
            if (num++ == 60){
//                st.changeFlag();//该方法如果没有wait语句可以使run结束进而使线程结束，一旦有wait且线程进入冻结，则不能结束线程
                t1.interrupt();//中断线程，让线程强制进入运行状态
                t2.interrupt();
                break;
            }
            System.out.println(Thread.currentThread().getName() + "...." + num);
        }
        System.out.println("over!");
    }
}

/*
stop已经过时，
如何停止线程？
只有一种方法，run方法结束，开启多线程运行，运行代码结构通常是循环结构，
只要控制循环，就可以让run方法结束，也就是线程结束

特殊情况：当线程处于冻结状态，就不会读取到标记，那么线程就不会结束
 */
class StopThread implements Runnable{
    private boolean flag = true;
    public void changeFlag(){
        flag = false;
    }
    public void run(){
        while (flag){
            System.out.println(Thread.currentThread().getName() + "...run");
        }
    }
}
class TestT12{
    public static void main(String[] args){
        StopThread st = new StopThread();
        Thread t1 = new Thread(st);
        Thread t2 = new Thread(st);
        t1.start();
        t2.start();
        int num = 0;
        while (true){
            if (num++ == 60){
//                st.changeFlag();//该方法如果没有wait语句可以使run结束进而使线程结束，一旦有wait且线程进入冻结，则不能结束线程
                t1.interrupt();//中断线程，让线程强制进入运行状态
                t2.interrupt();
                break;
            }
            System.out.println(Thread.currentThread().getName() + "...." + num);
        }
    }
}


/*
再次改写下面代码：lock这个新方法可以有多个锁，从而不用唤醒所有线程，进而只唤醒对方
jdk1.5提供了多线程的升级解决方案。将同步synchronized替换成lock操作，
将Object中的wait，notify, notifyAll替换成了Condition对象，该对象可以通过Lock锁对象进行获取
在该示例中实现了本方只换醒对方
 */

class Resource3{
    private String name;
    private int count;
    private boolean flag = false;
    private Lock lock = new ReentrantLock();
    private Condition condition_pro = lock.newCondition();//生产者锁
    private Condition condition_con = lock.newCondition();//消费者锁
    public  void set(String name) throws InterruptedException{
        lock.lock();
        try{
            while (flag)
                condition_pro.await();//抛出异常，直接在该函数抛出，生产者等待
            this.name = name + "---" + count++;
            System.out.println(Thread.currentThread().getName() + "---生产者---" + this.name);
            flag = true;
            condition_con.signal();//唤醒消费者
        }finally {
            lock.unlock();//释放所得操作一定要执行
        }
    }
    public void out() throws InterruptedException{
        lock.lock();
        try{
            while (!flag)
                condition_con.await();//消费者等待
            System.out.println(Thread.currentThread().getName() + "...消费者..." + this.name);
            flag = false;
            condition_pro.signal();//唤醒生产者
        }finally {
            lock.unlock();
        }
    }
}

/*
用新方法改写下面代码
jdk1.5提供了多线程的升级解决方案。将同步synchronized替换成lock操作，
将Object中的wait，notify, notifyAll替换成了Condition对象，该对象可以通过Lock锁对象进行获取
在该示例中实现了本方只换醒对方
 */
class Resource2{
    private String name;
    private int count;
    private boolean flag = false;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    public  void set(String name) throws InterruptedException{
        lock.lock();
        try{
            while (flag)
                condition.await();//抛出异常，直接在该函数抛出
            this.name = name + "---" + count++;
            System.out.println(Thread.currentThread().getName() + "---生产者---" + this.name);
            flag = true;
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }
    public void out() throws InterruptedException{
        lock.lock();
        try{
            while (!flag)
                condition.await();
            System.out.println(Thread.currentThread().getName() + "...消费者..." + this.name);
            flag = false;
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }
}
class Producer2 implements Runnable{
    private Resource2 r;
    Producer2(Resource2 r){
        this.r = r;
    }
    public void run(){
        while (true){
            try{
                r.set("+商品+");
            }catch (InterruptedException e){}

        }
    }
}
class Consumer2 implements Runnable{
    private Resource2 r;
    Consumer2(Resource2 r){
        this.r = r;
    }
    public void run(){
        while (true){
            try{
                r.out();
            }catch (InterruptedException e){}
        }
    }
}
class TestT11{
    public static void main(String[] args){
        Resource2 r = new Resource2();
        Producer2 pro = new Producer2(r);
        Consumer2 con = new Consumer2(r);
        Thread t1 = new Thread(pro);
        Thread t2 = new Thread(pro);
        Thread t3 = new Thread(con);
        Thread t4 = new Thread(pro);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
//        new Thread(new Producer(r)).start();
//        new Thread(new Consumer(r)).start();

    }
}

/*
解决下面的问题
解决多生产者-消费者问题
notifyAll（）唤醒所有线程
 */
class Resource1{
    private String name;
    private int count;
    private boolean flag = false;
    public synchronized void set(String name){
        while (flag)
            try {
                this.wait();
            }catch (Exception e){}
        this.name = name + "---" + count++;
        System.out.println(Thread.currentThread().getName() + "---生产者---" + this.name);
        flag = true;
        this.notifyAll();
    }
    public synchronized void out(){
        while (!flag)
            try {
                this.wait();
            }catch (Exception e){}
        System.out.println(Thread.currentThread().getName() + "...消费者..." + this.name);
        flag = false;
        this.notifyAll();
    }
}
class Producer1 implements Runnable{
    private Resource1 r;
    Producer1(Resource1 r){
        this.r = r;
    }
    public void run(){
        while (true){
            r.set("+商品+");
        }
    }
}
class Consumer1 implements Runnable{
    private Resource1 r;
    Consumer1(Resource1 r){
        this.r = r;
    }
    public void run(){
        while (true){
            r.out();
        }
    }
}
class TestT10{
    public static void main(String[] args){
        Resource1 r = new Resource1();
        Producer1 pro = new Producer1(r);
        Consumer1 con = new Consumer1(r);
        Thread t1 = new Thread(pro);
        Thread t2 = new Thread(pro);
        Thread t3 = new Thread(con);
        Thread t4 = new Thread(pro);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
//        new Thread(new Producer(r)).start();
//        new Thread(new Consumer(r)).start();

    }
}


/*
生产者---消费者
只适用与单生产者-消费者问题
多个线程可能出现问题，有可能生产一次消费两次，也可能生产2次消费一次，
因为notify只是唤醒等待线程池中的第一个线程，所以可能生产者1唤醒生产者2进而使生产者1的生产结果被覆盖
 */
class Resource{
    private String name;
    private int count;
    private boolean flag = false;
    public synchronized void set(String name){
        if (flag)
            try {
                this.wait();
            }catch (Exception e){}
        this.name = name + "---" + count++;
        System.out.println(Thread.currentThread().getName() + "---生产者---" + this.name);
        flag = true;
        this.notify();
    }
    public synchronized void out(){
        if (!flag)
            try {
                this.wait();
            }catch (Exception e){}
        System.out.println(Thread.currentThread().getName() + "---消费者---" + this.name);
        flag = false;
        this.notify();
    }
}
class Producer implements Runnable{
    private Resource r;
    Producer(Resource r){
        this.r = r;
    }
    public void run(){
        while (true){
            r.set("+商品+");
        }
    }
}
class Consumer implements Runnable{
    private Resource r;
    Consumer(Resource r){
        this.r = r;
    }
    public void run(){
        while (true){
            r.out();
        }
    }
}

class TestT9{
    public static void main(String[] args){
        Resource r = new Resource();
        Producer pro = new Producer(r);
        Consumer con = new Consumer(r);
        Thread t1 = new Thread(pro);
        Thread t2 = new Thread(pro);
        Thread t3 = new Thread(con);
        Thread t4 = new Thread(con);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
//        new Thread(new Producer(r)).start();
//        new Thread(new Consumer(r)).start();

    }
}


/*
代码优化
 */
class Res2{
    private String name;
    private String sex;
    private boolean flag = false;
    public synchronized void set(String name, String sex){
        if (flag)
            try {
                this.wait();
            }catch (Exception e){}
        this.name = name;
        this.sex = sex;
        flag = true;
        this.notify();
    }
    public synchronized void out(){
        if (!flag)
            try {
                this.wait();
            }catch (Exception e){}
        System.out.println(name + "...." + sex);
        flag = false;
        this.notify();
    }
}
class Input2 implements Runnable{
    //    Res r = new Res();
    private Res2 r;
    //    Object obj = new Object();//用这个锁input和output操作的不是同一个锁，还是会出现问题
    Input2(Res2 r){
        this.r = r;
    }
    public void run(){
        int x = 0;
        while (true){
                if (x == 0){
                    r.set("mike", "nan");
                }else {
                    r.set("丽丽", "女");
                }
                x = (x + 1) % 2;
            }
        }
    }

class Output2 implements Runnable{
    private Res2 r;
    Output2(Res2 r){
        this.r = r;
    }
    public void run(){
        while (true){
            r.out();
        }
    }
}
class TestT8{
    public static void main(String[] args){
        Res2 r = new Res2();
        new Thread(new Input2(r)).start();
        new Thread(new Output2(r)).start();

    }
}



/*
解决下面程序Output线程可能打印一个值多遍的问题
存一个打印一个
wait(),notify(),notifyAll()都使用在同步中，
因为要对持有监视器（锁）的线程操作。所以要使用在同步中，因为只有同步才具有锁
 */
class Res1{
    String name;
    String sex;
    boolean flag = false;
}
class Input1 implements Runnable{
    //    Res r = new Res();
    private Res1 r;
    //    Object obj = new Object();//用这个锁input和output操作的不是同一个锁，还是会出现问题
    Input1(Res1 r){
        this.r = r;
    }
    public void run(){
        int x = 0;
//        boolean flag = true;//也可以用布尔变量来控制交叉存储
        while (true){
            synchronized (r){//用这个锁input和output操作的不是同一个锁，还是会出现问题，可以用r，Input.class Output.class解决问题，因为唯一
                if (r.flag)
                    try {
                        r.wait();
                    }catch (Exception e){}

                if (x == 0){
                    r.name = "mike";
                    r.sex = "man";
                }else {
                    r.name = "丽丽";
                    r.sex = "女女女女女";
                }
                x = (x + 1) % 2;
                r.flag = true;
                r.notify();//通常唤醒线程池里第一个等待的线程
            }
        }
    }
}
class Output1 implements Runnable{
    //    Res r = new Res();
    private Res1 r;
    //    Object obj = new Object();
    Output1(Res1 r){
        this.r = r;
    }
    public void run(){
        while (true){
            synchronized (r){//用这个锁input和output操作的不是同一个锁，还是会出现问题.可以用r，Input.class Output.class解决问题，因为唯一
                if (!r.flag)
                    try {
                        r.wait();//wait必须用锁调用
                    }catch (Exception e){}
                System.out.println(r.name + " " + r.sex);
                r.flag = false;
                r.notify();
            }

        }
    }
}

/*
线程间通讯
因为每个线程执行时间不定，所以Output线程可能打印一个值多遍
 */
class Res{
    String name;
    String sex;
}
class Input implements Runnable{
//    Res r = new Res();
    private Res r;
//    Object obj = new Object();//用这个锁input和output操作的不是同一个锁，还是会出现问题
    Input(Res r){
        this.r = r;
    }
    public void run(){
        int x = 0;
//        boolean flag = true;//也可以用布尔变量来控制交叉存储
        while (true){
            synchronized (r){//用这个锁input和output操作的不是同一个锁，还是会出现问题，可以用r，Input.class Output.class解决问题，因为唯一
                if (x == 0){
                    r.name = "mike";
                    r.sex = "man";
                }else {
                    r.name = "丽丽";
                    r.sex = "女女女女女";
                }
            }
            x = (x + 1) % 2;
        }
    }
}
class Output implements Runnable{
//    Res r = new Res();
    private Res r;
//    Object obj = new Object();
    Output(Res r){
        this.r = r;
    }
    public void run(){
        while (true){
            synchronized (r){//用这个锁input和output操作的不是同一个锁，还是会出现问题.可以用r，Input.class Output.class解决问题，因为唯一
                System.out.println(r.name + " " + r.sex);
            }

        }
    }
}
class TestT7{
    public static void main(String[] args){
        Res r = new Res();
        Input in = new Input(r);
        Output out = new Output(r);
        Thread t1 = new Thread(in);
        Thread t2 = new Thread(out);
        t1.start();
        t2.start();
    }
}




/*
死锁：同步中嵌套同步
*/
class MyLock{
    static Object lock1 = new Object();
    static Object lock2 = new Object();
}
class LockTest implements Runnable{
    private boolean flag;
    LockTest(boolean flag){
        this.flag = flag;
    }
    public void run(){
        if (flag){
            synchronized (MyLock.lock1){
                System.out.println("if lock1");
                synchronized (MyLock.lock2){
                    System.out.println("if lock2");
                }
            }
        }else {
            synchronized (MyLock.lock2){
                System.out.println("else lock2");
                synchronized (MyLock.lock1){
                    System.out.println("else lock1");
                }
            }
        }
    }
}

class TestT6{
    public static void main(String[] args){
        Thread t1 = new Thread(new LockTest(true));
        Thread t2 = new Thread(new LockTest(false));
        t1.start();
//        try {//添加该块是想让t1等待一会，开启t2
//            Thread.sleep(10);
//        }catch (Exception e){}
//        t.flag = false;
        t2.start();
    }
}

//和上面一样出现死锁
class Ticket5 implements Runnable
{
    private int ticket = 100;
    boolean flag = true;
    Object obj = new Object();
    public void run(){
        if (flag){
            while (true){
                synchronized (obj){
                    show();//这里需要this锁
                }
            }
        }else {
            while (true){
                show();
            }
        }
    }
    public synchronized void show(){//锁this
        synchronized (obj){//这里需要obj锁
            if (ticket > 0){
                try{
                    Thread.sleep(10);//该方法会抛出异常，只能处理
                    //覆盖父类函数，如果父类函数没有抛出异常，则子类的相应函数不能抛出异常，只能try处理
                }catch (Exception e){}
                System.out.println(currentThread().getName()+"::sale::" + ticket--);
            }
        }
    }
}



/*
单例设计模式
一般使用饿汉式

面试一般面懒汉式
有什么不同？懒汉式特点，实例的延时加载
懒汉式延时加载有什么问题？如果多线程访问会出现安全问题，加同步解决，用同步函数或同步代码块可以解决，稍微低效，
用双重判断可以稍微优化，加同步使用的锁是该类所属的字节码文件对象 类名.class
*/
//饿汉式
class Single{
    private static final Single s = new Single();//final可以不写
    private Single(){}//不让外部构造对象
    public static Single getInstance(){
        return s;
    }
}

//懒汉式
class Single1{
    private static Single1 s = null;
    private Single1(){}
    public static Single1 getInstance(){//解决共享数据s的问题,这样 加同步比下面方法好,减少判断锁访问的次数
        if (s == null){
            synchronized (Single1.class){
                if (s == null)
                    s = new Single1();
            }
        }
        return s;
    }
//    public static synchronized Single1 getInstance(){//解决共享数据s的问题,这样 加同步后低效
//        if (s == null)
//            s = new Single1();
//        return s;
//    }
}





/*
静态进内存时，内存中没有本类的对象，但是一定有该类对应的字节码文件对象   类名.class       ,该对象的类型是Class
静态的同步方法使用的同步锁是该方法的字节码文件对象  类名.class
 */
class Ticket4 implements Runnable
{
    private static int ticket = 100;
    boolean flag = true;
    public void run(){
        if (flag){
            while (true){
                synchronized (Ticket4.class){
                    //如果该锁用obj可能会出现问题，改为this后没问题，出现问题,如果使用obj则同步用的不是同一个锁，
                    // 不满足同步前提，所以会出现错误情况
                    if (ticket > 0){
                        try{
                            Thread.sleep(10);//该方法会抛出异常，只能处理
                            //覆盖父类函数，如果父类函数没有抛出异常，则子类的相应函数不能抛出异常，只能try处理
                        }catch (Exception e){}
                        System.out.println(currentThread().getName()+"::sale::" + ticket--);
                    }
                }
            }
        }else {
            while (true){
                show();
            }
        }
    }
    public static synchronized void show(){//锁  类名.class
        if (ticket > 0)
            try{
                Thread.sleep(10);//该方法会抛出异常，只能处理
                //覆盖父类函数，如果父类函数没有抛出异常，则子类的相应函数不能抛出异常，只能try处理
            }catch (Exception e){}
        System.out.println(currentThread().getName()+"::sale::" + ticket--);
    }
}




/*
 同步前提：
         1：必须拥有两个或两个以上的线程
         2：必须时多个线程使用同一个锁

  验证同步函数锁为this
    使用两个线程买票，一个线程在同步代码块，一个线程在同步函数中，都执行买票动作
 */
class Ticket3 implements Runnable
{
    private int ticket = 100;
    boolean flag = true;
    Object obj = new Object();
    public void run(){
        if (flag){
            while (true){
                synchronized (this){
                    //如果该锁用obj可能会出现问题，改为this后没问题，出现问题,如果使用obj则同步用的不是同一个锁，
                    // 不满足同步前提，所以会出现错误情况
                    if (ticket > 0){
                        try{
                            Thread.sleep(10);//该方法会抛出异常，只能处理
                            //覆盖父类函数，如果父类函数没有抛出异常，则子类的相应函数不能抛出异常，只能try处理
                        }catch (Exception e){}
                        System.out.println(currentThread().getName()+"::sale::" + ticket--);
                    }
                }
            }
        }else {
            while (true){
                show();
            }
        }
    }
    public synchronized void show(){//锁this
        if (ticket > 0)
            try{
                Thread.sleep(10);//该方法会抛出异常，只能处理
                //覆盖父类函数，如果父类函数没有抛出异常，则子类的相应函数不能抛出异常，只能try处理
            }catch (Exception e){}
        System.out.println(currentThread().getName()+"::sale::" + ticket--);
    }
}
class TestT5{
    public static void main(String[] args){
        Object obj = new Object();
        Ticket3 t = new Ticket3();
        Thread t1 = new Thread(t);
        Thread t2 = new Thread(t);
        t1.start();
        try {//添加该块是想让t1等待一会，开启t2
            Thread.sleep(10);
        }catch (Exception e){}
        t.flag = false;
        t2.start();
    }
}



/*
修改卖票类为使用同步函数
 */
class Ticket2 implements Runnable
{
    private int ticket = 100;
    Object obj = new Object();
    public void run(){
        while (true){
            show();
        }
    }
    public synchronized void show(){
        if (ticket > 0)
            try{
                Thread.sleep(10);//该方法会抛出异常，只能处理
                //覆盖父类函数，如果父类函数没有抛出异常，则子类的相应函数不能抛出异常，只能try处理
            }catch (Exception e){}
        System.out.println(currentThread().getName()+"::sale::" + ticket--);
    }
}


/*
需求：银行有一金库
两个储户分别存300，每次存100，存3次

如何找问题：
1：明确哪些代码是多线程运行代码
2：明确共享数据
3：明确多线程运行代码中哪些语句是操作共享数据

 */
class Bank{
    private int sum;
    public synchronized void add(int n){//同步函数
            sum = sum + n;
            System.out.println("sum = " + sum);
    }
//    Object obj = new Object();
//    public void add(int n){
//        synchronized (obj){
//            sum = sum + n;
//            System.out.println("sum = " + sum);
//        }
//    }
}
class Cus implements Runnable{//TestT4
    private Bank b = new Bank();
    public void run(){
        for (int i = 0; i < 3; i++){
            b.add(100);
        }
    }
}
class TestT4{
    public static void main(String[] args){
        Cus c = new Cus();
        Thread t1 = new Thread(c);
        Thread t2 = new Thread(c);
        t1.start();
        t2.start();
    }
}

/*
通过分析，下面的卖票程序可能打印出0，-1，-2等错票
多线程的运行出现了安全问题
问题原因：当多条语句在操作同一个线程共享数据时，一个线程对多条语句只执行了一部分，还没有执行完，
        另一个线程参与进来执行，导致共享数据的错误
解决办法：对多条操作共享数据的语句，只能让一个线程执行完。在执行过程中，其他线程不可以参与执行

Java对于多线程的安全问题提供了专业的解决方案，就是同步代码块
synchronized（对象）
{
    需要被同步的代码
}
 */
class Ticket1 implements Runnable//extends Thread///下面程序的问题的解决代码
{
    private int ticket = 100;//创建线程的第二种方式：该方式将票独立出来，
    Object obj = new Object();
    public void run(){       //通过同步代码块解决了ticket访问的安全问题
        while (true){
            synchronized (obj){//这里必须传入一个对象，目前传obj，其他也行，这个对象称为锁
                if (ticket > 0)
                    try{
                        Thread.sleep(10);//该方法会抛出异常，只能处理
                        //覆盖父类函数，如果父类函数没有抛出异常，则子类的相应函数不能抛出异常，只能try处理
                    }catch (Exception e){}
                    System.out.println(currentThread().getName()+"::sale::" + ticket--);
            }
        }
    }
}


/*
需求：简单的卖票程序
多个窗口卖票
创建线程的第二种方式：
步骤：
    1：定义类实现Runnable接口
    2：覆盖Runnable接口中的run方法
    3：通过Thread类建立线程对象
    4：将Runnable接口的子类对象作为实际参数传递给Thread类的构造函数
    5：调用Thread类的start方法开启线程并调用Runnable接口子类的run方法
 */
//覆盖父类函数，如果父类函数没有抛出异常，则子类的相应函数不能抛出异常，只能try处理
class Ticket implements Runnable//extends Thread
{
    private int ticket = 100;//创建线程的第二种方式：该方式将票独立出来，但是该方法还是有问题，对ticket的访问问题
    public void run(){       //可能出现卖出<=0的票的情况
        while (true){
            if (ticket > 0)
                System.out.println(currentThread().getName()+"::sale::" + ticket--);
        }
    }
}
class TestT3{
    public static void main(String[] args){
        Ticket t = new Ticket();
        Thread t1 = new Thread(t);
        Thread t2 = new Thread(t);
        Thread t3 = new Thread(t);
        Thread t4 = new Thread(t);
        t1.start();
        t2.start();
        t3.start();
        t4.start();


        /*Ticket t1 = new Ticket();
        Ticket t2 = new Ticket();
        Ticket t3 = new Ticket();
        Ticket t4 = new Ticket();
        t1.start();
        t2.start();
        t3.start();
        t4.start();*/
    }
}


/*
练习：线程名字，
原来线程都有自己默认的名称，通过this.getName()获取
Thread-编号   改编号从0开始
 */
class testThread2 extends Thread
{
    //private String name;
    testThread2(String name){
        super(name);
        //this.name = name;
    }
    public void run()
    {
        for (int i = 0; i < 60; i++)
            System.out.println(currentThread().getName() + ":: run..." + ": "+ i);//标准通用方式，Thread.currentThread()返回当前线程对象
//            System.out.println((Thread.currentThread()==this)+"---" + this.getName() + ":: run..." + ": "+ i);
//            System.out.println(this.getName() + ":: run..." + ": "+ i);
    }
}
class TestT2{
    public static void main(String[] args){
        testThread2 t1 = new testThread2("one---");//创建一个线程
        testThread2 t2 = new testThread2("two---");//创建一个线程
        t1.start();
        t2.start();
        for (int i = 0; i < 60; i++)
            System.out.println("main..." + i);
    }
}


/*
练习：创建两个线程，
 */
class testThread1 extends Thread
{
    private String name;
    testThread1(String name){
        this.name = name;
    }
    public void run()
    {
        for (int i = 0; i < 60; i++)
            System.out.println("testThread run..." + name + ": "+ i);
    }
}
class TestT1{
    public static void main(String[] args){
        testThread1 t1 = new testThread1("one");//创建一个线程
        testThread1 t2 = new testThread1("two");//创建一个线程
        t1.start();
        t2.start();
        for (int i = 0; i < 60; i++)
            System.out.println("main..." + i);
    }
}





class testThread extends Thread
{
   public void run()
  {
      for (int i = 0; i < 60; i++)
          System.out.println("demo run");
  }
}
class TestT{
    public static void main(String[] args){
        testThread d = new testThread();//创建一个线程
        d.start();
        for (int i = 0; i < 60; i++)
            System.out.println("hello world");
    }
}


public class DuoXianCheng {
}
