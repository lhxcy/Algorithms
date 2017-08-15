package ZuoChengYun_exercise;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 目的：实现一种猫狗队列 ，具有add ,pollAll ,pollDog ,pollCat ,isEmpty ,isDogEmpty,isCatEmpty方法
 * Created by XCY on 2017/7/4.
 *
 * 可以将Pet，Cat，Dog类写入单独的文件然后导入
 */
//class Pet{
//    private String type;
//
//    public Pet(String type){
//        this.type=type;
//    }
//
//    public String GetPetType(){
//        return this.type;
//    }
//}
//
//class Dog extends Pet{
//    public Dog(){
//        super("dog");
//    }
//}
//
//class Cat extends Pet{
//    public Cat(){
//        super("cat");
//    }
//}
//
//class PetEnterQueue{//不同实例上盖上时间戳的方法来区分
//    private Pet pet;
//    private long count;//实例的时间戳
//
//    private PetEnterQueue(Pet pet,long count){
//        this.pet = pet;
//        this.count = count;
//    }
//
//    public Pet getPet(){
//        return this.pet;
//    }
//
//    public long getCount(){
//        return this.count;
//    }
//
//    public String getEntryPetType(){
//        return this.pet.GetPetType();
//    }
//}
public class _4_CatAndDog {
    public class Pet{
        private String type;

        public Pet(String type){
            this.type=type;
        }

        public String GetPetType(){
            return this.type;
        }
    }

    public class Dog extends Pet{
        public Dog(){
            super("dog");
        }
    }

    public class Cat extends Pet{
        public Cat(){
            super("cat");
        }
    }

    public class PetEnterQueue{//不同实例上盖上时间戳的方法来区分
        private Pet pet;
        private long count;//实例的时间戳

        private PetEnterQueue(Pet pet,long count){
            this.pet = pet;
            this.count = count;
        }

        public Pet getPet(){
            return this.pet;
        }

        public long getCount(){
            return this.count;
        }

        public String getEntryPetType(){
            return this.pet.GetPetType();
        }
    }


    private Queue<PetEnterQueue> DogQ;
    private Queue<PetEnterQueue> CatQ;
    private long count;

    public _4_CatAndDog(){
        this.DogQ = new LinkedList<PetEnterQueue>();
        this.CatQ = new LinkedList<PetEnterQueue>();
        this.count = 0;
    }

    public void add(Pet pet){
        if (pet.GetPetType().equals("dog")){
            this.DogQ.add(new PetEnterQueue(pet,this.count++));
        }else if (pet.GetPetType().equals("cat")){
            this.CatQ.add(new PetEnterQueue(pet,this.count++));
        }else {
            throw new RuntimeException("err,not dog or cat");
        }
    }

    public Pet pollAll(){//弹出插入顺序的第一个
        if (!this.DogQ.isEmpty()&&!this.CatQ.isEmpty()){
            if (this.DogQ.peek().getCount()<this.CatQ.peek().getCount()){
                return this.DogQ.poll().getPet();
            }else {
                return this.CatQ.peek().getPet();
            }
        }else if (!this.DogQ.isEmpty()){
            return this.DogQ.poll().getPet();
        }else if (!this.CatQ.isEmpty()){
            return this.CatQ.poll().getPet();
        }else {
            throw new RuntimeException("err,queue is empty!!!");
        }
    }

    public Dog pollDog(){
        if (!this.DogQ.isEmpty()){
            return (Dog) this.DogQ.poll().getPet();
        }else {
            throw new RuntimeException("err,Dog queue is empty!!!");
        }
    }

    public Cat pollCat(){
        if (!this.CatQ.isEmpty()){
            return (Cat) this.CatQ.poll().getPet();
        }else {
            throw new RuntimeException("err,Cat queue is empty!!!");
        }
    }

    public boolean isEmpty(){
        return this.DogQ.isEmpty()&&this.CatQ.isEmpty();
    }

    public boolean isDogEmpty(){
        return this.DogQ.isEmpty();
    }

    public boolean isCatEmpty(){
        return this.CatQ.isEmpty();
    }

}




