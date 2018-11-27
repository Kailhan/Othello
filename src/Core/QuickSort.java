package Core;

import AI.*;

public class QuickSort {

    private AI[] AIs;

    public QuickSort(AI[] AIs) {
        this.AIs = AIs;
    }

    private void quickSort(int lowerIndex, int higherIndex) {

        int i = lowerIndex;
        int j = higherIndex - 1;
        double pivot = AIs[lowerIndex+(higherIndex-lowerIndex)/2].getFitness();
        while (i <= j) {
            while (AIs[i].getFitness() < pivot) {
                i++;
            }
            while (AIs[j].getFitness() > pivot) {
                j--;
            }
            if (i <= j) {
                exchangeAIs(i, j);
                i++;
                j--;
            }
        }
        if (lowerIndex < j)
            quickSort(lowerIndex, j);
        if (i < higherIndex)
            quickSort(i, higherIndex);
    }

    private void exchangeAIs(int i, int j) {
        AI temp = AIs[i];
        AIs[i] = AIs[j];
        AIs[j] = temp;
    }

    public AI[] getAIs() {
        return AIs;
    }
}