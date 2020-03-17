package seedu.address.model.notes;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Represents a Note to be created.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Notes {

    public static final String MESSAGE_CONSTRAINTS =
            "operation should contain open, create, createfolder and delete only and it should not be left blank.";

    private static final String[] validOperationsDummy = {"open", "create", "delete", "createfolder"};

    public static final HashSet<String> VALID_OPERATIONS = new HashSet<>(Arrays.asList(validOperationsDummy));

    private String operation;
    private String path;

    public Notes(String operation, String path) {

        this.operation = operation;
        this.path = path;

    }


    public static boolean isValidOperation(String operation) {
        return VALID_OPERATIONS.contains(operation.toLowerCase());
    }

    public String getOperation() {
        return this.operation;
    }

    public String getPath() {
        return this.path;
    }


}
