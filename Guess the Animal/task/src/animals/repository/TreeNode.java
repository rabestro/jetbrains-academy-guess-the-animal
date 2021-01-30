package animals.repository;

public class TreeNode {
    private String data;
    private TreeNode right;
    private TreeNode left;

    TreeNode() {
    }

    public TreeNode(final String data) {
        this.data = data;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }

    public String getData() {
        return data;
    }

    public void setData(final String data) {
        this.data = data;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(final TreeNode right) {
        this.right = right;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(final TreeNode left) {
        this.left = left;
    }
}
