package animals.repository;

import java.util.logging.Logger;

public class KnowledgeTree {
    private static final Logger log = Logger.getLogger(KnowledgeTree.class.getName());
    private TreeNode root;
    private TreeNode current;

    public void reset() {
        current = root;
    }

    public boolean isAnimal() {
        return current.isLeaf();
    }

    public boolean isStatement() {
        return !isAnimal();
    }

    public TreeNode getCurrent() {
        return current;
    }

    public void next(final boolean direction) {
        current = direction ? current.getRight() : current.getLeft();
    }

    public void addAnimal(final String animal, final String statement, final boolean isRight) {
        log.entering(KnowledgeTree.class.getName(), "addAnimal");

        final var newAnimal = new TreeNode(animal);
        final var oldAnimal = new TreeNode(current.getData());
        current.setData(statement);
        current.setRight(isRight ? newAnimal : oldAnimal);
        current.setLeft(isRight ? oldAnimal : newAnimal);

        log.exiting(KnowledgeTree.class.getName(), "addAnimal");
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(final TreeNode root) {
        this.root = root;
        this.current = root;
    }

    public boolean isEmpty() {
        return root == null;
    }

}
