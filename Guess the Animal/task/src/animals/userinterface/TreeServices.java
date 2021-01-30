package animals.userinterface;

import animals.repository.KnowledgeTree;

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
}
