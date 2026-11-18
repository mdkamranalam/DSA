class Solution {
    public void sortColors(int[] nums) {
        int i = 0;
        int j = nums.length - 1;
        int k = 0;

        while (k <= j) {
            if (nums[k] == 1) {
                k++;
            } else if (nums[k] == 2) {
                swap(nums, k, j);
                j--;
            } else {
                swap(nums, i, k);
                i++;
                k++;
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}

// Time Complexity: O(n)
// Space Complexity: O(1)
