class Solution {
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {
        PriorityQueue<int[]> busyServers = new PriorityQueue<>((a, b) -> {
            if (a[1] != b[1]) {
                return a[1] - b[1];
            }
            return a[0] - b[0];
        });
        int[] counter = new int[k];
        TreeSet<Integer> available = new TreeSet<>();
        for (int num = 0; num < Math.min(k, arrival.length); num++) {
            counter[num] = 1;
            busyServers.add(new int[] { num, arrival[num] + load[num] });
        }
        int max = 1;
        for (int idx = k; idx < arrival.length; idx++) {
            int curTime = arrival[idx];
            while (!busyServers.isEmpty() && busyServers.peek()[1] <= curTime) {
                available.add(busyServers.poll()[0]);
            }
            if (available.size() == 0)
                continue;
            int serverRequired = idx % k;
            Integer ceiling = available.ceiling(serverRequired);
            int assignNum = (ceiling != null) ? ceiling : available.first();
            available.remove(assignNum);
            counter[assignNum]++;
            max = Math.max(max, counter[assignNum]);
            busyServers.offer(new int[] { assignNum, curTime + load[idx] });
        }
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < counter.length; i++) {
            if (counter[i] == max) {
                result.add(i);
            }
        }
        return result;
    }
}