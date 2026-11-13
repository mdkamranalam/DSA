class Solution {
    public long countNonDecreasingSubarrays(int[] nums, int k) {
        int n = nums.length;
        long totalSubarrays = 0;
        long cost = 0;
        int r = n - 1;
        Deque<int[]> deque = new ArrayDeque<>();
        for (int l = n - 1; l >= 0; l--) {
            int val = nums[l];
            int count = 1;
            while (!deque.isEmpty() && deque.peekFirst()[0] <= val) {
                int[] front = deque.pollFirst();
                cost += (long) (val - front[0]) * front[1];
                count += front[1];
            }
            deque.addFirst(new int[] { val, count });
            while (cost > k) {
                int[] back = deque.peekLast();
                cost -= (back[0] - nums[r]);
                back[1]--;
                if (back[1] == 0) {
                    deque.pollLast();
                }
                r--;
            }
            totalSubarrays += (r - l + 1);
        }
        return totalSubarrays;
    }
}

// Time Complexity: O(n)
// Space Complexity: O(n)