package nasa.model;

public interface HistoryModel<T> {

    boolean undo();

    boolean redo();

    void add(T list);
}
