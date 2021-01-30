package animals.userinterface;

import animals.domain.KnowledgeTree;
import animals.domain.TreeNode;

public final class Application extends TextInterface implements Runnable {

    @Override
    public void run() {
        log.entering(Application.class.getName(), "run");

        printConditional("greeting");
        println();
        println("animal.wantLearn");
        println("animal.askFavorite");

        final var favoriteAnimal = ask("animal", "favorite");
        new Game(new KnowledgeTree(new TreeNode(favoriteAnimal))).run();

        println("farewell");

        log.exiting(Application.class.getName(), "run");
    }

}