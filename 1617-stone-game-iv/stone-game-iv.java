class Solution {
    private static boolean[] lookup;
    static {
        int size = 100_001;
        lookup = new boolean[size];
        for (int i = 1; i * i < size; i++) {
            lookup[i * i] = true;
        }
        for (int i = 1; i < size; i++) {
            if (!lookup[i]) {
                for (int j = 1; i + j * j < size; j++) {
                    lookup[i + j * j] = true;
                }
            }
        }
    }

    public boolean winnerSquareGame(int n) {
        return lookup[n];
    }
}