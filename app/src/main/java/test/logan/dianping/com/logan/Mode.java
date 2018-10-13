package test.logan.dianping.com.logan;

public class Mode implements Comparable{

    private int age;

    public Mode(){

    }

    public Mode(int age){
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int compareTo(Object another) {
        if(another instanceof Mode){
            if(this.age > ((Mode) another).getAge()){
                return 1;
            }else if(this.age == ((Mode) another).getAge()){
                return 0;
            }else {
                return -1;
            }
        }
        return 0;
    }
}
