package animals.domain;

import java.util.logging.Logger;

public class KnowledgeTree {
    private static final Logger log = Logger.getLogger(KnowledgeTree.class.getName());

    private final TreeNode root;
    private TreeNode current;

    public KnowledgeTree(TreeNode root) {
        this.root = root;
        this.current = root;
    }

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

    public void next(boolean direction) {
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
}
