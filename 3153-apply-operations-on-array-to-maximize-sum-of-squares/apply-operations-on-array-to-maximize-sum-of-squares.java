class Solution {
    public int maxSum(List<Integer> nums, int k) {
        int[] numOnes = new int[32];
        for (int i = 0; i < nums.size(); i++) {
            int val = nums.get(i);
            for (int j = 0; j < 32; j++) {
                if ((val & 1) != 0) {
                    numOnes[j]++;
                }
                val >>>= 1;
            }
        }
        long result = 0;
        for (int i = 0; i < k; i++) {
            int val = 0;
            for (int j = 31; j >= 0; j--) {
                if (numOnes[j] > 0) {
                    val |= 1 << j;
                    numOnes[j]--;
                }
            }
            result = (result + ((long) val * (long) val)) % 1000000007;
        }
        return (int) result;
    }
}