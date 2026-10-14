class Solution {
    public int largestAltitude(int[] gain) {
        int ans = 0;
        int h = 0;
        for (int i = 0; i < gain.length; i++) {
            h += gain[i];
            ans = Math.max(ans, h);
        }
        return ans;
    }
}