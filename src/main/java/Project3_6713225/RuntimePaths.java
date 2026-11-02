package Project3_6713225;

import java.nio.file.*;

final class RuntimePaths {
    private static final Path ROOT = root();
    private static Path root() {
        try {
            Path location = Paths.get(RuntimePaths.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            return Files.isRegularFile(location) ? location.getParent() : Paths.get("").toAbsolutePath();
        } catch (Exception e) { throw new IllegalStateException(e); }
    }
    static String resources() {
        return ROOT.resolve("src/main/java/Project3_6713225/resources").toString() + java.io.File.separator;
    }
    static String data(String name) {
        String local = System.getenv("LOCALAPPDATA");
        Path dir = Paths.get(local == null ? System.getProperty("user.home") : local, "JumpUntilHell");
        try {
            Files.createDirectories(dir);
            Path file = dir.resolve(name);
            Path seed = ROOT.resolve("src/main/java/Project3_6713225").resolve(name);
            if (!Files.exists(file) && Files.exists(seed)) Files.copy(seed, file);
            return file.toString();
        } catch (java.io.IOException e) { throw new IllegalStateException(e); }
    }
}
