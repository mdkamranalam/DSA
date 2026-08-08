class Solution {
    public int[] validSequence(String word1, String word2) {
        int n = word1.length(), m = word2.length();
        int[] suf = new int[m + 1];
        suf[m] = n;
        int p = n - 1;
        for (int j = m - 1; j >= 0; j--) {
            while (p >= 0 && word1.charAt(p) != word2.charAt(j)) {
                p--;
            }
            suf[j] = p--;
        }
        int[] res = new int[m];
        int j = 0;
        boolean used = false;
        for (int i = 0; i < n && j < m; i++) {
            if (word1.charAt(i) == word2.charAt(j)) {
                res[j++] = i;
            } else if (!used && i + 1 <= suf[j + 1]) {
                used = true;
                res[j++] = i;
            }
        }
        return j == m ? res : new int[0];
    }
}