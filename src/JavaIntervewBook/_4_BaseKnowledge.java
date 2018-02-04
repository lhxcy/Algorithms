package JavaIntervewBook;

/**
 * Created by XCY on 2018/2/4.
 */
public class _4_BaseKnowledge {
}

class Obj{
    private String str = "default value";
    public void setStr(String str){
        this.str = str;
    }
    public String toString(){
        return str;
    }
}

class TestRef{
    private Obj aObj = new Obj();
    private int aInt = 0;
    private Obj getaObj(){
        return aObj;
    }
    public int getaInt(){
        return aInt;
    }
    public void changeObj(Obj aObj){
        aObj.setStr("change value");
    }
    public void changeInt(int val){
        aInt = val;
    }
    public static void main(String[] args){
        TestRef testRef = new TestRef();
        System.out.println(testRef.getaObj());
        testRef.changeObj(testRef.getaObj());
        System.out.println(testRef.getaObj());
        System.out.println(testRef.getaInt());
        testRef.changeInt(3);
        System.out.println(testRef.getaInt());

    }
}