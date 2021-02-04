package animals.repository;

import java.util.logging.Logger;

public class KnowledgeTree {
    private static final Logger log = Logger.getLogger(KnowledgeTree.class.getName());
    private TreeNode<String> root;
    private TreeNode<String> current;

    public void reset() {
        current = root;
    }

    public boolean isAnimal() {
        return current.isLeaf();
    }

    public boolean isStatement() {
        return !isAnimal();
    }

    public TreeNode<String> getCurrent() {
        return current;
    }

    public void next(final boolean direction) {
        current = direction ? current.getRight() : current.getLeft();
    }

    public TreeNode<String> getRoot() {
        return root;
    }

    public void setRoot(final TreeNode<String> root) {
        this.root = root;
        this.current = root;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void addAnimal(final String animal, final String statement, final boolean isRight) {
        log.entering(KnowledgeTree.class.getName(), "addAnimal", new Object[]{animal, statement, isRight});

        final var newAnimal = new TreeNode<>(animal);
        final var oldAnimal = new TreeNode<>(current.getData());
        current.setData(statement);
        current.setRight(isRight ? newAnimal : oldAnimal);
        current.setLeft(isRight ? oldAnimal : newAnimal);

        log.exiting(KnowledgeTree.class.getName(), "addAnimal", animal);
    }

    public boolean deleteAnimal(final String animal) {
        log.entering(KnowledgeTree.class.getName(), "deleteAnimal", animal);

        final var isSuccessful = deleteAnimal(animal, root, null);

        log.exiting(KnowledgeTree.class.getName(), "deleteAnimal", isSuccessful);
        return isSuccessful;
    }

    private boolean deleteAnimal(final String animal, final TreeNode<String> child, final TreeNode<String> parent) {
        if (child.isLeaf() && animal.equals(child.getData())) {
            final var source = parent.getRight() == child ? parent.getLeft() : parent.getRight();
            parent.setData(source.getData());
            parent.setRight(source.getRight());
            parent.setLeft(source.getLeft());
            return true;
        }
        return !child.isLeaf() &&
                (deleteAnimal(animal, child.getRight(), child) || deleteAnimal(animal, child.getLeft(), child));
    }
}
