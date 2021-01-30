package animals.repository;

import java.util.*;
import java.util.logging.Logger;

import static animals.userinterface.TextInterface.applyRules;
import static java.util.stream.Collectors.summarizingInt;

public class KnowledgeTree {
    private static final Logger log = Logger.getLogger(KnowledgeTree.class.getName());

    private final Map<String, List<String>> animals = new HashMap<>();
    private TreeNode root;
    private TreeNode current;

    public KnowledgeTree() {

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

    public Map<String, List<String>> getAnimals() {
        animals.clear();
        collectAnimals(root, new LinkedList<>());
        return animals;
    }

    private void collectAnimals(final TreeNode node, final Deque<String> facts) {
        if (node.isLeaf()) {
            animals.put(node.getData(), List.copyOf(facts));
            return;
        }
        final var statement = node.getData();
        facts.add(statement);
        collectAnimals(node.getRight(), facts);
        facts.removeLast();
        facts.add(applyRules("negative", statement));
        collectAnimals(node.getLeft(), facts);
        facts.removeLast();
    }

    public IntSummaryStatistics getStatistics() {
        return getAnimals().values().stream().collect(summarizingInt(List::size));
    }
}
