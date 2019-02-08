package datastructure;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class GraphSearch {
    public static class UndirectGraph { // 无向图
        private int v; // 顶点个数
        private LinkedList<Integer>[] adj; // 邻接表

        public UndirectGraph(int v) {
            this.v = v;
            adj = new LinkedList[v];
            for (int i = 0; i < v; i++) {
                adj[i] = new LinkedList<>();
            }
        }

        public void addEdge(int s, int t) { // 无向图一条边存两次
            adj[s].add(t);
            adj[t].add((s));
        }

        public void bfs(int s,int t) {
            if (s == t) {
                return;
            }
            boolean[] visited = new boolean[v];
            visited[s] = true;
            Queue<Integer> queue = new LinkedList<>();
            queue.add(s);
            int[] prev = new int[v];
            for (int i = 0; i < v; i++) {
                prev[i] = -1;
            }
            while (!queue.isEmpty()) {
                int w = queue.poll();
                for (int i = 0; i < adj[w].size(); i++) {
                    int q = adj[w].get(i);
                    if (!visited[q]) {
                        prev[q] = w;
                        if (q == t) {
                            print(prev, s, t);
                            return;
                        }
                        visited[q] = true;
                        queue.add(q);
                    }
                }
            }
        }

        boolean found = false; // 全局变量
        public void dfs(int s, int t) {
            found = false;
            boolean[] visited = new boolean[v];
            int[] prev = new int[v];
            for (int i = 0; i < v; i++) {
                prev[i] = -1;
            }
            recurDfs(s, t, visited, prev);
            if (found) {
                print(prev, s, t);
            }
        }

        private void recurDfs(int w, int t, boolean[] visited, int[] prev) {
            if (found) {
                return;
            }
            visited[w] = true;
            if (w == t) {
                found = true;
                return;
            }
            for (int i = 0; i < adj[w].size(); i++) {
                int q = adj[w].get(i);
                if (!visited[q]) {
                    prev[q] = w;
                    recurDfs(q, t, visited, prev);
                }
            }
        }

        private void nonRecurDfs(int s, int t) {
            boolean[] visited = new boolean[v];
            int[] prev = new int[v];
            for (int i = 0; i < v; i++) {
                prev[i] = -1;
            }
            Stack<Integer> stack = new Stack<>();
            stack.add(s);
            while (!stack.empty()) {
                int w = stack.pop();
                visited[w] = true;
                if (w == t) {
                    print(prev, s, t);
                    break;
                }
                for (int i = 0; i < adj[w].size(); i++) {
                    int q = adj[w].get(i);
                    if (!visited[q]) {
                        prev[q] = w;
                        stack.add(q);
                    }
                }
            }
        }
    }

    public static class DirectGraph { // 有向图
        private int v; // 顶点个数
        private LinkedList<Integer>[] adj; // 邻接表
        public DirectGraph(int v) {
            this.v = v;
            adj = new LinkedList[v];
            for (int i = 0; i < v; i++) {
                adj[i] = new LinkedList<>();
            }
        }

        public void addEdge(int s, int t) { // 有向图一边存一次
            adj[s].add(t);
        }

        public void bfs(int s,int t) {
            if (s == t) {
                return;
            }
            boolean[] visited = new boolean[v];
            visited[s] = true;
            Queue<Integer> queue = new LinkedList<>();
            queue.add(s);
            int[] prev = new int[v];
            for (int i = 0; i < v; i++) {
                prev[i] = -1;
            }
            while (!queue.isEmpty()) {
                int w = queue.poll();
                for (int i = 0; i < adj[w].size(); i++) {
                    int q = adj[w].get(i);
                    if (!visited[q]) {
                        prev[q] = w;
                        if (q == t) {
                            print(prev, s, t);
                            return;
                        }
                        visited[q] = true;
                        queue.add(q);
                    }
                }
            }
        }

        boolean found = false; // 全局变量
        public void dfs(int s, int t) {
            found = false;
            boolean[] visited = new boolean[v];
            int[] prev = new int[v];
            for (int i = 0; i < v; i++) {
                prev[i] = -1;
            }
            recurDfs(s, t, visited, prev);
            if (found) {
                print(prev, s, t);
            }
        }

        private void recurDfs(int w, int t, boolean[] visited, int[] prev) {
            if (found) {
                return;
            }
            visited[w] = true;
            if (w == t) {
                found = true;
                return;
            }
            for (int i = 0; i < adj[w].size(); i++) {
                int q = adj[w].get(i);
                if (!visited[q]) {
                    prev[q] = w;
                    recurDfs(q, t, visited, prev);
                }
            }
        }

        private void nonRecurDfs(int s, int t) {
            boolean[] visited = new boolean[v];
            int[] prev = new int[v];
            for (int i = 0; i < v; i++) {
                prev[i] = -1;
            }
            Stack<Integer> stack = new Stack<>();
            stack.add(s);
            while (!stack.empty()) {
                int w = stack.pop();
                visited[w] = true;
                if (w == t) {
                    print(prev, s, t);
                    break;
                }
                for (int i = 0; i < adj[w].size(); i++) {
                    int q = adj[w].get(i);
                    if (!visited[q]) {
                        prev[q] = w;
                        stack.add(q);
                    }
                }
            }
        }
    }

    public static void print(int[] prev, int s, int t) {
        if (prev[t] != -1 && t != s) {
            print(prev, s, prev[t]);
        }
        System.out.print(t + " ");
    }

    public static void main(String[] args) {
        System.out.println("无向图搜索");
        UndirectGraph ug = new UndirectGraph(8);
        ug.addEdge(0, 1);
        ug.addEdge(0, 3);
        ug.addEdge(1, 2);
        ug.addEdge(1, 4);
        ug.addEdge(2, 5);
        ug.addEdge(3, 4);
        ug.addEdge(4, 5);
        ug.addEdge(4, 6);
        ug.addEdge(5, 7);
        ug.addEdge(6, 7);
        int s = 0;
        int t = 7;
        ug.bfs(s, t);
        System.out.println();
        ug.dfs(s, t);
        System.out.println();
        ug.nonRecurDfs(s, t);
        System.out.println();

        System.out.println("有向图搜索");
        DirectGraph dg = new DirectGraph(8);
        dg.addEdge(0, 1);
        dg.addEdge(0, 3);
        dg.addEdge(1, 2);
        dg.addEdge(1, 4);
        dg.addEdge(2, 5);
        dg.addEdge(3, 4);
        dg.addEdge(4, 5);
        dg.addEdge(4, 6);
        dg.addEdge(5, 7);
        dg.addEdge(6, 7);
        dg.bfs(s, t);
        System.out.println();
        dg.dfs(s, t);
        System.out.println();
        dg.nonRecurDfs(s, t);
        System.out.println();
    }
}
