package seedu.address.logic.parser.cookbook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.cookbook.CookbookSearchByInventoryCommand;
import seedu.address.logic.commands.cookbook.CookbookSearchByKeywordCommand;
import seedu.address.logic.commands.cookbook.CookbookSearchByTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.recipe.RecipeContainsTagsPredicate;
import seedu.address.model.recipe.RecipeNameContainsKeywordsPredicate;

public class CookbookSearchCommandParserTest {
    private static final String VALID_KEYWORDS_STRING = "recipe k/key k/words";
    private static final String VALID_KEYWORDS_ARGUMENTS = " k/key k/words";
    private static final List<String> VALID_KEYWORDS_ONE = Arrays.asList("key", "words");
    private static final RecipeNameContainsKeywordsPredicate VALID_KEYWORD_PREDICATE_ONE =
            new RecipeNameContainsKeywordsPredicate(VALID_KEYWORDS_ONE);

    private static final String VALID_TAGS_STRING = "tag t/easy t/difficult";
    private static final String VALID_TAGS_ARGUMENTS = " t/easy t/difficult";
    private static final List<String> VALID_TAGS_ONE = Arrays.asList("easy", "difficult");
    private static final RecipeContainsTagsPredicate VALID_TAG_PREDICATE_ONE =
            new RecipeContainsTagsPredicate(VALID_TAGS_ONE);

    private static final String VALID_INVENTORY_STRING = "inventory";

    @Test
    public void parse_validInput() throws ParseException {
        assertEquals(new CookbookSearchCommandParser().parse(VALID_KEYWORDS_STRING),
            new CookbookSearchByKeywordCommand(VALID_KEYWORD_PREDICATE_ONE));
        assertEquals(new CookbookSearchCommandParser().parse(VALID_TAGS_STRING),
            new CookbookSearchByTagCommand(VALID_TAG_PREDICATE_ONE));
        assertEquals(new CookbookSearchCommandParser().parse(VALID_INVENTORY_STRING),
            new CookbookSearchByInventoryCommand());
    }

    @Test
    public void parse_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CookbookSearchCommandParser().parse(null));
    }

    @Test
    public void parseSearchByKeyword_validInput() throws ParseException {
        assertEquals(new CookbookSearchCommandParser().parseSearchByKeyword(VALID_KEYWORDS_ARGUMENTS),
            new CookbookSearchByKeywordCommand(VALID_KEYWORD_PREDICATE_ONE));
    }

    @Test
    public void parseSearchByKeyword_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CookbookSearchCommandParser().parseSearchByKeyword(null));
    }

    @Test
    public void parseSearchByTag_validInput() throws ParseException {
        assertEquals(new CookbookSearchCommandParser().parseSearchByTag(VALID_TAGS_ARGUMENTS),
            new CookbookSearchByTagCommand(VALID_TAG_PREDICATE_ONE));
    }

    @Test
    public void parseSearchByTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CookbookSearchCommandParser().parseSearchByTag(null));
    }

    @Test
    public void parseSearchByInventory_validInput() throws ParseException {
        assertEquals(new CookbookSearchCommandParser().parseSearchByInventory(VALID_INVENTORY_STRING),
            new CookbookSearchByInventoryCommand());
    }

    @Test
    public void parseSearchByInventory_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CookbookSearchCommandParser().parseSearchByInventory(null));
    }
}
