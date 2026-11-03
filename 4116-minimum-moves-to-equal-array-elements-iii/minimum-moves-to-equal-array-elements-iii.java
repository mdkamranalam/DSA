class Solution {
    public int minMoves(int[] nums) {
        int n = nums.length;
        int max = 0;
        int sum = 0;
        for (int i : nums) {
            max = Math.max(max, i);
            sum += i;
        }
        return max * n - sum;
    }
}