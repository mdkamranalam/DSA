class Solution {
    public long gcdSum(int[] nums) {

        int[] prefixGcd = new int[nums.length];
        int max = nums[0];

        for (int i = 0; i < nums.length; i++) {
            max = Math.max(max, nums[i]);
            prefixGcd[i] = gcd(nums[i], max);
        }

        Arrays.sort(prefixGcd);

        long sum = 0L;

        for (int i = 0; i < prefixGcd.length / 2; i++) {
            sum += gcd(prefixGcd[i], prefixGcd[prefixGcd.length - 1 - i]);
        }

        return sum;

    }

    private int gcd(int a, int b) {
        while (a != 0) {
            int temp = a;
            a = b % a;
            b = temp;
        }

        return b;
    }
}