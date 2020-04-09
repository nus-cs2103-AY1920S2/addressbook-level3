package seedu.address.model.profile;

//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.address.testutil.Assert.assertThrows;
//import static seedu.address.testutil.TypicalPersons.ALICE;
//import static seedu.address.testutil.TypicalPersons.BOB;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.address.model.profile.exceptions.DuplicatePersonException;
//import seedu.address.model.profile.exceptions.PersonNotFoundException;

public class UniquePersonListTest {

//    private final UniquePersonList uniqueProfileList = new UniquePersonList();
//
//    @Test
//    public void contains_nullPerson_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> uniqueProfileList.contains(null));
//    }
//
//    @Test
//    public void contains_personNotInList_returnsFalse() {
//        assertFalse(uniqueProfileList.contains(ALICE));
//    }
//
//    @Test
//    public void contains_personInList_returnsTrue() {
//        uniqueProfileList.add(ALICE);
//        assertTrue(uniqueProfileList.contains(ALICE));
//    }
//
//    @Test
//    public void add_nullPerson_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> uniqueProfileList.add(null));
//    }
//
//    @Test
//    public void add_duplicatePerson_throwsDuplicatePersonException() {
//        uniqueProfileList.add(ALICE);
//        assertThrows(DuplicatePersonException.class, () -> uniqueProfileList.add(ALICE));
//    }
//
//    @Test
//    public void setProfile_nullTargetPerson_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> uniqueProfileList.setProfile(null, ALICE));
//    }
//
//    @Test
//    public void setProfile_nullEditedPerson_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> uniqueProfileList.setProfile(ALICE, null));
//    }
//
//    @Test
//    public void setProfile_targetPersonNotInList_throwsPersonNotFoundException() {
//        assertThrows(PersonNotFoundException.class, () -> uniqueProfileList.setProfile(ALICE, ALICE));
//    }
//
//    @Test
//    public void setProfile_editedPersonIsSamePerson_success() {
//        uniqueProfileList.add(ALICE);
//        uniqueProfileList.setProfile(ALICE, ALICE);
//        UniquePersonList expectedUniqueProfileList = new UniquePersonList();
//        expectedUniqueProfileList.add(ALICE);
//        assertEquals(expectedUniqueProfileList, uniqueProfileList);
//    }
//
//    @Test
//    public void setProfile_editedPersonHasDifferentIdentity_success() {
//        uniqueProfileList.add(ALICE);
//        uniqueProfileList.setPersons(ALICE, BOB);
//        UniquePersonList expectedUniqueProfileList = new UniquePersonList();
//        expecteduniqueProfileList.add(BOB);
//        assertEquals(expecteduniqueProfileList, uniqueProfileList);
//    }
//
//    //    @Test
//    //    public void setProfile_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
//    //        uniqueProfileList.add(ALICE);
//    //        uniqueProfileList.add(BOB);
//    //        assertThrows(DuplicatePersonException.class, () -> uniqueProfileList.setProfile(ALICE, BOB));
//    //    }
//
//    @Test
//    public void remove_nullPerson_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> uniqueProfileList.remove(null));
//    }
//
//    @Test
//    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
//        assertThrows(PersonNotFoundException.class, () -> uniqueProfileList.remove(ALICE));
//    }
//
//    @Test
//    public void remove_existingPerson_removesPerson() {
//        uniqueProfileList.add(ALICE);
//        uniqueProfileList.remove(ALICE);
//        UniquePersonList expecteduniqueProfileList = new UniquePersonList();
//        assertEquals(expecteduniqueProfileList, uniqueProfileList);
//    }
//
//    @Test
//    public void setProfiles_nullUniquePersonList_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> uniqueProfileList.setProfiles((UniquePersonList) null));
//    }
//
//    @Test
//    public void setProfiles_uniqueProfileList_replacesOwnListWithProvidedUniquePersonList() {
//        uniqueProfileList.add(ALICE);
//        UniquePersonList expectedUniquePersonList = new UniquePersonList();
//        expectedUniquePersonList.add(BOB);
//        uniqueProfileList.setProfiles(expectedUniquePersonList);
//        assertEquals(expectedUniquePersonList, uniqueProfileList);
//    }
//
//    @Test
//    public void setProfiles_nullList_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> uniqueProfileList.setProfiles((List<Profile>) null));
//    }
//
//    @Test
//    public void setProfiles_list_replacesOwnListWithProvidedList() {
//        uniqueProfileList.add(ALICE);
//        List<Profile> profileList = Collections.singletonList(BOB);
//        uniqueProfileList.setProfiles(profileList);
//        UniquePersonList expectedUniquePersonList = new UniquePersonList();
//        expectedUniquePersonList.add(BOB);
//        assertEquals(expectedUniquePersonList, uniqueProfileList);
//    }
//
//    @Test
//    public void setProfiles_listWithDuplicatePersons_throwsDuplicatePersonException() {
//        List<Profile> listWithDuplicateProfiles = Arrays.asList(ALICE, ALICE);
//        assertThrows(DuplicatePersonException.class, () -> uniqueProfileList.setProfiles(listWithDuplicateProfiles));
//    }
//
//    @Test
//    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
//        assertThrows(UnsupportedOperationException.class, ()
//            -> uniqueProfileList.asUnmodifiableObservableList().remove(0));
//    }
}
