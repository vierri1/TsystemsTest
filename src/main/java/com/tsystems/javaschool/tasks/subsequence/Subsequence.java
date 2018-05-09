package com.tsystems.javaschool.tasks.subsequence;

import java.util.Collections;
import java.util.List;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) {
        // TODO: Implement the logic here
        if (x == null || y == null) {
            throw new IllegalArgumentException();
        }
        if (!y.containsAll(x)) {
            return false;
        }
        else {
            int indY = 0;
            int countSequence = 0;
            boolean find = false;
            for (int i = 0; i < x.size(); i++) {
                for (int j = indY; j < y.size(); j++) {
                    if (x.get(i).equals(y.get(j))) {
                        indY = j + 1;
                        find = true;
                        countSequence++;
                        break;
                    }
                }
                if (!find) {
                    break;
                }
                find = false;
            }
            if (countSequence == x.size()) {
                return true;
            }
            else {
                return false;
            }
        }
    }
}
