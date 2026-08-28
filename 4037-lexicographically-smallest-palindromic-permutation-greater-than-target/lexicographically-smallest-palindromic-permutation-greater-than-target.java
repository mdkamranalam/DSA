import java.util.Arrays;

class Solution {
    public String lexPalindromicPermutation(String s, String target) {
        int n = s.length();
        int halfLen = n / 2;
        int[] counts = new int[26];
        for (int i = 0; i < n; i++) {
            counts[s.charAt(i) - 'a']++;
        }

        // Step 1: Validate feasibility & identify middle character
        int oddCount = 0;
        char midChar = '\0';
        for (int i = 0; i < 26; i++) {
            if (counts[i] % 2 != 0) {
                oddCount++;
                midChar = (char) ('a' + i);
            }
        }

        if (oddCount > 1 || (n % 2 == 0 && oddCount > 0)) {
            return "";
        }

        int[] halfCounts = new int[26];
        for (int i = 0; i < 26; i++) {
            halfCounts[i] = counts[i] / 2;
        }

        String tHalf = target.substring(0, halfLen);

        // Step 2: Option A - Try exact match for first half (half_str == t_half)
        int[] tHalfCounts = new int[26];
        for (int i = 0; i < halfLen; i++) {
            tHalfCounts[tHalf.charAt(i) - 'a']++;
        }

        if (Arrays.equals(tHalfCounts, halfCounts)) {
            String midStr = (midChar != '\0') ? String.valueOf(midChar) : "";
            String candidate = tHalf + midStr + new StringBuilder(tHalf).reverse().toString();
            if (candidate.compareTo(target) > 0) {
                return candidate;
            }
        }

        // Step 3: Option B - Find largest matching prefix length k (half_str > t_half)
        int[] currentPrefix = new int[26];
        boolean[] validPrefix = new boolean[halfLen + 1];
        validPrefix[0] = true;

        for (int i = 0; i < halfLen; i++) {
            int charIdx = tHalf.charAt(i) - 'a';
            currentPrefix[charIdx]++;
            if (currentPrefix[charIdx] > halfCounts[charIdx]) {
                break;
            }
            validPrefix[i + 1] = true;
        }

        for (int k = halfLen - 1; k >= 0; k--) {
            if (!validPrefix[k]) {
                continue;
            }

            int[] remCounts = halfCounts.clone();
            for (int i = 0; i < k; i++) {
                remCounts[tHalf.charAt(i) - 'a']--;
            }

            int targetChar = tHalf.charAt(k) - 'a';
            int bestChar = -1;
            for (int c = targetChar + 1; c < 26; c++) {
                if (remCounts[c] > 0) {
                    bestChar = c;
                    break;
                }
            }

            if (bestChar != -1) {
                remCounts[bestChar]--;
                StringBuilder halfSb = new StringBuilder();
                halfSb.append(tHalf, 0, k);
                halfSb.append((char) ('a' + bestChar));

                for (int c = 0; c < 26; c++) {
                    for (int count = 0; count < remCounts[c]; count++) {
                        halfSb.append((char) ('a' + c));
                    }
                }

                String halfStr = halfSb.toString();
                String midStr = (midChar != '\0') ? String.valueOf(midChar) : "";
                String revHalf = new StringBuilder(halfStr).reverse().toString();

                return halfStr + midStr + revHalf;
            }
        }

        return "";
    }
}