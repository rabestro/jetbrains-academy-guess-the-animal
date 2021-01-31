package animals.userinterface;

import animals.repository.KnowledgeTree;

import static java.util.Collections.emptyList;

public final class TreeServices extends TextInterface {
    private final KnowledgeTree knowledgeTree;

    TreeServices(KnowledgeTree knowledgeTree) {
        this.knowledgeTree = knowledgeTree;
    }

    void listAnimals() {
        println("list.animals");
        knowledgeTree.getAnimals().keySet().stream()
                .map(animal -> applyRules("animalName", animal))
                .sorted()
                .forEach(name -> println("list.print", name));
    }

    void statistics() {
        final var stats = knowledgeTree.getStatistics();
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
        knowledgeTree.getAnimals().forEach((animal, facts) -> println("tree.list.printf", animal, facts.size()));
    }

    public void search() {
        final var animal = ask("tree.search.animal");
        final var animalName = applyRules("animal", animal);
        final var facts = knowledgeTree.getAnimals().getOrDefault(animal, emptyList());
        println(facts.isEmpty() ? "tree.search.noFacts" : "tree.search.facts", animal);
        facts.forEach(fact -> println("tree.search.printf", fact));
    }

}
