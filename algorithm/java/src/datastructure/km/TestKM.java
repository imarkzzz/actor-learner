package datastructure.km;

/**
 * Created by mark on 2019/4/26.
 */
public class TestKM {
    public static void main(String[] args){
        int[][] table = {{8, 2, 1}, {10, 3, 9}};
//        KM_DFS km = new KM_DFS();
        KM_BFS1 km = new KM_BFS1();
//        KM_BFS2 km = new KM_BFS2();
        int[] linkX = km.solve(table);
        print(table, linkX);
    }
    public static void print(int[][] table, int[] linkX) {
        for (int i = 0; i < linkX.length; i++) {
            System.out.println(i + ":" + table[i][linkX[i]]);
        }
    }
}
