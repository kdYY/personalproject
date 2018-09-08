import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by LY on 2018/9/4.
 */
public class test {
    public static void main(String[] args) {
//      int[] a = {5,3,7,19};
//      List list = Arrays.asList(a);
//      list.sort(new Comparator() {
//          @Override
//          public int compare(Object o1, Object o2) {
//              return (int)o2 - (int)o1;
//          }
//      });
//        final int sum = 0;
//      list.forEach((e)->{
//          System.out.println((Integer) e);
//          sum[0] &= e;
//      });
//      int exist = 0;
        System.out.println(compositionCount("duowanisgood"));

    }
    public static int compositionCount(String s){
        int i =1;
        int j =s.length()-2;
        int count =0;
        while((i<s.length()-3)&& i<j){
            if((s.charAt(i)==s.charAt(j))&& (j-i>=1)){
                count ++;
            }
            if(j-i==2 && j>i){
                i++;
                j =s.length()-2;
            }else
                j--;
        }
        return count;
    }
}
