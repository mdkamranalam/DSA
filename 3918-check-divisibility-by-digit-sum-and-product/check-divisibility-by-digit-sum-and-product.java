class Solution {
    public boolean checkDivisibility(int n) {
        int sum = 0;
        int prod = 1;
        int x = n;
        while (x != 0) {
            int v = x % 10;
            x /= 10;
            sum += v;
            prod *= v;
        }
        return n % (sum + prod) == 0;
    }
}