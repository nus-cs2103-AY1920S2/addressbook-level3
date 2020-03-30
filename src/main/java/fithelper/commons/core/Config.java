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

    /**
     * Path of User Profile data.
     */
    public static final Path USERPROFILE_DATA_PATH = Paths.get("data", "userprofile.json");

    /**
     * Path of Weight Records data.
     */
    public static final Path WEIGHTRECORDS_DATA_PATH = Paths.get("data", "weightrecords.json");

}

