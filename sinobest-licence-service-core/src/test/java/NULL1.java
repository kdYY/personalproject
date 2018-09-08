/**
 * Created by LY on 2018/9/4.
 */
public class NULL1{

    public static void print(){
        System.out.println("MTDP");
    }
    public static void main(String[] args) {
        try{
            ((NULL1)null).print();
            boolean result=false?false:true==false?true:false;
            System.out.println(result);
        }catch(NullPointerException e){
            System.out.println("NullPointerException");
        }
    }
}
