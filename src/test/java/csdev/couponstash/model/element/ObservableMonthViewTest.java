package csdev.couponstash.model.element;

import static csdev.couponstash.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class ObservableMonthViewTest {
    private final ObservableMonthView observableMonthView = new ObservableMonthView();

    @Test
    public void setValue_nullValue_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> observableMonthView.setValue(null));
    }

    @Test
    public void setValue_isSameValue_success() {
        observableMonthView.setValue("8-2020");
        ObservableMonthView expectedMonthView = new ObservableMonthView();
        expectedMonthView.setValue("8-2020");
        assertEquals(observableMonthView, expectedMonthView);
    }

    @Test
    public void getValue_isSameAsSetValue_success() {
        String input = "8-2020";
        observableMonthView.setValue(input);
        MonthView expectedMonthView = observableMonthView.getValue();
        assertEquals(input, expectedMonthView.getValue());
    }

    @Test
    public void equals() {
        ObservableMonthView otherObservableMonthView = new ObservableMonthView();

        //Same Object
        assertEquals(observableMonthView, observableMonthView);

        //Different object, Same Year Month
        assertEquals(observableMonthView, otherObservableMonthView);

        //Different object, different YearMonth
        observableMonthView.setValue("8-2020");
        otherObservableMonthView.setValue("9-2020");
        assertNotEquals(observableMonthView, otherObservableMonthView);
    }
}
