package seedu.address.model.hirelah.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonSerializableModel {
    private final boolean finalised;


    @JsonCreator
    public JsonSerializableModel(@JsonProperty("finalised") boolean finalised) { //potential error
        this.finalised = finalised;
    }

    public boolean toModelType() {
        return this.finalised;
    }
}
