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
     */
    public AVLTree() {
    }

    /**
     * public AVLNode getVirtualNode()
     * <p>
     * Returns a virtual node.
     */
    public AVLNode getVirtualNode() {
        return this.virtualNode;
    }

    /**
     * public boolean empty()
     * <p>
     * Returns true if and only if the tree is empty
     */
    public boolean empty() {
        return this.getRoot() == null;
    }

    /**
     * public AVLNode getMin()
     * <p>
     * Returns the node with the minimal key in the tree if,
     * or null if the tree is empty.
     */
    public AVLNode getMin() {
        return this.minNode;
    }

    /**
     * public AVLNode getMin()
     * <p>
     * Sets the node with the minimal key to min.
     */
    public void setMin(AVLNode min) {
        this.minNode = min;
    }

    /**
     * public AVLNode getMin()
     * <p>
     * Returns the node with the maximal key in the tree if,
     * or null if the tree is empty.
     */
    public AVLNode getMax() {
        return this.maxNode;
    }

    /**
     * public AVLNode getMin()
     * <p>
     * Sets the node with the maximal key to min.
     */
    public void setMax(AVLNode max) {
        this.maxNode = max;
    }

    /**
     * public AVLNode searchNode(int k)
     * <p>
     * Returns the item with key k in the tree,
     * or the theoretical parent if item with key k was not found in the tree.
     * <p>
     * precondition: this.empty() != true
     */
    public AVLNode searchNode(int k) {
        AVLNode node = this.getRoot();
        while (node.getKey() != k) {
            if (node.getKey() > k) {
                if (node.getLeft().isRealNode()) {
                    node = node.getLeft();
                } else {
                    break;
                }
            } else {
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
     * public boolean search(int k)
     * <p>
     * Returns the info of the item with key k in the tree,
     * or null if item with key k was not found in the tree.
     */
    public Boolean search(int k) {
        if (this.empty()) {
            return null;
        }
        AVLNode node = searchNode(k);
        return node.getKey() == k ? node.getValue() : null;
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
        if (this.empty()) {
            this.setRoot(new AVLNode(k, i, this.getVirtualNode()));
            this.setMin(this.getRoot());
            this.setMax(this.getRoot());
            this.getRoot().setParent(this.getVirtualNode());
            return 1;
        }
        AVLNode node = searchNode(k);
        if (node.getKey() == k) {
            return -1;
        }
        AVLNode newNode = new AVLNode(k, i, node);
        if (node.getKey() > k) {
            updateRelationsForNewLeftChild(node, newNode);
        } else {
            updateRelationsForNewRightChild(node, newNode);
        }

        AVLNode lastUpdatedNode = this.balanceTreeOnce(node);
        if (lastUpdatedNode.isRealNode()) { // in other words, if a rotation has been performed,
            this.balanceTreeOnce(lastUpdatedNode); // continue to update nodes in the path from the inserted node to the root
            return 2;
        }
        return 1;
    }

    /**
     * private void updateRelationsForNewLeftChild(AVLNode parent, AVLNode newNode)
     * <p>
     * updates the relations of the successors and predecessors of parent and newNode,
     * and update tree's min node if needed.
     */
    private void updateRelationsForNewLeftChild(AVLNode parent, AVLNode newNode) {
        parent.setLeft(newNode);
        if (this.getMin() == parent) {
            this.setMin(newNode);
        } else {
            updateSuccessor(parent.getPredecessor(), newNode);
        }
        updateSuccessor(newNode, parent);
    }

    /**
     * private void updateRelationsForNewRightChild(AVLNode parent, AVLNode newNode)
     * <p>
     * updates the relations of the successors and predecessors of parent and newNode,
     * and update tree's max node if needed.
     */
    private void updateRelationsForNewRightChild(AVLNode parent, AVLNode newNode) {
        parent.setRight(newNode);
        if (this.getMax() == parent) {
            this.setMax(newNode);
        } else {
            updateSuccessor(newNode, parent.getSuccessor());
        }
        updateSuccessor(parent, newNode);
    }

    /**
     * private void updateSuccessor(AVLNode node, AVLNode newNode)
     * <p>
     * updates the successor of node to be newNode and sets node as newNodes predecessor
     */
    private void updateSuccessor(AVLNode node, AVLNode newNode) {
        newNode.setPredecessor(node);
        node.setSuccessor(newNode);
    }

    /**
     * private AVLNode balanceTreeOnce(AVLNode node)
     * <p>
     * updates the fields of all the nodes from the bottom of the tree until the first rotation,
     * or until it reaches the root if no rotation has been performed.
     * Returns the root if and only if the tree is balanced
     * and all the nodes are up to date.
     */
    private AVLNode balanceTreeOnce(AVLNode node) {
        while (node.isRealNode()) { // iterating up through the tree to the first unbalanced element, if the tree is balanced we exit the loop and return the root
            node.updateNodeFields();
            if (this.isUnbalanced(node)) {
                this.balanceNode(node);
                return node;
            }
            node = node.getParent();
        }
        return this.getVirtualNode();
    }

    /**
     * private void balanceNode(AVLNode node)
     * <p>
     * Performs balance operations (rotations) on the given node, if needed.
     */
    private void balanceNode(AVLNode node) {
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
     */
    private boolean isUnbalanced(AVLNode node) {
        return node.getBalanceFactor() > 1 || node.getBalanceFactor() < -1;
    }

    /**
     * private void rotateRight(AVLNode node)
     * <p>
     * Rotates the tree right from the given node making it the right child of its pre left child
     */
    private void rotateRight(AVLNode node) {
        AVLNode leftChild = node.getLeft();
        node.setLeft(leftChild.getRight());
        leftChild.setParent(node.getParent());
        leftChild.setRight(node);
        node.setParent(leftChild);
        leftChild.getParent().setLeft(leftChild);


        node.updateNodeFields();
        leftChild.updateNodeFields();
        if (this.getRoot() == node) {
            this.setRoot(leftChild);
        }
    }

    /**
     * private void rotateLeft(AVLNode node)
     * <p>
     * Rotates the subtree left from the given node making it the left child of its pre right child
     */
    private void rotateLeft(AVLNode node) {
        AVLNode rightChild = node.getRight();
        node.setRight(rightChild.getLeft());
        rightChild.setParent(node.getParent());
        rightChild.setLeft(node);
        node.setParent(rightChild);
        rightChild.getParent().setRight(rightChild);


        node.updateNodeFields();
        rightChild.updateNodeFields();
        if (this.getRoot() == node) {
            this.setRoot(rightChild);
        }
    }

    /**
     * private void rotateLeftThenRight(AVLNode node)
     * <p>
     * Rotates the tree left from the given node's left child and the rotates right from the node
     */
    private void rotateLeftThenRight(AVLNode node) {
        rotateLeft(node.getLeft());
        rotateRight(node);
    }

    /**
     * private void rotateRightThenLeft(AVLNode node)
     * <p>
     * Rotates the tree right from the given node's right child and the rotates left from the node
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
     */
    public int delete(int k) {
        if (this.empty()) {
            return -1;
        }
        AVLNode node = searchNode(k);
        if (node.getKey() != k) {
            return -1;
        }

        if (this.getRoot() == node && node.getChildCount() == 0) { // the root is the only node in the tree
            this.setRoot(null);
            this.setMin(null);
            this.setMax(null);
            return 1;
        } else if (this.getMin() == node) { // the node to delete is the min element
            this.setMin(node.getSuccessor());
            node.getSuccessor().setPredecessor(node.getPredecessor());
            if (this.getRoot() != node) { // root is the min element
                node.getParent().setLeft(node.getRight());
            } else { // min element is not the root
                this.setRoot(node.getRight());
                this.getRoot().setParent(this.getVirtualNode());
            }

        } else if (this.getMax() == node) { // the node to delete is the max element
            this.setMax(node.getPredecessor());
            node.getPredecessor().setSuccessor(node.getSuccessor());
            if (this.getRoot() != node) { // root is the max element
                node.getParent().setRight(node.getLeft());
            } else { // max element is not the root
                this.setRoot(node.getLeft());
                this.getRoot().setParent(this.getVirtualNode());
            }
        } else if (node.getChildCount() == 1) {
            updateSuccessor(node.getPredecessor(), node.getSuccessor());
            if (node.getLeft().isRealNode()) { // the left child is the only child of the node
                if (node.isLeftChild()) { // the node is the left child of his parent
                    node.getParent().setLeft(node.getLeft());
                } else { // the node is the right child of his parent
                    node.getParent().setRight(node.getLeft());
                }
                node.getLeft().setParent(node.getParent());
            } else { // the right child is the only child of the node
                if (node.isLeftChild()) { // the node is the left child of his parent
                    node.getParent().setLeft(node.getRight());
                } else { // the node is the right child of his parent
                    node.getParent().setRight(node.getRight());
                }
                node.getRight().setParent(node.getParent());
            }

        } else if (node.getChildCount() == 2) { // the node has two children, so replaces it with his successor
            updateSuccessor(node.getPredecessor(), node.getSuccessor());
            AVLNode succ = node.getSuccessor();
            AVLNode parentOfRemovedSuccessor = succ.getParent() == node ? succ : succ.getParent();
            if (succ.isLeftChild()) {
                succ.getParent().setLeft(succ.getRight());
            } else {
                succ.getParent().setRight(succ.getRight());
            }
            succ.setParent(node.getParent());
            if (node.isLeftChild()) {
                node.getParent().setLeft(succ);
            } else {
                node.getParent().setRight(succ);
            }

            succ.setRight(node.getRight());
            node.getRight().setParent(succ);

            succ.setLeft(node.getLeft());
            node.getLeft().setParent(succ);

            if (this.getRoot() == node){ // if the node is the root
                this.setRoot(succ);
            }

            return balanceTree(parentOfRemovedSuccessor);  //TODO check if we need to add 1 for the swap with the successor of node
        } else { // A leaf which is nor the min or the max element
            updateSuccessor(node.getPredecessor(), node.getSuccessor());
            if (node.isLeftChild()) {
                node.getParent().setLeft(this.getVirtualNode());
            } else {
                node.getParent().setRight(this.getVirtualNode());
            }
        }
        AVLNode replacingNode = (node.isLeftChild() ? node.getParent().getLeft() : node.getParent().getRight());
        return balanceTree(replacingNode);
    }

    private int balanceTree(AVLNode node) {
        int rebalancingOperationsCounter = 0;
        AVLNode lastUpdatedNode = node;
        while (lastUpdatedNode.isRealNode()) {
            lastUpdatedNode = this.balanceTreeOnce(lastUpdatedNode);
            rebalancingOperationsCounter += 1;
        }
        return rebalancingOperationsCounter;
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
        boolean[] arr = new boolean[this.size()];
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
     * public int getRoot()
     * <p>
     * Sets the root AVL node.
     */
    public void setRoot(AVLNode root) {
        this.root = root;
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
    public class AVLNode {
        private int key;
        private boolean info;
        private int height;
        private AVLNode parent;
        private AVLNode left;
        private AVLNode right;
        private int subTreeSize;
        private boolean subTreeXor;
        private AVLNode successor;
        private AVLNode predecessor;

        public AVLNode() {
            this.key = -1;
            this.info = false;
            this.subTreeSize = 0;
            this.subTreeXor = false;
            this.height = -1;
        }

        public AVLNode(int key, boolean info, AVLNode parent) {
            this.key = key;
            this.info = info;
            this.height = 0;
            this.parent = parent;
            this.subTreeSize = 1;
            this.subTreeXor = info;
            this.left = getVirtualNode();
            this.right = getVirtualNode();
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
        public boolean getSubTreeXor() {
            return this.subTreeXor;
        }

        //returns the size of the node (0 for virtual nodes)
        public void setSubTreeXor(boolean xor) {
            this.subTreeXor = xor;
        }

        //returns the balance factor of the node, if it is a virtual node return 0
        public int getBalanceFactor() {
            return isRealNode() ? this.getLeft().getHeight() - this.getRight().getHeight() : 0;
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

        //updates height, size and xor fields of the node
        public void updateNodeFields() {
            AVLNode leftChild = this.getLeft();
            AVLNode rightChild = this.getRight();
            this.setHeight((leftChild.getHeight() > rightChild.getHeight() ? leftChild.getHeight() : rightChild.getHeight()) + 1);
            this.setSize(leftChild.getSize() + rightChild.getSize() + 1);
            this.setSubTreeXor(leftChild.getSubTreeXor() ^ rightChild.getSubTreeXor() ^ this.getValue());
        }

        //returns the number of the children of the node
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
        public boolean isLeftChild() {
            return this.getKey() < this.getParent().getKey();
        }
    }
}


