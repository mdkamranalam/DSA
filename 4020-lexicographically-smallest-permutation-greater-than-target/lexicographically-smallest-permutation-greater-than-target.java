class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int n = s.length();

        int[] cnt = new int[26];

        // Frequency of characters in s.
        for (char c : s.toCharArray()) {
            cnt[c - 'a']++;
        }

        /*
         * Match target from left to right.
         * cnt contains the characters not yet used.
         */
        int k = 0;

        while (k < n) {
            int c = target.charAt(k) - 'a';

            if (cnt[c] == 0) {
                break;
            }

            cnt[c]--;
            k++;
        }

        /*
         * Try to make the string greater.
         *
         * If k == n, target was completely matched, so start
         * from n - 1.
         *
         * Otherwise start from k, the first position that
         * couldn't be matched.
         */
        int start = Math.min(k, n - 1);

        for (int i = start; i >= 0; i--) {

            /*
             * When moving from position i+1 to i, restore
             * target[i] because it was part of the matched prefix.
             *
             * For i == k when k < n, target[i] was NOT consumed,
             * so we must not restore it.
             */
            if (i < k) {
                cnt[target.charAt(i) - 'a']++;
            }

            int t = target.charAt(i) - 'a';

            // Find the smallest available character > target[i].
            int greater = -1;

            for (int c = t + 1; c < 26; c++) {
                if (cnt[c] > 0) {
                    greater = c;
                    break;
                }
            }

            if (greater == -1) {
                continue;
            }

            /*
             * We found the first position where the answer
             * becomes greater than target.
             */
            StringBuilder ans = new StringBuilder(n);

            // Same prefix as target.
            ans.append(target, 0, i);

            // Smallest possible character greater than target[i].
            ans.append((char) ('a' + greater));
            cnt[greater]--;

            // Remaining characters should be as small as possible.
            for (int c = 0; c < 26; c++) {
                while (cnt[c] > 0) {
                    ans.append((char) ('a' + c));
                    cnt[c]--;
                }
            }

            return ans.toString();
        }

        return "";
    }
}