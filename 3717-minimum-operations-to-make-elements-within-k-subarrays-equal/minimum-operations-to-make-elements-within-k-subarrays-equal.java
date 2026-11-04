class Solution {
    class MedianWindow {
        TreeMap<Integer, Integer> left = new TreeMap<>();
        TreeMap<Integer, Integer> right = new TreeMap<>();

        long leftSum = 0;
        long rightSum = 0;

        int leftSize = 0;
        int rightSize = 0;

        void addLeft(int val) {
            left.merge(val, 1, Integer::sum);
            leftSum += val;
            leftSize++;
        }

        void addRight(int val) {
            right.merge(val, 1, Integer::sum);
            rightSum += val;
            rightSize++;
        }

        void removeLeft(int val) {
            remove(left, val);
            leftSum -= val;
            leftSize--;
        }

        void removeRight(int val) {
            remove(right, val);
            rightSum -= val;
            rightSize--;
        }

        void remove(TreeMap<Integer, Integer> map, int val) {
            int count = map.get(val);

            if (count == 1) {
                map.remove(val);
            } else {
                map.put(val, count - 1);
            }
        }

        void balance() {
            int targetLeft = (leftSize + rightSize + 1) / 2;

            while (leftSize > targetLeft) {
                int val = left.lastKey();
                removeLeft(val);
                addRight(val);
            }

            while (leftSize < targetLeft) {
                int val = right.firstKey();
                removeRight(val);
                addLeft(val);
            }
        }

        void add(int val) {
            if (left.isEmpty() || val <= left.lastKey()) {
                addLeft(val);
            } else {
                addRight(val);
            }

            balance();
        }

        void erase(int val) {
            if (left.containsKey(val)) {
                removeLeft(val);
            } else {
                removeRight(val);
            }

            balance();
        }

        long getCost() {
            long median = left.lastKey();

            return median * leftSize - leftSum
                    + rightSum - median * rightSize;
        }
    }

    public long minOperations(int[] nums, int x, int k) {

        int n = nums.length;
        int m = n - x + 1;

        long[] cost = new long[m];

        MedianWindow window = new MedianWindow();

        // Build the first window.
        for (int i = 0; i < x; i++) {
            window.add(nums[i]);
        }

        cost[0] = window.getCost();

        // Sliding window.
        for (int i = x; i < n; i++) {

            window.erase(nums[i - x]);
            window.add(nums[i]);

            cost[i - x + 1] = window.getCost();
        }

        // DP with rolling arrays.
        long INF = Long.MAX_VALUE / 4;

        long[] prev = new long[n + 1];
        Arrays.fill(prev, 0);

        for (int j = 1; j <= k; j++) {

            long[] curr = new long[n + 1];
            Arrays.fill(curr, INF);

            for (int i = 1; i <= n; i++) {

                // Skip current element.
                curr[i] = curr[i - 1];

                // Select a window ending at i - 1.
                if (i >= x && prev[i - x] != INF) {

                    curr[i] = Math.min(
                            curr[i],
                            prev[i - x] + cost[i - x]);
                }
            }

            prev = curr;
        }

        return prev[n];
    }
}
