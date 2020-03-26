package seedu.address.model;

public interface Versionable {
    public void commit();

    public void undo();
}
