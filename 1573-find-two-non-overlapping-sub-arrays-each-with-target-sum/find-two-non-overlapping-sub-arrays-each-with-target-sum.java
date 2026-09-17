public class Solution {
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;
        int[] minLen = new int[n];
        int INF = Integer.MAX_VALUE;
        int minSoFar = INF;
        int ans = INF;
        int sum = 0;
        int start = 0;

        for (int i = 0; i < n; i++) {
            sum += arr[i];
            while (sum > target) {
                sum -= arr[start];
                start++;
            }
            if (sum == target) {
                int currLen = i - start + 1;
                if (start > 0 && minLen[start - 1] != INF) {
                    ans = Math.min(ans, currLen + minLen[start - 1]);
                }
                minSoFar = Math.min(minSoFar, currLen);
            }
            minLen[i] = minSoFar;
        }

        return ans == INF ? -1 : ans;
    }
}