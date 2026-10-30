class Solution {
    public int shortestPath(int n, int[][] edges, String labels, int k) {
        List<List<int[]>> adj = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        for (int[] e : edges) {
            adj.get(e[0]).add(new int[] { e[1], e[2] });
        }

        int[][] cost = new int[n][k + 1];

        for (int[] row : cost) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        PriorityQueue<int[]> q = new PriorityQueue<>((a, b) -> Integer.compare(a[2], b[2]));
        q.add(new int[] { 0, 1, 0 });
        cost[0][1] = 0;

        while (!q.isEmpty()) {
            int[] curr = q.poll();
            int node = curr[0];
            int cons = curr[1];
            int wt = curr[2];

            if (wt > cost[node][cons]) {
                continue;
            }

            if (node == n - 1) {
                return wt;
            }

            char ch1 = labels.charAt(node);

            for (int[] nei : adj.get(node)) {
                int neiNode = nei[0];
                int neiWt = nei[1];

                char ch2 = labels.charAt(neiNode);

                if (ch1 == ch2 && cons == k) {
                    continue;
                }

                int newCons = (ch1 == ch2) ? cons + 1 : 1;
                int newWt = wt + neiWt;

                if (newWt < cost[neiNode][newCons]) {
                    cost[neiNode][newCons] = newWt;
                    q.add(new int[] { neiNode, newCons, newWt });
                }
            }
        }

        return -1;
    }
}