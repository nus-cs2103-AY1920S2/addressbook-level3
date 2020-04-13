package fithelper.model;

import static fithelper.testutil.AssertUtil.assertThrows;
import static fithelper.testutil.TypicalEntriesUtil.FOOD;
import static fithelper.testutil.TypicalEntriesUtil.SPORTS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fithelper.testutil.FitHelperBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new FitHelper(), new FitHelper(modelManager.getFitHelper()));
    }

    @Test
    public void hasEntry_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasEntry(null));
    }

    @Test
    public void hasEntry_entryNotInFitHelper_returnsFalse() {
        assertFalse(modelManager.hasEntry(FOOD));
    }

    @Test
    public void hasEntry_entryInFitHelper_returnsTrue() {
        modelManager.addEntry(FOOD);
        assertTrue(modelManager.hasEntry(FOOD));
    }

    @Test
    public void getFilteredFoodEntryList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredFoodEntryList().remove(0));
    }
    @Test
    public void getFilteredSportsEntryList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredSportsEntryList().remove(0));
    }


    @Test
    public void equals() {
        FitHelper fitHelper = new FitHelperBuilder().withEntry(FOOD).withEntry(SPORTS).build();
        UserProfile userProfile = new UserProfile();
        WeightRecords weightRecords = new WeightRecords();

        // same values -> returns true
        modelManager = new ModelManager(fitHelper, userProfile, weightRecords);
        ModelManager modelManagerCopy = new ModelManager(fitHelper, userProfile, weightRecords);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // different types -> returns false
        assertFalse(modelManager.equals(5));
    }
}
