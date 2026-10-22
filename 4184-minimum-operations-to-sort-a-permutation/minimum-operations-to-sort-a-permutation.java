class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;
        int idx = -1;

        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) {
                idx = i;
                break;
            }
        }

        boolean isInc = true;
        boolean isDec = true;

        for (int i = 0; i < n; i++) {
            int curr = nums[(idx + i) % n];
            int increase = i;
            int decrease = (n - i) % n;

            if (curr != increase)
                isInc = false;
            if (curr != decrease)
                isDec = false;
        }

        int minOp = Integer.MAX_VALUE;
        if (isInc) {
            int optA = idx;
            int optB = n - idx + 2;
            minOp = Math.min(optA, optB);
        }

        if (isDec) {
            int optA = idx + 2;
            int optB = n - idx;
            minOp = Math.min(minOp, Math.min(optA, optB));
        }
        return minOp == Integer.MAX_VALUE ? -1 : minOp;
    }
}