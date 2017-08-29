package algorithms;

import java.io.PrintStream;
import java.util.Objects;

/**
 * print obj
 * Created by XCY on 2017/3/23.
 * @author XCY
 * @version  v1.1
 */
public class Print {
    /**
     * print obj + "\n"
     * @param obj recieve
     */
    public static void println(Object obj){
        System.out.println(obj);
    }
    /**
     * print " "
     */
    public static void println(){
        System.out.println();
    }
    /**
     * print obj
     * @param obj recieve
     */
    public static void print(Object obj){
        System.out.print(obj);
    }
    /**
     * print obj
     * @param format ..
     * @param args ..
     * @return  ...
     */
    public static PrintStream printf(String format, Object...args){
        return System.out.printf(format,args);
//        System.out.format("x = %d, y = %f\n", x, y);
    }
}
