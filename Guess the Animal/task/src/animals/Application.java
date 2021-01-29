package animals;

public class Application extends TextInterface implements Runnable {

    @Override
    public void run() {
        log.entering(Application.class.getName(), "run");

        printConditional("greeting");
        println("animal.wantLearn");

        var firstAnimal = askAnimal("animal.first");
        var secondAnimal = askAnimal("animal.second");

        var positive = askStatement(firstAnimal, secondAnimal);
        var answer = askYesNo("game.isCorrect", firstAnimal);
        var negative = applyRules("negative", positive);


        println("game.learned");
        println("game.distinguish");

        print("animal.nice");
        println("animal.learnedMuch");
        println("farewell");

        log.exiting(Application.class.getName(), "run");
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