package animals.userinterface;

import animals.repository.KnowledgeTree;
import animals.repository.StorageService;
import animals.repository.TreeNode;

public final class Application extends TextInterface implements Runnable {
    private final KnowledgeTree knowledgeTree;

    private final StorageService storageService;

    public Application(final StorageService storageService) {
        this.storageService = storageService;
        knowledgeTree = new KnowledgeTree();
    }

    @Override
    public void run() {
        log.entering(Application.class.getName(), "run");

        printConditional("greeting");
        storageService.load(knowledgeTree);

        if (knowledgeTree.isEmpty()) {
            println();
            println("animal.wantLearn");
            println("animal.askFavorite");
            knowledgeTree.setRoot(new TreeNode(ask("animal")));
        }
        println("welcome");

        final var treeService = new TreeServices(knowledgeTree);

        new LocalMenu()
                .add("menu.entry.play", new GuessingGame(knowledgeTree))
                .add("menu.entry.list", treeService::list)
                .add("menu.entry.search", treeService::search)
                .add("menu.entry.statistics", treeService::statistics)
                .add("menu.entry.print", treeService::print)
                .addExit()
                .run();

        storageService.save(knowledgeTree);
        println();
        println("farewell");
        log.exiting(Application.class.getName(), "run");
    }


}