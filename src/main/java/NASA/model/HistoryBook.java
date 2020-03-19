package nasa.model;

import static java.util.Objects.requireNonNull;
import javafx.collections.ObservableList;
import nasa.model.history.ModuleListHistory;

public class HistoryBook<T> implements ReadOnlyHistory<T> {

    private final ModuleListHistory<T> moduleListHistory;
    private final ModuleListHistory<T> moduleListHistoryCollector;

    {
        moduleListHistory = new ModuleListHistory<>();
        moduleListHistoryCollector = new ModuleListHistory<>();
    }

    public HistoryBook() {}

    public HistoryBook(ReadOnlyHistory<T> history) {
        this();
        resetData(history);
    }

    public void resetData(ReadOnlyHistory<T> newData) {
        requireNonNull(newData);

        moduleListHistory.setStackList(newData.getModuleListHistory());
    }

    public ObservableList<T> getModuleListHistory() {
        return moduleListHistory.asUnmodifiableObservableList();
    }

    public void add(T item) {
        moduleListHistory.push(item);
    }

    public void undo() {
        moduleListHistory.pop();
        moduleListHistoryCollector.push(moduleListHistory.getPop());
    }

    public void redo() {
        if (!moduleListHistoryCollector.isEmpty()) {
           moduleListHistoryCollector.pop();
           moduleListHistory.push(moduleListHistoryCollector.getPop());
        }
    }
}
