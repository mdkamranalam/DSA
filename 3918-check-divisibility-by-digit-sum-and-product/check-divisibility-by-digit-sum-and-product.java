class Solution {
    public boolean checkDivisibility(int n) {
        int sum = 0;
        int prod = 1;
        int original = n;
        while (n != 0) {
            int last = n % 10;
            sum += last;
            prod *= last;
            n /= 10;
        }

        return original % (sum + prod) == 0;
    }
}