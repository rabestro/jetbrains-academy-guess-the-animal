package animals;

import animals.repository.StorageService;
import animals.userinterface.Application;

import java.util.logging.Logger;

public final class Main {
    private static final Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        log.entering(Main.class.getName(), "main", args);

        final var isTypeSpecified = args.length > 1 && args[0].equals("-type");
        final var storageService = isTypeSpecified
                ? StorageService.of(args[1])
                : StorageService.getDefaultService();

        new Application(storageService).run();

        log.exiting(Main.class.getName(), "main");
    }
}