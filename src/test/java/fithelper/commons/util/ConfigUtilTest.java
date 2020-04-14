package fithelper.commons.util;

import static fithelper.testutil.AssertUtil.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import fithelper.commons.core.Config;
import fithelper.commons.exceptions.DataConversionException;

public class ConfigUtilTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "ConfigUtilTest");

    @TempDir
    public Path tempDir;

    @Test
    public void read_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> read(null));
    }

    @Test
    public void read_missingFile_emptyResult() throws DataConversionException {
        assertFalse(read("NonExistentFile.json").isPresent());
    }

    private Optional<Config> read(String configFileInTestDataFolder) throws DataConversionException {
        Path configFilePath = addToTestDataPathIfNotNull(configFileInTestDataFolder);
        return ConfigUtil.readConfig(configFilePath);
    }

    @Test
    public void save_nullConfig_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> save(null, "SomeFile.json"));
    }

    @Test
    public void save_nullFile_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> save(new Config(), null));
    }

    private void save(Config config, String configFileInTestDataFolder) throws IOException {
        Path configFilePath = addToTestDataPathIfNotNull(configFileInTestDataFolder);
        ConfigUtil.saveConfig(config, configFilePath);
    }

    private Path addToTestDataPathIfNotNull(String configFileInTestDataFolder) {
        return configFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(configFileInTestDataFolder)
                : null;
    }


}
