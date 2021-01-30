import animals.Main;
import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

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
    CheckResult testFileFormats() throws IOException {
        final var result = new Scenario("file-formats").check();

        Stream.of("yaml", "json", "xml")
                .map("animals."::concat)
                .map(File::new)
                .filter(File::exists)
                .forEach(File::delete);

        return result;
    }
}