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
    CheckResult incorrectStatement() throws IOException {
        return new Scenario("incorrect-statement").check();
    }

    @DynamicTestingMethod
    CheckResult correctStatement() throws IOException {
        return new Scenario("correct-statement").check();
    }

    @DynamicTestingMethod
    CheckResult completeScenario() throws IOException {
        return new Scenario("complete-scenario").check();
    }

    @DynamicTestingMethod
    CheckResult factsQuestion() throws IOException {
        return new Scenario("facts-and-question").check();
    }
}

