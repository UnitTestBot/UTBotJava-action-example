package com.github.utbot;

public class Main {

    public boolean isSorted(int[] array) {
        for (int i = 0; i < array.length; ++i) {
            if (array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    }

    public static int square(int number) {
        return multiply(number, number);
    }

    private static int multiply(int x, int y) {
        return Math.multiplyExact(x, y);
    }
}
