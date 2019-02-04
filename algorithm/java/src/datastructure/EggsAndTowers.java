package datastructure;

/**
 * 有M层楼 / N个鸡蛋，要找到鸡蛋摔不碎的临界点，需要尝试几次？
 */
public class EggsAndTowers {
    public int getMinStepsMemo(int eggNum, int floorNum) {
        if (eggNum < 1 || floorNum < 1) {
            return 0;
        }
        // 备忘录，存储eggNum个鸡蛋，floorNum层楼条件下的最优化尝试次数
        int[] [] cache = new int[eggNum + 1][floorNum + 1];
        // 把备忘录每个元素初始化成最大的尝试次数
        for (int i=1; i<=eggNum; i++) {
            for(int j=1; j<=floorNum; j++) {
                cache[i][j] = j;
            }
        }

        for (int n=2; n<=eggNum; n++) {
            for (int m=1; m<=floorNum; m++) {
                for (int k=1; k<m; k++) {
                    // 扔鸡蛋的楼层从1到m枚举一遍，如果当前算出的尝试次数小于上一次算出的尝试次数，则取代上一次的尝试次数。
                    // 这里可以打印k的值，从而知道第一个鸡蛋是从第几层扔的。
                    cache[n][m] = Math.min(cache[n][m], 1 + Math.max(cache[n-1][k-1], cache[n][m-k]));
                }
            }
        }
        return cache[eggNum][floorNum];
    }

    /**
     * 从状态转移方程可以知道，每一次中间状态的尝试次数只和上一层（鸡蛋数量-1）和本层（当前鸡蛋数量）的值有关，所以可以优化空间复杂度
     * @param eggNum
     * @param floorNum
     * @return
     */
    public int getMinStepsOptimized(int eggNum, int floorNum) {
        if (eggNum < 1 || floorNum < 1) {
            return 0;
        }
        // 上一层备忘录，存储鸡蛋数量-1的floorNum层条件下的最优尝试次数
        int[] preCache = new int[floorNum + 1];
        // 当前备忘录，存储当前鸡蛋数量的floorNum层楼条件下的最优化尝试次数
        int[] currCache = new int[floorNum + 1];
        // 把备忘录每个元素初始化成最大的尝试次数
        for (int i=1; i<=floorNum; i++) {
            currCache[i] = i;
        }
        for (int n=2; n<=eggNum; n++) {
            // 当前备忘录拷贝给上一次备忘录，并重新初始化当前备忘录
            preCache = currCache.clone();
            for (int i=1; i<=floorNum; i++) {
                currCache[i] = i;
            }
            for (int m=1; m<=floorNum; m++) {
                for (int k=1; k<m; k++) {
                    // 扔鸡蛋的楼层从1到m枚举一遍，如果当前算出的尝试次数小于上一次算出的尝试次数，则取代上一次的尝试次数。
                    // 这里可以打印k的值，从而知道第一个鸡蛋是从第几层扔的。
                    currCache[m] = Math.min(currCache[m], 1 + Math.max(preCache[k-1], currCache[m-k]));
                }
            }
        }
        return currCache[floorNum];
    }

    public static void main(String[] args) {
        EggsAndTowers eggsAndTowers = new EggsAndTowers();
        System.out.println(eggsAndTowers.getMinStepsMemo(2, 500));
        System.out.println(eggsAndTowers.getMinStepsOptimized(2, 500));

    }
}
