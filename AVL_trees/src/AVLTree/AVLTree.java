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
     * the root of the tree, null if and only if the tree is empty
     */
    AVLNode root;

    /**
     * the node with the minimal key in the tree
     */
    AVLNode minNode;

    /**
     * the node with the maximal key in the tree
     */
    AVLNode maxNode;

    /**
     * public boolean empty()
     * <p>
     * returns true if and only if the tree is empty
     */
    public boolean empty() {
        return root == null;
    }

    /**
     * public AVLNode getMin()
     * <p>
     * returns true if and only if the tree is empty
     */
    public AVLNode getMin() {
        return this.minNode;
    }

    /**
     * public AVLNode getMin()
     * <p>
     * returns true if and only if the tree is empty
     */
    public AVLNode getMax() {
        return this.maxNode;
    }

    /**
     * public boolean search(int k)
     * <p>
     * Returns the info of the item with key k in the tree,
     * or null if item with key k was not found in the tree.
     */
    public Boolean search(int k) {
        if (empty()) {
            return null;
        }
        AVLNode node = this.getRoot();
        while (node.isRealNode() && node.getKey() != k) {
            if (node.getKey() > k) {
                node = node.getLeft();
            } else {
                node = node.getRight();
            }
        }
        return node.isRealNode() ? node.getValue() : null;
    }

    /**
     * public int insert(int k, boolean i)
     * <p>
     * inserts an item with key k and info i to the AVL tree.
     * the tree must remain valid (keep its invariants).
     * returns the number of nodes which require rebalancing operations (i.e. promotions or rotations).
     * This always includes the newly-created node.
     * returns -1 if an item with key k already exists in the tree.
     */
    public int insert(int k, boolean i) {
        return 42;    // to be replaced by student code
    }

    /**
     * public int delete(int k)
     * <p>
     * deletes an item with key k from the binary tree, if it is there;
     * the tree must remain valid (keep its invariants).
     * returns the number of nodes which required rebalancing operations (i.e. demotions or rotations).
     * returns -1 if an item with key k was not found in the tree.
     */
    public int delete(int k) {
        return 42;    // to be replaced by student code
    }

    /**
     * public Boolean min()
     * <p>
     * Returns the info of the item with the smallest key in the tree,
     * or null if the tree is empty
     */
    public Boolean min() {
        return this.empty() ? null : this.getMin().getValue();
    }

    /**
     * public Boolean max()
     * <p>
     * Returns the info of the item with the largest key in the tree,
     * or null if the tree is empty
     */
    public Boolean max() {
        return this.empty() ? null : this.getMax().getValue();
    }

    /**
     * public AVLNode[] nodesToArray()
     * <p>
     * Returns an array which contains all nodes in the tree,
     * sorted by their respective keys,
     * or an empty array if the tree is empty.
     */
    public AVLNode[] nodesToArray() {
        AVLNode[] arr = new AVLNode[this.size()];
        if (!this.empty()) {
            inOrder(this.getRoot(), 0, arr);
        }
        return arr;
    }

    /**
     * public void inOrder(AVLNode node, int index, AVLNode[] arr)
     * <p>
     * Auxiliary recursive function of nodesToArray(),
     * performs in-order tree walk while adding the node to the array.
     */
    public void inOrder(AVLNode node, int index, AVLNode[] arr) {
        if (node.isRealNode()) {
            inOrder(node.getLeft(), index, arr);
            arr[index + node.getLeft().getSize()] = node;
            inOrder(node.getRight(), index + node.getLeft().getSize() + 1, arr);
        }
    }

    /**
     * public int[] keysToArray()
     * <p>
     * Returns a sorted array which contains all keys in the tree,
     * or an empty array if the tree is empty.
     */
    public int[] keysToArray() {
        int[] arr = new int[this.size()];
        if (!this.empty()) {
            AVLNode[] nodesArr = nodesToArray();
            for (int i = 0; i < arr.length; i++) {
                arr[i] = nodesArr[i].getKey();
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
     */
    public boolean[] infoToArray() {
        boolean[] arr = new boolean[42];
        if (!this.empty()) {
            AVLNode[] nodesArr = nodesToArray();
            for (int i = 0; i < arr.length; i++) {
                arr[i] = nodesArr[i].getValue();
            }
        }
        return arr;
    }

    /**
     * public int size()
     * <p>
     * Returns the number of nodes in the tree.
     */
    public int size() {
        return this.empty() ? 0 : this.getRoot().getSize();
    }

    /**
     * public int getRoot()
     * <p>
     * Returns the root AVL node, or null if the tree is empty
     */
    public AVLNode getRoot() {
        return this.root;
    }

    /**
     * public boolean prefixXor(int k)
     * <p>
     * Given an argument k which is a key in the tree, calculate the xor of the values of nodes whose keys are
     * smaller or equal to k.
     * <p>
     * precondition: this.search(k) != null
     */
    public boolean prefixXor(int k) {
        AVLNode node = this.getRoot();
        boolean xorValue = false;
        while (node.getKey() != k) {
            if (node.getKey() > k) {
                node = node.getLeft();
            } else {
                xorValue ^= node.getValue();
                xorValue ^= node.getLeft().getSubTreeXor();
                node = node.getRight();
            }
        }
        return xorValue ^ node.getValue() ^ node.getLeft().getSubTreeXor();
    }

    /**
     * public AVLNode successor
     * <p>
     * given a node 'node' in the tree, return the successor of 'node' in the tree (or null if successor doesn't exist)
     *
     * @param node - the node whose successor should be returned
     * @return the successor of 'node' if exists, null otherwise
     */
    public AVLNode successor(AVLNode node) {
        return node.getSuccessor();
    }

    /**
     * public boolean succPrefixXor(int k)
     * <p>
     * This function is identical to prefixXor(int k) in terms of input/output. However, the implementation of
     * succPrefixXor should be the following: starting from the minimum-key node, iteratively call successor until
     * you reach the node of key k. Return the xor of all visited nodes.
     * <p>
     * precondition: this.search(k) != null
     */
    public boolean succPrefixXor(int k) {
        AVLNode node = this.getMin();
        boolean xorValue = false;
        while (node.getKey() <= k) {
            xorValue ^= node.getValue();
            node = successor(node);
        }
        return xorValue;
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
    public static class AVLNode { //TODO check not static
        int key;
        boolean info;
        int height;
        AVLNode parent;
        AVLNode left;
        AVLNode right;
        int subTreeSize;
        boolean subTreeXor;
        AVLNode successor;
        AVLNode predecessor;

        static AVLNode virtualNode = new AVLNode();

        public AVLNode() {
            this.key = -1;
            this.info = false;
            this.subTreeSize = 0;
            this.subTreeXor = false;
        }

        public AVLNode(int key, boolean info, AVLNode parent) {
            this.key = key;
            this.info = info;
            this.height = 0;
            this.parent = parent;
            this.subTreeSize = 1;
            this.subTreeXor = info;
            this.left = virtualNode;
            this.right = virtualNode;
        }

        //returns node's key (for virtual node return -1)
        public int getKey() {
            return key;
        }

        //returns node's value [info] (for virtual node return null)
        public boolean getValue() {
            return info;
        }

        //sets left child
        public void setLeft(AVLNode node) {
            this.left = node;
        }

        //returns left child (if there is no left child return null)
        public AVLNode getLeft() {
            return this.left;
        }

        //sets right child
        public void setRight(AVLNode node) {
            this.right = node;
        }

        //returns right child (if there is no right child return null)
        public AVLNode getRight() {
            return this.right;
        }

        //sets parent
        public void setParent(AVLNode node) {
            this.parent = node;
        }

        //returns the parent (if there is no parent return null)
        public AVLNode getParent() {
            return this.parent;
        }

        //returns True if this is a non-virtual AVL node
        public boolean isRealNode() {
            return this.key != -1;
        }

        //sets the height of the node
        public void setHeight(int height) {
            this.height = height;
        }

        //returns the height of the node (-1 for virtual nodes)
        public int getHeight() {
            return this.height;
        }

        //returns the size of the node (0 for virtual nodes)
        public int getSize() {
            return this.subTreeSize;
        }

        //sets the size of the node
        public void setSize(int size) {
            this.subTreeSize = size;
        }

        //returns the xor of the node (false for virtual nodes)
        public boolean getXor() {
            return this.subTreeXor;
        }

        //returns the size of the node (0 for virtual nodes)
        public void setXor(boolean xor) {
            this.subTreeXor = xor;
        }

        //returns the balance factor of the node
        public int getBalanceFactor() {
            return this.getLeft().getHeight() - this.getRight().getHeight();
        }

        //returns the successor of the node in the tree (or null if successor doesn't exist)
        public AVLNode getSuccessor() {
            return this.successor;
        }

        //sets the successor of the node in the tree
        public void setSuccessor(AVLNode successor) {
            this.successor = successor;
        }

        //returns the predecessor of the node in the tree (or null if successor doesn't exist)
        public AVLNode getPredecessor() {
            return this.predecessor;
        }

        //sets the predecessor of the node in the tree
        public void setPredecessor(AVLNode predecessor) {
            this.predecessor = predecessor;
        }

    }
}


