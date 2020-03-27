package seedu.address.model.notes;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



/**
 * Represents a Note to be created.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Notes {

    public static final String MESSAGE_CONSTRAINTS =
            "operation should contain open, create, createfolder and delete only and it should not be left blank.";

    public static final String HOME_DIRECTORY = System.getProperty("user.home");

    private static final String[] validOperationsDummy = {"open", "create", "delete", "createfolder", "list"};

    public static final HashSet<String> VALID_OPERATIONS = new HashSet<>(Arrays.asList(validOperationsDummy));

    private static ObservableList<Notes> filesArrayListFiltered;

    private String operation;
    private String path;

    public Notes(String operation, String path) {

        this.operation = operation;
        this.path = path;

    }

    public Notes(String path) {
        this.path = path;
        this.operation = null;
    }


    public static boolean isValidOperation(String operation) {
        return VALID_OPERATIONS.contains(operation.toLowerCase());
    }

    public static ObservableList<Notes> getAllFilesInFolder() {
        String pathName = HOME_DIRECTORY;

        File myFile = new File(pathName);
        ArrayList<Notes> filesArrayList = new ArrayList<>();

        File[] allFiles = myFile.listFiles();
        for (File f : allFiles) {
            String[] allFileName = f.toString().split(Pattern.quote(File.separator));
            String fileName = allFileName[allFileName.length - 1];
            if (fileName.charAt(0) == ('.')) {
                continue;
            }
            Notes note = new Notes(fileName);
            filesArrayList.add(note);
        }

        filesArrayListFiltered = FXCollections.observableArrayList(filesArrayList);
        return filesArrayListFiltered;
    }

    public static void setList(ArrayList<Notes> notesList) {

        filesArrayListFiltered.setAll(notesList);

    }

    public String getOperation() {
        return this.operation;
    }

    public String getPath() {
        return this.path;
    }




}
