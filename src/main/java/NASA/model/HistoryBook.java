package nasa.model;

import static java.util.Objects.requireNonNull;
import javafx.collections.ObservableList;
import nasa.model.history.ModuleListHistory;

public class HistoryBook<T> implements ReadOnlyHistory<T> {

    private ModuleListHistory<T> moduleListHistory;
    private ModuleListHistory<T> moduleListHistoryCollector;

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

    public void setModuleListHistory(ModuleListHistory<T> moduleListHistory) {
        this.moduleListHistory = moduleListHistory;
    }

    public void add(T item) {
        moduleListHistory.push(item);
    }

    public boolean undo() {
        boolean checkStack = !moduleListHistory.isEmpty();
        if (checkStack) {
            moduleListHistoryCollector.pushDirectly(moduleListHistory.getPop());
            moduleListHistory.pop();
        }
        return checkStack;
    }

    public boolean redo() {
        boolean checkItem = !moduleListHistoryCollector.isEmpty();
        if (checkItem) {
            moduleListHistory.push(moduleListHistoryCollector.popDirectly());
        }
        return checkItem;
    }

    public T getItem() {
        return moduleListHistory.getPop();
    }

    public T getUndoItem() {
        return moduleListHistoryCollector.getPop();
    }

}
