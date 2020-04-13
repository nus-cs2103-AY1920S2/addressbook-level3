package seedu.address.ui.personbio;

import javafx.beans.property.SimpleStringProperty;

/**
 * A class to represent the pair of field and data for each row of the personal detail table.
 */
public class FieldDataPair {
    private final SimpleStringProperty field;
    private final SimpleStringProperty data;

    public FieldDataPair(String field, String data) {
        this.field = new SimpleStringProperty(field);
        this.data = new SimpleStringProperty(data);
    }

    public void setData(String data) {
        this.data.set(data);
    }

    public String getData() {
        return data.get();
    }

    public String getField() {
        return field.get();
    }

    public void setField(String field) {
        this.field.set(field);
    }
}
