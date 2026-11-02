class Solution {
    public List<Integer> selfDividingNumbers(int left, int right) {
        List<Integer> result = new ArrayList<>();
        for (int i = left; i <= right; i++) {
            if (check(i)) {
                result.add(i);
            }
        }
        return result;
    }

    private boolean check(int x) {
        for (int i = x; i > 0; i /= 10) {
            if (i % 10 == 0 || x % (i % 10) != 0) {
                return false;
            }
        }
        return true;
    }
}