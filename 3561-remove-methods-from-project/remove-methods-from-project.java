class Solution {
    public List<Integer> remainingMethods(int n, int k, int[][] invocations) {
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());

        for (int[] e : invocations) {
            g[e[0]].add(e[1]);
        }

        boolean[] isSuspicous = new boolean[n];

        dfs(k, g, isSuspicous);

        for (int[] e : invocations) {
            if (!isSuspicous[e[0]] && isSuspicous[e[1]]) {
                List<Integer> ans = new ArrayList<>(n);
                for (int i = 0; i < n; i++) {
                    ans.add(i);
                }

                return ans;
            }
        }

        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (!isSuspicous[i]) {
                ans.add(i);
            }
        }
        return ans;
    }

    private void dfs(int i, List<Integer>[] g, boolean[] isSuspicous) {
        isSuspicous[i] = true;

        for (int y : g[i]) {
            if (!isSuspicous[y]) {
                dfs(y, g, isSuspicous);
            }
        }
    }
}