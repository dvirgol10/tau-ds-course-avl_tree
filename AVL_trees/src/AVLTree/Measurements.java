package AVLTree;

import AVLTree.AVLTree.BSTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Measurements {

    private static int[] randomIntArray(int size){
        Random random = new Random();
        int[] intArr = new int[size];
        for (int i = 0; i < intArr.length; i++) {
            intArr[i] = random.nextInt();
        }
        return intArr;
    }

    private static void insertMany(AVLTree avlTree, int... keysArray) {
        for (int key : keysArray) {
            avlTree.insert(key, false);
        }
    }

    private static void printAverageTime(long time, int counter, String funcName) {
        System.out.println(String.format("Average %d executions time of %s in milliseconds:  %f", counter, funcName, time / 1000000.0));
    }

    private static void comparePrefixXor(){
        AVLTree avlTree;
        int[] keysArray;

        for (int i = 1; i <= 5; i++){
            avlTree = new AVLTree();

            keysArray  = randomIntArray(500 * i);

            insertMany(avlTree, keysArray);

            Arrays.sort(keysArray);
            int counter = 0;
            long timePrefixXor = 0;
            long timeSuccPrefixXor = 0;
            long startTime;
            long endTime;

            for (int k : keysArray){
                startTime = System.nanoTime();
                avlTree.prefixXor(k);
                endTime = System.nanoTime();
                timePrefixXor += endTime - startTime;

                startTime = System.nanoTime();
                avlTree.succPrefixXor(k);
                endTime = System.nanoTime();
                timeSuccPrefixXor += endTime - startTime;

                counter += 1;

                if (counter == 100){
                    printAverageTime(timePrefixXor, counter, "prefixXor");
                    printAverageTime(timeSuccPrefixXor, counter, "succPrefixXor");
                }
            }

            printAverageTime(timePrefixXor, counter, "prefixXor");
            printAverageTime(timeSuccPrefixXor, counter, "succPrefixXor");
        }
    }

    private static int[] arithmeticProgressionArray(int size){
        int[] intArr = new int[size];
        for (int i = 0; i < intArr.length; i++) {
            intArr[i] = i + 1;
        }
        return intArr;
    }

    private static int[] balancedSeriesArray(int i) {
        return Arrays.copyOfRange(envBalancedSeriesArray(minimalPowerOf2(i)),0, i);
    }

    private static int minimalPowerOf2(int i) {
        return powOf2(log2(i)) == i ? i : powOf2(log2(i) + 1);
    }

    private static int[] envBalancedSeriesArray(int size){
        int[] ret = new int[size];
        int startLevel;
        for (int i = 0; i < log2(size); i++) {
            startLevel = powOf2(i);
            for (int j = 0; j < startLevel; j++) {
                ret[startLevel - 1 + j] = size * (1+2*j) / (startLevel*2);
            }
        }
        ret[size-1] = size;
        return ret;
    }

    private static int powOf2(int i2) {
        return (int) Math.pow(2, i2);
    }

    private static int log2(int i) {
        return (int) (Math.log(i) / Math.log(2));
    }



    private static long[] insertTimeCalc(int[] keysArray) {
        AVLTree avlTree = new AVLTree();
        BSTree bsTree = new BSTree();

        long timeInsertAVL = 0;
        long timeInsertBS = 0;

        long startTime;
        long endTime;

        for (int k : keysArray){
            startTime = System.nanoTime();
            avlTree.insert(k, false);
            endTime = System.nanoTime();
            timeInsertAVL += endTime - startTime;

            startTime = System.nanoTime();
            bsTree.insert(k, false);
            endTime = System.nanoTime();
            timeInsertBS += endTime - startTime;
        }

        return new long[]{timeInsertAVL, timeInsertBS};
    }

    private static void compareInsert(){
        long[] times;
        int size;
        int[] keysArray;

        for (int i = 1; i <= 5; i++){
            size = 1000 * i;

            keysArray = arithmeticProgressionArray(size);
            times = insertTimeCalc(keysArray);
            printAverageTime(times[0], size, "AVL(arithmetic progression)");
            printAverageTime(times[1], size, "BS(arithmetic progression)");

            keysArray = balancedSeriesArray(size);
            times = insertTimeCalc(keysArray);
            printAverageTime(times[0], size, "AVL(balanced series)");
            printAverageTime(times[1], size, "BS(balanced series)");

            keysArray = randomIntArray(size);
            times = insertTimeCalc(keysArray);
            printAverageTime(times[0], size, "AVL(random)");
            printAverageTime(times[1], size, "BS(random)");
        }
    }

    public static void main(String[] args) {
        comparePrefixXor(); //neutralize external noises
        for (int i = 0; i < 100; i++) {
            System.out.println("\n");
        }
        comparePrefixXor();
        compareInsert();
    }

}