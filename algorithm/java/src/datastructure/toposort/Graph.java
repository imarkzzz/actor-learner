package datastructure.toposort;

import java.util.*;

public class Graph {
    private int v; // 顶点个数
    private LinkedList<Integer>[] adj; // 邻接表

    public Graph(int v) {
        this.v = v;
        this.adj = new LinkedList[v];
        for (int i = 0; i < v; i++) {
            this.adj[i] = new LinkedList<>();
        }
    }

    public void addEdge(int s, int t) { // s 先于 t, 边 s -> t
        adj[s].add(t);
    }

    /**
     * 使用Kahn算法进行拓扑排序
     * @return
     */
    public List<Integer> topoSortByKahn() {
        int[] inDegree = new int[v]; // 统计每个顶点的入度
        for (int i = 0; i < v; i++) {
            for (Integer w: adj[i]) { // i -> w
                inDegree[w]++;
            }
        }
        LinkedList<Integer> queue = new LinkedList<>();
        for (int i = 0; i < v; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }
        List<Integer> sortResult = new ArrayList<>();
        while (!queue.isEmpty()) {
            int i = queue.remove();
            sortResult.add(i);
            for (int j = 0; j < adj[i].size(); j++) {
                int k = adj[i].get(j);
                inDegree[k]--;
                if (inDegree[k] == 0) queue.add(k);
            }
        }
        return sortResult;
    }

    /**
     * 使用深度优先进行拓扑排序
     * @return
     */
    public List<Integer> topoSortByDFS() {
        // 先构建逆邻接表， 边 s -> t 表示， s 依赖 t， t 先于 s
        LinkedList<Integer>[] inverseAdj = new LinkedList[v];
        for (int i = 0; i < v; i++) {
            inverseAdj[i] = new LinkedList<>();
        }
        for (int i = 0; i < v; i++) {
            for (int j = 0; j < adj[i].size(); j++) {
                int w = adj[i].get(j);
                inverseAdj[w].add(i);
            }
        }

        boolean[] visited = new boolean[v];
        List<Integer> sortResult = new ArrayList<>();
        for (int i = 0; i < v; i++) {
            if (!visited[i]) {
                dfs(i, inverseAdj, visited, sortResult);
            }
        }
        return sortResult;
    }

    private void dfs(int vertex, LinkedList<Integer>[] inverseAdj, boolean[] visited, List<Integer> sortResult) {
        visited[vertex] = true;
        for (int i = 0; i < inverseAdj[vertex].size(); i++) {
            int w = inverseAdj[vertex].get(i);
            if (visited[w]) {
                continue;
            }
            dfs(w, inverseAdj, visited, sortResult);
        }
        sortResult.add(vertex);
    }

    /**
     * 使用广度优先算法进行拓扑排序
     * @return
     */
    public List<Integer> topoSortByBFS() {
        List<Integer> sortResult = new ArrayList<>();
        // 先构建逆邻接表， 边 s -> t 表示， s 依赖 t， t 先于 s
        LinkedList<Integer>[] inverseAdj = new LinkedList[v];
        for (int i = 0; i < v; i++) {
            inverseAdj[i] = new LinkedList<>();
        }
        for (int i = 0; i < v; i++) {
            for (int j = 0; j < adj[i].size(); j++) {
                int w = adj[i].get(j);
                inverseAdj[w].add(i);
            }
        }
        Queue<Integer> queue = new LinkedList<>();
        Stack<Integer> stack = new Stack<>();

        boolean[] visited = new boolean[v];
        for (int i = 0; i < v; i++) {
            if (!visited[i]) {
                queue.add(i);
            }
            while (!queue.isEmpty()) {
                int k = queue.remove();
                visited[k] = true;
                stack.add(k);
                for (int j = 0; j < inverseAdj[k].size(); j++) {
                    int w = inverseAdj[k].get(j);
                    queue.add(w);
                }
            }
        }
        visited = new boolean[v];
        while (!stack.empty()) {
            int w = stack.pop();
            if (!visited[w]) {
                visited[w] = true;
                sortResult.add(w);
            }
        }
        return sortResult;
    }

    public static void main(String[] args) {
        // 依赖关系
        String[][] dressOrders = {
                {"内裤", "裤子"}, // 内裤 -> 裤子
                {"内裤", "鞋子"},
                {"裤子", "鞋子"},
                {"裤子", "腰带"},
                {"袜子", "鞋子"},
                {"衬衣", "外套"},
                {"衬衣", "领带"}
        };
        Map<String, Integer> dressIdxDict = new HashMap<>();
        Map<Integer, String> idxDressDict = new HashMap<>();

        for (int i = 0; i < dressOrders.length; i++) {
            String[] dressOrder = dressOrders[i];
            for (int j = 0; j < 2; j++) {
                String dress = dressOrder[j];
                if (!dressIdxDict.containsKey(dress)) {
                    Integer idx = dressIdxDict.size();
                    dressIdxDict.put(dress, idx);
                    idxDressDict.put(idx, dress);
                }
            }
        }
        // 构建依赖图
        Graph graph = new Graph(dressIdxDict.size());
        for (int i = 0; i < dressOrders.length; i++) {
            String[] dressOrder = dressOrders[i];
            graph.addEdge(dressIdxDict.get(dressOrder[0]), dressIdxDict.get(dressOrder[1]));
        }
        List<Integer> sortResult = graph.topoSortByKahn();
        print(sortResult, idxDressDict);
        sortResult = graph.topoSortByDFS();
        print(sortResult, idxDressDict);
        sortResult = graph.topoSortByBFS();
        print(sortResult, idxDressDict);
    }

    /**
     * 打印拓扑排序结果
     * @param sortResult 拓扑排序结果
     * @param idxDressDict 元素字典
     */
    public static void print(List<Integer> sortResult, Map<Integer, String> idxDressDict) {
        if (sortResult.isEmpty()) return;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sortResult.size(); i++) {
            sb.append(" -> " + idxDressDict.get(sortResult.get(i)));
        }
        System.out.println(sb.toString().substring(4));
    }
}
