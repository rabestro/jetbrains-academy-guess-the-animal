package animals.userinterface;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toUnmodifiableMap;

public class TextInterface {
    protected static final Logger log = Logger.getLogger(TextInterface.class.getName());

    private static final Pattern MESSAGE_DELIMITER = Pattern.compile("\\f");
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();
    private static final Map<String, Pattern> patterns;
    private static final ResourceBundle rules;

    static {
        rules = ResourceBundle.getBundle("patterns");
        patterns = rules.keySet().stream()
                .filter(key -> !key.endsWith(".replace"))
                .collect(toUnmodifiableMap(key -> key, key -> Pattern.compile(rules.getString(key))));
        log.config(patterns::toString);
    }

    private final ResourceBundle resourceBundle;

    public TextInterface() {
        this(ResourceBundle.getBundle("messages"));
    }

    public TextInterface(final ResourceBundle bundle) {
        this.resourceBundle = bundle;
    }

    private static String pickMessage(final String[] messages) {
        return messages[random.nextInt(messages.length)];
    }

    private String getText(final String key) {
        if (isNull(resourceBundle) || !resourceBundle.containsKey(key)) {
            return key;
        }
        if (resourceBundle.getObject(key) instanceof String[]) {
            return pickMessage(resourceBundle.getStringArray(key));
        }
        return pickMessage(MESSAGE_DELIMITER.split(resourceBundle.getString(key)));
    }

    public void println() {
        System.out.println();
    }

    public void println(final String key, final Object... args) {
        this.print(key, args);
        System.out.println();
    }

    public void print(final String key, final Object... args) {
        System.out.print(MessageFormat.format(getText(key), args));
    }

    public String ask(final String key, final Object... args) {
        while (true) {
            println(key + ".prompt", args);
            final var answer = readToLowerCase();
            if (patterns.get(key + ".isCorrect").matcher(answer).matches()) {
                return applyRules(key, answer);
            }
            println(key + ".error");
        }
    }

    public boolean askYesNo(final String key, final Object... args) {
        println(key, args);
        while (true) {
            final var answer = readToLowerCase();
            if (patterns.get("isPositiveAnswer").matcher(answer).matches()) {
                return true;
            }
            if (patterns.get("isNegativeAnswer").matcher(answer).matches()) {
                return false;
            }
            println("ask.again");
        }
    }

    public String readToLowerCase() {
        return scanner.nextLine().toLowerCase().trim();
    }

    public String applyRules(final String rule, final String data) {
        for (int i = 1; ; i++) {
            final var key = rule + "." + i;
            final var pattern = patterns.get(key + ".pattern");

            if (isNull(pattern)) {
                return data;
            }
            final var matcher = pattern.matcher(data);
            if (matcher.matches()) {
                return matcher.replaceFirst(rules.getString(key + ".replace"));
            }
        }
    }

    public void printConditional(final String messageName) {
        log.entering(TextInterface.class.getName(), "printConditional: " + messageName);
        final var messages = new ArrayList<String>();
        final var time = LocalTime.now();
        final var date = LocalDate.now();

        final Map<String, Predicate<String>> conditions = Map.of(
                "time.after", startTime -> time.isAfter(LocalTime.parse(startTime)),
                "time.before", endTime -> time.isBefore(LocalTime.parse(endTime)),
                "date.after", startDate -> date.isAfter(LocalDate.parse(date.getYear() + "-" + startDate)),
                "date.before", endDate -> date.isBefore(LocalDate.parse(date.getYear() + "-" + endDate)),
                "date.equals", someDate -> date.equals(LocalDate.parse(date.getYear() + "-" + someDate)));

        final Predicate<String> isConditionalMessage = key -> key.startsWith(messageName + ".");

        final Predicate<String> isConditionActual = message -> {
            final var period = message.substring(messageName.length() + 1) + ".";
            return conditions.entrySet().stream()
                    .filter(entry -> resourceBundle.containsKey(period + entry.getKey()))
                    .allMatch(entry -> entry.getValue().test(resourceBundle.getString(period + entry.getKey())));
        };
        final Function<String, List<String>> splitMessage = key -> Arrays.asList(
                MESSAGE_DELIMITER.split(resourceBundle.getString(key)));

        resourceBundle.keySet().stream()
                .filter(isConditionalMessage)
                .filter(isConditionActual)
                .map(splitMessage)
                .forEach(messages::addAll);

        if (resourceBundle.containsKey(messageName)) {
            messages.addAll(splitMessage.apply(messageName));
        }

        this.println(pickMessage(messages.toArray(String[]::new)));
        log.exiting(TextInterface.class.getName(), messageName, messages);
    }

    public static String capitalize(final String data) {
        return data.substring(0, 1).toUpperCase() + data.substring(1).toLowerCase();
    }

}