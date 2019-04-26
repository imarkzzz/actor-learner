package datastructure.km;

import java.util.Arrays;

/**
 * Created by mark on 2019/4/26.
 */
public class KM_BFS2 {
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
        int[] slackY = new int[lenY]; // Y 点的松弛变量
        Arrays.fill(yPre, -1);
        Arrays.fill(slackY, Integer.MAX_VALUE);
        int[] queue = new int[lenX];
        int qs = 0, qe =0; // 队列开始结束索引
        queue[qe++] = startX;
        while (true) { // 循环直到找到匹配
            while (qs < qe && !find) {
                int x = queue[qs++];
                S[x] = true;
                for (int y = 0; y < lenY; y++) {
                    if (T[y]) {
                        continue;
                    }
                    int d = lX[x] + lY[y] - table[x][y];
                    if (d == 0) { // 相等子树中的边
                        T[y] = true;
                        yPre[y] = x;
                        if (linkY[y] == -1) {
                            endY = y;
                            find = true;
                            break;
                        } else {
                            queue[qe++] = linkY[y];
                        }
                    } else if (slackY[y] > d) { // 不在相等子树中的边，看是否能够更新松弛变量
                        slackY[y] = d;
                        yPre[y] = x;
                    }
                }
            }
            if (find) {
                break;
            }
            a = Integer.MAX_VALUE;
            for (int y = 0; y < lenY; y++) { // 找到最小的松弛值
                if (!T[y]) {
                    a = Math.min(a, slackY[y]);
                }
            }
            for (int x = 0; x < lenX; x++) { // 根据 a 修改标号值
                if (S[x]) {
                    lX[x] -= a;
                }
            }
            for (int y = 0; y < lenY; y++) {
                if (T[y]) {
                    lY[y] += a;
                }
            }
            qs = qe = 0;
            for (int y = 0; y < lenY; y++) { // 重要！！！控制修改标号之后需要检查的x点
                if (!T[y] && slackY[y] == a) { // 查看那些y点新加入到T集合，注意，这些y点的前向x点都记录在了yPre里面，所以这些x点不用再次入队
                    T[y] = true;
                    if (linkY[y] == -1) {
                        endY = y;
                        find = true;
                        break;
                    } else { // 新加入的 y 点已经有匹配了，将它匹配的 x 加到队列
                        queue[qe++] = linkY[y];
                    }
                }
                slackY[y] -= a;    // 所有松弛值减去a。(对于T集合中的松弛值已经没用了，对于不在T集合里面的y点，
            }                     // 它们的松弛值是通过S集合中的x点求出的，S集合中的x点的标号值在上面都减去了a，所以这里松弛值也要减去a)
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
