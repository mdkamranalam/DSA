class Solution {
    public int countCompleteComponents(int n, int[][] edges) {
        List<Integer>[] adjList = new ArrayList[n];

        for (int vertex = 0; vertex < n; vertex++) {
            adjList[vertex] = new ArrayList<>();
        }

        for (int i = 0; i < edges.length; i++) {
            adjList[edges[i][0]].add(edges[i][1]);
            adjList[edges[i][1]].add(edges[i][0]);
        }

        int counter = 0;
        boolean[] visited = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (visited[i] == true)
                continue;
            int[] componentInfo = new int[2];
            dfs(adjList, visited, i, componentInfo);
            if (componentInfo[0] * (componentInfo[0] - 1) == componentInfo[1])
                counter++;

        }
        return counter;

    }

    public void dfs(List<Integer>[] adjList, boolean[] visited, int ind, int[] componentInfo) {
        visited[ind] = true;
        componentInfo[0]++;
        componentInfo[1] += adjList[ind].size();

        for (int i = 0; i < adjList[ind].size(); i++) {
            int node = adjList[ind].get(i);
            if (visited[node] == false) {
                dfs(adjList, visited, node, componentInfo);
            }
        }
    }
}