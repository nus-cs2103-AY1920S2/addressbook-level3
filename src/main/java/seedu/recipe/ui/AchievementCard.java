package seedu.recipe.ui;

import java.io.IOException;
import java.util.Random;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.recipe.model.Date;
import seedu.recipe.model.achievement.Quote;
import seedu.recipe.model.achievement.Streak;
import seedu.recipe.model.cooked.Record;

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
    private final String styleScoreHeader = "-fx-font-family: \"Arial\";\n"
            + "-fx-text-fill: #7FFF00;\n"
            + "-fx-font-weight: bold;"
            + "-fx-font-size: 14pt;\n";

    @FXML
    private HBox cardPane;
    @FXML
    private Label dayHeader;
    @FXML
    private Label scoreTitle;
    @FXML
    private Label score;
    @FXML
    private Label contentTitle;
    @FXML
    private Label content;

    public AchievementCard(ObservableList<Quote> quotes, ObservableList<Record> records) throws IOException {
        super(FXML);
        Date today = Date.today();
        //System.out.println(quote.getContent());
        Random rand = new Random();
        //System.out.print(quotes.size());
        int randomInt = rand.nextInt(quotes.size());
        this.quote = quotes.get(randomInt);
        Streak streak = new Streak(records, today);
        this.streak = streak;
        this.streak.updateStreak();
        records.addListener((ListChangeListener<Record>) record -> {
            while (record.next()) {
                this.streak.updateStreak();
            }
        });
        dayHeader.setText(today.getDayOfWeek() + ": " + today.toString());
        dayHeader.setStyle(weekStyleHeader);
        System.out.println(records.size());

        BorderPane border = new BorderPane();
        scoreTitle.setText("Streak:");
        scoreTitle.setStyle(styleScoreHeader);
        score.setWrapText(true);
        score.setText(Integer.toString(streak.getCurrStreak()));
        score.setStyle(styleHeader);
        score.setWrapText(true);

        contentTitle.setText("Quote of the Day:");
        contentTitle.setStyle(styleScoreHeader);
        content.setText(quote.getContent());
        content.setStyle(styleHeader);
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
