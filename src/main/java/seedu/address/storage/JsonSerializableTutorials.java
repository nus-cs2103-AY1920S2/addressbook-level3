package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.tutorial.ReadOnlyTutorial;
import seedu.address.model.event.tutorial.Tutorial;
import seedu.address.model.event.tutorial.TutorialTAble;

/**
 * An Immutable TutorialTAble that is serializable to JSON format.
 */
@JsonRootName(value = "tutorials")
public class JsonSerializableTutorials {

    public static final String MESSAGE_DUPLICATE_TUTORIAL = "Tutorials list contains duplicate tutorial(s).";

    private final List<JsonAdaptedTutorial> tutorials = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableTutorials} with the given tutorials.
     */
    @JsonCreator
    public JsonSerializableTutorials(@JsonProperty("tutorials") List<JsonAdaptedTutorial> tutorials) {
        this.tutorials.addAll(tutorials);
    }

    /**
     * Converts a given {@code ReadOnlyTutorial} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableTutorials}.
     */
    public JsonSerializableTutorials(ReadOnlyTutorial source) {
        tutorials.addAll(source.getAllTutorials().stream().map(JsonAdaptedTutorial::new).collect(Collectors.toList()));
    }

    /**
     * Converts this TutorialTAble into the model's {@code TutorialStorage} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public TutorialTAble toModelType() throws IllegalValueException {
        TutorialTAble tutorialTAble = new TutorialTAble();
        for (JsonAdaptedTutorial jsonAdaptedTutorial : tutorials) {
            Tutorial tutorial = jsonAdaptedTutorial.toModelType();
            if (tutorialTAble.hasTutorial(tutorial)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TUTORIAL);
            }
            tutorialTAble.addTutorial(tutorial);
        }
        return tutorialTAble;
    }
}
