import animals.Main;
import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;

import java.io.IOException;

public class GuessAnimalTest extends StageTest<String> {
    public GuessAnimalTest() {
        super(Main.class);
    }

    @DynamicTestingMethod
    CheckResult theFirstQuestion() throws IOException {
        return new Scenario("the-first-question").check();
    }

    @DynamicTestingMethod
    CheckResult positiveAnswer() throws IOException {
        return new Scenario("positive-answers").check();
    }

    @DynamicTestingMethod
    CheckResult negativeAnswer() throws IOException {
        return new Scenario("negative-answers").check();
    }

    @DynamicTestingMethod
    CheckResult incorrectAnswer() throws IOException {
        return new Scenario("unclear-answers").check();
    }

    @DynamicTestingMethod
    CheckResult guessingGame() throws IOException {
        return new Scenario("guessing-game").check();
    }

}