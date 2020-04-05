package seedu.address.testutil;

import seedu.address.model.academics.Academics;
import seedu.address.model.academics.Assessment;

/**
 * A utility class to help with building Academics objects.
 * Example usage: <br>
 *     {@code Academics ac = new AcademicsBuilder().withAssessment("John", "Doe").build();}
 */
public class AcademicsBuilder {

    private Academics academics;

    public AcademicsBuilder() {
        academics = new Academics();
    }

    public AcademicsBuilder(Academics academics) {
        this.academics = academics;
    }

    /**
     * Adds a new {@code Assessment} to the {@code Academics} that we are building.
     */
    public AcademicsBuilder withAssessment(Assessment assessment) {
        academics.addAssessment(assessment);
        return this;
    }

    public Academics build() {
        return academics;
    }
}
