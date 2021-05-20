package AVLTree;

import AVLTree.AVLTree.AVLNode;

public class BigTest {
    private static boolean checkNodeFields(AVLTree tree, int key, int height, int parentKey, int leftKey, int rightKey, int subTreeSize, boolean subTreeXor, int successorKey, int predecessorKey) {
        AVLNode node = tree.searchNode(key);
        AVLNode parent = tree.searchNode(parentKey);
        AVLNode left = leftKey == -1 ? tree.getVirtualNode() : tree.searchNode(leftKey);
        AVLNode right = rightKey == -1 ? tree.getVirtualNode() : tree.searchNode(rightKey);
        AVLNode successor = tree.searchNode(successorKey);
        AVLNode predecessor = tree.searchNode(predecessorKey);
        boolean parentKeyBoolean = parentKey == key ? node.getParent() == null : node.getParent() == parent;
        boolean successorKeyBoolean = successorKey == key ? !node.getSuccessor().isRealNode() : node.getSuccessor() == successor;
        boolean predecessorKeyBoolean = predecessorKey == key ? !node.getPredecessor().isRealNode() : node.getPredecessor() == predecessor;

        return
                node.getKey() == key &&
                        node.getHeight() == height &&
                        left.getKey() == leftKey &&
                        parent.getKey() == parentKey &&
                        parentKeyBoolean &&
                        node.getLeft() == left &&
                        right.getKey() == rightKey &&
                        node.getRight() == right &&
                        node.getSize() == subTreeSize &&
                        node.getSubTreeXor() == subTreeXor &&
                        successorKeyBoolean &&
                        predecessorKeyBoolean

                ;
    }

    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        tree.insert(20, false);
        boolean one = testOne(tree);
        tree.insert(10, false);
        boolean two = testTwo(tree);
        tree.insert(5, false); // rotate right
        boolean three = testThree(tree);
        tree.insert(15, false);
        boolean four = testFour(tree);
        tree.insert(17, false); // rotate left then right
        boolean five = testFive(tree);
        tree.insert(19, false); // rotate left
        boolean six = testSix(tree);
        tree.insert(16, false);
        boolean seven = testSeven(tree);
        tree.insert(21, false);
        boolean eight = testEight(tree);
        tree.insert(22, false);
        boolean nine = testNine(tree);
        tree.insert(23, false); // rotate left
        boolean ten = testTen(tree);
        tree.insert(7,false);
        boolean eleven = testEleven(tree);
        tree.insert(6,false); // rotate right the left
        boolean twelve = testTwelve(tree);

        tree.delete(20); // rotate left and replacing
        boolean thirteen = testThirteen(tree);
        tree.delete(15); // bypass
        boolean fourteen = testFourteen(tree);
        tree.delete(16); // rotate right and leaf
        boolean fifteen = testFifteen(tree);

        tree.insert(8,false);
        tree.insert(3,false);
        tree.insert(12,false);
        boolean sixteen = testSixteen(tree);

        tree.delete(23); // rotate left then right twice, delete max
        boolean seventeen = testSeventeen(tree);
        tree.delete(22);
        boolean eighteen = testEighteen(tree);
        tree.delete(10);
        boolean nineteen = testNineteen(tree);
        tree.delete(12); // rotate right then left
        boolean twenty = testTwenty(tree);
        tree.delete(21);
        boolean twentyone = testTwentyone(tree);
        tree.delete(8); // rotate right
        boolean twentytwo = testTwentytwo(tree);
        tree.delete(19); // bypass
        boolean twentythree = testTwentythree(tree);
        tree.delete(3);
        boolean twentyfour = testTwentyfour(tree);
        tree.delete(17); // rotate left then right
        boolean twentyfive = testTwentyfive(tree);
        tree.delete(5); // rotate left then right
        tree.delete(7); // rotate left then right
        boolean twentysix = testTwentysix(tree);
        tree.delete(6);
        boolean twentyseven = tree.empty();

        System.out.println("one " + one);
        System.out.println("two " + two);
        System.out.println("three " + three);
        System.out.println("four " + four);
        System.out.println("five " + five);
        System.out.println("six " + six);
        System.out.println("seven " + seven);
        System.out.println("eight " + eight);
        System.out.println("nine " + nine);
        System.out.println("ten " + ten);
        System.out.println("eleven " + eleven);
        System.out.println("twelve " + twelve);
        System.out.println("thirteen " + thirteen);
        System.out.println("fourteen " + fourteen);
        System.out.println("fifteen " + fifteen);
        System.out.println("sixteen " + sixteen);
        System.out.println("seventeen " + seventeen);
        System.out.println("eighteen " + eighteen);
        System.out.println("nineteen " + nineteen);
        System.out.println("twenty " + twenty);
        System.out.println("twenty one " + twentyone);
        System.out.println("twenty two " + twentytwo);
        System.out.println("twenty three " + twentythree);
        System.out.println("twenty four " + twentyfour);
        System.out.println("twenty five " + twentyfive);
        System.out.println("twenty six " + twentysix);
        System.out.println("twenty seven " + twentyseven);

    }

    private static boolean testOne(AVLTree tree) {
        boolean isRoot = tree.getRoot().getKey() == 20;
        boolean isMin = tree.getMin().getKey() == 20;
        boolean isMax = tree.getMax().getKey() == 20;

        boolean node20check = checkNodeFields(
                tree, 20, 0, 20, -1, -1, 1, false, 20, 20
        );
        return
                isRoot && isMin && isMax &&
                        node20check
                ;
    }

    private static boolean testTwo(AVLTree tree) {
        boolean isRoot = tree.getRoot().getKey() == 20;
        boolean isMin = tree.getMin().getKey() == 10;
        boolean isMax = tree.getMax().getKey() == 20;

        boolean node20check = checkNodeFields(
                tree, 20, 1, 20, 10, -1, 2, false, 20, 10
        );
        boolean node10check = checkNodeFields(
                tree, 10, 0, 20, -1, -1, 1, false, 20, 10
        );


        return
                isRoot && isMin && isMax &&
                        node20check && node10check
                ;
    }

    private static boolean testThree(AVLTree tree) {
        boolean isRoot = tree.getRoot().getKey() == 10;
        boolean isMin = tree.getMin().getKey() == 5;
        boolean isMax = tree.getMax().getKey() == 20;

        boolean node20check = checkNodeFields(
                tree, 20, 0, 10, -1, -1, 1, false, 20, 10
        );
        boolean node10check = checkNodeFields(
                tree, 10, 1, 10, 5, 20, 3, false, 20, 5
        );
        boolean node5check = checkNodeFields(
                tree, 5, 0, 10, -1, -1, 1, false, 10, 5
        );


        return
                isRoot && isMin && isMax &&
                        node20check && node10check && node5check
                ;
    }

    private static boolean testFour(AVLTree tree) {
        boolean isRoot = tree.getRoot().getKey() == 10;
        boolean isMin = tree.getMin().getKey() == 5;
        boolean isMax = tree.getMax().getKey() == 20;

        boolean node20check = checkNodeFields(
                tree, 20, 1, 10, 15, -1, 2, false, 20, 15
        );
        boolean node10check = checkNodeFields(
                tree, 10, 2, 10, 5, 20, 4, false, 15, 5
        );
        boolean node5check = checkNodeFields(
                tree, 5, 0, 10, -1, -1, 1, false, 10, 5
        );
        boolean node15check = checkNodeFields(
                tree, 15, 0, 20, -1, -1, 1, false, 20, 10
        );


        return
                isRoot && isMin && isMax &&
                        node20check && node10check && node5check && node15check
                ;
    }

    private static boolean testFive(AVLTree tree) {
        boolean isRoot = tree.getRoot().getKey() == 10;
        boolean isMin = tree.getMin().getKey() == 5;
        boolean isMax = tree.getMax().getKey() == 20;

        boolean node20check = checkNodeFields(
                tree, 20, 0, 17, -1, -1, 1, false, 20, 17
        );
        boolean node10check = checkNodeFields(
                tree, 10, 2, 10, 5, 17, 5, false, 15, 5
        );
        boolean node5check = checkNodeFields(
                tree, 5, 0, 10, -1, -1, 1, false, 10, 5
        );
        boolean node15check = checkNodeFields(
                tree, 15, 0, 17, -1, -1, 1, false, 17, 10
        );
        boolean node17check = checkNodeFields(
                tree, 17, 1, 10, 15, 20, 3, false, 20, 15
        );


        return
                isRoot && isMin && isMax &&
                        node20check && node10check && node5check && node15check && node17check
                ;
    }

    private static boolean testSix(AVLTree tree) {
        boolean isRoot = tree.getRoot().getKey() == 17;
        boolean isMin = tree.getMin().getKey() == 5;
        boolean isMax = tree.getMax().getKey() == 20;

        boolean node20check = checkNodeFields(
                tree, 20, 1, 17, 19, -1, 2, false, 20, 19
        );
        boolean node10check = checkNodeFields(
                tree, 10, 1, 17, 5, 15, 3, false, 15, 5
        );
        boolean node5check = checkNodeFields(
                tree, 5, 0, 10, -1, -1, 1, false, 10, 5
        );
        boolean node15check = checkNodeFields(
                tree, 15, 0, 10, -1, -1, 1, false, 17, 10
        );
        boolean node17check = checkNodeFields(
                tree, 17, 2, 17, 10, 20, 6, false, 19, 15
        );
        boolean node19check = checkNodeFields(
                tree, 19, 0, 20, -1, -1, 1, false, 20, 17
        );

        return
                isRoot && isMin && isMax &&
                        node20check && node10check && node5check && node15check && node17check && node19check
                ;
    }

    private static boolean testSeven(AVLTree tree) {
        boolean isRoot = tree.getRoot().getKey() == 17;
        boolean isMin = tree.getMin().getKey() == 5;
        boolean isMax = tree.getMax().getKey() == 20;

        boolean node20check = checkNodeFields(
                tree, 20, 1, 17, 19, -1, 2, false, 20, 19
        );
        boolean node10check = checkNodeFields(
                tree, 10, 2, 17, 5, 15, 4, false, 15, 5
        );
        boolean node5check = checkNodeFields(
                tree, 5, 0, 10, -1, -1, 1, false, 10, 5
        );
        boolean node15check = checkNodeFields(
                tree, 15, 1, 10, -1, 16, 2, false, 16, 10
        );
        boolean node17check = checkNodeFields(
                tree, 17, 3, 17, 10, 20, 7, false, 19, 16
        );
        boolean node19check = checkNodeFields(
                tree, 19, 0, 20, -1, -1, 1, false, 20, 17
        );
        boolean node16check = checkNodeFields(
                tree, 16, 0, 15, -1, -1, 1, false, 17, 15
        );


        return
                isRoot && isMin && isMax &&
                        node20check && node10check && node5check && node15check && node17check && node19check && node16check
                ;
    }

    private static boolean testEight(AVLTree tree) {
        boolean isRoot = tree.getRoot().getKey() == 17;
        boolean isMin = tree.getMin().getKey() == 5;
        boolean isMax = tree.getMax().getKey() == 21;

        boolean node20check = checkNodeFields(
                tree, 20, 1, 17, 19, 21, 3, false, 21, 19
        );
        boolean node10check = checkNodeFields(
                tree, 10, 2, 17, 5, 15, 4, false, 15, 5
        );
        boolean node5check = checkNodeFields(
                tree, 5, 0, 10, -1, -1, 1, false, 10, 5
        );
        boolean node15check = checkNodeFields(
                tree, 15, 1, 10, -1, 16, 2, false, 16, 10
        );
        boolean node17check = checkNodeFields(
                tree, 17, 3, 17, 10, 20, 8, false, 19, 16
        );
        boolean node19check = checkNodeFields(
                tree, 19, 0, 20, -1, -1, 1, false, 20, 17
        );
        boolean node16check = checkNodeFields(
                tree, 16, 0, 15, -1, -1, 1, false, 17, 15
        );
        boolean node21check = checkNodeFields(
                tree, 21, 0, 20, -1, -1, 1, false, 21, 20
        );


        return
                isRoot && isMin && isMax &&
                        node20check && node10check && node5check && node15check && node17check && node19check && node16check && node21check
                ;
    }

    private static boolean testNine(AVLTree tree) {
        boolean isRoot = tree.getRoot().getKey() == 17;
        boolean isMin = tree.getMin().getKey() == 5;
        boolean isMax = tree.getMax().getKey() == 22;

        boolean node20check = checkNodeFields(
                tree, 20, 2, 17, 19, 21, 4, false, 21, 19
        );
        boolean node10check = checkNodeFields(
                tree, 10, 2, 17, 5, 15, 4, false, 15, 5
        );
        boolean node5check = checkNodeFields(
                tree, 5, 0, 10, -1, -1, 1, false, 10, 5
        );
        boolean node15check = checkNodeFields(
                tree, 15, 1, 10, -1, 16, 2, false, 16, 10
        );
        boolean node17check = checkNodeFields(
                tree, 17, 3, 17, 10, 20, 9, false, 19, 16
        );
        boolean node19check = checkNodeFields(
                tree, 19, 0, 20, -1, -1, 1, false, 20, 17
        );
        boolean node16check = checkNodeFields(
                tree, 16, 0, 15, -1, -1, 1, false, 17, 15
        );
        boolean node21check = checkNodeFields(
                tree, 21, 1, 20, -1, 22, 2, false, 22, 20
        );
        boolean node22check = checkNodeFields(
                tree, 22, 0, 21, -1, -1, 1, false, 22, 21
        );


        return
                isRoot && isMin && isMax &&
                        node20check && node10check && node5check && node15check && node17check && node19check && node16check && node21check && node22check
                ;
    }

    private static boolean testTen(AVLTree tree) {
        boolean isRoot = tree.getRoot().getKey() == 17;
        boolean isMin = tree.getMin().getKey() == 5;
        boolean isMax = tree.getMax().getKey() == 23;

        boolean node20check = checkNodeFields(
                tree, 20, 2, 17, 19, 22, 5, false, 21, 19
        );
        boolean node10check = checkNodeFields(
                tree, 10, 2, 17, 5, 15, 4, false, 15, 5
        );
        boolean node5check = checkNodeFields(
                tree, 5, 0, 10, -1, -1, 1, false, 10, 5
        );
        boolean node15check = checkNodeFields(
                tree, 15, 1, 10, -1, 16, 2, false, 16, 10
        );
        boolean node17check = checkNodeFields(
                tree, 17, 3, 17, 10, 20, 10, false, 19, 16
        );
        boolean node19check = checkNodeFields(
                tree, 19, 0, 20, -1, -1, 1, false, 20, 17
        );
        boolean node16check = checkNodeFields(
                tree, 16, 0, 15, -1, -1, 1, false, 17, 15
        );
        boolean node21check = checkNodeFields(
                tree, 21, 0, 22, -1, -1, 1, false, 22, 20
        );
        boolean node22check = checkNodeFields(
                tree, 22, 1, 20, 21, 23, 3, false, 23, 21
        );
        boolean node23check = checkNodeFields(
                tree, 23, 0, 22, -1, -1, 1, false, 23, 22
        );


        return
                isRoot && isMin && isMax &&
                        node20check && node10check && node5check && node15check && node17check && node19check && node16check && node21check && node22check && node23check
                ;
    }

    private static boolean testEleven(AVLTree tree) {
        boolean isRoot = tree.getRoot().getKey() == 17;
        boolean isMin = tree.getMin().getKey() == 5;
        boolean isMax = tree.getMax().getKey() == 23;

        boolean node20check = checkNodeFields(
                tree, 20, 2, 17, 19, 22, 5, false, 21, 19
        );
        boolean node10check = checkNodeFields(
                tree, 10, 2, 17, 5, 15, 5, false, 15, 7
        );
        boolean node5check = checkNodeFields(
                tree, 5, 1, 10, -1, 7, 2, false, 7, 5
        );
        boolean node15check = checkNodeFields(
                tree, 15, 1, 10, -1, 16, 2, false, 16, 10
        );
        boolean node17check = checkNodeFields(
                tree, 17, 3, 17, 10, 20, 11, false, 19, 16
        );
        boolean node19check = checkNodeFields(
                tree, 19, 0, 20, -1, -1, 1, false, 20, 17
        );
        boolean node16check = checkNodeFields(
                tree, 16, 0, 15, -1, -1, 1, false, 17, 15
        );
        boolean node21check = checkNodeFields(
                tree, 21, 0, 22, -1, -1, 1, false, 22, 20
        );
        boolean node22check = checkNodeFields(
                tree, 22, 1, 20, 21, 23, 3, false, 23, 21
        );
        boolean node23check = checkNodeFields(
                tree, 23, 0, 22, -1, -1, 1, false, 23, 22
        );
        boolean node7check = checkNodeFields(
                tree, 7, 0, 5, -1, -1, 1, false, 10, 5
        );


        return
                isRoot && isMin && isMax &&
                        node20check && node10check && node5check && node15check && node17check && node19check && node16check && node21check && node22check && node23check && node7check
                ;
    }

    private static boolean testTwelve(AVLTree tree) {
        boolean isRoot = tree.getRoot().getKey() == 17;
        boolean isMin = tree.getMin().getKey() == 5;
        boolean isMax = tree.getMax().getKey() == 23;

        boolean node20check = checkNodeFields(
                tree, 20, 2, 17, 19, 22, 5, false, 21, 19
        );
        boolean node10check = checkNodeFields(
                tree, 10, 2, 17, 6, 15, 6, false, 15, 7
        );
        boolean node5check = checkNodeFields(
                tree, 5, 0, 6, -1, -1, 1, false, 6, 5
        );
        boolean node15check = checkNodeFields(
                tree, 15, 1, 10, -1, 16, 2, false, 16, 10
        );
        boolean node17check = checkNodeFields(
                tree, 17, 3, 17, 10, 20, 12, false, 19, 16
        );
        boolean node19check = checkNodeFields(
                tree, 19, 0, 20, -1, -1, 1, false, 20, 17
        );
        boolean node16check = checkNodeFields(
                tree, 16, 0, 15, -1, -1, 1, false, 17, 15
        );
        boolean node21check = checkNodeFields(
                tree, 21, 0, 22, -1, -1, 1, false, 22, 20
        );
        boolean node22check = checkNodeFields(
                tree, 22, 1, 20, 21, 23, 3, false, 23, 21
        );
        boolean node23check = checkNodeFields(
                tree, 23, 0, 22, -1, -1, 1, false, 23, 22
        );
        boolean node7check = checkNodeFields(
                tree, 7, 0, 6, -1, -1, 1, false, 10, 6
        );
        boolean node6check = checkNodeFields(
                tree, 6, 1, 10, 5, 7, 3, false, 7, 5
        );


        return
                isRoot && isMin && isMax &&
                        node20check && node10check && node5check && node15check && node17check && node19check && node16check && node21check && node22check && node23check && node7check && node6check
                ;
    }

    private static boolean testThirteen(AVLTree tree) {
        boolean isRoot = tree.getRoot().getKey() == 17;
        boolean isMin = tree.getMin().getKey() == 5;
        boolean isMax = tree.getMax().getKey() == 23;

        boolean node10check = checkNodeFields(
                tree, 10, 2, 17, 6, 15, 6, false, 15, 7
        );
        boolean node5check = checkNodeFields(
                tree, 5, 0, 6, -1, -1, 1, false, 6, 5
        );
        boolean node15check = checkNodeFields(
                tree, 15, 1, 10, -1, 16, 2, false, 16, 10
        );
        boolean node17check = checkNodeFields(
                tree, 17, 3, 17, 10, 22, 11, false, 19, 16
        );
        boolean node19check = checkNodeFields(
                tree, 19, 1, 22, -1, 21, 2, false, 21, 17
        );
        boolean node16check = checkNodeFields(
                tree, 16, 0, 15, -1, -1, 1, false, 17, 15
        );
        boolean node21check = checkNodeFields(
                tree, 21, 0, 19, -1, -1, 1, false, 22, 19
        );
        boolean node22check = checkNodeFields(
                tree, 22, 2, 17, 19, 23, 4, false, 23, 21
        );
        boolean node23check = checkNodeFields(
                tree, 23, 0, 22, -1, -1, 1, false, 23, 22
        );
        boolean node7check = checkNodeFields(
                tree, 7, 0, 6, -1, -1, 1, false, 10, 6
        );
        boolean node6check = checkNodeFields(
                tree, 6, 1, 10, 5, 7, 3, false, 7, 5
        );


        return
                isRoot && isMin && isMax &&
                        node10check && node5check && node15check && node17check && node19check && node16check && node21check && node22check && node23check && node7check && node6check
                ;
    }

    private static boolean testFourteen(AVLTree tree) {
        boolean isRoot = tree.getRoot().getKey() == 17;
        boolean isMin = tree.getMin().getKey() == 5;
        boolean isMax = tree.getMax().getKey() == 23;

        boolean node10check = checkNodeFields(
                tree, 10, 2, 17, 6, 16, 5, false, 16, 7
        );
        boolean node5check = checkNodeFields(
                tree, 5, 0, 6, -1, -1, 1, false, 6, 5
        );
        boolean node17check = checkNodeFields(
                tree, 17, 3, 17, 10, 22, 10, false, 19, 16
        );
        boolean node19check = checkNodeFields(
                tree, 19, 1, 22, -1, 21, 2, false, 21, 17
        );
        boolean node16check = checkNodeFields(
                tree, 16, 0, 10, -1, -1, 1, false, 17, 10
        );
        boolean node21check = checkNodeFields(
                tree, 21, 0, 19, -1, -1, 1, false, 22, 19
        );
        boolean node22check = checkNodeFields(
                tree, 22, 2, 17, 19, 23, 4, false, 23, 21
        );
        boolean node23check = checkNodeFields(
                tree, 23, 0, 22, -1, -1, 1, false, 23, 22
        );
        boolean node7check = checkNodeFields(
                tree, 7, 0, 6, -1, -1, 1, false, 10, 6
        );
        boolean node6check = checkNodeFields(
                tree, 6, 1, 10, 5, 7, 3, false, 7, 5
        );


        return
                isRoot && isMin && isMax &&
                        node10check && node5check && node17check && node19check && node16check && node21check && node22check && node23check && node7check && node6check
                ;
    }

    private static boolean testFifteen(AVLTree tree) {
        boolean isRoot = tree.getRoot().getKey() == 17;
        boolean isMin = tree.getMin().getKey() == 5;
        boolean isMax = tree.getMax().getKey() == 23;

        boolean node10check = checkNodeFields(
                tree, 10, 1, 6, 7, -1, 2, false, 17, 7
        );
        boolean node5check = checkNodeFields(
                tree, 5, 0, 6, -1, -1, 1, false, 6, 5
        );
        boolean node17check = checkNodeFields(
                tree, 17, 3, 17, 6, 22, 9, false, 19, 10
        );
        boolean node19check = checkNodeFields(
                tree, 19, 1, 22, -1, 21, 2, false, 21, 17
        );
        boolean node21check = checkNodeFields(
                tree, 21, 0, 19, -1, -1, 1, false, 22, 19
        );
        boolean node22check = checkNodeFields(
                tree, 22, 2, 17, 19, 23, 4, false, 23, 21
        );
        boolean node23check = checkNodeFields(
                tree, 23, 0, 22, -1, -1, 1, false, 23, 22
        );
        boolean node7check = checkNodeFields(
                tree, 7, 0, 10, -1, -1, 1, false, 10, 6
        );
        boolean node6check = checkNodeFields(
                tree, 6, 2, 17, 5, 10, 4, false, 7, 5
        );


        return
                isRoot && isMin && isMax &&
                        node10check && node5check && node17check && node19check && node21check && node22check && node23check && node7check && node6check
                ;
    }

    private static boolean testSixteen(AVLTree tree) {
        boolean isRoot = tree.getRoot().getKey() == 17;
        boolean isMin = tree.getMin().getKey() == 3;
        boolean isMax = tree.getMax().getKey() == 23;

        boolean node10check = checkNodeFields(
                tree, 10, 1, 8, -1, 12, 2, false, 12, 8
        );
        boolean node5check = checkNodeFields(
                tree, 5, 1, 6, 3, -1, 2, false, 6, 3
        );
        boolean node17check = checkNodeFields(
                tree, 17, 4, 17, 6, 22, 12, false, 19, 12
        );
        boolean node19check = checkNodeFields(
                tree, 19, 1, 22, -1, 21, 2, false, 21, 17
        );
        boolean node21check = checkNodeFields(
                tree, 21, 0, 19, -1, -1, 1, false, 22, 19
        );
        boolean node22check = checkNodeFields(
                tree, 22, 2, 17, 19, 23, 4, false, 23, 21
        );
        boolean node23check = checkNodeFields(
                tree, 23, 0, 22, -1, -1, 1, false, 23, 22
        );
        boolean node7check = checkNodeFields(
                tree, 7, 0, 8, -1, -1, 1, false, 8, 6
        );
        boolean node6check = checkNodeFields(
                tree, 6, 3, 17, 5, 8, 7, false, 7, 5
        );
        boolean node8check = checkNodeFields(
                tree, 8, 2, 6, 7, 10, 4, false, 10, 7
        );
        boolean node3check = checkNodeFields(
                tree, 3, 0, 5, -1, -1, 1, false, 5, 3
        );
        boolean node12check = checkNodeFields(
                tree, 12, 0, 10, -1, -1, 1, false, 17, 10
        );


        return
                isRoot && isMin && isMax &&
                        node10check && node5check && node17check && node19check && node21check && node22check && node23check && node7check && node6check && node8check && node3check && node12check
                ;
    }

    private static boolean testSeventeen(AVLTree tree) {
        boolean isRoot = tree.getRoot().getKey() == 8;
        boolean isMin = tree.getMin().getKey() == 3;
        boolean isMax = tree.getMax().getKey() == 22;

        boolean node10check = checkNodeFields(
                tree, 10, 1, 17, -1, 12, 2, false, 12, 8
        );
        boolean node5check = checkNodeFields(
                tree, 5, 1, 6, 3, -1, 2, false, 6, 3
        );
        boolean node17check = checkNodeFields(
                tree, 17, 2, 8, 10, 21, 6, false, 19, 12
        );
        boolean node19check = checkNodeFields(
                tree, 19, 0, 21, -1, -1, 1, false, 21, 17
        );
        boolean node21check = checkNodeFields(
                tree, 21, 1, 17, 19, 22, 3, false, 22, 19
        );
        boolean node22check = checkNodeFields(
                tree, 22, 0, 21, -1, -1, 1, false, 22, 21
        );
        boolean node7check = checkNodeFields(
                tree, 7, 0, 6, -1, -1, 1, false, 8, 6
        );
        boolean node6check = checkNodeFields(
                tree, 6, 2, 8, 5, 7, 4, false, 7, 5
        );
        boolean node8check = checkNodeFields(
                tree, 8, 3, 8, 6, 17, 11, false, 10, 7
        );
        boolean node3check = checkNodeFields(
                tree, 3, 0, 5, -1, -1, 1, false, 5, 3
        );
        boolean node12check = checkNodeFields(
                tree, 12, 0, 10, -1, -1, 1, false, 17, 10
        );


        return
                isRoot && isMin && isMax &&
                        node10check && node5check && node17check && node19check && node21check && node22check && node7check && node6check && node8check && node3check && node12check
                ;
    }

    private static boolean testEighteen(AVLTree tree) {
        boolean isRoot = tree.getRoot().getKey() == 8;
        boolean isMin = tree.getMin().getKey() == 3;
        boolean isMax = tree.getMax().getKey() == 21;

        boolean node10check = checkNodeFields(
                tree, 10, 1, 17, -1, 12, 2, false, 12, 8
        );
        boolean node5check = checkNodeFields(
                tree, 5, 1, 6, 3, -1, 2, false, 6, 3
        );
        boolean node17check = checkNodeFields(
                tree, 17, 2, 8, 10, 21, 5, false, 19, 12
        );
        boolean node19check = checkNodeFields(
                tree, 19, 0, 21, -1, -1, 1, false, 21, 17
        );
        boolean node21check = checkNodeFields(
                tree, 21, 1, 17, 19, -1, 2, false, 21, 19
        );
        boolean node7check = checkNodeFields(
                tree, 7, 0, 6, -1, -1, 1, false, 8, 6
        );
        boolean node6check = checkNodeFields(
                tree, 6, 2, 8, 5, 7, 4, false, 7, 5
        );
        boolean node8check = checkNodeFields(
                tree, 8, 3, 8, 6, 17, 10, false, 10, 7
        );
        boolean node3check = checkNodeFields(
                tree, 3, 0, 5, -1, -1, 1, false, 5, 3
        );
        boolean node12check = checkNodeFields(
                tree, 12, 0, 10, -1, -1, 1, false, 17, 10
        );


        return
                isRoot && isMin && isMax &&
                        node10check && node5check && node17check && node19check && node21check && node7check && node6check && node8check && node3check && node12check
                ;
    }

    private static boolean testNineteen(AVLTree tree) {
        boolean isRoot = tree.getRoot().getKey() == 8;
        boolean isMin = tree.getMin().getKey() == 3;
        boolean isMax = tree.getMax().getKey() == 21;

        boolean node5check = checkNodeFields(
                tree, 5, 1, 6, 3, -1, 2, false, 6, 3
        );
        boolean node17check = checkNodeFields(
                tree, 17, 2, 8, 12, 21, 4, false, 19, 12
        );
        boolean node19check = checkNodeFields(
                tree, 19, 0, 21, -1, -1, 1, false, 21, 17
        );
        boolean node21check = checkNodeFields(
                tree, 21, 1, 17, 19, -1, 2, false, 21, 19
        );
        boolean node7check = checkNodeFields(
                tree, 7, 0, 6, -1, -1, 1, false, 8, 6
        );
        boolean node6check = checkNodeFields(
                tree, 6, 2, 8, 5, 7, 4, false, 7, 5
        );
        boolean node8check = checkNodeFields(
                tree, 8, 3, 8, 6, 17, 9, false, 12, 7
        );
        boolean node3check = checkNodeFields(
                tree, 3, 0, 5, -1, -1, 1, false, 5, 3
        );
        boolean node12check = checkNodeFields(
                tree, 12, 0, 17, -1, -1, 1, false, 17, 8
        );


        return
                isRoot && isMin && isMax &&
                        node5check && node17check && node19check && node21check && node7check && node6check && node8check && node3check && node12check
                ;
    }

    private static boolean testTwenty(AVLTree tree) {
        boolean isRoot = tree.getRoot().getKey() == 8;
        boolean isMin = tree.getMin().getKey() == 3;
        boolean isMax = tree.getMax().getKey() == 21;

        boolean node5check = checkNodeFields(
                tree, 5, 1, 6, 3, -1, 2, false, 6, 3
        );
        boolean node17check = checkNodeFields(
                tree, 17, 0, 19, -1, -1, 1, false, 19, 8
        );
        boolean node19check = checkNodeFields(
                tree, 19, 1, 8, 17, 21, 3, false, 21, 17
        );
        boolean node21check = checkNodeFields(
                tree, 21, 0, 19, -1, -1, 1, false, 21, 19
        );
        boolean node7check = checkNodeFields(
                tree, 7, 0, 6, -1, -1, 1, false, 8, 6
        );
        boolean node6check = checkNodeFields(
                tree, 6, 2, 8, 5, 7, 4, false, 7, 5
        );
        boolean node8check = checkNodeFields(
                tree, 8, 3, 8, 6, 19, 8, false, 17, 7
        );
        boolean node3check = checkNodeFields(
                tree, 3, 0, 5, -1, -1, 1, false, 5, 3
        );


        return
                isRoot && isMin && isMax &&
                        node5check && node17check && node19check && node21check && node7check && node6check && node8check && node3check
                ;
    }

    private static boolean testTwentyone(AVLTree tree) {
        boolean isRoot = tree.getRoot().getKey() == 8;
        boolean isMin = tree.getMin().getKey() == 3;
        boolean isMax = tree.getMax().getKey() == 19;

        boolean node5check = checkNodeFields(
                tree, 5, 1, 6, 3, -1, 2, false, 6, 3
        );
        boolean node19check = checkNodeFields(
                tree, 19, 1, 8, 17, -1, 2, false, 19, 17
        );
        boolean node7check = checkNodeFields(
                tree, 7, 0, 6, -1, -1, 1, false, 8, 6
        );
        boolean node6check = checkNodeFields(
                tree, 6, 2, 8, 5, 7, 4, false, 7, 5
        );
        boolean node8check = checkNodeFields(
                tree, 8, 3, 8, 6, 19, 7, false, 17, 7
        );
        boolean node3check = checkNodeFields(
                tree, 3, 0, 5, -1, -1, 1, false, 5, 3
        );
        boolean node17check = checkNodeFields(
                tree, 17, 0, 19, -1, -1, 1, false, 19, 8
        );


        return
                isRoot && isMin && isMax &&
                        node5check && node19check && node7check && node6check && node8check && node3check && node17check
                ;
    }

    private static boolean testTwentytwo(AVLTree tree) {
        boolean isRoot = tree.getRoot().getKey() == 7;
        boolean isMin = tree.getMin().getKey() == 3;
        boolean isMax = tree.getMax().getKey() == 19;

        boolean node5check = checkNodeFields(
                tree, 5, 1, 7, 3, 6, 3, false, 6, 3
        );
        boolean node19check = checkNodeFields(
                tree, 19, 1, 7, 17, -1, 2, false, 19, 8
        );
        boolean node7check = checkNodeFields(
                tree, 7, 2, 7, 5, 19, 6, false, 17, 6
        );
        boolean node6check = checkNodeFields(
                tree, 6, 0, 5, -1, -1, 1, false, 7, 5
        );
        boolean node3check = checkNodeFields(
                tree, 3, 0, 5, -1, -1, 1, false, 5, 3
        );
        boolean node17check = checkNodeFields(
                tree, 17, 0, 19, -1, -1, 1, false, 19, 7
        );


        return
                isRoot && isMin && isMax &&
                        node5check && node19check && node7check && node6check && node3check && node17check
                ;
    }

    private static boolean testTwentythree(AVLTree tree) {
        boolean isRoot = tree.getRoot().getKey() == 7;
        boolean isMin = tree.getMin().getKey() == 3;
        boolean isMax = tree.getMax().getKey() == 17;

        boolean node5check = checkNodeFields(
                tree, 5, 1, 7, 3, 6, 3, false, 6, 3
        );
        boolean node7check = checkNodeFields(
                tree, 7, 2, 7, 5, 17, 5, false, 17, 6
        );
        boolean node6check = checkNodeFields(
                tree, 6, 0, 5, -1, -1, 1, false, 7, 5
        );
        boolean node3check = checkNodeFields(
                tree, 3, 0, 5, -1, -1, 1, false, 5, 3
        );
        boolean node17check = checkNodeFields(
                tree, 17, 0, 7, -1, -1, 1, false, 17, 7
        );


        return
                isRoot && isMin && isMax &&
                        node5check && node7check && node6check && node3check && node17check
                ;
    }

    private static boolean testTwentyfour(AVLTree tree) {
        boolean isRoot = tree.getRoot().getKey() == 7;
        boolean isMin = tree.getMin().getKey() == 5;
        boolean isMax = tree.getMax().getKey() == 17;

        boolean node5check = checkNodeFields(
                tree, 5, 1, 7, -1, 6, 2, false, 6, 5
        );
        boolean node7check = checkNodeFields(
                tree, 7, 2, 7, 5, 17, 4, false, 17, 6
        );
        boolean node6check = checkNodeFields(
                tree, 6, 0, 5, -1, -1, 1, false, 7, 5
        );
        boolean node17check = checkNodeFields(
                tree, 17, 0, 7, -1, -1, 1, false, 17, 7
        );


        return
                isRoot && isMin && isMax &&
                        node5check && node7check && node6check && node17check
                ;
    }

    private static boolean testTwentyfive(AVLTree tree) {
        boolean isRoot = tree.getRoot().getKey() == 6;
        boolean isMin = tree.getMin().getKey() == 5;
        boolean isMax = tree.getMax().getKey() == 7;

        boolean node5check = checkNodeFields(
                tree, 5, 0, 6, -1, -1, 1, false, 6, 5
        );
        boolean node7check = checkNodeFields(
                tree, 7, 0, 6, -1, -1, 1, false, 7, 6
        );
        boolean node6check = checkNodeFields(
                tree, 6, 1, 6, 5, 7, 3, false, 7, 5
        );


        return
                isRoot && isMin && isMax &&
                        node5check && node7check && node6check
                ;
    }

    private static boolean testTwentysix(AVLTree tree) {
        boolean isRoot = tree.getRoot().getKey() == 6;
        boolean isMin = tree.getMin().getKey() == 6;
        boolean isMax = tree.getMax().getKey() == 6;


        boolean node6check = checkNodeFields(
                tree, 6, 0, 6, -1, -1, 1, false, 6, 6
        );


        return
                isRoot && isMin && isMax &&
                        node6check
                ;
    }
}