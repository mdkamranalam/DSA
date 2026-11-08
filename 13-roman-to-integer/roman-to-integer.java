class Solution {
    public int romanToInt(String s) {
        s = s.toUpperCase();
        int answer = 0;

        for (int j = 0; j < s.length(); j++) {
            char i = s.charAt(j);
            if (i == 'I') {
                if (j + 1 < s.length() && s.charAt(j + 1) == 'V') {
                    answer = answer + 4;
                    j++;
                } else if (j + 1 < s.length() && s.charAt(j + 1) == 'X') {
                    answer = answer + 9;
                    j++;
                } else {
                    answer = answer + 1;
                }
            } else if (i == 'V') {
                answer = answer + 5;
            } else if (i == 'X') {
                if (j + 1 < s.length() && s.charAt(j + 1) == 'L') {
                    answer = answer + 40;
                    j++;
                } else if (j + 1 < s.length() && s.charAt(j + 1) == 'C') {
                    answer = answer + 90;
                    j++;
                } else {
                    answer = answer + 10;
                }
            } else if (i == 'L') {
                answer = answer + 50;
            } else if (i == 'C') {
                if (j + 1 < s.length() && s.charAt(j + 1) == 'D') {
                    answer = answer + 400;
                    j++;
                } else if (j + 1 < s.length() && s.charAt(j + 1) == 'M') {
                    answer = answer + 900;
                    j++;
                } else {
                    answer = answer + 100;
                }
            } else if (i == 'D') {
                answer = answer + 500;
            } else if (i == 'M') {
                answer = answer + 1000;
            }
        }
        return answer;

    }
}