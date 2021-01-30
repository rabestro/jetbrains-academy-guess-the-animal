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
        println();
        storageService.load(knowledgeTree);

        if (knowledgeTree.isEmpty()) {
            println("animal.wantLearn");
            println("animal.askFavorite");
            knowledgeTree.setRoot(new TreeNode(ask("animal")));
        }
        println();

        new Game(knowledgeTree).run();

        storageService.save(knowledgeTree);
        println();
        println("farewell");
        log.exiting(Application.class.getName(), "run");
    }


}