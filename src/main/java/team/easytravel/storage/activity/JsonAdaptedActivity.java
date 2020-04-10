package team.easytravel.storage.activity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import team.easytravel.commons.core.time.DateTime;
import team.easytravel.commons.exceptions.IllegalValueException;
import team.easytravel.model.listmanagers.activity.Activity;
import team.easytravel.model.listmanagers.activity.Duration;
import team.easytravel.model.util.attributes.Location;
import team.easytravel.model.util.attributes.Title;
import team.easytravel.model.util.attributes.tag.Tag;
import team.easytravel.storage.JsonAdaptedTag;

/**
 * Jackson-friendly version of {@link Activity}.
 */
class JsonAdaptedActivity {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Activity's %s field is missing!";

    private final String title;
    private final Integer duration;
    private final String location;
    private final String scheduledDateTime;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedActivity} with the given activity details.
     */
    @JsonCreator
    public JsonAdaptedActivity(@JsonProperty("title") String title,
                               @JsonProperty("duration") Integer duration,
                               @JsonProperty("location") String location,
                               @JsonProperty("scheduledDateTime") String scheduledDateTime,
                               @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.title = title;
        this.duration = duration;
        this.location = location;
        this.scheduledDateTime = scheduledDateTime;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Activity} into this class for Jackson use.
     */
    public JsonAdaptedActivity(Activity source) {
        title = source.getTitle().value;
        duration = source.getDuration().value;
        location = source.getLocation().value;
        if (source.getScheduledDateTime().isPresent()) {
            scheduledDateTime = source.getScheduledDateTime().get().getStorageFormat();
        } else {
            scheduledDateTime = null;
        }
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted activity object into the model's {@code Activity} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted activity.
     */
    public Activity toModelType() throws IllegalValueException {
        final List<Tag> activityTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            activityTags.add(tag.toModelType());
        }

        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        if (!Title.isValidTitle(title)) {
            throw new IllegalValueException(Title.MESSAGE_CONSTRAINTS);
        }
        final Title modelTitle = new Title(title);

        if (duration == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Duration.class.getSimpleName()));
        }
        if (!Duration.isValidDuration(duration)) {
            throw new IllegalValueException(Duration.MESSAGE_CONSTRAINTS);
        }
        final Duration modelDuration = new Duration(duration);

        if (location == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Location.class.getSimpleName()));
        }
        if (!Location.isValidLocation(location)) {
            throw new IllegalValueException(Location.MESSAGE_CONSTRAINTS);
        }
        final Location modelLocation = new Location(location);

        Optional<DateTime> modelScheduledDateTime;
        if (scheduledDateTime == null) {
            modelScheduledDateTime = Optional.empty();
        } else {
            modelScheduledDateTime = Optional.of(DateTime.fromString(scheduledDateTime));
        }

        final Set<Tag> modelTags = new HashSet<>(activityTags);
        return new Activity(modelTitle, modelDuration, modelLocation, modelTags, modelScheduledDateTime);
    }

}
