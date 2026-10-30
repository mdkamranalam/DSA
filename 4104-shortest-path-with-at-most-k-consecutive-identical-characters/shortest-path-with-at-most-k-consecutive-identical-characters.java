class Solution {
    public class State {
        int u;
        int cst;
        int d;

        public State(int u, int cst, int d) {
            this.u = u;
            this.cst = cst;
            this.d = d;
        }
    }

    public int shortestPath(int n, int[][] edges, String labels, int k) {
        int INF = Integer.MAX_VALUE;
        ArrayList<int[]> graph[] = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            graph[edge[0]].add(new int[] { edge[1], edge[2] });
        }
        int dist[][] = new int[n][k + 1];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], INF);
        }
        dist[0][1] = 0;
        PriorityQueue<State> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.cst, b.cst));
        pq.add(new State(0, 0, 1));

        while (!pq.isEmpty()) {
            State curr = pq.remove();
            int u = curr.u;
            int cst = curr.cst;
            int d = curr.d;

            if (cst > dist[u][d])
                continue;
            if (u == n - 1)
                return (int) cst;

            for (int neigh[] : graph[u]) {
                int v = neigh[0];
                int w = neigh[1];
                int newd = (labels.charAt(u) == labels.charAt(v)) ? d + 1 : 1;
                if (newd > k)
                    continue;

                if (cst + w < dist[v][newd]) {
                    dist[v][newd] = cst + w;
                    pq.add(new State(v, cst + w, newd));
                }
            }
        }

        int ans = INF;
        for (int i = 0; i <= k; i++) {
            ans = Math.min(ans, dist[n - 1][i]);
        }

        return ans == INF ? -1 : ans;

    }
}