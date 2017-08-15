package algorithms;

import java.io.PrintStream;
import java.util.Objects;

/**
 * Created by XCY on 2017/3/23.
 */
public class Print {
    public static void println(Object obj){
        System.out.println(obj);
    }
    public static void println(){
        System.out.println();
    }
    public static void print(Object obj){
        System.out.print(obj);
    }
    public static PrintStream printf(String format, Object...args){
        return System.out.printf(format,args);
//        System.out.format("x = %d, y = %f\n", x, y);
    }
}
