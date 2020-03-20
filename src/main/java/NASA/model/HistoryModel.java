package nasa.model;

public interface HistoryModel<T> {

    boolean undo();

    void redo();

    void add(T list);
}
