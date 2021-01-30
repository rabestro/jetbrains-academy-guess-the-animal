package animals.userinterface;

public final class Application extends TextInterface implements Runnable {

    @Override
    public void run() {
        log.entering(Application.class.getName(), "run");

        printConditional("greeting");
        println();
        println("animal.wantLearn");

        final var animal1 = ask("animal" , "first");
        final var animal2 = ask("animal" , "second");
        final var positive = ask("statement", animal1, animal2);
        final var isCorrect = askYesNo("game.isCorrect", animal2);
        final var negative = applyRules("negative", positive);

        final var fact1 = applyRules("animalFact", isCorrect ? negative : positive);
        final var fact2 = applyRules("animalFact", isCorrect ? positive : negative);

        println("game.learned");
        printFact(fact1, animal1);
        printFact(fact2, animal2);
        println("game.distinguish");
        println(" - " + capitalize(applyRules("question", positive)));
        println();
        print("animal.nice");
        println("animal.learnedMuch");
        println("farewell");

        log.exiting(Application.class.getName(), "run");
    }

    private void printFact(final String fact, final String animal) {
        println(" - " + capitalize(String.format(fact, applyRules("definite", animal))) + ".");
    }

}