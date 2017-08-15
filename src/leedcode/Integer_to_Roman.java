package leedcode;

/**
 * Created by XCY on 2017/4/3.
 */
public class Integer_to_Roman {
    public String intToRoman(int num) {
        String []elem1_10={"","I","II","III","IV","V","VI","VII","VIII","IX"};
        String []elem10_100={"","X","XX","XXX","XL","L","LX","LXX","LXXX","XC"};
        String []elem100_1000={"","C","CC","CCC","CD","D","DC","DCC","DCCC","CM"};
        String []elem1000_3000={"","M","MM","MMM"};
        int thousand=num/1000;
        num=num%1000;
        int hundred=num/100;
        num=num%100;
        int ten=num/10;
        int remainder=num%10;
        String str="";
        str=elem1000_3000[thousand]+elem100_1000[hundred]+elem10_100[ten]+elem1_10[remainder];
        return str;
    }
}
