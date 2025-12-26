package io.github.bahaa.webgpu.tools;

public enum OperatingSystem {
    ;

    public static OS current() {
        final var osName = System.getProperty("os.name");

        if (osName == null) {
            return OS.OTHER;
        } else if (osName.startsWith("Windows")) {
            return OS.WINDOWS;
        } else if (osName.startsWith("Linux")) {
            return OS.LINUX;
        } else if (osName.startsWith("Mac")) {
            return OS.MACOS;
        } else {
            return OS.OTHER;
        }
    }

    public enum OS {
        WINDOWS("windows"),
        LINUX("linux"),
        MACOS("macos"),
        OTHER(null);

        private final String id;

        OS(final String id) {
            this.id = id;
        }

        public String id() {
            return this.id;
        }
    }
}
