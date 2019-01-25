package datastructure;

import java.util.Random;

/**
 * Created by mark on 2019/1/25.
 */

public class SkipList<T> {
    public class SkipListNode <T>{
        public int key;
        public T value;
        public SkipListNode<T> up, down, left, right; // 上下左右 四个指针
        public static final int HEAD_KEY = Integer.MIN_VALUE; // 负无穷
        public static final int TAIL_KEY = Integer.MAX_VALUE; // 正无穷
        public SkipListNode(int k, T v) {
            key = k;
            value = v;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null) {
                return false;
            }
//            if (!(o instanceof SkipListNode<?>)) {
//                return false;
//            }
            SkipListNode<T> ent;
            try {
                ent = (SkipListNode<T>) o; // 类型检查
            } catch (ClassCastException e) {
                return false;
            }
            return (ent.getKey() == key && (ent.getValue() == value));
        }

        @Override
        public String toString() {
            return "SkipListNode{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }
    private SkipListNode<T> head, tail;
    private int nodes; // 节点总数
    private int listLevel; // 层数
    private Random random; // 用于投掷硬币
    private static final double PROBABILITY = 0.5; // 向上提升一个的概率
    
    public SkipList() {
        random = new Random();
        clear();
    }

    /**
     * 清空跳表
      */
    public void clear() {
        head = new SkipListNode<T>(SkipListNode.HEAD_KEY, null);
        tail = new SkipListNode<T>(SkipListNode.TAIL_KEY, null);
        horizontalLink(head, tail);
    }
    public boolean isEmpty() {
        return nodes == 0;
    }

    public int size() {
        return nodes;
    }

    /**
     * 在最下面一层，找到要插入的位置前面的那个key
     * @param key
     * @return
     */
    public SkipListNode<T> findNode(int key) {
        SkipListNode<T> p = head;
        while (true) {
            while (p.right.key != SkipListNode.TAIL_KEY && p.right.key <= key) {
                p = p.right;
            }
            if (p.down != null) {
                p = p.down;
            } else {
                break;
            }
        }
        return p;
    }

    /**
     * 查找是否存储key，存在则返回该节点，否则返回null
     * @param key
     * @return
     */
    public SkipListNode<T> search(int key) {
        SkipListNode<T> p = findNode(key);
        if (key == p.getKey()) {
            return p;
        } else {
            return null;
        }
    }

    /**
     * 向跳表中添加key-value
     * @param k
     * @param v
     */
    public void put(int k, T v) {
        SkipListNode<T> p = findNode(k);
        // 如果key值相同，替换原来的value即可结束
        if (k == p.getKey()) {
            p.value = v;
            return;
        }
        SkipListNode<T> q = new SkipListNode<T>(k, v);
        backLink(p, q);
        int currentLevel = 0; // 当前所在的层级是0
        // 抛硬币
        while (random.nextDouble() < PROBABILITY) {
            // 如果超出了高度，需要重建一个顶层
            if (currentLevel >= listLevel) {
                listLevel ++;
                // 每层都有左右两个哨兵节点
                SkipListNode<T> p1 = new SkipListNode<T>(SkipListNode.HEAD_KEY, null);
                SkipListNode<T> p2 = new SkipListNode<T>(SkipListNode.TAIL_KEY, null);
                horizontalLink(p1, p2);
                verticalLink(p1, head);
                verticalLink(p2, tail);
                head = p1;
                tail = p2;
            }
            // 将p移动到上一层
            while (p.up == null) {
                 p = p.left;
            }
            p = p.up;

            SkipListNode<T> e = new SkipListNode<T>(k, null); // 只保存key就ok
            backLink(p, e); // 将e插入到p的后面
            verticalLink(e, q); // 将e和q上下连接
            q=e;
            currentLevel ++;
        }
        nodes++; // 节点数递增
     }

    /**
     * 垂直双向连接
     * @param p
     * @param q
     */
    private void verticalLink(SkipListNode<T> p, SkipListNode<T> q) {
        p.down = q;
        q.up = p;
    }

    /**
     * 在p后插入q
     * @param p
     * @param q
     */
    private void backLink(SkipListNode<T> p, SkipListNode<T> q) {
        q.left = p;
        q.right = p.right;
        p.right.left = q;
        p.right = q;
    }

    /**
     * 水平双向连接
     * @param p
     * @param q
     */
    private void horizontalLink(SkipListNode<T> p, SkipListNode<T> q) {
        p.right = q;
        q.left = p;
    }

    public static void main(String[] args) {
        SkipList<String> list = new SkipList<>();
        System.out.println(list);
        list.put(2, "love");
        list.put(1, "I");
        list.put(3, "java");
        list.put(5, "much");
        list.put(4, "very");
        list.put(6, "too");
        for (int i = 0; i < 7; i++) {
            System.out.println(list.search(i));
        }
    }
}
