class Solution {
    int[] head, to, next;
    int node = 0;

    public int dfs(int u, boolean[] visited) {
        int link = 0;
        if (!visited[u]) {
            node++;
            visited[u] = true;
            for (int i = head[u]; i != -1; i = next[i]) {
                link += dfs(to[i], visited);
                link++;
            }
        }
        return link;
    }

    public boolean check(int link, int node) {
        return (link == (node * (node - 1)));
    }

    public void addEdge(int u, int v, int ind) {
        to[ind] = v;
        next[ind] = head[u];
        head[u] = ind;
    }

    public int countCompleteComponents(int n, int[][] edges) {
        int m = edges.length;
        this.head = new int[n];
        Arrays.fill(head, -1);
        this.to = new int[m * 2];
        this.next = new int[m * 2];
        int ind = 0;
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            addEdge(u, v, ind++);
            addEdge(v, u, ind++);
        }
        boolean[] visited = new boolean[n];
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                this.node = 0;
                int link = dfs(i, visited);
                if (check(link, node))
                    count++;
            }
        }
        return count;
    }
}