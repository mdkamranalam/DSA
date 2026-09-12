class Solution {

    /*
     * Each interval:
     * [left, right, weight, originalIndex]
     */
    static class Interval {
        int left;
        int right;
        long weight;
        int index;

        Interval(int left, int right, long weight, int index) {
            this.left = left;
            this.right = right;
            this.weight = weight;
            this.index = index;
        }
    }

    /*
     * Represents the best solution for a DP state.
     */
    static class Result {
        long score;
        int[] indices;

        Result(long score, int[] indices) {
            this.score = score;
            this.indices = indices;
        }
    }

    private Interval[] arr;
    private int[][] next;
    private Result[][] memo;
    private boolean[][] visited;

    public int[] maximumWeight(List<List<Integer>> intervals) {

        int n = intervals.size();

        arr = new Interval[n];

        for (int i = 0; i < n; i++) {
            List<Integer> in = intervals.get(i);

            arr[i] = new Interval(
                    in.get(0),
                    in.get(1),
                    in.get(2),
                    i);
        }

        /*
         * Sort by starting position.
         * If starts are equal, sorting by end is useful for
         * deterministic processing.
         */
        java.util.Arrays.sort(arr, (a, b) -> {
            if (a.left != b.left) {
                return Integer.compare(a.left, b.left);
            }

            if (a.right != b.right) {
                return Integer.compare(a.right, b.right);
            }

            return Integer.compare(a.index, b.index);
        });

        /*
         * next[i] = first interval whose left endpoint
         * is strictly greater than arr[i].right.
         */
        next = new int[n][1];

        for (int i = 0; i < n; i++) {
            next[i][0] = findNext(i, n);
        }

        /*
         * We have at most 4 intervals to choose.
         */
        memo = new Result[n + 1][5];
        visited = new boolean[n + 1][5];

        Result answer = solve(0, 4);

        return answer.indices;
    }

    /*
     * Binary search for the first interval with:
     *
     * arr[mid].left > arr[i].right
     *
     * Notice the strict '>' because intervals sharing
     * an endpoint are considered overlapping.
     */
    private int findNext(int i, int n) {

        int low = i + 1;
        int high = n;

        while (low < high) {

            int mid = low + (high - low) / 2;

            if (arr[mid].left > arr[i].right) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        return low;
    }

    /*
     * solve(i, remaining):
     *
     * We are currently considering interval i.
     * We can select at most 'remaining' more intervals.
     *
     * Returns the maximum score and lexicographically
     * smallest index array achieving that score.
     */
    private Result solve(int i, int remaining) {

        if (i == arr.length || remaining == 0) {
            return new Result(0, new int[0]);
        }

        if (visited[i][remaining]) {
            return memo[i][remaining];
        }

        visited[i][remaining] = true;

        /*
         * Option 1: Skip current interval.
         */
        Result skip = solve(i + 1, remaining);

        /*
         * Option 2: Take current interval.
         */
        Result nextResult = solve(next[i][0], remaining - 1);

        int[] takenIndices = addAndSort(
                arr[i].index,
                nextResult.indices);

        Result take = new Result(
                arr[i].weight + nextResult.score,
                takenIndices);

        /*
         * Choose the better candidate:
         * 1. Higher score
         * 2. Lexicographically smaller indices if scores tie
         */
        Result best = better(skip, take);

        memo[i][remaining] = best;

        return best;
    }

    /*
     * Compare two results.
     */
    private Result better(Result a, Result b) {

        if (a.score != b.score) {
            return a.score > b.score ? a : b;
        }

        /*
         * Same score -> lexicographically smaller array.
         */
        if (isLexicographicallySmaller(a.indices, b.indices)) {
            return a;
        }

        return b;
    }

    /*
     * Add an original index to the solution and keep the
     * resulting array sorted.
     *
     * This is important because the answer must be compared
     * lexicographically using the original interval indices.
     */
    private int[] addAndSort(int index, int[] existing) {

        int[] result = new int[existing.length + 1];

        int pos = 0;

        /*
         * Insert index into its sorted position.
         */
        while (pos < existing.length && existing[pos] < index) {
            result[pos] = existing[pos];
            pos++;
        }

        result[pos] = index;

        while (pos < existing.length) {
            result[pos + 1] = existing[pos];
            pos++;
        }

        return result;
    }

    /*
     * Lexicographical comparison of two integer arrays.
     *
     * Example:
     * [1, 4] < [2, 3]
     *
     * because 1 < 2.
     */
    private boolean isLexicographicallySmaller(int[] a, int[] b) {

        int len = Math.min(a.length, b.length);

        for (int i = 0; i < len; i++) {

            if (a[i] != b[i]) {
                return a[i] < b[i];
            }
        }

        /*
         * If one is a prefix of the other, the shorter array
         * is lexicographically smaller.
         */
        return a.length < b.length;
    }
}
