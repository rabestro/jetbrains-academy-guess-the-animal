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


    public void list() {
        println("tree.list.animals");
        getAnimals().forEach((animal, facts) -> println("tree.list.printf", animal, facts.size()));
    }

    public void search() {
        final var animal = ask("tree.search.animal");
        final var animalName = applyRules("animal", animal);
        final var facts = getAnimals().getOrDefault(animal, emptyList());
        println(facts.isEmpty() ? "tree.search.noFacts" : "tree.search.facts", animal);
        facts.forEach(fact -> println("tree.search.printf", fact));
    }

    public Map<String, List<String>> getAnimals() {
        animals.clear();
        collectAnimals(knowledgeTree.getRoot(), new LinkedList<>());
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
