package nasa.model;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;

/**
 * A wrapper class to handle various history data.
 * @param <T> Type
 */
public class HistoryManager<T> implements HistoryModel<T> {

    private final HistoryBook<T> historyBook;
    private final HistoryBook<T> uiHistoryBook;

    public HistoryManager(ReadOnlyHistory<T> historyBook, ReadOnlyHistory<T> uiHistoryBook) {
        requireNonNull(historyBook);
        requireNonNull(uiHistoryBook);

        this.historyBook = new HistoryBook<>(historyBook);
        this.uiHistoryBook = new HistoryBook<>(uiHistoryBook);
    }

    public ObservableList<T> getHistoryList() {
        return historyBook.getModuleListHistory();
    }

    public HistoryBook<T> getHistoryBook() {
        return historyBook;
    }

    public HistoryBook<T> getUiHistoryBook() {
        return uiHistoryBook;
    }

    public T getItem() {
        return historyBook.getItem();
    }

    public T getUiItem() {
        return uiHistoryBook.getItem();
    }

    public void addUiHistory(T uiList) {
        uiHistoryBook.add(uiList);
    }

    @Override
    public void add(T moduleList, T uiList) {
        historyBook.add(moduleList);
        uiHistoryBook.add(uiList);
    }

    @Override
    public boolean undo() {
        uiHistoryBook.undo();
        return historyBook.undo();
    }

    @Override
    public boolean redo() {
        uiHistoryBook.redo();
        return historyBook.redo();
    }
}
