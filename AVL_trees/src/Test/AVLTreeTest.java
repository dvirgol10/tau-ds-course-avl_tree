import AVLTree.AVLTree;
import AVLTree.AVLTree.AVLNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


class AVLTreeTest {

    AVLTree tree = new AVLTree();

    @BeforeEach
    public void beforeTest() {
        //System.out.println("This happens before each test!");
        resetTree();
    }


    @Test
    public void empty() {
        Assertions.assertTrue(tree.empty());
    }

    @Test
    public void search() {
        tree.insert(2, false);
        tree.insert(1, true);
        Assertions.assertEquals(false, tree.search(2));
        Assertions.assertEquals(true, tree.search(1));
        Assertions.assertNull(tree.search(3));
    }

    @Test
    public void insert() {
        Assertions.assertEquals(1, tree.insert(1, true));
        Assertions.assertEquals(1, tree.insert(2, false));
        Assertions.assertEquals(-1, tree.insert(2, false));
        Assertions.assertEquals(2, tree.insert(3, true));
    }

    @Test
    public void delete() {
        Assertions.assertEquals(-1, tree.delete(1));
        insertMany(1, 2, 3);
        Assertions.assertEquals(-1, tree.delete(4));
        Assertions.assertEquals(1, tree.delete(2));
        insertMany(4, 2);
        Assertions.assertEquals(2, tree.delete(4));
        insertMany(5, 0, 4);
        Assertions.assertEquals(1, tree.delete(1));
        Assertions.assertEquals(2, tree.delete(0));
        resetTree();
        insertMany(50, 25, 75, 10, 30, 60, 80, 5, 15, 27, 55, 1);
        Assertions.assertEquals(3, tree.delete(80));
    }

    @Test
    public void min() {
        Assertions.assertNull(tree.min());
        insertMany(10);
        Assertions.assertEquals(false, tree.min());
        insertMany(new Tuple<>(5, true));
        Assertions.assertEquals(true, tree.min());
        insertMany(15);
        Assertions.assertEquals(true, tree.min());
    }

    @Test
    public void max() {
        Assertions.assertNull(tree.max());
        insertMany(10);
        Assertions.assertEquals(false, tree.max());
        insertMany(new Tuple<>(5, true));
        Assertions.assertEquals(false, tree.max());
        insertMany(new Tuple<>(15, true));
        Assertions.assertEquals(true, tree.max());
    }

    @Test
    public void keysToArray() {
        int[] ints = {50, 25, 75, 10, 30, 60, 80, 5, 15, 27, 55, 1};
        insertMany(ints);
        Assertions.assertArrayEquals(Arrays.stream(ints).sorted().toArray(), tree.keysToArray());
    }


    @Test
    public void infoToArray() {
        insertMany(Tuple.zip(new Integer[]{2, 1, 3}, new Boolean[]{false, true, true}));
        Assertions.assertArrayEquals(tree.infoToArray(), new boolean[]{true, false, true});
        resetTree();
        int[] ints = {50, 25, 75, 10, 30, 60, 80, 5, 15, 27, 55, 1};
        insertMany(ints);
        Assertions.assertArrayEquals(tree.infoToArray(), createDuplicatedValue(false, ints.length));
    }

    @Test
    public void size(){
        Assertions.assertEquals(0, tree.size());
        int[] ints = {50, 25, 75, 10, 30, 60, 80, 5, 15, 27, 55, 1};
        insertMany(ints);
        Assertions.assertEquals(ints.length, tree.size());
    }

    @Test
    public void getRoot() {
        Assertions.assertNull(tree.getRoot());
        insertMany(50, 25, 75, 10, 30, 60, 80, 5, 15, 27, 55, 1);
        Assertions.assertEquals(50, tree.getRoot().getKey());
    }

    @Test
    public void successor() {
        int[] ints = {50, 25, 75, 10, 30, 60, 80, 5, 15, 27, 55, 1};
        int[] sorted = Arrays.stream(ints).sorted().toArray();
        insertMany(ints);
        AVLNode node = getMin(tree);
        for (int i = 0; i < sorted.length - 1; i++) {
            Assertions.assertEquals(sorted[i+1], tree.successor(node).getKey());
            node = tree.successor(node);
        }
        Assertions.assertNull(tree.successor(node));
    }

    private AVLNode getMin(AVLTree tree) {
        if (!tree.empty()) {
            AVLNode node = tree.getRoot();
            while (node.getLeft().isRealNode()) {
                node = node.getLeft();
            }
            return node;
        }
        return null;
    }

    @Test
    public void prefixXor() {
        Integer[] ints = {50, 25, 75, 10, 30, 60, 80, 5, 15, 27, 55, 1};
        int n = ints.length;
        Boolean[] values = new Boolean[n];
        for (int i = 0; i < n; i++) {
            Iterator<Integer> sorted = Arrays.stream(ints).sorted().iterator();
            for (int j = 0; j < n; j++) {
                values[j] = (n % (1 << j)) == 1;
            }
            resetTree();
            insertMany(Tuple.zip(ints, values));
            boolean xor = false;
            for (int j = 0; j < n; j++) {
                xor ^= (n % (1 << j)) == 1;
                Assertions.assertEquals(xor, tree.prefixXor(sorted.next()));
            }
        }
    }

    @Test
    public void succPrefixXor() {
        Integer[] ints = {50, 25, 75, 10, 30, 60, 80, 5, 15, 27, 55, 1};
        int n = ints.length;
        Boolean[] values = new Boolean[n];
        for (int i = 0; i < n; i++) {
            Iterator<Integer> sorted = Arrays.stream(ints).sorted().iterator();
            for (int j = 0; j < n; j++) {
                values[j] = (n % (1 << j)) == 1;
            }
            resetTree();
            insertMany(Tuple.zip(ints, values));
            boolean xor = false;
            for (int j = 0; j < n; j++) {
                xor ^= (n % (1 << j)) == 1;
                Assertions.assertEquals(xor, tree.succPrefixXor(sorted.next()));
            }
        }
    }

    private boolean[] createDuplicatedValue(boolean t, int length) {
        boolean[] arr = new boolean[length];
        for (int i = 0; i < length; i++) {
            arr[i] = t;
        }
        return arr;
    }

    private void resetTree() {
        tree = new AVLTree();
    }

    /**
     * inserts all the the keys given as argument to the tree with {@link AVLTree.AVLNode.info} false
     */
    private void insertMany(int... keysArray) {
        for (int key : keysArray) {
            tree.insert(key, false);
        }
    }

    @SafeVarargs
    private final void insertMany(Tuple<Integer, Boolean>... keysInfoArray) {
        for (Tuple<Integer, Boolean> keyInfo : keysInfoArray) {
            if (keyInfo != null)
                tree.insert(keyInfo.getKey(), keyInfo.getValue());
        }
    }

    private void insertMany(List<Tuple<Integer, Boolean>> keysInfoArray) {
        for (Tuple<Integer, Boolean> keyInfo : keysInfoArray) {
            if (keyInfo != null)
                tree.insert(keyInfo.getKey(), keyInfo.getValue());
        }
    }


    static final class Tuple<K, T> {
        private final K key;
        private final T value;

        Tuple(K key, T value) {
            this.key = key;
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public K getKey() {
            return key;
        }

        public static <K, T> List<Tuple<K, T>> zip(K[] keys, T[] values) {
            List<Tuple<K, T>> zippedTuples = new ArrayList<>();
            if (keys.length == values.length) {
                for (int i = 0; i < keys.length; i++) {
                    zippedTuples.add(new Tuple<>(keys[i], values[i]));
                }
            }
            return zippedTuples;
        }
    }

}