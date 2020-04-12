package nasa.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Jackson-friendly version filter property.
 */
public class JsonAdaptedFilterProperty {

    private final String filterProperty;

    @JsonCreator
    public JsonAdaptedFilterProperty(@JsonProperty("filterProperty") String filterProperty) {
        this.filterProperty = filterProperty;
    }

    public String toModelType() {
        return filterProperty;
    }
}
