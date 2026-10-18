class Solution {
    private static final long MOD = 1_000_000_007L;
    private long[] fact;
    private long[] invFact;

    public int numOfWays(int[] nums) {
        int n = nums.length;
        fact = new long[n + 1];
        invFact = new long[n + 1];

        fact[0] = 1;
        for (int i = 1; i <= n; i++) {
            fact[i] = fact[i - 1] * i % MOD;
        }

        invFact[n] = modPow(fact[n], MOD - 2);
        for (int i = n; i >= 1; i--) {
            invFact[i - 1] = invFact[i] * i % MOD;
        }

        long result = countWays(nums);
        return (int) ((result - 1 + MOD) % MOD);
    }

    private long countWays(int[] nums) {
        int n = nums.length;
        if (n <= 2) {
            return 1;
        }

        int root = nums[0];
        int leftSize = 0;
        for (int i = 1; i < n; i++) {
            if (nums[i] < root) {
                leftSize++;
            }
        }

        int rightSize = n - 1 - leftSize;
        int[] left = new int[leftSize];
        int[] right = new int[rightSize];

        int leftIndex = 0;
        int rightIndex = 0;
        for (int i = 1; i < n; i++) {
            if (nums[i] < root) {
                left[leftIndex++] = nums[i];
            } else {
                right[rightIndex++] = nums[i];
            }
        }

        long leftWays = countWays(left);
        long rightWays = countWays(right);
        long interleavings = combination(n - 1, leftSize);

        return interleavings * leftWays % MOD * rightWays % MOD;
    }

    private long combination(int n, int k) {
        return fact[n] * invFact[k] % MOD * invFact[n - k] % MOD;
    }

    private long modPow(long base, long exponent) {
        long result = 1;
        while (exponent > 0) {
            if ((exponent & 1) == 1) {
                result = result * base % MOD;
            }
            base = base * base % MOD;
            exponent >>= 1;
        }
        return result;
    }
}