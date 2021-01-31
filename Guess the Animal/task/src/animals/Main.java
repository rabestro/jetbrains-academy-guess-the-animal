package animals;

import animals.repository.StorageService;
import animals.userinterface.Application;

import java.util.logging.Logger;

public final class Main {
    private static final Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        log.entering(Main.class.getName(), "main", args);
        final var type = args.length > 1 && args[0].equals("-type") ? args[1] : "yaml";

        new Application(StorageService.of(type)).run();

        log.exiting(Main.class.getName(), "main");
    }
}