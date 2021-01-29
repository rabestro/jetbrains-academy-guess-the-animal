import animals.Main;
import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.io.IOException;
import java.util.HashSet;

public class GuessAnimalTest extends StageTest<String> {
    private static final int TEST_RANDOM_RESPONSES = 10;

    public GuessAnimalTest() {
        super(Main.class);
    }

    @DynamicTestingMethod
    CheckResult testAnimals() throws IOException {
        return new Scenario("animals").check();
    }

    @DynamicTestingMethod
    CheckResult negativeAnswers() throws IOException {
        return new Scenario("negative-answers").check();
    }

    @DynamicTestingMethod
    CheckResult positiveAnswers() throws IOException {
        return new Scenario("positive-answers").check();
    }

    @DynamicTestingMethod
    CheckResult unclearAnswers() throws IOException {
        return new Scenario("unclear-answers").check();
    }

    @DynamicTestingMethod
    CheckResult testRandomGoodbye() throws IOException {
        final var farewell = new HashSet<String>();

        for (int tries = TEST_RANDOM_RESPONSES; tries > 0; tries--) {
            final var main = new TestedProgram(Main.class);
            main.start();
            farewell.add(main.execute("cat\nyes\n"));
        }

        if (farewell.size() > 1) {
            return CheckResult.correct();
        } else {
            return CheckResult.wrong("You program should use different ways to farewell the user.");
        }
    }

    @DynamicTestingMethod
    CheckResult testRandomYesNoClarification() throws IOException {
        final var clarification = new HashSet<String>();

        for (int tries = TEST_RANDOM_RESPONSES; tries > 0; tries--) {
            final var main = new TestedProgram(Main.class);
            main.start();
            clarification.add(main.execute("cat\n#\n"));
        }

        if (clarification.size() > 1) {
            return CheckResult.correct();
        } else {
            return CheckResult.wrong("You program should use different ways to ask clarification question.");
        }
    }
}