package seedu.recipe.ui;

import java.io.IOException;
import java.util.Random;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.recipe.model.Date;
import seedu.recipe.model.achievement.Quote;
import seedu.recipe.model.achievement.Streak;

/**
 * UI class for achievement tab
 */
public class AchievementCard extends UiPart<Region> {

    private static final String FXML = "AchievementCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */
    private final Streak streak;
    private final Quote quote;
    private final String styleHeader = "-fx-font-family: \"Segoe UI\";\n"
            + "-fx-text-fill: #FFFFFF;\n"
            + "-fx-font-weight: bold;";
    private final String weekStyleHeader = styleHeader + "-fx-font-size: 15pt;\n";

    @FXML
    private HBox cardPane;
    @FXML
    private Label dayHeader;
    @FXML
    private Label score;
    @FXML
    private Label content;

    public AchievementCard(ObservableList<Quote> quotes, Streak streak) throws IOException {
        super(FXML);
        //System.out.println(quote.getContent());
        Random rand = new Random();
        System.out.print(quotes.size());
        int randomInt = rand.nextInt(quotes.size());
        this.quote = quotes.get(randomInt);
        this.streak = streak;
        Date today = Date.today();
        dayHeader.setText(today.getDayOfWeek() + ": " + today.toString());
        dayHeader.setStyle(weekStyleHeader);

        score.setText("Streak: " + streak.getCurrStreak());
        score.setWrapText(true);
        content.setText("Quote of the Day: " + quote.getContent());
        content.setWrapText(true);

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AchievementCard)) {
            return false;
        }

        // state check
        AchievementCard card = (AchievementCard) other;
        return content.getText().equals(card.content.getText());
    }
}
