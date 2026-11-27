class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int maxFactor = 0;
        int mn = Integer.MAX_VALUE;
        
        for (int[] item : items) {
            maxFactor = Math.max(maxFactor, item[0]);
            mn = Math.min(mn, item[1]);
        }

        int[] freq = new int[maxFactor + 1];
        for (int[] item : items) {
            freq[item[0]]++;
        }

        int[] multiplesCount = new int[maxFactor + 1];
        for (int i = 1; i <= maxFactor; i++) {
            if (freq[i] == 0) continue;
            for (int j = i; j <= maxFactor; j += i) {
                multiplesCount[i] += freq[j];
            }
        }

        int[] f = new int[budget + 1];
        for (int[] item : items) {
            int cnt = multiplesCount[item[0]];
            int price = item[1];
            for (int j = budget; j >= price; j--) {
                f[j] = Math.max(f[j], f[j - price] + cnt);
            }
        }

        int ans = 0;
        for (int i = 0; i <= budget; i++) {
            ans = Math.max(ans, f[i] + (budget - i) / mn);
        }

        return ans;
    }
}

// Time Complexity: O(nlogn + n⋅b)
// Space Complexity: O(M + b)