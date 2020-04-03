package seedu.address.storage;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDERTYPE;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FileUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * This is a helper for retrieve the data from the csv file and process the data before return to ParseUtil.
 */
public class CsvProcessor {
    public static final String MESSAGE_READ_FAILED = "The csv file is unable to read.";
    public static final String MESSAGE_INVALID_FORMAT = "The data in csv file should start with \"ot/\".";

    private static final Logger logger = LogsCenter.getLogger(CsvProcessor.class);

    /**
     * Retrieve data from the given filePath.
     * @param filePath which the file is stored.
     * @return List of String which the individual order data has been split.
     * @throws ParseException throws if invalid filePath and the csv cannot be read.
     */
    public static List<String> retrieveData(Path filePath) throws ParseException {
        requireNonNull(filePath);

        String fileData;
        try {
            logger.info("Retrieving data from csv file...");
            fileData = FileUtil.readFromFile(filePath);
        } catch (IOException e) {
            logger.info("Failed to read the csv file at: " + filePath.toAbsolutePath());
            throw new ParseException(MESSAGE_READ_FAILED);
        }

        return processData(fileData);
    }

    /**
     * Return the individual order data by part after processing.
     * @param fileData that to be processed.
     * @return List of String that individual order data has been split.
     * @throws ParseException throws if the data is not start with order type prefix.
     */
    private static List<String> processData(String fileData) throws ParseException {
        requireNonNull(fileData);
        String fileDataTrimmed = fileData.trim();
        List<String> data = new ArrayList<>();

        // Check the first prefix is ot/.
        if (!fileDataTrimmed.startsWith(PREFIX_ORDERTYPE.toString())) {
            logger.info("Invalid format found - Csv data does not start with orderType prefix.");
            throw new ParseException(MESSAGE_INVALID_FORMAT);
        }

        return getCsvData(fileDataTrimmed);
    }

    /**
     * Get the Csv string and remove the first order type prefix. Eventually, it will split the string
     * by subsequence order type prefix and copy to list.
     * @param sentence to be processed.
     * @return List of String that individual order data has been split.
     */
    private static List<String> getCsvData(String sentence) {
        requireNonNull(sentence);
        List<String> data = new ArrayList<>();

        // Remove the first order type prefix and split the data according to order type prefix.
        logger.fine("Processing csv data into list of string ...");
        String[] result = sentence.replaceFirst(PREFIX_ORDERTYPE.toString(), "").split(PREFIX_ORDERTYPE.toString());
        Collections.addAll(data, result);
        return data;
    }
}
