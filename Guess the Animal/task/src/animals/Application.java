package animals;

public class Application extends TextInterface implements Runnable {

    @Override
    public void run() {
        log.entering(Application.class.getName(), "run");

        printConditional("greeting");
        println("animal.wantLearn");

        var animal = askAnimal("animal.askFavorite");
        var answer = askYesNo("is.it", animal);

        println("answered", answer ? "Yes" : "No");
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

}