package team.easytravel.testutil.activity;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import team.easytravel.model.listmanagers.activity.Activity;
import team.easytravel.model.listmanagers.activity.Duration;
import team.easytravel.model.util.attributes.Location;
import team.easytravel.model.util.attributes.Title;
import team.easytravel.model.util.attributes.tag.Tag;
import team.easytravel.model.util.sampledata.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class ActivityBuilder {

    public static final String DEFAULT_TITLE = "CHEESELAND OWO";
    public static final Integer DEFAULT_DURATION = 2;
    public static final String DEFAULT_LOCATION = "LAND OF CHEESE";

    private Title title;
    private Duration duration;
    private Location location;
    private Set<Tag> tags;

    public ActivityBuilder() {
        title = new Title(DEFAULT_TITLE);
        duration = new Duration(DEFAULT_DURATION);
        location = new Location(DEFAULT_LOCATION);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code activity}.
     */
    public ActivityBuilder(Activity activity) {
        title = activity.getTitle();
        duration = activity.getDuration();
        location = activity.getLocation();
        tags = new HashSet<>(activity.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public ActivityBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Activity} that we are building.
     */
    public ActivityBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Activity} that we are building.
     */
    public ActivityBuilder withLocation(String address) {
        this.location = new Location(address);
        return this;
    }

    /**
     * Sets the {@code Duration} of the {@code Activity} that we are building.
     */
    public ActivityBuilder withDuration(Integer duration) {
        this.duration = new Duration(duration);
        return this;
    }

    public Activity build() {
        return new Activity(title, duration, location, tags, Optional.empty());
    }

}
