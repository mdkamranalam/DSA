class Solution {
    public int removeDuplicates(int[] nums) {
        int n = nums.length;
        int i = 0;
        int j = 1;
        int ans = 1;
        while (j < n) {
            if (nums[j] == nums[j - 1]) {
                j++;
                continue;
            }

            nums[i + 1] = nums[j];
            i++;
            ans++;
            j++;
        }
        return ans;
    }
}