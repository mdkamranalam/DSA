class Solution {
    private List<Integer>[] graph;

    private int[] parent;
    private int[] depth;
    private int[] size;
    private int[] heavy;

    private int[] head;
    private int[] pos;
    private int[] nodeAtPos;

    private int currentPos;

    private SegmentTree segmentTree;

    private char[] values;

    public List<Boolean> palindromePath(
            int n,
            int[][] edges,
            String s,
            String[] queries) {

        /*
         * Build adjacency list
         */
        graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];

            graph[u].add(v);
            graph[v].add(u);
        }

        values = s.toCharArray();

        /*
         * Step 1:
         * Calculate parent, depth, subtree size
         * and heavy child for every node.
         */
        parent = new int[n];
        depth = new int[n];
        size = new int[n];
        heavy = new int[n];

        Arrays.fill(parent, -1);
        Arrays.fill(heavy, -1);

        dfs(0, -1);

        /*
         * Step 2:
         * Heavy-Light Decomposition.
         */
        head = new int[n];
        pos = new int[n];
        nodeAtPos = new int[n];

        currentPos = 0;

        decompose(0, 0);

        /*
         * Step 3:
         * Create the array corresponding to HLD order.
         */
        int[] baseArray = new int[n];

        for (int node = 0; node < n; node++) {
            baseArray[pos[node]] = charMask(values[node]);
        }

        /*
         * Step 4:
         * Segment tree for XOR.
         */
        segmentTree = new SegmentTree(baseArray);

        /*
         * Step 5:
         * Process queries.
         */
        List<Boolean> answer = new ArrayList<>();

        for (String query : queries) {

            String[] parts = query.split(" ");

            if (parts[0].equals("update")) {

                int node = Integer.parseInt(parts[1]);
                char newChar = parts[2].charAt(0);

                values[node] = newChar;

                /*
                 * Update this node's position in
                 * the segment tree.
                 */
                segmentTree.update(
                        pos[node],
                        charMask(newChar)
                );

            } else {

                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);

                int mask = queryPath(u, v);

                /*
                 * A string can be rearranged into a palindrome
                 * iff at most one character has odd frequency.
                 *
                 * Therefore at most one bit can be set.
                 */
                boolean possible = (mask & (mask - 1)) == 0;

                answer.add(possible);
            }
        }

        return answer;
    }

    /*
     * DFS to calculate:
     *
     * parent[node]
     * depth[node]
     * size[node]
     * heavy[node]
     *
     * heavy[node] = child having the largest subtree.
     */
    private void dfs(int node, int par) {

        parent[node] = par;
        size[node] = 1;

        int maxSubtreeSize = 0;

        for (int next : graph[node]) {

            if (next == par) {
                continue;
            }

            depth[next] = depth[node] + 1;

            dfs(next, node);

            size[node] += size[next];

            if (size[next] > maxSubtreeSize) {
                maxSubtreeSize = size[next];
                heavy[node] = next;
            }
        }
    }

    /*
     * HLD decomposition.
     *
     * Nodes belonging to the same heavy chain
     * receive consecutive positions.
     */
    private void decompose(int node, int chainHead) {

        head[node] = chainHead;
        pos[node] = currentPos;
        nodeAtPos[currentPos] = node;

        currentPos++;

        /*
         * First process the heavy child.
         * This keeps the heavy chain contiguous.
         */
        if (heavy[node] != -1) {
            decompose(heavy[node], chainHead);
        }

        /*
         * Then process light children.
         */
        for (int next : graph[node]) {

            if (next == parent[node] || next == heavy[node]) {
                continue;
            }

            decompose(next, next);
        }
    }

    /*
     * Returns XOR of character masks along
     * the path from u to v.
     */
    private int queryPath(int u, int v) {

        int result = 0;

        while (head[u] != head[v]) {

            /*
             * Make u belong to the deeper chain.
             */
            if (depth[head[u]] < depth[head[v]]) {
                int temp = u;
                u = v;
                v = temp;
            }

            /*
             * Query from the chain head to u.
             */
            result ^= segmentTree.query(
                    pos[head[u]],
                    pos[u]
            );

            /*
             * Move u to the parent of its chain head.
             */
            u = parent[head[u]];
        }

        /*
         * Now u and v are in the same heavy chain.
         */
        if (depth[u] > depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        /*
         * Query from u to v.
         */
        result ^= segmentTree.query(
                pos[u],
                pos[v]
        );

        return result;
    }

    /*
     * Convert a character into a 26-bit mask.
     *
     * a -> 000...001
     * b -> 000...010
     * c -> 000...100
     * ...
     */
    private int charMask(char c) {
        return 1 << (c - 'a');
    }

    /*
     * Segment Tree
     *
     * Each node stores XOR of character masks
     * in its range.
     */
    static class SegmentTree {

        private int[] tree;
        private int n;

        SegmentTree(int[] arr) {

            n = arr.length;

            tree = new int[4 * n];

            build(1, 0, n - 1, arr);
        }

        private void build(
                int node,
                int left,
                int right,
                int[] arr) {

            if (left == right) {
                tree[node] = arr[left];
                return;
            }

            int mid = left + (right - left) / 2;

            build(
                    node * 2,
                    left,
                    mid,
                    arr
            );

            build(
                    node * 2 + 1,
                    mid + 1,
                    right,
                    arr
            );

            tree[node] =
                    tree[node * 2] ^
                    tree[node * 2 + 1];
        }

        void update(int index, int value) {

            update(
                    1,
                    0,
                    n - 1,
                    index,
                    value
            );
        }

        private void update(
                int node,
                int left,
                int right,
                int index,
                int value) {

            if (left == right) {
                tree[node] = value;
                return;
            }

            int mid = left + (right - left) / 2;

            if (index <= mid) {
                update(
                        node * 2,
                        left,
                        mid,
                        index,
                        value
                );
            } else {
                update(
                        node * 2 + 1,
                        mid + 1,
                        right,
                        index,
                        value
                );
            }

            tree[node] =
                    tree[node * 2] ^
                    tree[node * 2 + 1];
        }

        int query(int queryLeft, int queryRight) {

            return query(
                    1,
                    0,
                    n - 1,
                    queryLeft,
                    queryRight
            );
        }

        private int query(
                int node,
                int left,
                int right,
                int queryLeft,
                int queryRight) {

            /*
             * Completely outside.
             */
            if (queryRight < left || right < queryLeft) {
                return 0;
            }

            /*
             * Completely inside.
             */
            if (queryLeft <= left && right <= queryRight) {
                return tree[node];
            }

            int mid = left + (right - left) / 2;

            int leftResult = query(
                    node * 2,
                    left,
                    mid,
                    queryLeft,
                    queryRight
            );

            int rightResult = query(
                    node * 2 + 1,
                    mid + 1,
                    right,
                    queryLeft,
                    queryRight
            );

            return leftResult ^ rightResult;
        }
    }
}