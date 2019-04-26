package datastructure.km;

import java.util.Arrays;

/**
 * Created by mark on 2019/4/26.
 */
public class KM_BFS1 {
    private int[][] table = null;     // 权重矩阵（m x n, m <= n）
    private int[] lX = null;          // X标号值
    private int[] lY = null;          // Y标号值
    private int[] linkX = null;      // X点对应的匹配点
    private int[] linkY = null;      // Y点对应的匹配点
    private int lenX = 0;                // 矩阵行数
    private int lenY = 0;                // 矩阵列数
    private int a = 0;                // 标号修改量
    private boolean[] S = null; // S集合
    private boolean[] T = null; // T集合

    public int[] solve(int[][] table){ // 入口，输入权重矩阵
        this.table = table;
        init();
        for (int x = 0; x < lenX; x++ ){  // 为每一个x寻找匹配
            bfs(x);
        }
        return linkX;
    }

    private void bfs(int startX) { // 为一个x点寻找匹配
        boolean find = false;
        int endY = -1;
        int[] yPre = new int[lenY]; // 标识搜索路径上y点的前一个点
        Arrays.fill(yPre, -1);
        a = Integer.MAX_VALUE;
        int[] queue = new int[lenX];
        int qs = 0, qe =0; // 队列开始结束索引
        queue[qe++] = startX;
        while (true) { // 循环直到找到匹配
            while (qs < qe && !find) {
                int x = queue[qs++];
                S[x] = true;
                for (int y = 0; y < lenY; y++) {
                    int d = lX[x] + lY[y] - table[x][y];
                    if (d == 0) { // 相等子树中的边
                        if (T[y]) {
                            continue;
                        }
                        T[y] = true;
                        yPre[y] = x;
                        if (linkY[y] == -1) {
                            endY = y;
                            find = true;
                            break;
                        } else {
                            queue[qe++] = linkY[y];
                        }
                    } else { // 不在相等子树中的边，记录一下最小差值
                        a = Math.min(a, d);
                    }
                }
            }
            if (find) {
                break;
            }
            qs = qe = 0;
            for (int i = 0; i < lenX; i++) { // 根据a修改标号值
                if (S[i]) {
                    lX[i] -= a;
                    queue[qe++] = i; // 把所有在S中的点加回到队列中
                }
            }
            for (int i = 0; i < lenY; i++) { // 根据a修改标号值
                if (T[i]) {
                    lY[i] += a;
                }
            }
            a = Integer.MAX_VALUE;
        }
        while (endY != -1) { // 找到可扩路最后的y点后，回溯并扩充
            int preX = yPre[endY], preY = linkX[preX];
            linkX[preX] = endY;
            linkY[endY] = preX;
            endY = preY;
        }
    }

    private void init(){
        this.lenX = table.length;
        this.lenY = table[0].length;
        this.lX = new int[lenX];
        this.lY = new int[lenY];
        this.S = new boolean[lenX];
        this.T = new boolean[lenY];

        Arrays.fill(lX, Integer.MIN_VALUE);
        for (int x = 0; x < lenX; x++ ){
            for (int y = 0; y < lenY; y++){
                if (table[x][y] > lX[x] ){
                    lX[x] = table[x][y];
                }
            }
        }
        this.linkX = new int[lenX];
        this.linkY = new int[lenY];
        Arrays.fill(linkX, -1);
        Arrays.fill(linkY, -1);
    }
}
