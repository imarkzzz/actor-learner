package datastructure.km;

import java.util.Arrays;

/**
 * Created by mark on 2019/4/25.
 */
public class KM_DFS {
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
            a = Integer.MAX_VALUE;
            while (!dfs(x)){   // 找到可扩路结束，否则修改标号值
                LModified(S, T);
                Arrays.fill(S, false);
                Arrays.fill(T, false);
                a = Integer.MAX_VALUE;
            }
        }
        return linkX;
    }

    private boolean dfs(int x){  // 深度优先搜索
        S[x] = true;
        for (int y = 0; y < lenY; y++){
            if (T[y]){
                continue;
            }
            int d = lX[x] + lY[y] - table[x][y];
            if (d == 0){  // 在相等子树中
                T[y] = true;
                if (linkY[y] == -1 || dfs(linkY[y])){     // 1. y顶点没有匹配，那么进行匹配
                    linkX[x] = y;                                   // 2. dfs寻找可扩路成功，那么这条x，y就会因为可扩路的扩展而交换到匹配中
                    linkY[y] = x;
                    return true;
                }
            } else if (a > d){    // 不在相等子树中
                a = d;
            }
        }
        return false;
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

    private void LModified(boolean[] S, boolean[] T){ // 修改标号值
        for (int i = 0; i < lenX; i++){
            if (S[i]){
                lX[i] -= a;
            }
        }
        for (int i = 0; i < lenY; i++) {
            if (T[i]){
                lY[i] += a;
            }
        }
    }
}
