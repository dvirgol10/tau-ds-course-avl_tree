import AVLTree.AVLTree;
import AVLTree.AVLTree.AVLNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;


class AVLTreeTest {

    IAVLTree tree = new IAVLTree();

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
        isValidTree();
        tree.insert(1, true);
        isValidTree();
        Assertions.assertEquals(false, tree.search(2));
        Assertions.assertEquals(true, tree.search(1));
        Assertions.assertNull(tree.search(3));
    }

    @Test
    public void insert() {
        Assertions.assertEquals(1, tree.insert(1, true));
        isValidTree();
        Assertions.assertEquals(1, tree.insert(2, false));
        isValidTree();
        Assertions.assertEquals(-1, tree.insert(2, false));
        isValidTree();
        Assertions.assertEquals(2, tree.insert(3, true));
    }

    private void isValidTree() {
        for (Tuple<AVLNode, Integer> nodeInteger :
                tree.getIterator()) {
            AVLNode node = nodeInteger.getKey();
            AVLNode leftChild = node.getLeft();
            AVLNode rightChild = node.getRight();
            Assertions.assertEquals(Math.max(leftChild.getHeight(), rightChild.getHeight()) + 1, node.getHeight());
            Assertions.assertEquals(leftChild.getSize() + rightChild.getSize() + 1, node.getSize());
            Assertions.assertEquals(leftChild.getSubTreeXor() ^ rightChild.getSubTreeXor() ^ node.getValue(), node.getSubTreeXor());
        }
    }

    @Test
    public void delete() {
        Assertions.assertEquals(-1, tree.delete(1));
        insertMany(1, 2, 3);
        isValidTree();
        Assertions.assertEquals(-1, tree.delete(4));
        isValidTree();
        Assertions.assertEquals(1, tree.delete(2));
        isValidTree();
        insertMany(4, 2);
        isValidTree();
        Assertions.assertEquals(2, tree.delete(4));
        isValidTree();
        insertMany(5, 0, 4);
        isValidTree();
        Assertions.assertEquals(1, tree.delete(1));
        isValidTree();
        Assertions.assertEquals(2, tree.delete(0));
        resetTree();
        insertMany(50, 25, 75, 10, 30, 60, 80, 5, 15, 27, 55, 1);
        isValidTree();
        Assertions.assertEquals(3, tree.delete(80));
        isValidTree();
    }

    @Test
    public void min() {
        Assertions.assertNull(tree.min());
        insertMany(10);
        isValidTree();
        Assertions.assertEquals(false, tree.min());
        insertMany(new Tuple<>(5, true));
        isValidTree();
        Assertions.assertEquals(true, tree.min());
        insertMany(15);
        isValidTree();
        Assertions.assertEquals(true, tree.min());
    }

    @Test
    public void max() {
        Assertions.assertNull(tree.max());
        insertMany(10);
        isValidTree();
        Assertions.assertEquals(false, tree.max());
        insertMany(new Tuple<>(5, true));
        isValidTree();
        Assertions.assertEquals(false, tree.max());
        insertMany(new Tuple<>(15, true));
        isValidTree();
        Assertions.assertEquals(true, tree.max());
    }

    @Test
    public void keysToArray() {
        int[] ints = {50, 25, 75, 10, 30, 60, 80, 5, 15, 27, 55, 1};
        insertMany(ints);
        isValidTree();
        Assertions.assertArrayEquals(Arrays.stream(ints).sorted().toArray(), tree.keysToArray());
    }


    @Test
    public void infoToArray() {
        insertMany(Tuple.zip(new Integer[]{2, 1, 3}, new Boolean[]{false, true, true}));
        isValidTree();
        Assertions.assertArrayEquals(tree.infoToArray(), new boolean[]{true, false, true});
        resetTree();
        int[] ints = {50, 25, 75, 10, 30, 60, 80, 5, 15, 27, 55, 1};
        insertMany(ints);
        isValidTree();
        Assertions.assertArrayEquals(tree.infoToArray(), createDuplicatedValue(false, ints.length));
    }

    @Test
    public void size() {
        Assertions.assertEquals(0, tree.size());
        int[] ints = {50, 25, 75, 10, 30, 60, 80, 5, 15, 27, 55, 1};
        insertMany(ints);
        isValidTree();
        Assertions.assertEquals(ints.length, tree.size());
    }

    @Test
    public void getRoot() {
        Assertions.assertNull(tree.getRoot());
        insertMany(50, 25, 75, 10, 30, 60, 80, 5, 15, 27, 55, 1);
        isValidTree();
        Assertions.assertEquals(50, tree.getRoot().getKey());
    }

    @Test
    public void successor() {
        Integer[] ints = {50, 25, 75, 10, 30, 60, 80, 5, 15, 27, 55, 1};
        Integer[] sorted = Stream.concat(Arrays.stream(ints).sorted(), Stream.of((Integer) null)).toArray(Integer[]::new);
        insertMany(Arrays.stream(ints).mapToInt(i -> i).toArray());
        isValidTree();
        for (Tuple<AVLNode, Integer> nodeInteger :
                tree.getIterator()) {
            Assertions.assertEquals(sorted[nodeInteger.getValue() + 1], (tree.successor(nodeInteger.getKey()) == null ? null : tree.successor(nodeInteger.getKey()).getKey()));
        }
    }

    private AVLNode getMin() {
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
            isValidTree();
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
            isValidTree();
            boolean xor = false;
            for (int j = 0; j < n; j++) {
                xor ^= (n % (1 << j)) == 1;
                Assertions.assertEquals(xor, tree.succPrefixXor(sorted.next()));
            }
        }
    }

    class IAVLTree extends AVLTree {
        treeIterator getIterator() {
            return new treeIterator();
        }
    }

    class treeIterator implements Iterator<Tuple<AVLNode, Integer>>, Iterable<Tuple<AVLNode, Integer>> {
        AVLNode current = tree.getVirtualNode();
        int counter = 0;

        @Override
        public boolean hasNext() {
            return !current.isRealNode() || tree.successor(current) != null;
        }

        @Override
        public Tuple<AVLNode, Integer> next() {
            counter++;
            if (current.isRealNode())
                current = tree.successor(current);
            else
                current = getMin();
            return new Tuple<AVLNode, Integer>(current, counter - 1);
        }

        @Override
        public Iterator<Tuple<AVLNode, Integer>> iterator() {
            return this;
        }

        @Override
        public void forEach(Consumer<? super Tuple<AVLNode, Integer>> action) {
            Iterable.super.forEach(action);
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
        tree = new IAVLTree();
    }

    /**
     * inserts all the the keys given as argument to the tree with info false
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