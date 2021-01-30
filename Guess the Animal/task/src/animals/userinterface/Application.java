package animals.userinterface;

import animals.repository.KnowledgeTree;
import animals.repository.TreeNode;

public final class Application extends TextInterface implements Runnable {

    @Override
    public void run() {
        log.entering(Application.class.getName(), "run");

        printConditional("greeting");
        println();
        println("animal.wantLearn");
        println("animal.askFavorite");
        println();

        new Game(new KnowledgeTree(new TreeNode(ask("animal")))).run();

        println("farewell");

        log.exiting(Application.class.getName(), "run");
    }

}