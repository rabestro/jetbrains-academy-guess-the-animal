package animals.userinterface;

import animals.domain.KnowledgeTree;

public final class Game extends TextInterface implements Runnable {
    private final KnowledgeTree db;

    public Game(final KnowledgeTree db) {
        this.db = db;
    }

    @Override
    public void run() {
        do {
            println("game.letsPlay");
            println("game.think");
            println("game.enter");
            readToLowerCase();

            while (db.isStatement()) {
                db.next(askYesNo("question"));
            }

            if (askYesNo("guessAnimal")) {
                println("game.win");
            } else {
                giveUp();
            }

            db.reset();
            print("game.thanks");

        } while (askYesNo("game.again"));
    }

    private void giveUp() {
        println("game.giveUp");
        final var animal = ask("animal", "guessed");
        final var guessedAnimal = db.getCurrent().getData();
        final var statement = ask("statement", animal, guessedAnimal);
        final var isCorrect = askYesNo("game.isCorrect", animal);

        db.addAnimal(animal, statement, isCorrect);

        println("game.learned");
        final var negative = applyRules("negative", statement);
        final var fact1 = applyRules("animalFact", isCorrect ? statement : negative);
        final var fact2 = applyRules("animalFact", isCorrect ? negative : statement);

        printFact(fact1, animal);
        printFact(fact2, guessedAnimal);
        println("game.distinguish");
        println(" - " + capitalize(applyRules("question", statement)));
        println();
        print("animal.nice");
        println("animal.learnedMuch");
    }

    @Override
    public boolean askYesNo(final String key, final Object... args) {
        return super.askYesNo(capitalize(applyRules(key, db.getCurrent().getData())));
    }

    private void printFact(final String fact, final String animal) {
        println(" - " + capitalize(String.format(fact, applyRules("definite", animal))) + ".");
    }
}
