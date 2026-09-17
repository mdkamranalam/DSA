class Solution {
    public int minSumOfLengths(int[] arr, int target) {
        int len = arr.length;
        int[] minLen = new int[len + 1];
        Arrays.fill(minLen, len + 1);
        int result = len + 1;
        for (int i = 0, j = 0, sum = 0; i < len; i++) {
            sum += arr[i];
            while (j <= i && sum > target) {
                sum -= arr[j];
                j++;
            }
            minLen[i + 1] = minLen[i];
            if (sum == target) {
                minLen[i + 1] = Math.min(minLen[i + 1], i - j + 1);
                if (j > 0) {
                    result = Math.min(result, i - j + 1 + minLen[j]);
                }
            }
        }
        return result > len ? -1 : result;

    }
}