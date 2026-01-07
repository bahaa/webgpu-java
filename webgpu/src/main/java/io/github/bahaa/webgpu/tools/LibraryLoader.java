package io.github.bahaa.webgpu.tools;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.System.Logger.Level;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public enum LibraryLoader {
    ;

    private static final System.Logger LOGGER = System.getLogger(LibraryLoader.class.getName());

    public static void loadLibrary(final String name) {
        final var path = generateLibraryPath(name);

        LOGGER.log(Level.DEBUG, "Loading library {0} from classpath...", path);

        try (final var is = LibraryLoader.class
                .getClassLoader()
                .getResourceAsStream(path.toString())) {

            if (is == null) {
                LOGGER.log(Level.DEBUG, "Could not find {0} in classpath.", path);
                // Library not found in the class. We don't want to fail here as the library might exist in the current
                // directory or system libraries.
                return;
            }

            final var directory = Files.createTempDirectory(name);
            final var filePath = directory.resolve(path.getFileName());

            try {
                Files.copy(is, filePath, StandardCopyOption.REPLACE_EXISTING);
                System.load(filePath.toAbsolutePath().toString());
            } finally {
                var _ = filePath.toFile().delete();
                var _ = directory.toFile().delete();
            }
        } catch (final UnsatisfiedLinkError e) {
            LOGGER.log(Level.INFO, "Could not load library {0} from classpath.");
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static Path generateLibraryPath(final String name) {
        return Path.of(
                "foreign",
                "%s-%s".formatted(Platform.currentOS().id(), System.getProperty("os.arch")),
                System.mapLibraryName(name)
        );
    }

}
