package seedu.recipe.storage.achievement;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.recipe.commons.exceptions.IllegalValueException;
import seedu.recipe.model.achievement.Content;
import seedu.recipe.model.achievement.Quote;
import seedu.recipe.model.recipe.Name;

/**
 * Jackson-friendly version of {@link Quote}.
 */
public class JsonAdaptedQuote {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Quote's %s field is missing!";

    private final String content;
    private final List<JsonAdaptedQuote> quotes = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedQuote} with the given quote details.
     */
    @JsonCreator
    public JsonAdaptedQuote(@JsonProperty("content") String content,
                             @JsonProperty("goals") List<JsonAdaptedQuote> quotes) {
        this.content = content;
        if (quotes != null) {
            this.quotes.addAll(quotes);
        }
    }

    /**
     * Converts a given {@code Quote} into this class for Jackson use.
     */
    public JsonAdaptedQuote(Quote source) {
        content = source.getContent();
    }

    /**
     * Converts this Jackson-friendly adapted record object into the model's {@code Quote} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted quote.
     */
    public Quote toModelType() throws IllegalValueException {

        if (content == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Content.class.getSimpleName()));
        }

        if (!Content.isValidContent(content)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Content modelContent = new Content(content);

        return new Quote(modelContent);
    }
}
