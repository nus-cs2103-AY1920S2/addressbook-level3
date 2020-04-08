package nasa.model.quote;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Random;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import nasa.commons.core.LogsCenter;
import nasa.commons.util.FileUtil;

/**
 * Class to store motivation quotes.
 */
public class Quote {

    /**
     * Quotes being stored in {@code lines}.
     */
    private static ObservableList<String> lines = FXCollections.observableArrayList();

    public static ObservableList<String> getLines() {
        return lines;
    }

    /**
     * Initialise data by retrieving it from resources.
     */
    public static void readFile() {
        try {
            Path relative = Path.of("data/quotes.txt");
            InputStream readQuote = Quote.class.getClassLoader()
                    .getResourceAsStream("text/quotes.txt");
            assert readQuote != null;
            String res = new String(readQuote.readAllBytes());
            FileUtil.createIfMissing(relative);
            FileUtil.writeToFile(relative, res);
            lines.addAll(FileUtil.readFromFile(relative).split("\n"));
        } catch (IOException error) {
            Logger logger = LogsCenter.getLogger(LogsCenter.class);
            logger.info(error.getMessage());
        }
    }

    public static String getQuote() {
        Random random = new Random();
        int i = random.nextInt(lines.size());
        return lines.get(i);
    }

}
