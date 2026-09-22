class Solution {
    public int[] resultArray(int[] nums, int k, int[][] queries) {
        SegmentTree st = new SegmentTree(nums, k);

        int[] output = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int[] query = queries[i];
            st.update(query[0], query[1]);

            output[i] = st.query(query[2], nums.length - 1, query[3]);
        }

        return output;
    }
}

class SegmentTree {
    final int modulo;
    final int[][] data;
    final int[] modProd;
    final int size;

    protected SegmentTree(int[] nums, int k) {
        modulo = k;
        size = nums.length;
        data = new int[size << 2 | 1][k];
        modProd = new int[size << 2 | 1];

        build(1, 0, size - 1, nums);
    }

    private void build(int idx, int l, int r, int[] nums) {
        if (l == r) {
            data[idx][nums[l] % modulo] = 1;
            modProd[idx] = nums[l] % modulo;
            return;
        }

        int mid = (l + r) >> 1;
        build(idx << 1, l, mid, nums);
        build(idx << 1 | 1, mid + 1, r, nums);

        int[] lhs = data[idx << 1];
        int[] rhs = data[idx << 1 | 1];

        for (int i = 0; i < modulo; i++) {
            data[idx][i] = lhs[i];
        }
        for (int i = 0; i < modulo; i++) {
            data[idx][(i * modProd[idx << 1]) % modulo] += rhs[i];
        }

        modProd[idx] = (modProd[idx << 1] * modProd[idx << 1 | 1]) % modulo;
    }

    protected void update(int index, int val) {
        update(1, 0, size - 1, index, val);
    }

    private void update(int idx, int l, int r, int pos, int val) {
        if (l == r) {
            data[idx] = new int[modulo];
            data[idx][val % modulo] = 1;
            modProd[idx] = val % modulo;
            return;
        }

        int mid = (l + r) >> 1;
        if (pos <= mid) {
            update(idx << 1, l, mid, pos, val);
        } else {
            update(idx << 1 | 1, mid + 1, r, pos, val);
        }

        int[] lhs = data[idx << 1];
        int[] rhs = data[idx << 1 | 1];

        for (int i = 0; i < modulo; i++) {
            data[idx][i] = lhs[i];
        }
        for (int i = 0; i < modulo; i++) {
            data[idx][(i * modProd[idx << 1]) % modulo] += rhs[i];
        }

        modProd[idx] = (modProd[idx << 1] * modProd[idx << 1 | 1]) % modulo;
    }

    protected int query(int from, int to, int x) {
        Pair res = query(1, 0, size - 1, from, to);
        return res == null ? 0 : res.data[x];
    }

    private Pair query(int idx, int l, int r, int from, int to) {
        if (from > r || to < r)
            return null;
        if (l >= from && r <= to)
            return new Pair(data[idx], modProd[idx]);

        int mid = (l + r) >> 1;

        return combine(query(idx << 1, l, mid, from, to), query(idx << 1 | 1, mid + 1, r, from, to));
    }

    private Pair combine(Pair left, Pair right) {
        if (left == null)
            return right;
        if (right == null)
            return left;
        int[] res = new int[modulo];
        for (int i = 0; i < modulo; i++) {
            res[i] = left.data[i];
        }
        for (int i = 0; i < modulo; i++) {
            res[(i * left.modProd) % modulo] += right.data[i];
        }

        left.data = res;
        left.modProd = (left.modProd * right.modProd) % modulo;
        return left;
    }

    private static class Pair {
        int[] data;
        int modProd;

        protected Pair(int[] data, int modProd) {
            this.data = data;
            this.modProd = modProd;
        }
    }
}