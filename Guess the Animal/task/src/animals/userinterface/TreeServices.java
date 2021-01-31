package animals.userinterface;

import animals.repository.KnowledgeTree;
import animals.repository.TreeNode;

import java.util.*;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.summarizingInt;

public final class TreeServices extends TextInterface {
    private final KnowledgeTree knowledgeTree;
    private final Map<String, List<String>> animals = new HashMap<>();

    TreeServices(KnowledgeTree knowledgeTree) {
        this.knowledgeTree = knowledgeTree;
    }

    void listAnimals() {
        println("list.animals");
        getAnimals().keySet().stream()
                .map(animal -> applyRules("animalName", animal))
                .sorted()
                .forEach(name -> println("list.print", name));
    }

    void statistics() {
        final var stats = getStatistics();
        println("tree.stats.title");
        println("tree.stats.root", knowledgeTree.getRoot().getData());
        println("tree.stats.nodes", stats.getCount() * 2 - 1);
        println("tree.stats.animals", stats.getCount());
        println("tree.stats.statements", stats.getCount() - 1);
        println("tree.stats.height", stats.getMax());
        println("tree.stats.minimum", stats.getMin());
        println("tree.stats.average", stats.getAverage());
    }


    void list() {
        println("tree.list.animals");
        getAnimals().forEach((animal, facts) -> printf("tree.list.printf", animal, facts.size()));
    }

    void search() {
        final var animal = ask("animal");
        final var facts = getAnimals().getOrDefault(animal, emptyList());
        println(facts.isEmpty() ? "tree.search.noFacts" : "tree.search.facts", name(animal));
        facts.forEach(fact -> printf("tree.search.printf", fact));
    }

    void delete() {
        if (knowledgeTree.getRoot().isLeaf()) {
            println("tree.delete.root");
            return;
        }
        println("tree.delete.animal");
        final var animal = ask("animal");
        final var isOk = knowledgeTree.deleteAnimal(null, knowledgeTree.getRoot(), animal);
        println(isOk ? "tree.delete.successful" : "tree.delete.fail", name(animal));
    }

    void print() {
        printNode(knowledgeTree.getRoot(), false, " ");
    }

    private String name(final String animal) {
        return applyRules("animalName", animal);
    }

    private void printNode(final TreeNode<String> node, final boolean isRight, String prefix) {
        if (node.isLeaf()) {
            printf("tree.print.printf", prefix, getLine(isRight), node.getData());
            return;
        }
        printf("tree.print.printf", prefix, getLine(isRight), applyRules("question", node.getData()));
        prefix += isRight ? resourceBundle.getString("tree.print.vertical") : " ";
        printNode(node.getRight(), true, prefix);
        printNode(node.getLeft(), false, prefix);
    }

    private String getLine(final boolean isBranch) {
        return resourceBundle.getString(isBranch ? "tree.print.branch" : "tree.print.corner");
    }

    private Map<String, List<String>> getAnimals() {
        animals.clear();
        collectAnimals(knowledgeTree.getRoot(), new LinkedList<>());
        return animals;
    }

    private void collectAnimals(final TreeNode<String> node, final Deque<String> facts) {
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

    private IntSummaryStatistics getStatistics() {
        return getAnimals().values().stream().collect(summarizingInt(List::size));
    }

}
