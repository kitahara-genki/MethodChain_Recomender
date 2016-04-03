import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.PI;

public class Foo {
        enum TYPE {AAA, BBB, CCC};


        public static void main(String[] args) {
        	List<String> list = new ArrayList<String>();
                int x = new Rectangle(10,20,30,40).getX();
                double pi = PI;
                list.add("Foo");
                list.add("Bar");
                list.add("Baz");
                int n = Integer.valueOf("123").intValue();
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