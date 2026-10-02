class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        backtrack(result, new StringBuffer(), 0, 0, n);
        return result;
    }

    private void backtrack(List<String> result, StringBuffer temp, int l, int r, int n) {
        if (temp.length() == n * 2) {
            result.add(temp.toString());
            return;
        }

        if (l < n) {
            temp.append('(');
            backtrack(result, temp, l + 1, r, n);
            temp.deleteCharAt(temp.length() - 1);
        }

        if (r < l) {
            temp.append(')');
            backtrack(result, temp, l, r + 1, n);
            temp.deleteCharAt(temp.length() - 1);
        }
    }
}