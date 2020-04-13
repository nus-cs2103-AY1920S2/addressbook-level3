//@@author Clouddoggo
package seedu.jelphabot.model.productivity;

import static seedu.jelphabot.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class ProductivityListTest {

    private final ProductivityList productivityList = new ProductivityList();

    @Test
    void addProductivity_nullProductivity_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> productivityList.addProductivity(null));
    }

    @Test
    void setProductivity_nullProductivity_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> productivityList.setProductivity(null));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()-> productivityList
                                                                   .asUnmodifiableObservableList()
                                                                   .remove(0));
    }
}
