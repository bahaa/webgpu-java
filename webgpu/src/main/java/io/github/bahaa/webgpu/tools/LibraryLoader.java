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

            final var fileName = path.getFileName().toString();
            final var prefix = fileName.substring(0, fileName.lastIndexOf('.'));
            final var suffix = fileName.substring(fileName.lastIndexOf('.') + 1);
            final var file = java.io.File.createTempFile(prefix, suffix);
            file.deleteOnExit();

            Files.copy(is, file.toPath(), StandardCopyOption.REPLACE_EXISTING);

            System.load(file.getAbsolutePath());
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
