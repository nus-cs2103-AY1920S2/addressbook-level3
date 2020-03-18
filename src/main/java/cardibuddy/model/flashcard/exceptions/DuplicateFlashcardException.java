package cardibuddy.model.flashcard.exceptions;

/**
 * Signals that the operation will result in duplicate Flashcards (Flashcards are considered duplicates if they have
 * the same question and type). Flashcards with the same question may not be duplicates if there types are different.
 */
public class DuplicateFlashcardException extends RuntimeException {
    public DuplicateFlashcardException() {
        super("Operation would result in duplicate flashcards");
    }
}
