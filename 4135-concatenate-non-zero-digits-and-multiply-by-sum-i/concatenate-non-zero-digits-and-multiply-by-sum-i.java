class Solution {
    public long sumAndMultiply(int n) {
        int p = 1;
        int x = 0;
        int sum = 0;
        for (; n > 0; n /= 10) {
            int v = n % 10;
            if (v != 0) {
                sum += v;
                x += p * v;
                p *= 10;
            }
        }
        return 1L * x * sum;
    }
}