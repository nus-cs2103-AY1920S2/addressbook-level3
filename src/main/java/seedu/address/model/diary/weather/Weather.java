package seedu.address.model.diary.weather;

/**
 * Dummy javadocs
 */
public abstract class Weather {
    private final String weather;

    protected Weather(String weather) {
        this.weather = weather;
    }

    @Override
    public String toString() {
        return "[" + weather + "]";
    }
}
