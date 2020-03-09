package fithelper.commons.core;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Config values used by the app
 */
public class Config {

    /**
     * Path of FitHelper data.
     */
    public static final Path FITHELPER_DATA_PATH = Paths.get("data", "fithelper.json");

    public static final Path DEFAULT_CONFIG_FILE = Paths.get("config.json");

    private Config() {

    }
}

