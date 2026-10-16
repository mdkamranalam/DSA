import java.util.Arrays;

class Solution_segtree {
    static final int MOD = 1_000_000_007;

    static class SegmentTree {
        long[] lazy;
        long[] rangeSum;
        long[] rangeSumOfSquares;
        int size;

        SegmentTree(int size) {
            this.size = size;
            lazy = new long[4 * size];
            rangeSum = new long[4 * size];
            rangeSumOfSquares = new long[4 * size];
        }

        void applyLazy(int left, int right, int node) {
            if (left != right) {
                lazy[node * 2 + 1] += lazy[node];
                lazy[node * 2 + 2] += lazy[node];
            }
            long length = right - left + 1;
            long newSum = rangeSum[node] + lazy[node] * length;
            long newSumOfSquares = rangeSumOfSquares[node] + lazy[node] * rangeSum[node] * 2
                    + lazy[node] * lazy[node] * length;

            rangeSum[node] = newSum % MOD;
            rangeSumOfSquares[node] = newSumOfSquares % MOD;
            lazy[node] = 0;
        }

        // overload to emulate C++ default arguments (left = 0, right = -1, node = 0)
        void markDistinctRange(int queryStart, int queryEnd) {
            markDistinctRange(queryStart, queryEnd, 0, -1, 0);
        }

        void markDistinctRange(int queryStart, int queryEnd, int left, int right, int node) {
            if (right == -1)
                right += size;
            applyLazy(left, right, node);

            if (right < queryStart || left > queryEnd)
                return;
            if (left >= queryStart && right <= queryEnd) {
                lazy[node] = 1;
                applyLazy(left, right, node);
                return;
            }

            int mid = (left + right) >> 1;
            markDistinctRange(queryStart, queryEnd, left, mid, node * 2 + 1);
            markDistinctRange(queryStart, queryEnd, mid + 1, right, node * 2 + 2);

            rangeSum[node] = (rangeSum[node * 2 + 1] + rangeSum[node * 2 + 2]) % MOD;
            rangeSumOfSquares[node] = (rangeSumOfSquares[node * 2 + 1] + rangeSumOfSquares[node * 2 + 2]) % MOD;
        }
    }

    public int sumCounts(int[] nums) {
        int n = nums.length;
        int[] lastOccurrence = new int[100001];
        Arrays.fill(lastOccurrence, -1);

        SegmentTree tree = new SegmentTree(n);
        long answer = 0;

        for (int i = 0; i < n; i++) {

            int start = lastOccurrence[nums[i]] + 1, end = i;
            tree.markDistinctRange(start, end);

            answer = (answer + tree.rangeSumOfSquares[0]) % MOD;

            lastOccurrence[nums[i]] = i;
        }
        return (int) answer;
    }
}

//////////// Fenwick tree approach

class Solution {
    static final int MOD = 1_000_000_007;

    // Fenwick tree (BIT) supporting range-update, range-sum-query in O(log n)
    static class FenwickTree {
        long[] treeMul; // Binary Indexed Tree #1 (multiplier part)
        long[] treeAdd; // Binary Indexed Tree #2 (offset part)
        int size;

        FenwickTree(int size) {
            this.size = size;
            treeMul = new long[size + 2];
            treeAdd = new long[size + 2];
        }

        private void pointUpdate(long[] tree, int index, long delta) {
            for (; index <= size; index += index & (-index)) {
                tree[index] = (tree[index] + delta) % MOD;
            }
        }

        // adds `delta` to every element in 1-indexed inclusive range [left, right]
        void rangeAdd(int left, int right, long delta) {
            pointUpdate(treeMul, left, delta);
            pointUpdate(treeMul, right + 1, -delta);
            pointUpdate(treeAdd, left, delta * (left - 1) % MOD);
            pointUpdate(treeAdd, right + 1, -delta * right % MOD);
        }

        private long prefixQuery(long[] tree, int index) {
            long result = 0;
            for (; index > 0; index -= index & (-index)) {
                result = (result + tree[index]) % MOD;
            }
            return result;
        }

        private long prefixSum(int index) {
            long value = (((long) index % MOD) * prefixQuery(treeMul, index) - prefixQuery(treeAdd, index)) % MOD;
            return (value + MOD) % MOD;
        }

        // sum of elements in 1-indexed inclusive range [left, right]
        long rangeSum(int left, int right) {
            if (left > right)
                return 0;
            long value = (prefixSum(right) - prefixSum(left - 1)) % MOD;
            return (value + MOD) % MOD;
        }
    }

    public int sumCounts(int[] nums) {
        int n = nums.length;
        int[] lastOccurrence = new int[100001];
        Arrays.fill(lastOccurrence, -1);

        FenwickTree tree = new FenwickTree(n);
        long totalSumOfSquares = 0; // equivalent to the segment tree's rangeSumOfSquares[0]
        long answer = 0;

        for (int i = 0; i < n; i++) {

            int start = lastOccurrence[nums[i]] + 1, end = i;
            // convert 0-indexed [start, end] to 1-indexed [left, right] for the BIT
            int left = start + 1, right = end + 1;
            long rangeLength = right - left + 1;

            long sumBeforeUpdate = tree.rangeSum(left, right);
            // Δ(sum of squares) when adding +1 to each element in the range:
            // sum((c+1)^2 - c^2) = sum(2c + 1) = 2*sumBeforeUpdate + rangeLength
            totalSumOfSquares = (totalSumOfSquares + 2 * sumBeforeUpdate + rangeLength) % MOD;

            tree.rangeAdd(left, right, 1);

            answer = (answer + totalSumOfSquares) % MOD;

            lastOccurrence[nums[i]] = i;
        }
        return (int) answer;
    }
}