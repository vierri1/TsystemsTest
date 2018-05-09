package com.tsystems.javaschool.tasks.pyramid;

import java.util.Collections;
import java.util.List;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) {
        // TODO : Implement your solution here
        try {
            Collections.sort(inputNumbers);
        } catch (OutOfMemoryError ex) {
            throw new CannotBuildPyramidException();
        } catch (NullPointerException e) {
            throw new CannotBuildPyramidException();
        }
        int[][] pyramid = null;
        int dimensionPyramid = 0;
        int countElements = 0;
        for (int i = 1; countElements < inputNumbers.size(); i++){
            countElements = countElements + i;
            dimensionPyramid++;
        }
        if (countElements != inputNumbers.size()) {
            throw new CannotBuildPyramidException();
        }
        else {
            System.out.println("dimension " + dimensionPyramid);
            System.out.println("countColumns " + (dimensionPyramid * 2 - 1));
            int countColumns = dimensionPyramid * 2 -1;
            pyramid = new int[dimensionPyramid][countColumns];
            for (int i = 0; i < dimensionPyramid; i++) {
                for (int j = 0; j < countColumns; j++) {
                    pyramid[i][j] = 0;
                }
            }

            int placeInRow = dimensionPyramid - 1;
            int countElementsInRow = 1;
            int listInd = 0;
            for (int i = 0; i < dimensionPyramid; i++) {
                for (int j = 0; j < countElementsInRow; j++) {
                    pyramid[i][placeInRow] = inputNumbers.get(listInd);
                    listInd++;
                    placeInRow = placeInRow + 2;
                }
                countElementsInRow++;
                placeInRow = dimensionPyramid - countElementsInRow;
            }
        }
        return pyramid;
    }


}
