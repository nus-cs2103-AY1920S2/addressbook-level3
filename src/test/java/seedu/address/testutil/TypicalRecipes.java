package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GOAL_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GOAL_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.recipe.Recipe;

/**
 * A utility class containing a list of {@code Recipe} objects to be used in tests.
 */
public class TypicalRecipes {

    public static final Recipe ALICE = new RecipeBuilder().withName("Alice Pauline")
            .withEmail("alice@example.com")
            .withTime("94351253")
            .withGoals("friends").build();
    public static final Recipe BENSON = new RecipeBuilder().withName("Benson Meier")
            .withEmail("johnd@example.com").withTime("98765432")
            .withGoals("owesMoney", "friends").build();
    public static final Recipe CARL = new RecipeBuilder().withName("Carl Kurz").withTime("95352563")
            .withEmail("heinz@example.com").build();
    public static final Recipe DANIEL = new RecipeBuilder().withName("Daniel Meier").withTime("87652533")
            .withEmail("cornelia@example.com").withGoals("friends").build();
    public static final Recipe ELLE = new RecipeBuilder().withName("Elle Meyer").withTime("9482224")
            .withEmail("werner@example.com").build();
    public static final Recipe FIONA = new RecipeBuilder().withName("Fiona Kunz").withTime("9482427")
            .withEmail("lydia@example.com").build();
    public static final Recipe GEORGE = new RecipeBuilder().withName("George Best").withTime("9482442")
            .withEmail("anna@example.com").build();

    // Manually added
    public static final Recipe HOON = new RecipeBuilder().withName("Hoon Meier").withTime("8482424")
            .withEmail("stefan@example.com").build();
    public static final Recipe IDA = new RecipeBuilder().withName("Ida Mueller").withTime("8482131")
            .withEmail("hans@example.com").build();

    // Manually added - Recipe's details found in {@code CommandTestUtil}
    public static final Recipe AMY = new RecipeBuilder().withName(VALID_NAME_AMY).withTime(VALID_TIME_AMY)
            .withEmail(VALID_EMAIL_AMY).withGoals(VALID_GOAL_FRIEND).build();
    public static final Recipe BOB = new RecipeBuilder().withName(VALID_NAME_BOB).withTime(VALID_TIME_BOB)
            .withEmail(VALID_EMAIL_BOB).withGoals(VALID_GOAL_HUSBAND, VALID_GOAL_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalRecipes() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical recipes.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Recipe recipe : getTypicalRecipes()) {
            ab.addRecipe(recipe);
        }
        return ab;
    }

    public static List<Recipe> getTypicalRecipes() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
