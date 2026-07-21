
public class Solution {

    public int maxActiveSectionsAfterTrade(String input) {
        int totalNumberOfOnes = 0;
        int numberOfZerosBeforeOnes = 0;
        int maxNumberOfZerosToTransform = 0;

        for (int i = 0; i < input.length(); ++i) {

            int numberOfOnesBetweenZeros = 0;
            while (i < input.length() && input.charAt(i) == '1') {
                ++i;
                ++totalNumberOfOnes;
                ++numberOfOnesBetweenZeros;
            }

            int numberOfZerosAfterOnes = 0;
            while (i < input.length() && input.charAt(i) == '0') {
                ++i;
                ++numberOfZerosAfterOnes;
            }

            if (numberOfZerosBeforeOnes > 0 && numberOfOnesBetweenZeros > 0 && numberOfZerosAfterOnes > 0) {
                maxNumberOfZerosToTransform = Math.max(maxNumberOfZerosToTransform,
                        numberOfZerosBeforeOnes + numberOfZerosAfterOnes);
            }

            --i;
            numberOfZerosBeforeOnes = numberOfZerosAfterOnes;
        }

        return totalNumberOfOnes + maxNumberOfZerosToTransform;
    }
}