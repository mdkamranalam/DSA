class Solution {
    public int[] lexicographicallySmallestArray(int[] nums, int limit) {

        int n = nums.length;
        int[][] m = new int[n][2];

        for (int i = 0; i < n; i++) {
            m[i][0] = nums[i];
            m[i][1] = i;
        }
        Arrays.sort(m, (a, b) -> a[0] - b[0]);

        int start = 0;
        while (start < n) {

            int end = start;
            while (end + 1 < n && m[end + 1][0] - m[end][0] <= limit) {
                end++;
            }

            int[] indices = new int[end - start + 1];
            for (int i = start; i <= end; i++) {
                indices[i - start] = m[i][1];
            }

            Arrays.sort(indices);

            for (int i = 0; i < indices.length; i++) {
                nums[indices[i]] = m[start + i][0];
            }

            start = end + 1;
        }
        return nums;
    }
}