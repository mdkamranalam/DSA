class Solution {
    public int minInsertions(String s) {
        int ans = 0;
        int x = 0;
        int n = s.length();
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c == '(') {
                x++;
            } else {
                if (i < n - 1 && s.charAt(i + 1) == ')') {
                    i++;
                } else {
                    ans++;
                }

                if (x == 0) {
                    ans++;
                } else {
                    x--;
                }
            }
        }

        ans += x << 1;
        return ans;
    }
}