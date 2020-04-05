package seedu.address.model.notes;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Pattern;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.ui.NotesListPanel;


/**
 * Represents a Note to be created.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Notes {

    public static final String MESSAGE_CONSTRAINTS =
            "operation should contain open, create, createfolder and delete only and it should not be left blank.";

    public static final String MESSAGE_CONSTRAINTS_PATH_TYPE =
            "Path type should only be abs(Absolute) or rel(Relative)";

    public static final String MESSAGE_CONSTRAINTS_TYPE =
            "Type must be only file or folder";

    public static final String HOME_DIRECTORY = System.getProperty("user.home");

    private static String currentDirectory = HOME_DIRECTORY;

    private static ObservableValue<String> observableCurrentDirectory =
            new ReadOnlyObjectWrapper<>("Current Directory " + currentDirectory);

    private static final String[] validOperationsDummy = {"open", "create", "delete", "createfolder", "list"};

    private static final String[] validType = {"file", "folder"};

    private static final String[] validPathType = {"abs", "rel"};

    public static final HashSet<String> VALID_OPERATIONS = new HashSet<>(Arrays.asList(validOperationsDummy));

    private static ObservableList<Notes> filesArrayListFiltered;

    private String path;
    private String type;
    private String pathType;

    public Notes(String path, String pathType) {
        this.path = path;
        this.pathType = pathType;
    }

    public Notes(String path, String type, String pathType) {
        this.path = path;
        this.type = type;
        this.pathType = pathType;
    }

    public Notes(String path) {
        this.path = path;
    }


    public static boolean isValidOperation(String operation) {
        return VALID_OPERATIONS.contains(operation.toLowerCase());
    }

    public static ObservableList<Notes> getAllFilesInFolder() {

        File myFile = new File(HOME_DIRECTORY);
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

    public static boolean isValidType(String checkType) {

        for (String type : validType) {
            if (checkType.equals(type)) {
                return true;
            }

        }
        return false;

    }

    public static boolean isValidPathType(String checkPathType) {

        for (String pathType : validPathType) {
            if (checkPathType.equals(pathType)) {
                return true;
            }
        }
        return false;

    }

    public static String getCurrentDirectory() {
        return currentDirectory;
    }


    public static void setCurrentDirectory(String directory) {

        currentDirectory = directory;
    }

    public String getPath() {
        return this.path;
    }

    public String getFilePathType() {
        return this.pathType;
    }

    public String getType() {
        return this.type;
    }




}
