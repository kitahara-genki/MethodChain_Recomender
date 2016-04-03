import static java.lang.Math.*;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import Foo.TYPE;


public class test2 {
	enum TYPE {AAA, BBB, CCC};
    

    public static void main(String[] args) {
    	List<String> list = new ArrayList<String>();
            Rectangle re = new Rectangle(10,20,30,40);
            new Rectangle(1,1,1,1).grow(6, 7);
            int n = re.x;
            double pi = PI;
            list.add("Foo");
            double d2=
            list.add("Bar");
            int n = (int)(Math.random()*5);
            
            double d = Math.atan2(0.1, 0.3);
            
            for (String str : list) {
                    System.out.println(str + " ");
            }

            for (TYPE type : TYPE.values()) {
                    System.out.println(type.name() + " ");
            }
    }

    public void method1(String[] arg0, int arg 1) {
    	list.add("");
    }
    public void method2(int arg0, int arg2, int arg3) {
    	list.add("");
    }
}
