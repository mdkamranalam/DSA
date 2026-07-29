class Solution {
    static final long LIMIT = 1_000_000L;

    public String smallestPalindrome(String s, int k) {
        int[] freq = new int[26];
        for (char c : s.toCharArray())
            freq[c - 'a']++;

        int[] half = new int[26];
        String mid = "";

        int halfLen = 0;
        for (int i = 0; i < 26; i++) {
            half[i] = freq[i] / 2;
            halfLen += half[i];
            if ((freq[i] & 1) == 1)
                mid = String.valueOf((char) ('a' + i));
        }

        if (countWays(half, halfLen) < k)
            return "";

        StringBuilder left = new StringBuilder();

        while (halfLen > 0) {
            for (int c = 0; c < 26; c++) {
                if (half[c] == 0)
                    continue;

                half[c]--;
                long ways = countWays(half, halfLen - 1);

                if (ways >= k) {
                    left.append((char) ('a' + c));
                    halfLen--;
                    break;
                } else {
                    k -= ways;
                    half[c]++;
                }
            }
        }

        StringBuilder ans = new StringBuilder();
        ans.append(left);
        ans.append(mid);
        ans.append(left.reverse());

        return ans.toString();
    }

    private long countWays(int[] half, int total) {
        long res = 1;

        int remain = total;

        for (int x : half) {
            if (x == 0)
                continue;
            res *= comb(remain, x);
            if (res > LIMIT)
                return LIMIT;
            remain -= x;
        }

        return Math.min(res, LIMIT);
    }

    private long comb(int n, int r) {
        if (r > n)
            return 0;
        r = Math.min(r, n - r);

        long ans = 1;

        for (int i = 1; i <= r; i++) {
            ans = ans * (n - r + i) / i;
            if (ans > LIMIT)
                return LIMIT;
        }

        return Math.min(ans, LIMIT);
    }
}