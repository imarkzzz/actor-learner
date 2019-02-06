package datastructure;

public class StringMatchKnuthMorrisPratt {

    /**
     * 失效函数
     * @param b 模式串
     * @param m 模式串长度
     * @param nexts 失效函数
     * @return
     */
    private static void getNexts(char[] b, int m, int[] nexts) {
        nexts[0] = -1;
        int k = -1;
        for (int i = 1; i < m; i++) {
            while (k != -1 && b[k+1] != b[i]) {
                k = nexts[k];
            }
            if (b[k + 1] == b[i]) {
                ++k;
            }
            nexts[i] = k;
        }
    }
    /**
     * KMP 算法
     * @param n 主串长度
     * @param b 模式串
     * @param m 模式串长度
     * @return
     */
    public static int kmp(char[] a, int n, char[] b, int m) {
        int[] nexts = new int[m];
        getNexts(b, m, nexts);
        int j = 0;
        for (int i = 0; i < n; i++) {
            while (j > 0 && a[i] != b[j]) {
                j = nexts[j - 1] + 1;
            }
            if (a[i] == b[j]) {
                ++j;
            }
            if (j == m) { // 找到模式串了
                return i - m + 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        char[] a = "aaaaaaaaaaaaaaaaabaaa".toCharArray();
        int n = a.length;
        char[] b = "baaa".toCharArray();
        int m = b.length;
        StringMatchKnuthMorrisPratt kmp = new StringMatchKnuthMorrisPratt();
        System.out.println(kmp.kmp(a, n, b, m));
    }
}
