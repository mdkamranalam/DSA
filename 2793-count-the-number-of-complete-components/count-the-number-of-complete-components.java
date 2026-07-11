class Solution {
    int[] parent, size;

    public int find(int i) {
        if (parent[i] == i) {
            return i;
        }
        return parent[i] = find(parent[i]);
    }

    public void union(int i, int j) {
        int parenti = find(i);
        int parentj = find(j);
        if (parenti != parentj) {
            if (size[parenti] > size[parentj]) {
                size[parenti] += size[parentj];
                parent[parentj] = parenti;
            } else {
                size[parentj] += size[parenti];
                parent[parenti] = parentj;
            }
        }
    }

    public int countCompleteComponents(int n, int[][] edges) {
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
        for (int[] e : edges) {
            int u = e[0], v = e[1];
            union(u, v);
        }
        int[] edge = new int[n];
        for (int e[] : edges) {
            int u = find(e[0]);
            edge[u]++;
        }
        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (find(parent[i]) == i) {
                int node = size[i];
                int required = node * (node - 1) / 2;
                if (required == edge[i])
                    ans++;
            }
        }
        return ans;
    }
}