class Solution {
    public int distinctSubseqII(String s) {
        long mod = 1000000007;
        int n = s.length();
        long[] dp = new long[n + 1];
        dp[0] = 1;

        int[] last = new int[26];
        for (int i = 0; i < 26; i++) {
            last[i] = -1;
        }

        char[] chars = s.toCharArray();
        for (int i = 0; i < n; i++) {
            int x = chars[i] - 'a';
            dp[i + 1] = (dp[i] * 2) % mod;
            if (last[x] >= 0) {
                dp[i + 1] = (dp[i + 1] - dp[last[x]] + mod) % mod;
            }
            last[x] = i;
        }

        return (int) ((dp[n] - 1 + mod) % mod);
    }
}