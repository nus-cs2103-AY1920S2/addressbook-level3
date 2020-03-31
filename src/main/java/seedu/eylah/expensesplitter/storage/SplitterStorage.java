package seedu.eylah.expensesplitter.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.eylah.commons.exceptions.DataConversionException;
import seedu.eylah.commons.storage.Storage;
import seedu.eylah.expensesplitter.model.ReadOnlyPersonAmountBook;
import seedu.eylah.expensesplitter.model.ReadOnlyReceiptBook;

/**
 * API of the Storage component.
 */
public interface SplitterStorage extends PersonAmountStorage, ReceiptStorage, Storage {

    @Override
    Path getPersonAmountBookFilePath();

    @Override
    Optional<ReadOnlyPersonAmountBook> readPersonAmountBook() throws DataConversionException, IOException;

    @Override
    void savePersonAmountBook(ReadOnlyPersonAmountBook personAmountBook) throws IOException;

    @Override
    Path getReceiptBookFilePath();

    @Override
    Optional<ReadOnlyReceiptBook> readReceiptBook() throws DataConversionException, IOException;

    @Override
    void saveReceiptBook(ReadOnlyReceiptBook receiptBook) throws IOException;
}
