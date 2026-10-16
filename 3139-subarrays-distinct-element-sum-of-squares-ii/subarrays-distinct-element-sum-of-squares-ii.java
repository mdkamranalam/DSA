class Solution {
    static final long MOD = 1_000_000_007L;

    long[] sum;
    long[] squareSum;
    long[] lazy;

    public int sumCounts(int[] nums) {
        int n = nums.length;

        sum = new long[4 * n];
        squareSum = new long[4 * n];
        lazy = new long[4 * n];

        // nums[i] <= 10^5
        int[] last = new int[100001];

        // -1 means the value has not appeared yet.
        java.util.Arrays.fill(last, -1);

        long answer = 0;

        for (int r = 0; r < n; r++) {
            int x = nums[r];

            int previous = last[x];

            // Distinct count increases for starting indices
            // [previous + 1, r].
            rangeAdd(1, 0, n - 1, previous + 1, r);

            // All positions after r are still zero, so the root
            // contains exactly the sum of squares for all subarrays
            // ending at r.
            answer = (answer + squareSum[1]) % MOD;

            last[x] = r;
        }

        return (int) answer;
    }

    private void rangeAdd(
            int node,
            int left,
            int right,
            int queryLeft,
            int queryRight) {

        if (queryLeft > right || queryRight < left) {
            return;
        }

        if (queryLeft <= left && right <= queryRight) {
            apply(node, left, right);
            return;
        }

        push(node, left, right);

        int mid = left + (right - left) / 2;

        rangeAdd(
                node * 2,
                left,
                mid,
                queryLeft,
                queryRight);

        rangeAdd(
                node * 2 + 1,
                mid + 1,
                right,
                queryLeft,
                queryRight);

        pull(node);
    }

    private void apply(int node, int left, int right) {
        long length = right - left + 1L;

        // If every d becomes d + 1:
        //
        // (d + 1)^2 = d^2 + 2d + 1
        //
        squareSum[node] = (squareSum[node]
                + 2L * sum[node]
                + length) % MOD;

        sum[node] = (sum[node] + length) % MOD;

        lazy[node] = (lazy[node] + 1) % MOD;
    }

    private void push(int node, int left, int right) {
        if (lazy[node] == 0 || left == right) {
            return;
        }

        int mid = left + (right - left) / 2;

        long pending = lazy[node];

        /*
         * Usually lazy propagation can contain values > 1,
         * so apply the increment repeatedly in mathematical form.
         *
         * For k increments:
         *
         * (d + k)^2 = d^2 + 2kd + k^2
         *
         * Therefore we handle the pending value directly.
         */
        applyMultiple(node * 2, left, mid, pending);
        applyMultiple(node * 2 + 1, mid + 1, right, pending);

        lazy[node] = 0;
    }

    private void applyMultiple(
            int node,
            int left,
            int right,
            long k) {

        long length = right - left + 1L;

        long oldSum = sum[node];

        // sum(d + k) = sum(d) + k * length
        sum[node] = (oldSum + k * length) % MOD;

        // sum((d + k)^2)
        //
        // = sum(d^2) + 2k * sum(d) + k^2 * length
        long kSquared = (k * k) % MOD;

        squareSum[node] = (squareSum[node]
                + (2L * k % MOD) * oldSum
                + kSquared * (length % MOD))
                % MOD;

        lazy[node] = (lazy[node] + k) % MOD;
    }

    private void pull(int node) {
        sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % MOD;

        squareSum[node] = (squareSum[node * 2]
                + squareSum[node * 2 + 1])
                % MOD;
    }
}