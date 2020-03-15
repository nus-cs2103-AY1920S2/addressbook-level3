package cardibuddy.model;

import static java.util.Objects.requireNonNull;
import static cardibuddy.commons.util.CollectionUtil.requireAllNonNull;

import cardibuddy.model.deck.Deck;
import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import cardibuddy.commons.core.GuiSettings;
import cardibuddy.commons.core.LogsCenter;
import cardibuddy.model.CardiBuddy;
import cardibuddy.model.Model;
import cardibuddy.model.ReadOnlyCardiBuddy;
import cardibuddy.model.ReadOnlyUserPrefs;
import cardibuddy.model.UserPrefs;
import cardibuddy.model.flashcard.Flashcard;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(cardibuddy.model.ModelManager.class);

    private final CardiBuddy cardiBuddy;
    private final UserPrefs userPrefs;
    private final FilteredList<Flashcard> filteredFlashcards;

    /**
     * Initializes a ModelManager with the given cardiBuddy and userPrefs.
     */
    public ModelManager(ReadOnlyCardiBuddy cardiBuddy, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(cardiBuddy, userPrefs);

        logger.fine("Initializing with address book: " + cardiBuddy + " and user prefs " + userPrefs);

        this.cardiBuddy = new CardiBuddy(cardiBuddy);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredFlashcards = new FilteredList<>(this.cardiBuddy.getFlashcardList());
    }

    public ModelManager() {
        this(new CardiBuddy(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getCardiBuddyFilePath() {
        return userPrefs.getCardiBuddyFilePath();
    }

    @Override
    public void setCardiBuddyFilePath(Path cardiBuddyFilePath) {
        requireNonNull(cardiBuddyFilePath);
        userPrefs.setCardiBuddyFilePath(cardiBuddyFilePath);
    }

    //=========== CardiBuddy ================================================================================

    @Override
    public void setCardiBuddy(ReadOnlyCardiBuddy cardiBuddy) {
        this.cardiBuddy.resetData(cardiBuddy);
    }

    @Override
    public ReadOnlyCardiBuddy getCardiBuddy() {
        return cardiBuddy;
    }

    @Override
    public boolean hasDeck(Deck deck) {
        requireNonNull(deck);
        return cardiBuddy.hasDeck(deck);
    }

    @Override
    public void deleteDeck(Deck target) {

    }

    @Override
    public void addDeck(Deck deck) {
        cardiBuddy.addDeck(deck);
        updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);
    }

    @Override
    public void setDeck(Deck target, Deck editedDeck) {

    }

    public boolean hasCard(Flashcard person) {
        return true;
    }

    public void deleteCard(Flashcard target) {
        cardiBuddy.removeFlashcard(target);
    }

    public void addCard(Flashcard person) {
        cardiBuddy.addFlashcard(person);
        updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);
    }

    public void setCard(Flashcard target, Flashcard editedFlashcard) {
        requireAllNonNull(target, editedFlashcard);

        cardiBuddy.setFlashcard(target, editedFlashcard);
    }

    //=========== Filtered Flashcard List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Flashcard} backed by the internal list of
     * {@code versionedCardiBuddy}
     */
    @Override
    public ObservableList<Flashcard> getFilteredFlashcardList() {
        return filteredFlashcards;
    }

    @Override
    public void updateFilteredFlashcardList(Predicate<Flashcard> predicate) {
        requireNonNull(predicate);
        filteredFlashcards.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof cardibuddy.model.ModelManager)) {
            return false;
        }

        // state check
        cardibuddy.model.ModelManager other = (cardibuddy.model.ModelManager) obj;
        return cardiBuddy.equals(other.cardiBuddy)
                && userPrefs.equals(other.userPrefs)
                && filteredFlashcards.equals(other.filteredFlashcards);
    }

}
