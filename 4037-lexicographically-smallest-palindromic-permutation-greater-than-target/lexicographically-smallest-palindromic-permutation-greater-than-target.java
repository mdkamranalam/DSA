class Solution {
    public String lexPalindromicPermutation(
            String s, String target) {

        int n = s.length();

        int[] freq = new int[26];

        for (char ch : s.toCharArray()) {
            freq[ch - 'a']++;
        }

        // A palindrome can have at most one odd frequency
        int odd = 0;
        int middle = -1;

        for (int i = 0; i < 26; i++) {
            if (freq[i] % 2 == 1) {
                odd++;
                middle = i;
            }
        }

        if (odd > 1) {
            return "";
        }

        int halfLen = n / 2;

        // Number of each character available in the first half
        int[] half = new int[26];

        for (int i = 0; i < 26; i++) {
            half[i] = freq[i] / 2;
        }

        /*
         * First check whether target's first half
         * can itself be used as the palindrome's first half.
         */
        int[] remaining = half.clone();
        boolean possible = true;

        for (int i = 0; i < halfLen; i++) {
            int c = target.charAt(i) - 'a';

            if (remaining[c] == 0) {
                possible = false;
                break;
            }

            remaining[c]--;
        }

        if (possible) {
            String firstHalf = target.substring(0, halfLen);

            String candidate = makePalindrome(firstHalf, middle);

            if (candidate.compareTo(target) > 0) {
                return candidate;
            }
        }

        /*
         * Find the smallest first half greater than
         * target's first half.
         *
         * Try changing the rightmost possible position first.
         */
        for (int i = halfLen - 1; i >= 0; i--) {

            remaining = half.clone();
            boolean ok = true;

            // Match target[0 ... i-1]
            for (int j = 0; j < i; j++) {

                int c = target.charAt(j) - 'a';

                if (remaining[c] == 0) {
                    ok = false;
                    break;
                }

                remaining[c]--;
            }

            if (!ok) {
                continue;
            }

            // At position i, choose the smallest
            // character strictly greater than target[i]
            int targetChar = target.charAt(i) - 'a';

            for (int c = targetChar + 1; c < 26; c++) {

                if (remaining[c] > 0) {

                    remaining[c]--;

                    StringBuilder firstHalf = new StringBuilder();

                    // Prefix equal to target
                    firstHalf.append(target, 0, i);

                    // Bigger character
                    firstHalf.append((char) ('a' + c));

                    // Fill remaining positions with smallest chars
                    for (int k = 0; k < 26; k++) {
                        while (remaining[k] > 0) {
                            firstHalf.append((char) ('a' + k));
                            remaining[k]--;
                        }
                    }

                    return makePalindrome(firstHalf.toString(), middle);
                }
            }
        }

        return "";
    }

    private String makePalindrome(String firstHalf, int middle) {

        StringBuilder ans = new StringBuilder();

        ans.append(firstHalf);

        // Middle character for odd length
        if (middle != -1) {
            ans.append((char) ('a' + middle));
        }

        // Reverse first half
        for (int i = firstHalf.length() - 1; i >= 0; i--) {
            ans.append(firstHalf.charAt(i));
        }

        return ans.toString();
    }
}