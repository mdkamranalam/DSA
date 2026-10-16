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

        int[] last = new int[100001];
        java.util.Arrays.fill(last, -1);

        long answer = 0;

        for (int r = 0; r < n; r++) {
            int x = nums[r];

            int previous = last[x];

            rangeAdd(
                    1,
                    0,
                    n - 1,
                    previous + 1,
                    r);

            answer = (answer + squareSum[1]) % MOD;

            last[x] = r;
        }

        return (int) answer;
    }

    private void rangeAdd(
            int node,
            int left,
            int right,
            int ql,
            int qr) {

        if (ql > right || qr < left) {
            return;
        }

        if (ql <= left && right <= qr) {
            apply(node, left, right);
            return;
        }

        push(node, left, right);

        int mid = left + (right - left) / 2;

        rangeAdd(node * 2, left, mid, ql, qr);
        rangeAdd(node * 2 + 1, mid + 1, right, ql, qr);

        pull(node);
    }

    private void apply(int node, int left, int right) {
        long len = right - left + 1L;

        // d -> d + 1
        //
        // (d + 1)^2 = d^2 + 2d + 1

        squareSum[node] = (squareSum[node]
                + 2L * sum[node]
                + len) % MOD;

        sum[node] = (sum[node] + len) % MOD;

        lazy[node] = (lazy[node] + 1) % MOD;
    }

    private void push(int node, int left, int right) {
        if (lazy[node] == 0 || left == right) {
            return;
        }

        int mid = left + (right - left) / 2;

        // Every pending update is +1.
        long pending = lazy[node];

        applyTimes(node * 2, left, mid, pending);
        applyTimes(node * 2 + 1, mid + 1, right, pending);

        lazy[node] = 0;
    }

    private void applyTimes(
            int node,
            int left,
            int right,
            long k) {

        long len = right - left + 1L;
        long oldSum = sum[node];

        // sum(d + k)
        sum[node] = (oldSum + k * len) % MOD;

        // sum((d + k)^2)
        squareSum[node] = (squareSum[node]
                + 2L * k % MOD * oldSum
                + (k * k % MOD) * (len % MOD)) % MOD;

        lazy[node] = (lazy[node] + k) % MOD;
    }

    private void pull(int node) {
        sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % MOD;

        squareSum[node] = (squareSum[node * 2]
                + squareSum[node * 2 + 1])
                % MOD;
    }
}