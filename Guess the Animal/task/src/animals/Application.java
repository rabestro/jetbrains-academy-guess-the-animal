package animals;

public final class Application extends TextInterface implements Runnable {

    @Override
    public void run() {
        log.entering(Application.class.getName(), "run");

        printConditional("greeting");
        println();
        println("animal.wantLearn");

        final var animal1 = askAnimal("animal.first");
        final var animal2 = askAnimal("animal.second");
        final var positive = askStatement(animal1, animal2);
        final var isCorrect = askYesNo("game.isCorrect", animal2);
        final var negative = applyRules("negative", positive);

        final var fact1 = applyRules("animalFact", isCorrect ? negative : positive);
        final var fact2 = applyRules("animalFact", isCorrect ? positive : negative);

        println("game.learned");
        printFact(fact1, animal1);
        printFact(fact2, animal2);
        println("game.distinguish");
        println(capitalize(applyRules("question", positive)));
        println();
        print("animal.nice");
        println("animal.learnedMuch");
        println("farewell");

        log.exiting(Application.class.getName(), "run");
    }

    private void printFact(final String fact, final String animal) {
        println(" - " + capitalize(String.format(fact, applyRules("definite", animal))));
    }

    public String askAnimal(final String prompt) {
        while (true) {
            println(prompt);
            final var answer = readToLowerCase();
            if (PATTERNS.get("isCorrectAnimal").matcher(answer).matches()) {
                return applyRules("animal", answer);
            }
            println("animal.error");
        }
    }

    public String askStatement(final String first, final String second) {
        while (true) {
            println("game.specifyFact", first, second);
            println("game.statement");

            final var answer = readToLowerCase();
            if (PATTERNS.get("isCorrectStatement").matcher(answer).matches()) {
                return applyRules("statement", answer);
            }
            println("game.example");
        }
    }

}