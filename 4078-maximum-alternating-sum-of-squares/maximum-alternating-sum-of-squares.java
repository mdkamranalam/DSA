class Solution {
    public long maxAlternatingSum(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            nums[i] = Math.abs(nums[i]);
        }
        Arrays.sort(nums);
        long ans = 0L;
        int i = 0;
        int j = nums.length - 1;
        while (i < j && i < nums.length && j >= 0 && j != i) {
            ans += (1L * nums[j] * nums[j]);
            ans -= (1L * nums[i] * nums[i]);
            i++;
            j--;
        }
        if (i == j) {
            ans += (1L * nums[i] * nums[i]);
        }
        return ans;
    }
}