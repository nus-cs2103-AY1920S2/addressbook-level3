package seedu.address.ui;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.ObservableList;

import seedu.address.model.notes.Notes;

/**
 * Represents a NotesExporter which handles the UI aspects of the Notes feature.
 */
public class NotesExporter {
    private final ObservableList<Notes> notesList;

    /**
     * Constructor of NotesManager
     * @param notesList
     */
    public NotesExporter(ObservableList<Notes> notesList) {
        this.notesList = notesList;
    }

    /**
     * Function which saves the notes to a .csv file is user's current directory.
     */
    public void saveToCsv() {
        ArrayList<ArrayList<String>> rows = new ArrayList<>();

        for (Notes note : notesList) {
            ArrayList<String> noteContent = new ArrayList<>();
            noteContent.add(note.getStudent());
            noteContent.add(note.getPriority());
            noteContent.add(note.getDateTime());
            noteContent.add(note.getContent());
            rows.add(noteContent);
        }

        try {
            FileWriter csvWriter = new FileWriter("data/studentNotes.csv");
            csvWriter.append("Student");
            csvWriter.append(",");
            csvWriter.append("Priority");
            csvWriter.append(",");
            csvWriter.append("DateTime");
            csvWriter.append(",");
            csvWriter.append("Content");
            csvWriter.append("\n");

            for (ArrayList<String> rowData : rows) {
                csvWriter.append(String.join(",", rowData));
                csvWriter.append("\n");
            }

            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }



}
