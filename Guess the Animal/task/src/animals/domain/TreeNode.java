package animals.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TreeNode {
    private String data;
    private TreeNode right;
    private TreeNode left;

    TreeNode() {
    }

    public TreeNode(final String data) {
        this.data = data;
    }

    @JsonIgnore
    public boolean isLeaf() {
        return left == null && right == null;
    }

    public String getData() {
        return data;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }
}