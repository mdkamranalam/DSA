/*
Approach: Two-phase greedy. 1) Expand around centers to find minimal valid palindromes (length >= k).
 We stop expanding once a valid length is reached (greedy: smaller palindromes leave more space).
 2) Interval scheduling to find max non-overlapping set, favoring earliest end times.

Time: O(n * k) — O(n) centers, expanding outward at most k/2 times before breaking.
Space: O(n) — storing at most 2n intervals.

GOTCHAS & DOUBTS (from session):
- The `2*n` Center Trick: Iterates 0 to 2n-1. Even center means left == right (odd length).
  Odd center means right == left + 1 (even length). Multiplexes both types in one loop.
- Phase 2 isn't passive tracking: It's active greedy interval scheduling. When intervals overlap,
  swapping to the one that finishes earlier (prevEnd = arr[1]) maximizes room for later palindromes.
*/
class Solution {
    public int maxPalindromes(String s, int k) {
        // Stores intervals as [start, end) for scheduling
        List<int[]> intervals = new ArrayList<>();
        int n = s.length();
        
        // Multiplex odd/even centers to avoid writing 2 loops. -> O(n) ← DOMINANT (outer)
        for(int center=0; center<2*n; center++) {
            // Integer division natively sets base index for both types
            int left = center/2;
            // +0 for odd-length (same char), +1 for even-length (gap)
            int right = left + center%2;
            
            // Expand as long as palindrome holds. -> O(k) inner
            while(left>=0 && right<n && s.charAt(left)==s.charAt(right)) {
                if(right - left + 1 >=k) {
                    // EDGE: Break immediately on valid length to keep interval as short as possible.
                    intervals.add(new int[]{left, right+1}); // right+1 for exclusive end time
                    break;
                } 
                left--; right++;
            }
        }
        
        int prevEnd = 0, ans = 0;
        // Process intervals (naturally sorted by center/start time). -> O(n)
        for(int[] arr : intervals) {
            if(arr[0]>=prevEnd) {
                // No overlap, safely take this interval
                prevEnd = arr[1];
                ans++;
            } else {
                if(arr[1]<prevEnd) {
                    // Overlaps, but finishes earlier? Swap to it! Greedy choice maximizes remaining space.
                    prevEnd = arr[1];
                }
            }
        }
        return ans;
    }
}