package seedu.address.model.customer;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests whether a {@code Customer}'s attributes fulfill the given properties.
 */
public class JointCustomerPredicate implements Predicate<Customer> {
    private final List<Predicate<Customer>> predicates;

    public JointCustomerPredicate(List<Predicate<Customer>> predicates) {
        this.predicates = predicates;
    }

    @Override
    public boolean test(Customer customer) {
        return predicates.stream()
                .allMatch(predicate -> predicate.test(customer));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof JointCustomerPredicate // instanceof handles nulls
                && predicates.equals(((JointCustomerPredicate) other).predicates)); // state check
    }

    @Override
    public String toString() {
        String msg = "";

        if (predicates.size() > 1) {
            for (Predicate predicate : predicates) {
                if (msg.isEmpty()) {
                    if (predicate instanceof NameContainsKeywordsPredicate) {
                        msg = String.format("No customers named %s ", predicate.toString());
                    } else if (predicate instanceof AddressContainsKeywordsPredicate) {
                        msg = String.format("No customers staying in the area %s ", predicate.toString());
                    } else if (predicate instanceof EmailContainsKeywordsPredicate) {
                        msg = String.format("No customers with email %s ", predicate.toString());
                    } else if (predicate instanceof PhoneContainsKeywordsPredicate) {
                        msg = String.format("No customers with phone number %s ", predicate.toString());
                    }
                } else {
                    if (predicate instanceof NameContainsKeywordsPredicate) {
                        msg += String.format("named %s ", predicate.toString());
                    } else if (predicate instanceof AddressContainsKeywordsPredicate) {
                        msg += String.format("staying in the area %s ", predicate.toString());
                    } else if (predicate instanceof EmailContainsKeywordsPredicate) {
                        msg += String.format("with email %s ", predicate.toString());
                    } else if (predicate instanceof PhoneContainsKeywordsPredicate) {
                        msg += String.format("with phone number %s ", predicate.toString());
                    }
                }
            }
            msg += "found!";
        } else {
            Predicate<Customer> predicate = predicates.get(0);
            if (predicate instanceof NameContainsKeywordsPredicate) {
                msg = String.format("No customers named %s found!", predicate.toString());
            } else if (predicate instanceof AddressContainsKeywordsPredicate) {
                msg = String.format("No customers staying in the area %s found!", predicate.toString());
            } else if (predicate instanceof EmailContainsKeywordsPredicate) {
                msg = String.format("No customers with email %s found!", predicate.toString());
            } else if (predicate instanceof PhoneContainsKeywordsPredicate) {
                msg = String.format("No customers with phone number %s found!", predicate.toString());
            }
        }
        return msg;
    }
}
