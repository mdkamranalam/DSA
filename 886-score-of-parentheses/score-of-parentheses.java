class Solution {
    public int scoreOfParentheses(String s) {
        int ans = 0;
        int count = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                count++;
            } else {
                count--;
                if (s.charAt(i - 1) == '(') {
                    ans += 1 << count;
                }
            }
        }

        return ans;
    }
}