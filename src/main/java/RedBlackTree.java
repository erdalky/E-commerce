package com.student_work;

public class RedBlackTree {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node root;
    private int size;

    private static class Node {
        int productId;
        Product product;
        Node left, right;
        boolean color;

        Node(Product product, boolean color) {
            this.product = product;
            this.productId = product.getId();
            this.color = color;
        }
    }

    public RedBlackTree() {
        root = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void insert(Product product) {
        root = insert(root, product);
        root.color = BLACK;
    }

    public Product search(int id) {
        Node x = root;
        while (x != null) {
            if (id < x.productId) x = x.left;
            else if (id > x.productId) x = x.right;
            else return x.product;
        }
        return null;
    }

    public int height() {
        return height(root);
    }

    private int height(Node h) {
        if (h == null) return 0;
        return 1 + Math.max(height(h.left), height(h.right));
    }

    public int countRedLinks() {
        return countRedLinks(root);
    }

    private int countRedLinks(Node h) {
        if (h == null) return 0;
        int count = (h.color == RED) ? 1 : 0;
        return count + countRedLinks(h.left) + countRedLinks(h.right);
    }

    public void printTree() {
        printTree(root, 0);
    }

    private void printTree(Node h, int depth) {
        if (h == null) return;
        printTree(h.right, depth + 1);
        System.out.println(" ".repeat(depth * 4) + h.productId + (h.color == RED ? " (R)" : " (B)"));
        printTree(h.left, depth + 1);
    }

    private Node insert(Node h, Product product) {
        if (h == null) {
            size++;
            return new Node(product, RED);
        }

        int cmp = product.getId() - h.productId;

        if (cmp < 0) h.left = insert(h.left, product);
        else if (cmp > 0) h.right = insert(h.right, product);
        else h.product = product;

        if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right)) flipColors(h);

        return h;
    }

    private boolean isRed(Node x) {
        return x != null && x.color == RED;
    }

    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    private void flipColors(Node h) {
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    public boolean validate() {
        return isBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE)
                && isBalanced(root)
                && noRightRedLinks(root)
                && noDoubleRed(root);
    }

    private boolean isBST(Node x, int min, int max) {
        if (x == null) return true;
        if (x.productId <= min || x.productId >= max) return false;
        return isBST(x.left, min, x.productId) && isBST(x.right, x.productId, max);
    }

    private boolean noRightRedLinks(Node x) {
        if (x == null) return true;
        if (isRed(x.right)) return false;
        return noRightRedLinks(x.left) && noRightRedLinks(x.right);
    }

    private boolean noDoubleRed(Node x) {
        if (x == null) return true;
        if (isRed(x) && (isRed(x.left) || isRed(x.right))) return false;
        return noDoubleRed(x.left) && noDoubleRed(x.right);
    }

    private boolean isBalanced(Node root) {
        int black = blackHeight(root);
        return isBalanced(root, black);
    }

    private boolean isBalanced(Node x, int black) {
        if (x == null) return black == 0;
        if (!isRed(x)) black--;
        return isBalanced(x.left, black) && isBalanced(x.right, black);
    }

    private int blackHeight(Node x) {
        int count = 0;
        while (x != null) {
            if (!isRed(x)) count++;
            x = x.left;
        }
        return count;
    }

    public boolean is23() {
        return is23(root);
    }

    private boolean is23(Node x) {
        if (x == null) return true;
        if (isRed(x.right)) return false;
        if (isRed(x) && isRed(x.left)) return false;
        return is23(x.left) && is23(x.right);
    }
}
