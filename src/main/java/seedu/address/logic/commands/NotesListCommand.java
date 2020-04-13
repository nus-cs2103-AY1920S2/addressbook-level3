package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES_PATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES_PATH_TYPE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_NOTES;

import java.io.File;
import java.util.ArrayList;

import seedu.address.model.Model;
import seedu.address.model.notes.Notes;


/**
 * NotesCommand is created when a notecommand is parsed, to return what operation to be done.
 * implementation is not yet completed
 */
public class NotesListCommand extends Command {

    public static final String COMMAND_WORD = "notesList";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List files in specified directory "
            + "Parameters: "
            + PREFIX_NOTES_PATH + "PATH "
            + PREFIX_NOTES_PATH_TYPE + "PATH TYPE \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NOTES_PATH + "Desktop "
            + PREFIX_NOTES_PATH_TYPE + "abs";

    private static final String MESSAGE_NOTHING_HAPPENED = "Nothing Happened ";
    private static final String MESSAGE_LISTED_DIR = "Listed Directory at ";
    private static final String MESSAGE_NOT_DIR = " is not a directory";
    private static final String MESSAGE_NOT_EXIST = " does not exist";



    private String path;
    private String filePath;
    private Notes note;

    /**
     * Creates a new NotesListCommand to list a new note.
     *
     * @param note the operation and location that will be done to the note.
     */
    public NotesListCommand(Notes note) {

        this.note = note;
        this.path = note.getPath();
        this.filePath = note.getFilePathType();
    }


    /**
     * Tentative, may remove this function.
     * @param path dummy
     * @return dummy
     */
    public CommandResult listFiles(String path) {
        String pathName = "";

        if (this.filePath.equals("abs")) {
            pathName = Notes.HOME_DIRECTORY + File.separatorChar + path;
        } else {
            pathName = Notes.getCurrentDirectory() + File.separatorChar + path;
        }

        File myFile = new File(pathName);
        if (myFile.exists() == false) {
            return new CommandResult(pathName + MESSAGE_NOT_EXIST);
        }
        if (myFile.isDirectory() == false) {
            return new CommandResult(pathName + MESSAGE_NOT_DIR);
        }
        ArrayList<Notes> filesArrayList = new ArrayList<>();

        File[] allFiles = myFile.listFiles();
        for (File f : allFiles) {
            String[] allFileName = f.toString().split(File.separator);
            String filename = allFileName[allFileName.length - 1];
            if (filename.charAt(0) == ('.')) {
                continue;
            }
            Notes note = new Notes(filename);
            filesArrayList.add(note);
        }
        Notes.setList(filesArrayList);
        Notes.setCurrentDirectory(pathName);
        return new CommandResult(MESSAGE_LISTED_DIR + pathName);
    }

    /**
     * Tentative, to be updated.
     * @param path dummy
     * @return dummy
     */
    public static ArrayList<Notes> listfilesArray(String path) {

        File myFile = new File(path);
        ArrayList<Notes> filesArrayList = new ArrayList<>();

        File[] allFiles = myFile.listFiles();
        for (File f : allFiles) {
            String[] allFileName = f.toString().split(File.separator);
            String filename = allFileName[allFileName.length - 1];
            if (filename.charAt(0) == ('.')) {
                continue;
            }
            Notes note = new Notes(filename);
            filesArrayList.add(note);
        }
        return filesArrayList;

    }

    public Notes getNote() {
        return this.note;
    }


    @Override
    public CommandResult execute(Model model) {

        model.updateNotesList(PREDICATE_SHOW_ALL_NOTES);
        return listFiles(this.path);

    }

    @Override
    public boolean equals(Object other) {

        NotesListCommand otherNotesOpen = (NotesListCommand) other;
        return this.note.equals(otherNotesOpen.getNote());

    }
}
