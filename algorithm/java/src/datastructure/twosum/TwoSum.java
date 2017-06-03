package datastructure.twosum;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by mark on 17-5-30.
 */
public class TwoSum {
    HashMap<Integer, Integer> hm = new HashMap<>();

    public void save(int input) {
        // 如果之前不存在则赋值0
        int originalCount = hm.getOrDefault(input, 0);
        hm.put(input, originalCount);
    }

    public boolean test(int target) {
        Iterator<Integer> it = hm.keySet().iterator();
        int val;
        boolean isDouble;
        while (it.hasNext()) {
            val = it.next();
            if (hm.containsKey(target - val)) {
                // 如果某数的两倍为 target 值,那么只有该数出现两次以上，才能返回 true
                isDouble = target == val * 2;
                if (!(isDouble && hm.get(val) == 1)) {
                    return true;
                }
            }
        }
        return false;
    }
}
