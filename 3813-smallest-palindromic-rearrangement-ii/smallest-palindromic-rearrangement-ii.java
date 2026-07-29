class Solution {
    static final int CAP = 1_000_001;

    public String smallestPalindrome(String s, int k) {
        int[] freq = new int[26];
        for (char ch : s.toCharArray())
            freq[ch - 'a']++;

        int[] half = new int[26];
        String mid = "";
        int halfLen = 0;
        for (int i = 0; i < 26; i++) {
            half[i] = freq[i] / 2;
            halfLen += half[i];
            if ((freq[i] & 1) == 1)
                mid = String.valueOf((char) ('a' + i));
        }

        PrimeHelper helper = new PrimeHelper(halfLen);

        if (helper.countWays(half) < k)
            return "";

        StringBuilder left = new StringBuilder();

        for (int pos = 0; pos < halfLen; pos++) {
            for (int c = 0; c < 26; c++) {
                if (half[c] == 0)
                    continue;

                half[c]--;
                int ways = helper.countWays(half);

                if (ways >= k) {
                    left.append((char) ('a' + c));
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

    static class PrimeHelper {
        int[] primes;
        int[][] factExp;

        PrimeHelper(int maxN) {
            build(maxN);
        }

        private void build(int maxN) {
            if (maxN == 0) {
                primes = new int[0];
                factExp = new int[1][0];
                return;
            }

            int[] spf = new int[maxN + 1];
            for (int i = 2; i <= maxN; i++) {
                if (spf[i] == 0) {
                    for (int j = i; j <= maxN; j += i)
                        if (spf[j] == 0)
                            spf[j] = i;
                }
            }

            int cnt = 0;
            for (int i = 2; i <= maxN; i++)
                if (spf[i] == i)
                    cnt++;

            primes = new int[cnt];
            int[] idx = new int[maxN + 1];
            int p = 0;
            for (int i = 2; i <= maxN; i++) {
                if (spf[i] == i) {
                    primes[p] = i;
                    idx[i] = p++;
                }
            }

            factExp = new int[maxN + 1][cnt];

            for (int i = 1; i <= maxN; i++) {
                System.arraycopy(factExp[i - 1], 0, factExp[i], 0, cnt);

                int x = i;
                while (x > 1) {
                    int prime = spf[x];
                    factExp[i][idx[prime]]++;
                    x /= prime;
                }
            }
        }

        int countWays(int[] cnts) {
            int total = 0;
            for (int x : cnts)
                total += x;

            int[] exp = new int[primes.length];

            for (int i = 0; i < primes.length; i++)
                exp[i] = factExp[total][i];

            for (int c : cnts) {
                if (c == 0)
                    continue;
                for (int i = 0; i < primes.length; i++)
                    exp[i] -= factExp[c][i];
            }

            long ans = 1;
            for (int i = 0; i < primes.length; i++) {
                int e = exp[i];
                if (e == 0)
                    continue;
                ans = mulPowCap(ans, primes[i], e);
                if (ans >= CAP)
                    return CAP;
            }
            return (int) ans;
        }

        private long mulPowCap(long cur, int base, int exp) {
            long b = base;
            long res = cur;

            while (exp > 0) {
                if ((exp & 1) == 1) {
                    res *= b;
                    if (res >= CAP)
                        return CAP;
                }
                exp >>= 1;
                if (exp > 0) {
                    b *= b;
                    if (b >= CAP)
                        b = CAP;
                }
            }
            return Math.min(res, CAP);
        }
    }
}