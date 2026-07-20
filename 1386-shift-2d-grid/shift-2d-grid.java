class Solution {
    public List<List<Integer>> shiftGrid(int[][] grid, int k) {
        List<List<Integer>> ans = new ArrayList<>();
        int m = grid.length;
        int n = grid[0].length;
        int total = m * n;
        k = k % total;
        int[][] res = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int idx = i * n + j;
                int newidx = (idx + k) % total;

                int row = newidx / n;
                int col = newidx % n;
                res[row][col] = grid[i][j];

            }
        }
        for (int i = 0; i < m; i++) {
            List<Integer> list = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                list.add(res[i][j]);
            }
            ans.add(list);
        }
        return ans;
    }
}