package seedu.address.model.nusmodule;

/**
 * Represents a note for any module.
 */
public class Note {
    private String name;
    private String content;

    public Note(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
