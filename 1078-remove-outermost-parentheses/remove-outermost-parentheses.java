class Solution {
    public String removeOuterParentheses(String s) {
        StringBuilder ans = new StringBuilder();
        int count = 0;
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (c == '(') {
                if (++count > 1) {
                    ans.append(c);
                }
            } else {
                if (--count > 0) {
                    ans.append(c);
                }
            }
        }
        return ans.toString();
    }
}