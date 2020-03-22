package nasa.model;

import static java.util.Objects.requireNonNull;
import javafx.collections.ObservableList;
import nasa.model.history.ModuleListHistory;
import nasa.model.module.UniqueModuleList;

public class HistoryManager<T> implements HistoryModel<T> {

    private final HistoryBook<T> historyBook;

    public HistoryManager(ReadOnlyHistory<T> historyBook) {
        requireNonNull(historyBook);

        this.historyBook = new HistoryBook<>(historyBook);
    }

    public HistoryManager() {
        this.historyBook = new HistoryBook<>();
    }

    public ObservableList<T> getHistoryList() {
        return historyBook.getModuleListHistory();
    }

    public HistoryBook<T> getHistoryBook() {
        return historyBook;
    }

    public T getItem() {
        return historyBook.getItem();
    }

    public T getUndoItem() {
        return historyBook.getUndoItem();
    }

    @Override
    public void add(T moduleList) {
        historyBook.add(moduleList);
    }

    @Override
    public boolean undo() {
        return historyBook.undo();
    }

    @Override
    public boolean redo() {
        return historyBook.redo();
    }
}
