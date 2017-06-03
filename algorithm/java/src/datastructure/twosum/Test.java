package datastructure.twosum;

/**
 * Created by mark on 17-5-30.
 */
public class Test {
    public static void main(String[] args) {
        TwoSum ts = new TwoSum();
        ts.save(1);
        ts.save(3);
        ts.save(5);
        ts.save(7);
        ts.save(7);
        System.out.println(ts.test(14));
        System.out.println(ts.test(7));
    }
}
