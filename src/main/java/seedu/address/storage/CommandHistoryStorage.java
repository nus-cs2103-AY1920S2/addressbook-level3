package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * This represents the storage for the command history.
 */
public class CommandHistoryStorage {

    private Path commandHistoryPath;

    public CommandHistoryStorage(Path commandHistoryPath) {
        this.commandHistoryPath = commandHistoryPath;
        createNewFile();
    }

    /**
     * Creates an empty new file for future use if the file does not currently
     * exists.
     */
    private void createNewFile() {
        File file = commandHistoryPath.toFile();

        if (file.exists()) {
            return;
        }

        file.getParentFile().mkdirs();

        try {
            file.createNewFile();
        } catch (IOException ex) {
            System.out.println(ex.getStackTrace());
        }
    }

    /**
     * Returns an ArrayList of each command string from storage. If the file does
     * not exist, this method will call {@code createNewFile} to ensure that the
     * file is created for future use.
     */
    public ArrayList<String> readFromStorage() {
        ArrayList<String> commands = new ArrayList<>();
        try {
            BufferedReader reader = Files.newBufferedReader(commandHistoryPath);

            String line = reader.readLine();
            while (line != null) {
                commands.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException ex) {
            // create new file
            System.out.println(ex.getStackTrace());
        }
        return commands;
    }

    /**
     * Saves the given command history to the storage.
     *
     * @param history cannot be null.
     */
    public void saveToStorage(ArrayList<String> history) {
        requireNonNull(history);
        try {
            BufferedWriter writer = Files.newBufferedWriter(commandHistoryPath);

            for (String command : history) {
                writer.write(command);
                writer.newLine();
            }
            writer.close();
        } catch (IOException ex) {
            System.out.println(ex.getStackTrace());
        }
    }

    /**
     * Clears the history from storage.
     */
    public void clearStorage() {
        try {
            BufferedWriter writer = Files.newBufferedWriter(commandHistoryPath);

            writer.close();
        } catch (IOException ex) {
            System.out.println(ex.getStackTrace());
        }
    }
}
