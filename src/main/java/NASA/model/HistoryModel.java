package nasa.model;

public interface HistoryModel<T> {

    void undo();

    void redo();

    void add(T list);
}
