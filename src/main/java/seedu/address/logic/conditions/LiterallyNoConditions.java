package seedu.address.logic.conditions;

/**
 * Literally no conditional filtering is being done here
 *
 * @param <T>
 */
public class LiterallyNoConditions<T> implements Conditions<T> {

    /**
     * Like I said. Literally no conditional filtering is being done here.
     *
     * @param   objToTest
     * @return  Always True
     */
    @Override
    public Boolean satisfies(T objToTest) {
        return true;
    }
}
