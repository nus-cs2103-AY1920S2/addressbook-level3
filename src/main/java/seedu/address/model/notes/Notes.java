package seedu.address.model.notes;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.regex.Pattern;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


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

    private static final String[] validType = {"file", "folder"};

    private static final String[] validPathType = {"abs", "rel"};

    private static ObservableList<Notes> filesArrayListFiltered;

    private String path;
    private String type;
    private String pathType;

    public Notes(String path, String pathType) {
        requireNonNull(path);
        this.path = path;
        this.pathType = pathType;
    }

    public Notes(String path, String type, String pathType) {
        requireNonNull(path);
        this.path = path;
        this.type = type;
        this.pathType = pathType;
    }

    public Notes(String path) {
        requireNonNull(path);
        this.path = path;
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
        Comparator<Notes> comparator = new Comparator<Notes>() {
            @Override
            public int compare(Notes o1, Notes o2) {
                return (o1.getPath().compareToIgnoreCase(o2.getPath()));
            }
        };
        FXCollections.sort(filesArrayListFiltered, comparator);


    }

    /**
     * Return if it is a valid type.
     *
     * @param checkType type that is to be checked
     * @return true if valid false if not
     */
    public static boolean isValidType(String checkType) {

        for (String type : validType) {
            if (checkType.equals(type)) {
                return true;
            }

        }
        return false;

    }

    /**
     * Return if it is a valid path.
     *
     * @param checkPathType path that is to be checked
     * @return true if valid false if not
     */
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

    @Override
    public boolean equals(Object other) {

        boolean flag = other == this // short circuit if same object
                || (other instanceof Notes // instanceof handles nulls
                && this.path.equals(((Notes) other).getPath())
                && this.pathType.equals(((Notes) other).getFilePathType()));
        if (flag == false) {
            return false;
        } else {
            boolean typeFlag = false;
            if (type == null) {
                typeFlag = true;
            } else {
                typeFlag = this.type.equals(((Notes) other).getType());
            }
            return flag && typeFlag;
        }
    }

    @Override
    public int hashCode() {
        return (path + pathType).hashCode();
    }


}
