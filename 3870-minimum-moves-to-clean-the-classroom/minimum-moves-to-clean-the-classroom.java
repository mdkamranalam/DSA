class Solution {

    class node {
        int i;
        int j;
        int step;
        int liter;
        int energy;

        public node(int i, int j, int step, int liter, int energy) {
            this.i = i;
            this.j = j;
            this.step = step;
            this.liter = liter;
            this.energy = energy;
        }
    }

    public int minMoves(String[] classroom, int e) {

        int n = classroom.length;
        int m = classroom[0].length();

        int x = -1;
        int y = -1;

        int l = 0;

        int[][] id = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                id[i][j] = -1;

                char ch = classroom[i].charAt(j);

                if (ch == 'S') {
                    x = i;
                    y = j;
                }

                if (ch == 'L') {
                    id[i][j] = l;
                    l++;
                }
            }
        }

        int total = 1 << l;

        int[][][] visited = new int[n][m][total];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < total; k++) {
                    visited[i][j][k] = -1;
                }
            }
        }

        Queue<node> pq = new LinkedList<>();

        pq.add(new node(x, y, 0, 0, e));

        visited[x][y][0] = e;

        int[] dx = { -1, 1, 0, 0 };
        int[] dy = { 0, 0, -1, 1 };

        while (pq.size() > 0) {

            node abc = pq.poll();

            int i = abc.i;
            int j = abc.j;
            int step = abc.step;
            int liter = abc.liter;
            int energy = abc.energy;

            if (classroom[i].charAt(j) == 'L') {
                int idd = id[i][j];

                liter = liter | (1 << idd);
            }

            if (liter == total - 1) {
                return step;
            }

            if (classroom[i].charAt(j) == 'R') {
                energy = e;
            }

            if (energy == 0) {
                continue;
            }

            for (int d = 0; d < 4; d++) {

                int ni = i + dx[d];
                int nj = j + dy[d];

                if (ni < 0 || ni >= n || nj < 0 || nj >= m) {
                    continue;
                }

                if (classroom[ni].charAt(nj) == 'X') {
                    continue;
                }

                int newEnergy = energy - 1;
                int newLiter = liter;

                if (classroom[ni].charAt(nj) == 'L') {
                    int idd = id[ni][nj];

                    newLiter = newLiter | (1 << idd);
                }

                if (classroom[ni].charAt(nj) == 'R') {
                    newEnergy = e;
                }

                if (visited[ni][nj][newLiter] >= newEnergy) {
                    continue;
                }

                visited[ni][nj][newLiter] = newEnergy;

                pq.add(new node(
                        ni,
                        nj,
                        step + 1,
                        newLiter,
                        newEnergy));
            }
        }

        return -1;
    }
}