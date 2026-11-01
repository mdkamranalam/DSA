class Solution {
    public int minMoves(int[] nums) {
        long sum = 0;
        int min = nums[0];
        for (int x : nums) {
            sum += x;
            min = Math.min(min, x);
        }
        return (int) (sum - (long) min * nums.length);
    }
}