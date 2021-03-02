package testing;

import java.util.*;

public class javaTest {
    public static void print2DArray(int[][] array) {
        System.out.println("2D array:");
        for (int i = 0; i < array.length; i++) {
            for (int n = 0; n < array[0].length; n++) {
                System.out.print(array[i][n]);
            }
            System.out.println();
        }
    }
    public static int[][] clone2DArray(int[][] array) {
        int[][] returnArray = new int[array.length][array[0].length];
        for (int i = 0; i < array.length; i++) {
            returnArray[i] = array[i].clone();
        }
        return returnArray;
    }
    public static void main(String args[]) {
        int[][] population = {{0, 1, 2},
                              {1, 2, 3}};
        int[][] newPop = {{9, 9, 9},
                            {9, 9, 9}};
        population = clone2DArray(newPop);
        // population = newPop.clone();
        newPop[0][0] = 1;
        print2DArray(population);
        
    }
}
