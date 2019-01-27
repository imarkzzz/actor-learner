package datastructure;

/**
 * Created by mark on 2019/1/27.
 */
public class RedBlackTreePractice {
    private static class Node<T> {
        public static final boolean RED_NODE = true;
        public static final boolean BLACK_NODE = false;

        public boolean nodeColor; // 节点颜色
        public Comparable key; // 当前节点搜索key
        public T value; // 当前节点存储值

        public int nodeNumber = 0; // 以当前节点为根节点的子树的节点个数
        public Node leftNode, rightNode; // 当前节点的左右子节点

        public Node(Comparable key, T value, int number, boolean color) {
            this.key = key;
            this.value = value;
            this.nodeNumber = number;
            this.nodeColor = color;
        }

        public boolean isRed() {
            return this.nodeColor == RED_NODE;
        }

        public int getSize() {
            return nodeNumber;
        }

        protected int size() {
            int nodeNumber = 1;
            if (leftNode != null) {
                nodeNumber += leftNode.getSize();
            }
            if (rightNode != null) {
                nodeNumber += rightNode.getSize();
            }
            return nodeNumber;
        }
    }

    public static class RedBlackTree<T> {
        private Node<T> root; // 根节点

        protected Node<T> rotateLeft(Node node) {
            Node tmp = node.rightNode;
            node.rightNode = tmp.leftNode;
            tmp.leftNode = node;
            tmp.nodeColor = node.nodeColor;
            node.nodeColor = Node.RED_NODE;
            tmp.nodeNumber = node.nodeNumber;
            node.nodeNumber = node.size();
            return tmp;
        }

        protected Node rotateRight(Node node) {
            Node tmp = node.leftNode;
            node.leftNode = tmp.rightNode;
            tmp.rightNode = node;
            tmp.nodeColor = node.nodeColor;
            tmp.nodeColor = Node.RED_NODE;
            tmp.nodeNumber = node.nodeNumber;
            node.nodeNumber = node.size();
            return tmp;
        }

        protected Node rotateColor(Node node) {
            node.nodeColor = Node.RED_NODE;
            node.leftNode.nodeColor = Node.BLACK_NODE;
            node.rightNode.nodeColor = Node.BLACK_NODE;
            return node;
        }

        protected boolean isRed(Node node) {
            if (node == null) {
                return false;
            }
            return node.isRed();
        }

        public Node search(Comparable key) {
            return search(key, this.root);
        }

        public Node search(Comparable key, Node<T> node) {
            if (node == null) {
                return null;
            }
            int cmp = key.compareTo(node.key);
            if (cmp < 0) {
                return search(key, node.leftNode);
            } else if (cmp > 0) {
                return search(key, node.rightNode);
            } else {
                return node;
            }
        }

        public void insert(Comparable key, T value) {
            Node<T> node = insertSubTree(this.root, key, value);
            node.nodeColor = Node.BLACK_NODE;
            this.root = node;
        }

        protected Node<T> insertSubTree(Node<T> node, Comparable key, T value) {
            if (node == null) {
                return new Node<T>(key, value, 1, Node.RED_NODE);
            }
            int cmp = key.compareTo(node.key);
            if (cmp < 0) {
                node.leftNode = insertSubTree(node.leftNode, key, value);
            } else if (cmp > 0) {
                node.rightNode = insertSubTree(node.rightNode, key, value);
            } else {
                node.value = value;
            }

            if (!isRed(node.leftNode) && isRed(node.rightNode)) {
                node = rotateLeft(node);
            }

            if (isRed(node.leftNode) && isRed(node.rightNode)) {
                node = rotateRight(node);
            }

            node.nodeNumber = node.size();
            return node;
        }
    }

    public static void main(String[] args) {
        RedBlackTree<String> tree = new RedBlackTree<>();
        int[] insertValue = new int[]{12, 1, 9, 10, 7, 2, 38, 8, 4};
        for(int value : insertValue) {
            tree.insert(value, "value_" + value);
        }

        for(int key : insertValue) {
            Node node = tree.search(key);
            System.out.println(node.value);
        }
    }
}
