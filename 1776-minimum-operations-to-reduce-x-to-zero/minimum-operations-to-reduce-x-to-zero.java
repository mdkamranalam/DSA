class Solution {
    public int minOperations(int[] nums, int x) {
        int sum = 0;
        for (int i : nums) {
            sum += i;
        }

        int target = sum - x;
        int left = 0, right = 0;
        int windowSum = 0;
        int maxWindow = Integer.MIN_VALUE;
        while (right < nums.length) {
            int r = nums[right];
            right++;
            windowSum += r;

            while (left < right && windowSum > target) {
                int l = nums[left];
                left++;
                windowSum -= l;
            }

            if (windowSum == target) {
                maxWindow = Math.max(maxWindow, right - left);
            }
        }

        return maxWindow == Integer.MIN_VALUE ? -1 : nums.length - maxWindow;
    }
}