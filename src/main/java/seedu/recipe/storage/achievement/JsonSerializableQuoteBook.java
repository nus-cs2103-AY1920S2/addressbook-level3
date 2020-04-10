package seedu.recipe.storage.achievement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.recipe.commons.exceptions.IllegalValueException;
import seedu.recipe.model.ReadOnlyQuoteBook;
import seedu.recipe.model.achievement.Quote;
import seedu.recipe.model.achievement.QuoteBook;


/**
 * An Immutable QuoteBook that is serializable to JSON format.
 */
@JsonRootName(value = "quotebook")
public class JsonSerializableQuoteBook {

    public static final String MESSAGE_DUPLICATE_QUOTE = "Quotes list contains duplicate quote(s).";

    private final List<JsonAdaptedQuote> quotes = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableQuoteBook} with the given quotes.
     */
    @JsonCreator
    public JsonSerializableQuoteBook(@JsonProperty("quotes") List<JsonAdaptedQuote> quotes) {
        this.quotes.addAll(quotes);
    }

    /**
     * Converts a given {@code ReadOnlyQuoteBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableQuoteBook}.
     */
    public JsonSerializableQuoteBook(ReadOnlyQuoteBook source) {
        quotes.addAll(source.getQuoteList().stream().map(JsonAdaptedQuote::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this record book into the model's {@code QuoteBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */

    public QuoteBook toModelType() throws IllegalValueException {
        QuoteBook quoteBook = new QuoteBook();
        for (JsonAdaptedQuote jsonAdaptedQuote : quotes) {
            Quote quote = jsonAdaptedQuote.toModelType();
            if (quoteBook.hasQuote(quote)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_QUOTE);
            }
            quoteBook.addQuote(quote);
        }
        return quoteBook;
    }

}
