package AVLTree;
import java.util.Date;
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
        System.out.println(String.format("Average %d executions time of %s in milliseconds: %t", counter, funcName, time));
    }

    private static void comparePrefixXor(){
        for (int i = 1; i <= 5; i++){
            AVLTree avlTree = new AVLTree();

            int[] keysArray = randomIntArray(500 * i);

            insertMany(avlTree, keysArray);

            Arrays.sort(keysArray);
            int counter = 0;
            long timePrefixXor = 0;
            long timeSuccPrefixXor = 0;

            for (int k : keysArray){
                long startTime = new Date().getTime();
                avlTree.prefixXor(k);
                long endTime = new Date().getTime();
                timePrefixXor += endTime - startTime;

                startTime = new Date().getTime();
                avlTree.succPrefixXor(k);
                endTime = new Date().getTime();
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

    public static void main(String[] args) {
        comparePrefixXor();
    }

}
