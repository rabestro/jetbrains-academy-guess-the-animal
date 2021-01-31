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

    public void addAnimal(final String animal, final String statement, final boolean isRight) {
        log.entering(KnowledgeTree.class.getName(), "addAnimal");

        final var newAnimal = new TreeNode<>(animal);
        final var oldAnimal = new TreeNode<>(current.getData());
        current.setData(statement);
        current.setRight(isRight ? newAnimal : oldAnimal);
        current.setLeft(isRight ? oldAnimal : newAnimal);

        log.exiting(KnowledgeTree.class.getName(), "addAnimal");
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

    public boolean deleteAnimal(final TreeNode<String> parent, final TreeNode<String> child, final String animal) {
        if (child.isLeaf() && animal.equals(child.getData())) {
            final var source = parent.getRight() == child ? parent.getLeft() : parent.getRight();
            parent.setData(source.getData());
            parent.setRight(source.getRight());
            parent.setLeft(source.getLeft());
            return true;
        }
        return !child.isLeaf() &&
                (deleteAnimal(child, child.getRight(), animal) || deleteAnimal(child, child.getLeft(), animal));
    }
}
