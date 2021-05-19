package AVLTree; //TODO remove in the submitted file

/**
 * public class AVLNode
 * <p>
 * This class represents an AVLTree with integer keys and boolean values.
 * <p>
 * IMPORTANT: do not change the signatures of any function (i.e. access modifiers, return type, function name and
 * arguments. Changing these would break the automatic tester, and would result in worse grade.
 * <p>
 * However, you are allowed (and required) to implement the given functions, and can add functions of your own
 * according to your needs.
 */

public class AVLTree {
    /**
     * Enum Direction that holds two directions for prevention of code duplication in the rotation code
     */
    enum Direction {
        Right, Left;

        //time complexity: O(1)
        public Direction reverseDir() {
            if (this == Direction.Left) {
                return Right;
            }
            return Left;
        }

    }

    /**
     * a virtual node that will be the child of a node without at least one real node
     */
    private final AVLNode virtualNode = new AVLNode();

    /**
     * The root of the tree, null if and only if the tree is empty
     */
    private AVLNode root;

    /**
     * The node with the minimal key in the tree
     */
    private AVLNode minNode;

    /**
     * The node with the maximal key in the tree
     */
    private AVLNode maxNode;

    /**
     * This constructor creates an empty AVLTree.
     * <p>
     * time complexity: O(1)
     */
    public AVLTree() {
    }

    /**
     * public AVLNode getVirtualNode()
     * <p>
     * Returns a virtual node.
     * <p>
     * time complexity: O(1)
     */
    public AVLNode getVirtualNode() {
        return this.virtualNode;
    }

    /**
     * public AVLNode getRoot()
     * <p>
     * Returns the root AVL node, or null if the tree is empty
     * <p>
     * time complexity: O(1)
     */
    public AVLNode getRoot() {
        return this.root;
    }

    /**
     * public void setRoot()
     * <p>
     * Sets the root AVL node.
     * <p>
     * time complexity: O(1)
     */
    public void setRoot(AVLNode root) {
        this.root = root;
    }

    /**
     * public boolean empty()
     * <p>
     * Returns true if and only if the tree is empty
     * <p>
     * time complexity: O(1)
     */
    public boolean empty() {
        return this.getRoot() == null;
    }

    /**
     * public int size()
     * <p>
     * Returns the number of nodes in the tree.
     * <p>
     * time complexity: O(1)
     */
    public int size() {
        return this.empty() ? 0 : this.getRoot().getSize();
    }

    /**
     * public AVLNode getMin()
     * <p>
     * Returns the node with the minimal key in the tree if,
     * or null if the tree is empty.
     * <p>
     * time complexity: O(1)
     */
    public AVLNode getMin() {
        return this.minNode;
    }

    /**
     * public void setMin()
     * <p>
     * Sets the node with the minimal key to min.
     * <p>
     * time complexity: O(1)
     */
    public void setMin(AVLNode min) {
        this.minNode = min;
    }

    /**
     * public AVLNode getMax()
     * <p>
     * Returns the node with the maximal key in the tree if,
     * or null if the tree is empty.
     * <p>
     * time complexity: O(1)
     */
    public AVLNode getMax() {
        return this.maxNode;
    }

    /**
     * public void setMax()
     * <p>
     * Sets the node with the maximal key to max.
     * <p>
     * time complexity: O(1)
     */
    public void setMax(AVLNode max) {
        this.maxNode = max;
    }

    /**
     * public AVLNode searchNode(int k)
     * <p>
     * Returns the item with key k in the tree,
     * or the theoretical parent if an item with key k was not found in the tree.
     * <p>
     * precondition: this.empty() != true
     * <p>
     * time complexity: O(log(n)) when n is the number of nodes in the tree
     */
    public AVLNode searchNode(int k) {
        AVLNode node = this.getRoot();
        while (node.getKey() != k) { // iterating down from the top
            if (node.getKey() > k) {
                if (node.getLeft().isRealNode()) { // checking if we got to the virtual node (if the node existed in the tree it should have been instead of the virtual node)
                    node = node.getLeft();
                } else { // it reaches the virtual node - returns the "his parent"
                    break;
                }
            } else { // symmetric for the other side
                if (node.getRight().isRealNode()) {
                    node = node.getRight();
                } else {
                    break;
                }
            }
        }
        return node;
    }

    /**
     * public Boolean search(int k)
     * <p>
     * Returns the info of the item with key k in the tree,
     * or null if item with key k was not found in the tree.
     * <p>
     * time complexity: O(log(n)) when n is the number of nodes in the tree
     */
    public Boolean search(int k) {
        if (this.empty()) {
            return null;
        }
        AVLNode node = searchNode(k);
        return node.getKey() == k ? node.getValue() : null; // if the return value doesn't match with the key it means the node does not exists
    }

    /**
     * public int insert(int k, boolean i)
     * <p>
     * inserts an item with key k and info i to the AVL tree.
     * the tree must remain valid (keep its invariants).
     * returns the number of nodes which require rebalancing operations (i.e. promotions or rotations).
     * This always includes the newly-created node.
     * returns -1 if an item with key k already exists in the tree.
     * <p>
     * time complexity: O(log(n)) when n is the number of node in the tree
     */
    public int insert(int k, boolean i) {
        if (this.empty()) { // if the tree is empty, initializes it with a new node with key k and info i
            this.setRoot(new AVLNode(k, i, this.getVirtualNode()));
            this.setMin(this.getRoot());
            this.setMax(this.getRoot());
            this.getRoot().setParent(this.getVirtualNode());
            return 1;
        }
        AVLNode node = searchNode(k); // node is the item with key k in the tree, or the theoretical parent if an item with key k was not found in the tree.
        if (node.getKey() == k) { // if a node with key k exists, return -1
            return -1;
        }
        AVLNode newNode = new AVLNode(k, i, node);
        if (node.getKey() > k) {
            updateRelationsForNewLeftChild(node, newNode);
        } else {
            updateRelationsForNewRightChild(node, newNode);
        }

        AVLNode lastUpdatedNode = this.balanceTreeOnce(node); // balance the tree - as we seen in class, after an insertion at most one balancing operations is needed
        if (lastUpdatedNode.isRealNode()) { // in other words, if a rotation has been performed,
            this.balanceTreeOnce(lastUpdatedNode); // continues to update nodes in the path from the inserted node to the root
            return 2;
        }
        return 1;
    }

    /**
     * private void updateRelationsForNewLeftChild(AVLNode parent, AVLNode newNode)
     * <p>
     * updates the relations of the successors and predecessors of parent and newNode,
     * and update tree's min node if needed.
     * <p>
     * time complexity: O(1)
     */
    private void updateRelationsForNewLeftChild(AVLNode parent, AVLNode newNode) {
        parent.setLeft(newNode);
        if (this.getMin() == parent) {
            this.setMin(newNode);
        }
        updateSuccessor(parent.getPredecessor(), newNode); // sets the new node as the successor of the parent's predecessor
        updateSuccessor(newNode, parent); // sets parent as the successor of the new node
    }

    /**
     * private void updateRelationsForNewRightChild(AVLNode parent, AVLNode newNode)
     * <p>
     * updates the relations of the successors and predecessors of parent and newNode,
     * and update tree's max node if needed.
     * <p>
     * time complexity: O(1)
     */
    private void updateRelationsForNewRightChild(AVLNode parent, AVLNode newNode) { // same for updateRelationsForNewLeftChild
        parent.setRight(newNode);
        if (this.getMax() == parent) {
            this.setMax(newNode);
        }
        updateSuccessor(newNode, parent.getSuccessor());
        updateSuccessor(parent, newNode);
    }

    /**
     * protected void updateSuccessor(AVLNode node, AVLNode newNode)
     * <p>
     * updates the successor of node to be newNode and sets node as newNodes predecessor
     * <p>
     * time complexity: O(1)
     */
    protected void updateSuccessor(AVLNode node, AVLNode newNode) {
        newNode.setPredecessor(node);
        node.setSuccessor(newNode);
    }

    /**
     * protected AVLNode balanceTreeOnce(AVLNode node)
     * <p>
     * updates the fields of all the nodes from the bottom of the tree until the first rotation,
     * or until it reaches the root if no rotation has been performed.
     * Returns the root if and only if the tree is balanced
     * and all the nodes are up to date.
     * <p>
     * time complexity: O(d) when d is the of length of the path between the given node and the node that needs balancing, or the root if there is no such thing
     */
    protected AVLNode balanceTreeOnce(AVLNode node) {
        while (node.isRealNode()) { // iterates up through the tree to the first unbalanced element, if the tree is balanced we exit the loop and return the virtual node
            node.updateNodeFields(); // update size, height and subTreeXor of the node in O(1)
            if (this.isUnbalanced(node)) {
                this.balanceNode(node);
                return node.getParent(); // if node is unbalanced, balance it and return it's parent that "took it's place" as a result of the rotation
            }
            node = node.getParent();
        }
        return this.getVirtualNode();
    }

    /**
     * private void balanceNode(AVLNode node)
     * <p>
     * Performs balance operations (rotations) on the given node, if needed.
     * <p>
     * time complexity: O(1)
     */
    private void balanceNode(AVLNode node) { // checks the cases as we learned in class that matches both insertions and deletions
        if (node.getBalanceFactor() == 2) {
            if (node.getLeft().getBalanceFactor() == -1) {
                this.rotateLeftThenRight(node);
            } else if (node.getLeft().getBalanceFactor() == 1 || node.getLeft().getBalanceFactor() == 0) {
                this.rotateRight(node);
            }
        } else if (node.getBalanceFactor() == -2) {
            if (node.getRight().getBalanceFactor() == -1 || node.getRight().getBalanceFactor() == 0) {
                this.rotateLeft(node);
            } else if (node.getRight().getBalanceFactor() == 1) {
                this.rotateRightThenLeft(node);
            }
        }
    }

    /**
     * private boolean isUnbalanced(AVLNode node)
     * <p>
     * return true if and only if the node is unbalanced.
     * <p>
     * time complexity: O(1)
     */
    private boolean isUnbalanced(AVLNode node) {
        return node.getBalanceFactor() > 1 || node.getBalanceFactor() < -1;
    }

    /**
     * private void rotateInDir(AVLNode node, Direction dir)
     * <p>
     * implement rotation for both sides using the {@link Direction} enum
     *
     * @param node the node to rotate from
     * @param dir  the direction of the rotation
     * <p>
     * time complexity: O(1)
     */
    private void rotateInDir(AVLNode node, Direction dir) {
        Direction nodesPreviousDirection = getDirectionFromParent(node); // save the node's direction relative to it's parent for the replacement later on
        AVLNode child = node.getChildInDir(dir.reverseDir()); // get the child for the swap

        node.setChildInDir(child.getChildInDir(dir), dir.reverseDir()); // replace the child in the rotation
        node.getChildInDir(dir.reverseDir()).setParent(node);

        child.setParent(node.getParent());
        child.setChildInDir(node, dir);
        node.setParent(child);
        child.getParent().setChildInDir(child, nodesPreviousDirection); // set the child as the node's previous parent in the same direction

        node.updateNodeFields(); // update the fields - the order matters!
        child.updateNodeFields();
        if (this.getRoot() == node) {
            this.setRoot(child);
        }
    }


    /**
     * private void rotateRight(AVLNode node)
     * <p>
     * Rotates the tree right from the given node making it the right child of its pre left child
     * <p>
     * time complexity: O(1)
     */
    private void rotateRight(AVLNode node) { // rotating as we learned in class in O(1)
        rotateInDir(node, Direction.Right);
    }

    /**
     * private void rotateLeft(AVLNode node)
     * <p>
     * Rotates the subtree left from the given node making it the left child of its pre right child
     * <p>
     * time complexity: O(1)
     */
    private void rotateLeft(AVLNode node) {
        rotateInDir(node, Direction.Left);
    }

    /**
     * private void rotateLeftThenRight(AVLNode node)
     * <p>
     * Rotates the tree left from the given node's left child and the rotates right from the node
     * <p>
     * time complexity: O(1)
     */
    private void rotateLeftThenRight(AVLNode node) {
        rotateLeft(node.getLeft());
        rotateRight(node);
    }

    /**
     * private void rotateRightThenLeft(AVLNode node)
     * <p>
     * Rotates the tree right from the given node's right child and the rotates left from the node
     * <p>
     * time complexity: O(1)
     */
    private void rotateRightThenLeft(AVLNode node) {
        rotateRight(node.getRight());
        rotateLeft(node);
    }

    /**
     * public int delete(int k)
     * <p>
     * deletes an item with key k from the binary tree, if it is there;
     * the tree must remain valid (keep its invariants).
     * returns the number of nodes which required rebalancing operations (i.e. demotions or rotations).
     * returns -1 if an item with key k was not found in the tree.
     * <p>
     * time complexity: O(log(n)) when n is the number of node in the tree
     */
    public int delete(int k) {
        if (this.empty()) {
            return -1;
        }
        AVLNode node = searchNode(k);
        if (node.getKey() != k) { // if the node with key k is not in the tree
            return -1;
        }

        AVLNode nodeToBeBalancedFrom = node.getParent();

        updateSuccessor(node.getPredecessor(), node.getSuccessor()); // keep the correctness of the successor and predecessor pointers

        if (this.getRoot() == node && node.getChildCount() == 0) { // the root is the only node in the tree
            this.setRoot(null);
            this.setMin(null);
            this.setMax(null);
            return 1;
        } else if (this.getMin() == node) { // the node to delete is the min element
            this.setMin(node.getSuccessor());
            node.getRight().setParent(node.getParent());
            if (this.getRoot() != node) { // root is not the min element
                node.getParent().setLeft(node.getRight());
            } else { // min element is the root
                this.setRoot(node.getRight());
                this.getRoot().setParent(this.getVirtualNode());
            }

        } else if (this.getMax() == node) { // the node to delete is the max element
            this.setMax(node.getPredecessor());
            node.getLeft().setParent(node.getParent());
            if (this.getRoot() != node) { // root is not the max element
                node.getParent().setRight(node.getLeft());
            } else { // max element is the root
                this.setRoot(node.getLeft());
                this.getRoot().setParent(this.getVirtualNode());
            }
        } else {
            if (node.getChildCount() == 1) {
                // if the node at question has only one child we can bypass it by replacing it with it's only child

                AVLNode child = node.getChildInDir((node.getLeft().isRealNode() ? Direction.Left : Direction.Right)); // gets the child to perform the bypass with

                node.getParent().setChildInDir(child, getDirectionFromParent(node)); // perform bypass

                child.setParent(node.getParent());

            } else if (node.getChildCount() == 2) {
            /*
             if the node has two children, perform a replacement with it's predecessor.
             as we seen in class the node's predecessor has to be the max element in the left child sub-tree which is
             either his direct child if it has no right child or a node further down right in his right child's sub-tree.
             1. perform a bypass with the left child (the predecessor) - afterwards the balance is from the predecessor.
             2. perform a bypass with the predecessor - afterwards the balance is from the predecessor previous parent.
             */
                AVLNode pre = node.getPredecessor();

                nodeToBeBalancedFrom = pre.getParent() == node ? pre : pre.getParent(); // addressing the 2 cases

                pre.getParent().setChildInDir(pre.getLeft(), getDirectionFromParent(pre)); // the first part of the bypass

                pre.setParent(node.getParent()); // the second part of the bypass
                node.getParent().setChildInDir(pre, getDirectionFromParent(node));

                replaceChildren(node, pre);

                if (this.getRoot() == node) { // if the node is the root, the new root will be his predecessor
                    this.setRoot(pre);
                }
            } else { // a leaf which is neither the min nor the max element
                node.getParent().setChildInDir(this.getVirtualNode(), getDirectionFromParent(node));
            }
        }
        /*
        if the case of deletion isn't replacement the node with his successor,
        balance the tree from the deleted node up to the root.
        The balance operation also updates the fields of the nodes and counts the number of the rotations.
         */
        return balanceTree(nodeToBeBalancedFrom);
    }

    /**
     * private void replaceChildren(AVLNode oldParent, AVLNode newParent)
     * <p>
     * sets the oldParent children to be the newParent children.
     * <p>
     * time complexity: O(1)
     */
    private void replaceChildren(AVLNode oldParent, AVLNode newParent) {
        newParent.setRight(oldParent.getRight()); // right child
        oldParent.getRight().setParent(newParent);

        newParent.setLeft(oldParent.getLeft()); // left child
        oldParent.getLeft().setParent(newParent);
    }

    /**
     * private Direction getDirectionFromParent(AVLNode node)
     * <p>
     * retrieves the nodes direction from it's parent.
     * i.e. if the node is the right child the method will return Direction.Right.
     * if the given node is the root the returned value will be Direction.Right.
     * <p>
     * time complexity: O(1)
     */
    private Direction getDirectionFromParent(AVLNode node) {
        return node.isLeftChild() ? Direction.Left : Direction.Right;
    }

    /**
     * private int balanceTree(AVLNode node)
     * <p>
     * performs balancing operations to the root.
     * returns the number of balancing operations that were performed + 1.
     * <p>
     * time complexity: O(log(n)) when n is the number of node in the tree
     */
    private int balanceTree(AVLNode node) {
        int rebalancingOperationsCounter = 0;
        AVLNode lastUpdatedNode = node;
        while (lastUpdatedNode.isRealNode()) { // balance to the top until the tree is balanced
            lastUpdatedNode = this.balanceTreeOnce(lastUpdatedNode);
            rebalancingOperationsCounter += 1; // for each balance operation it increments the counter, and for the last iteration when the tree is balanced it increments the counter for the insertion itself
        }
        return rebalancingOperationsCounter;
    }

    /**
     * public Boolean min()
     * <p>
     * Returns the info of the item with the smallest key in the tree,
     * or null if the tree is empty
     * <p>
     * time complexity: O(1)
     */
    public Boolean min() {
        return this.empty() ? null : this.getMin().getValue();
    }

    /**
     * public Boolean max()
     * <p>
     * Returns the info of the item with the largest key in the tree,
     * or null if the tree is empty
     * <p>
     * time complexity: O(1)
     */
    public Boolean max() {
        return this.empty() ? null : this.getMax().getValue();
    }

    /**
     * public void inOrder(AVLNode node, int offset, AVLNode[] arr)
     * <p>
     * Auxiliary recursive function of nodesToArray(),
     * performs in-order tree walk while adding the node to the array.
     * <p>
     * time complexity: O(n) when n is the number of node in the tree
     */
    public void inOrder(AVLNode node, int offset, AVLNode[] arr) {
        if (node.isRealNode()) {
            inOrder(node.getLeft(), offset, arr);
            arr[offset + node.getLeft().getSize()] = node; // assigns the node in the right position in the in-order array
            inOrder(node.getRight(), offset + node.getLeft().getSize() + 1, arr);
        }
    }

    /**
     * public AVLNode[] nodesToArray()
     * <p>
     * Returns an array which contains all nodes in the tree,
     * sorted by their respective keys,
     * or an empty array if the tree is empty.
     * <p>
     * time complexity: O(n) when n is the number of node in the tree
     */
    public AVLNode[] nodesToArray() {
        AVLNode[] arr = new AVLNode[this.size()];
        if (!this.empty()) {
            inOrder(this.getRoot(), 0, arr);
        }
        return arr;
    }

    /**
     * public int[] keysToArray()
     * <p>
     * Returns a sorted array which contains all keys in the tree,
     * or an empty array if the tree is empty.
     * <p>
     * time complexity: O(n) when n is the number of node in the tree
     */
    public int[] keysToArray() {
        int[] arr = new int[this.size()];
        if (!this.empty()) {
            AVLNode[] nodesArr = nodesToArray(); // in-order traversal O(n)
            for (int i = 0; i < arr.length; i++) {
                arr[i] = nodesArr[i].getKey(); // extracts keys in O(n)
            }
        }
        return arr;
    }

    /**
     * public boolean[] infoToArray()
     * <p>
     * Returns an array which contains all info in the tree,
     * sorted by their respective keys,
     * or an empty array if the tree is empty.
     * <p>
     * time complexity: O(n) when n is the number of node in the tree
     */
    public boolean[] infoToArray() {
        boolean[] arr = new boolean[this.size()];
        if (!this.empty()) {
            AVLNode[] nodesArr = nodesToArray(); // in-order traversal O(n)
            for (int i = 0; i < arr.length; i++) {
                arr[i] = nodesArr[i].getValue(); // extracts keys in O(n)
            }
        }
        return arr;
    }

    /**
     * public boolean prefixXor(int k)
     * <p>
     * Given an argument k which is a key in the tree, calculate the xor of the values of nodes whose keys are
     * smaller or equal to k.
     * <p>
     * precondition: this.search(k) != null
     * <p>
     * time complexity: O(log(n)) when n is the number of node in the tree
     */
    public boolean prefixXor(int k) {
        AVLNode node = this.getRoot(); // starts a traversal top down to the node
        boolean xorValue = false;
        while (node.getKey() != k) {
            if (node.getKey() > k) { // if the node with key k is to the left (with a key that is bigger than k), advances in its direction and doesn't xor it
                node = node.getLeft();
            } else { // if the node is to the right, xor it and and all the nodes left sub tree (which is achieved in O(1))
                xorValue ^= node.getValue();
                xorValue ^= node.getLeft().getSubTreeXor();
                node = node.getRight();
            }
        }
        return xorValue ^ node.getValue() ^ node.getLeft().getSubTreeXor(); // when it reaches the node, also xor its info as well as all of the nodes in its left sub-tree
    }

    /**
     * public AVLNode successor
     * <p>
     * given a node 'node' in the tree, return the successor of 'node' in the tree (or null if successor doesn't exist)
     *
     * @param node - the node whose successor should be returned
     * @return the successor of 'node' if exists, null otherwise
     * <p>
     * time complexity: O(1)
     */
    public AVLNode successor(AVLNode node) {
        return node.getSuccessor().isRealNode() ? node.getSuccessor() : null;
    }

    /**
     * public boolean succPrefixXor(int k)
     * <p>
     * This function is identical to prefixXor(int k) in terms of input/output. However, the implementation of
     * succPrefixXor should be the following: starting from the minimum-key node, iteratively call successor until
     * you reach the node of key k. Return the xor of all visited nodes.
     * <p>
     * precondition: this.search(k) != null
     * <p>
     * time complexity: O(n) when n is the number of node in the tree
     */
    public boolean succPrefixXor(int k) {
        AVLNode node = this.getMin(); // starting from the minimum-key node
        boolean xorValue = false;
        while (node.getKey() < k) { // sequentially advances to each node successor until we reach the node with key k
            xorValue ^= node.getValue();
            node = successor(node);
        }
        return xorValue ^ node.getValue();
    }


    /**
     * public class AVLNode
     * <p>
     * This class represents a node in the AVL tree.
     * <p>
     * IMPORTANT: do not change the signatures of any function (i.e. access modifiers, return type, function name and
     * arguments. Changing these would break the automatic tester, and would result in worse grade.
     * <p>
     * However, you are allowed (and required) to implement the given functions, and can add functions of your own
     * according to your needs.
     */
    public class AVLNode {
        private final int key;
        private final boolean info;
        private int height;
        private AVLNode parent;
        private AVLNode left;
        private AVLNode right;
        private int subTreeSize;
        private boolean subTreeXor;
        private AVLNode successor;
        private AVLNode predecessor;

        //time complexity: O(1)
        public AVLNode() {
            this.key = -1;
            this.info = false;
            this.subTreeSize = 0;
            this.subTreeXor = false;
            this.height = -1;
        }

        //time complexity: O(1)
        public AVLNode(int key, boolean info, AVLNode parent) {
            this.key = key;
            this.info = info;
            this.height = 0;
            this.parent = parent;
            this.subTreeSize = 1;
            this.subTreeXor = info;
            this.left = getVirtualNode();
            this.right = getVirtualNode();
            this.successor = getVirtualNode();
            this.predecessor = getVirtualNode();
        }

        //returns True if this is a non-virtual AVL node
        //time complexity: O(1)
        public boolean isRealNode() {
            return this.key != -1;
        }

        //returns node's key (for virtual node return -1)
        //time complexity: O(1)
        public int getKey() {
            return this.key;
        }

        //returns node's value [info] (for virtual node return null)
        //time complexity: O(1)
        public Boolean getValue() {
            return this.isRealNode() ? this.info : null;
        }

        //sets left child
        //time complexity: O(1)
        public void setLeft(AVLNode node) {
            if (this.isRealNode()) {
                this.left = node;
            }
        }

        //returns left child (if there is no left child return null)
        //time complexity: O(1)
        public AVLNode getLeft() {
            return this.left;
        }

        //sets right child
        //time complexity: O(1)
        public void setRight(AVLNode node) {
            if (this.isRealNode()) {
                this.right = node;
            }
        }

        //returns right child (if there is no right child return null)
        //time complexity: O(1)
        public AVLNode getRight() {
            return this.right;
        }

        //sets child in direction dir
        //time complexity: O(1)
        public void setChildInDir(AVLNode node, Direction dir) {
            if (dir == Direction.Left) {
                this.setLeft(node);
                return;
            }
            this.setRight(node);
        }

        //returns child in direction dir (if there is no right child return null)
        //time complexity: O(1)
        public AVLNode getChildInDir(Direction dir) {
            if (dir == Direction.Left) {
                return this.getLeft();
            }
            return this.getRight();
        }

        //sets parent
        //time complexity: O(1)
        public void setParent(AVLNode node) {
            if (this.isRealNode()) {
                this.parent = node;
            }
        }

        //returns the parent (if there is no parent return null)
        //time complexity: O(1)
        public AVLNode getParent() {
            return this.parent;
        }

        //sets the height of the node
        //time complexity: O(1)
        public void setHeight(int height) {
            this.height = height;
        }

        //returns the height of the node (-1 for virtual nodes)
        //time complexity: O(1)
        public int getHeight() {
            return this.height;
        }

        //sets the size of the node
        //time complexity: O(1)
        public void setSize(int size) {
            this.subTreeSize = size;
        }

        //returns the size of the node (0 for virtual nodes)
        //time complexity: O(1)
        public int getSize() {
            return this.subTreeSize;
        }

        //returns the size of the node (0 for virtual nodes)
        //time complexity: O(1)
        public void setSubTreeXor(boolean xor) {
            this.subTreeXor = xor;
        }

        //returns the xor of the node (false for virtual nodes)
        //time complexity: O(1)
        public boolean getSubTreeXor() {
            return this.subTreeXor;
        }

        //sets the successor of the node in the tree
        //time complexity: O(1)
        public void setSuccessor(AVLNode successor) {
            if (this.isRealNode()) {
                this.successor = successor;
            }
        }

        //returns the successor of the node in the tree (or the virtual node if successor doesn't exist)
        //time complexity: O(1)
        public AVLNode getSuccessor() {
            return this.successor;
        }

        //sets the predecessor of the node in the tree
        //time complexity: O(1)
        public void setPredecessor(AVLNode predecessor) {
            if (this.isRealNode()) {
                this.predecessor = predecessor;
            }
        }

        //returns the predecessor of the node in the tree (or the virtual node if successor doesn't exist)
        //time complexity: O(1)
        public AVLNode getPredecessor() {
            return this.predecessor;
        }

        //returns the balance factor of the node, if it is a virtual node return 0
        //time complexity: O(1)
        public int getBalanceFactor() {
            return isRealNode() ? this.getLeft().getHeight() - this.getRight().getHeight() : 0;
        }

        //updates height, size and xor fields of the node
        //time complexity: O(1)
        public void updateNodeFields() {
            AVLNode leftChild = this.getLeft();
            AVLNode rightChild = this.getRight();
            this.setHeight(Math.max(leftChild.getHeight(), rightChild.getHeight()) + 1); // updates the height by the maximum height of the children
            this.setSize(leftChild.getSize() + rightChild.getSize() + 1); // updates the size by the size of the children
            this.setSubTreeXor(leftChild.getSubTreeXor() ^ rightChild.getSubTreeXor() ^ this.getValue()); // updates the xor by the xor of the children
        }

        //returns the number of the children of the node
        //time complexity: O(1)
        public int getChildCount() {
            int count = 0;
            if (this.getLeft().isRealNode()) {
                count += 1;
            }
            if (this.getRight().isRealNode()) {
                count += 1;
            }
            return count;
        }

        //returns true if and only if the node is the left child of his parent
        //time complexity: O(1)
        public boolean isLeftChild() {
            return this.getKey() < this.getParent().getKey();
        }

        //TODO remove in the submitted file
        @Override
        public String toString() {
            return "AVLNode{" +
                    "key=" + this.key +
                    ", info=" + this.info +
                    '}';
        }
    }



    public static class BSTree extends AVLTree {
        @Override
        public void updateSuccessor(AVLNode node, AVLNode newNode) {
        }
        @Override
        public AVLNode balanceTreeOnce(AVLNode node) {
            return this.getVirtualNode();
        }


    }


//TODO remove in the submitted file

    public void printTree() {
        String[] visual = treeRepresentation(root);
        for (String s : visual) {
            System.out.println(s);
        }
    }

    private static String[] treeRepresentation(AVLNode node) {
        // Return a list of textual representations of the levels in t
        String zis = String.valueOf(node.getKey());
        String[] leftTxt;
        String[] rightTxt;
        if (!node.getLeft().isRealNode())
            leftTxt = new String[]{"┴"};
        else
            leftTxt = treeRepresentation(node.getLeft());
        if (!node.getRight().isRealNode())
            rightTxt = new String[]{"┴"};
        else
            rightTxt = treeRepresentation(node.getRight());
        return concat(leftTxt, zis, rightTxt);
    }

    private static String[] concat(String[] left, String root, String[] right) {
        // Return a concatenation of textual representation of
        // a root node, its left node, and its right node
        int leftWidth = left[left.length - 1].length(); // levels in left
        int rightWidth = right[right.length - 1].length(); // levels in right
        int rootWidth = root.length();
        String[] result = new String[2 + Math.max(left.length, right.length)];
        result[0] = mul(" ", leftWidth + 1) + root + mul(" ", rightWidth + 1); // first row
        int ls = leftSpace(left[0]);
        int rs = rightSpace(right[0]);
        result[1] = mul(" ", ls) + mul(" ", leftWidth - ls) + "/" + mul(" ", rootWidth) + "\\" + mul(" ", rs) + mul(" ", rightWidth - rs); // second row
        String row;
        for (int i = 0; i < Math.max(left.length, right.length); i++) {
            // connect the i row in left to row i in right
            row = "";

            if (i < left.length) {
                row += left[i];
            } else {
                row += mul(" ", leftWidth);
            }

            row += mul(" ", rootWidth + 2);

            if (i < right.length) {
                row += right[i];
            } else {
                row += mul(" ", rightWidth);
            }

            result[i + 2] = row;
        }
        return result;
    }

    private static int leftSpace(String row) {
        int i = row.length() - 1;
        while (row.charAt(i) == ' ') {
            i--;
        }
        return i + 1;
        // returns the index of where the second whitespace starts
    }

    private static int rightSpace(String row) {
        int i = 0;
        while (row.charAt(i) == ' ') {
            i++;
        }
        return i;
        // returns the index of where the first whitespace ends
    }

    private static String mul(String a, int t) {
        if (t == 0) {
            return "";
        }
        return mul(a, t - 1) + a;
        // return new string of a+a...+a - t times
    }

}
