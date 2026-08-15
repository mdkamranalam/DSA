class Solution {
    public int longestSubsequence(int[] nums) {
        int xor = 0, countZero = 0;
        int n = nums.length;
        for (int i : nums) {
            xor ^= i;
            countZero += i == 0 ? 1 : 0;
        }
        if (xor != 0) {
            return n;
        }

        return countZero == n ? 0 : n - 1;
    }
}