import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

public class GuessAnimalTest extends StageTest<String> {

    @DynamicTest
    CheckResult testLanguages() throws IOException {
        final var languages = Map.of("en", "english", "eo", "esperanto");
        for (var entry: languages.entrySet()) {
            final var fileName = "animal" + ("en".equals(entry.getKey()) ? "." : "_" + entry.getKey() + ".");
            final var locale = Locale.getDefault();
            final var language = System.getProperty("user.language");
            Locale.setDefault(new Locale(entry.getKey()));
            System.setProperty("user.language", entry.getKey());

            deleteFiles(fileName);
            final var result = new Scenario(entry.getValue()).check();
            deleteFiles(fileName);

            Locale.setDefault(locale);
            System.setProperty("user.language", language);
            if (!result.isCorrect()) {
                return result;
            }
        }
        return CheckResult.correct();
    }

    private void deleteFiles(String fileName) {
        Stream.of("yaml", "json", "xml")
                .map(fileName::concat)
                .map(File::new)
                .filter(File::exists)
                .forEach(File::delete);
    }
}