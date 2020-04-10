package team.easytravel.testutil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import team.easytravel.commons.core.index.Index;
import team.easytravel.model.Model;
import team.easytravel.model.listmanagers.fixedexpense.FixedExpense;


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
            throw new RuntimeException(e);
        }
        return SANDBOX_FOLDER.resolve(fileName);
    }

    /**
     * Returns the middle index of the Fixed Expense in the {@code model}'s fixed expense list.
     */
    public static Index getFixedExpenseMidIndex(Model model) {
        return Index.fromOneBased(model.getFilteredFixedExpenseList().size() / 2);
    }

    /**
     * Returns the last index of the Fixed Expense in the {@code model}'s person list.
     */
    public static Index getFixedExpenseLastIndex(Model model) {
        return Index.fromOneBased(model.getFilteredFixedExpenseList().size());
    }

    /**
     * Returns the person in the {@code model}'s person list at {@code index}.
     */
    public static FixedExpense getFixedExpense(Model model, Index index) {
        return model.getFilteredFixedExpenseList().get(index.getZeroBased());
    }
}
