package datastructure;

public class StringMatchBoyerMoore {
    /**
     * 字符集大小
     */
    private static final int SIZE = 256;

    /**
     * 生成Bad Character 规则，得到 xi
     * @param b 模式串
     * @param m 模式串的长度
     * @param bc 记录每个字符在模式串中最后出现的位置
     */
    private void generateBC(char[] b, int m, int[] bc) {
        for (int i = 0; i < SIZE; i++) {
            bc[i] = -1;
        }
        for (int i = 0; i < m; i++) {
            int ascii = (int)b[i];
            // 如果坏字符在模式串里多处出现，选择最靠后的那个，
            // 因为这样不会让模式滑动过多，导致本来可能匹配的情况呗滑动略过。
            bc[ascii] = i;
        }
    }

    /**
     * 生成 Good Suffix 规则
     * @param b 模式串
     * @param m 模式串长度
     * @param suffix 如果公共后缀的长度是k，那么记录suffix[k]=j(j表示公共后缀子串的起始下标)
     * @param prefix 如果j等于0，也就是说，公共后缀子串也是模式串的前缀子串，那么记录prefix[k]=true
     */
    private void generateGS(char[] b, int m, int[] suffix, boolean[] prefix) {
        for (int i = 0; i < m; i++) {
            suffix[i] = -1;
            prefix[i] = false;
        }
        for (int i = 0; i < m - 1; i++) {
            int j = i;
            int k = 0; // 公共后缀子串长度
            while (j >= 0 && b[j] == b[m-1-k]) { // b[0, i]
                --j;
                ++k;
                suffix[k] = j + 1; // j+1 表示公共后缀子串在 b[0, i]中的起始下标
            }
            if (j == -1) { // 如果公共后缀子串也是模式串的前缀子串
                prefix[k] = true;
            }
        }
    }

    /**
     * 按好后缀规则移动位数
     * @param j 坏字符对应的模式串中的字符下标
     * @param m 模式串长度
     * @param suffix 如果公共后缀的长度是k，那么记录suffix[k]=j(j表示公共后缀子串的起始下标)
     * @param prefix 如果j等于0，也就是说，公共后缀子串也是模式串的前缀子串，那么记录prefix[k]=true
     * @return
     */
    private int moveByGS(int j, int m, int[] suffix, boolean[] prefix) {
        int k = m - 1 - j; // 好后缀长度
        if (suffix[k] != -1) return j - suffix[k] + 1;
        for (int r = j + 2; r <= m - 1; r++) {
            if (prefix[m-r]) {
                return r;
            }
        }
        return m;
    }
    /**
     * BM算法
     * @param a 主串
     * @param n 主串长度
     * @param b 模式串
     * @param m 模式串长度
     * @return
     */
    public int bm(char[] a, int n, char[] b, int m) {
        int[] bc = new int[SIZE]; // 记录模式串中每个字符最后出现的位置
        generateBC(b, m, bc); // 构建坏字符哈希表
        int[] suffix = new int[m];
        boolean[] prefix = new boolean[m];
        generateGS(b, m, suffix, prefix);
        int i = 0; // i 表示主串与模式串对齐的第一个字符
        while (i <= n -m) {
            int j;
            for (j = m - 1; j >= 0; --j) { // 模式串从后往前匹配
                if(a[i+j] != b[j]) { // 坏字符对应的下标是j(si)
                    break;
                }
            }
            if (j < 0) {
                return i; // 匹配成功，返回主串与模式串第一个匹配的字符
            }
            // 向后滑动(si - xi)位(si=j,xi=bc[a[i+j]])
            int x = j - bc[(int)a[i+j]];
            int y = 0;
            if (j < m -1) { // 如果有好后缀的话
                y = moveByGS(j, m, suffix, prefix);
            }
            i = i + Math.max(x, y);
        }
        return -1;
    }

    public static void main(String[] args) {
        char[] a = "aaaaaaaaaaaaaaaaabaaa".toCharArray();
        int n = a.length;
        char[] b = "baaa".toCharArray();
        int m = b.length;
        StringMatchBoyerMoore bm = new StringMatchBoyerMoore();
        System.out.println(bm.bm(a, n, b, m));
        System.out.println(a[17]);
    }
}
