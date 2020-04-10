package team.easytravel.storage.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import team.easytravel.commons.exceptions.IllegalValueException;
import team.easytravel.model.listmanagers.ActivityManager;
import team.easytravel.model.listmanagers.ReadOnlyActivityManager;
import team.easytravel.model.listmanagers.activity.Activity;


/**
 * An Immutable activityManager that is serializable to JSON format.
 */
@JsonRootName(value = "activityManager")
class JsonSerializableActivityManager {

    public static final String MESSAGE_DUPLICATE_ACTIVITY = "Activity list contains duplicate activities.";

    private final List<JsonAdaptedActivity> activities = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableActivityManager} with the given activities.
     */
    @JsonCreator
    public JsonSerializableActivityManager(@JsonProperty("activities") List<JsonAdaptedActivity> activities) {
        this.activities.addAll(activities);
    }

    /**
     * Converts a given {@code ReadOnlyActivityManager} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableActivityManager}.
     */
    public JsonSerializableActivityManager(ReadOnlyActivityManager source) {
        activities.addAll(source.getActivityList().stream().map(JsonAdaptedActivity::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this activityManager into the model's {@code ActivityManager} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ActivityManager toModelType() throws IllegalValueException {
        ActivityManager activityManager = new ActivityManager();
        for (JsonAdaptedActivity jsonAdaptedActivity : activities) {
            Activity activity = jsonAdaptedActivity.toModelType();
            if (activityManager.hasActivity(activity)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ACTIVITY);
            }
            activityManager.addActivity(activity);
        }
        return activityManager;
    }

}
