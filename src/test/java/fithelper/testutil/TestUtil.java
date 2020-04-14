package fithelper.testutil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import fithelper.commons.core.index.Index;
import fithelper.model.Model;
import fithelper.model.entry.Entry;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    private static final Path SANDBOX_FOLDER = Paths.get("src", "test", "data", "sandbox");

    /**
     * Appends {@code fileName} to the sandbox folder path and returns the resulting path.
     * Creates the sandbox folder if it doesn't exist.
     */
    public static Path getFilePathInSandboxFolder(String fileName) {
        try {
            Files.createDirectories(SANDBOX_FOLDER);
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
        return SANDBOX_FOLDER.resolve(fileName);
    }

    /**
     * Returns the middle index of the entry in the {@code model}'s entry list.
     */
    public static Index getMidIndex(Model model) {
        return Index.fromOneBased(model.getFilteredFoodEntryList().size() / 2);
    }

    /**
     * Returns the last index of the entry in the {@code model}'s entry list.
     */
    public static Index getLastIndex(Model model) {
        return Index.fromOneBased(model.getFilteredFoodEntryList().size());
    }

    /**
     * Returns the entry in the {@code model}'s entry list at {@code index}.
     */
    public static Entry getEntry(Model model, Index index) {
        return model.getFilteredFoodEntryList().get(index.getZeroBased());
    }
}
