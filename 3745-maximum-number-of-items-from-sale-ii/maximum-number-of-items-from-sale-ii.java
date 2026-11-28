class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int[] freq = new int[n + 1];
        long minPrice = Long.MAX_VALUE;

        for (int[] item : items) {
            freq[item[0]]++;
            minPrice = Math.min(minPrice, item[1]);
        }

        int[] multCount = new int[n + 1];
        for (int f = 1; f <= n; f++) {
            if (freq[f] == 0)
                continue;
            for (int m = f; m <= n; m += f) {
                multCount[f] += freq[m];
            }
        }

        List<long[]> candidates = new ArrayList<>();
        for (int[] item : items) {
            int f = item[0];
            long p = item[1];
            int freeLimit = multCount[f] - 1;
            if (freeLimit > 0 && p < 2 * minPrice) {
                candidates.add(new long[] { p, freeLimit });
            }
        }

        candidates.sort(Comparator.comparingLong(a -> a[0]));

        long remBudget = budget;
        long totalItems = 0;

        for (long[] cand : candidates) {
            long p = cand[0];
            long limit = cand[1];
            long maxFullCost = p * limit;

            if (remBudget >= maxFullCost) {
                totalItems += 2 * limit;
                remBudget -= maxFullCost;
            } else {
                long q = remBudget / p;
                long rem = remBudget % p;

                long best = 2 * q + (rem / minPrice);
                if (q >= 1) {
                    best = Math.max(best, 2 * (q - 1) + ((rem + p) / minPrice));
                }

                totalItems += best;
                return (int) totalItems;
            }
        }

        totalItems += remBudget / minPrice;
        return (int) totalItems;
    }
}

// Time Complexity: O(n logn)
// Space Complexity: O(n)