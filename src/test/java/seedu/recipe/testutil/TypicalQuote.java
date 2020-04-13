package seedu.recipe.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.recipe.model.achievement.Content;
import seedu.recipe.model.achievement.Quote;
import seedu.recipe.model.achievement.QuoteBook;

/**
 * A utility class containing a list of {@code Quote} objects to be used in tests.
 */
public class TypicalQuote {
    public static final Quote QUOTE_1 = new Quote(new Content("QUOTE 1"));
    public static final Quote QUOTE_2 = new Quote(new Content("QUOTE 2"));
    public static final Quote QUOTE_3 = new Quote(new Content("QUOTE 3"));
    public static final Quote QUOTE_4 = new Quote(new Content("QUOTE 4"));

    /**
     * Returns an {@code QuoteBook} with all the typical recipes.
     */
    public static QuoteBook getTypicalQuoteBook() {
        QuoteBook qb = new QuoteBook();
        for (Quote quote : getTypicalQuotes()) {
            qb.addQuote(quote);
        }
        return qb;
    }


    public static List<Quote> getTypicalQuotes() {
        return new ArrayList<>(Arrays.asList(QUOTE_1, QUOTE_2, QUOTE_3, QUOTE_4));
    }
}
