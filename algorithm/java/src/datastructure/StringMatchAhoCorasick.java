package datastructure;

import java.util.LinkedList;
import java.util.Queue;

public class StringMatchAhoCorasick {
    private static int SIZE = 26;  // 字符集只包含 a~z 这26个字母
    public class AcNode {
        public char data;
        public AcNode[] children = new AcNode[SIZE];
        public boolean isEndingChar = false; // 结尾字符
        public int length = -1; // 当isEndingChar=true 时，记录模式串长度
        public AcNode fail; // 失败指针
        public AcNode(char data) {
            this.data = data;
        }
    }
    private AcNode root =  new AcNode('/'); // 存储无意义字符

    // 往 Trie 树中插入一个模式串
    public void addPattern(char[] pattern) {
        AcNode p = root;
        for (int i = 0; i < pattern.length; i++) {
            int index = pattern[i] - 'a';
            if (p.children[index] == null) {
                AcNode newNode = new AcNode(pattern[i]);
                p.children[index] = newNode;
            }
            p = p.children[index];
        }
        p.isEndingChar = true;
        p.length = pattern.length;
    }

    /**
     * 构建失败指针
     */
    public void buildFailurePointer() {
        Queue<AcNode> queue = new LinkedList<>();
        root.fail = null;
        queue.add(root);
        while (!queue.isEmpty()) {
            AcNode p = queue.remove();
            for (int i = 0; i < SIZE; i++) {
                AcNode pc = p.children[i];
                if (pc == null) {
                    continue;
                }
                if (p == root) {
                    pc.fail = root;
                } else {
                    AcNode q = p.fail;
                    while (q != null) {
                        AcNode qc = q.children[pc.data - 'a'];
                        if (qc != null) {
                            pc.fail = qc;
                            break;
                        }
                        q = q.fail;
                    }
                    if (q == null) {
                        pc.fail = root;
                    }
                }
                queue.add(pc);
            }
        }
    }

    /**
     * 匹配主串中是否出现模式串集合中的模式
     * @param text 主串
     */
    public void match(char[] text) {
        int n = text.length;
        AcNode p = root;
        for (int i = 0; i < n; i++) {
            int idx = text[i] - 'a';
            while (p.children[idx] == null && p != root) {
                p = p.fail; // 失败指针发挥作用的地方
            }
            p = p.children[idx];
            if (p == null) { // 如果没有匹配，从 root 开始重新匹配
                p = root;
            }
            AcNode tmp = p;
            while (tmp != root) { // 打印出可以匹配的模式串
                if (tmp.isEndingChar) {
                    int pos = i - tmp.length + 1;
                    StringBuilder sb = new StringBuilder();
                    for (int k = 0; k < tmp.length; k++) {
                        sb.append(text[pos+k]);
                    }
                    System.out.println(" 匹配字符串: " + sb.toString() + ", 匹配起始下标: " + pos + ", 长度:"  + tmp.length);
                }
                tmp = tmp.fail;
            }
        }
    }

    public static void main(String[] args) {
        char[][] patterns= { "c".toCharArray(), "bc".toCharArray(), "bcd".toCharArray(), "abcd".toCharArray()};
        StringMatchAhoCorasick ac = new StringMatchAhoCorasick();
        for (int i = 0; i < patterns.length; i++) {
            char[] pattern =  patterns[i];
            ac.addPattern(pattern);
        }
        ac.buildFailurePointer();
        ac.addPattern(new char[]{'a', 'g', 'f'});
        ac.buildFailurePointer();
        char[] text = "cgabc".toCharArray();
        ac.match(text);
    }
}
