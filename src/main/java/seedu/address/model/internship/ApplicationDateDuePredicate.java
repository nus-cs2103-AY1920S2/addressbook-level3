package seedu.address.model.internship;

import java.time.LocalDate;
import java.time.Period;
import java.util.function.Predicate;

/**
 * Tests that a {@code InternshipApplication}'s {@code Application Date} is within 7 days from current date.
 */
public class ApplicationDateDuePredicate implements Predicate<InternshipApplication> {
    private final LocalDate currentDate;

    public ApplicationDateDuePredicate() {
        this.currentDate = LocalDate.now();
    }

    @Override
    public boolean test(InternshipApplication internshipApplication) {
        LocalDate applicationDate = internshipApplication.getApplicationDate().fullApplicationDate;
        // count days between current internship application date and current date
        Period period = Period.between(currentDate, applicationDate);
        boolean check1 = applicationDate.compareTo(currentDate) > 0;
        boolean check2 = period.getDays() <= 7;
        System.out.println("check1 is: " + check1);
        System.out.println(check2);
        return (applicationDate.compareTo(currentDate) > 0) && (period.getDays() <= 7);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ApplicationDateDuePredicate // instanceof handles nulls
                && currentDate.equals(((ApplicationDateDuePredicate) other).currentDate)); // state check
    }
}

