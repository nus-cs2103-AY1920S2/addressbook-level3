//@@author aakanksha-rai

package tatracker.model.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tatracker.testutil.Assert.assertThrows;
import static tatracker.testutil.group.TypicalGroups.MANY_STUDENTS;
import static tatracker.testutil.group.TypicalGroups.MANY_STUDENTS_COPY;
import static tatracker.testutil.group.TypicalGroups.NO_STUDENTS;
import static tatracker.testutil.group.TypicalGroups.ONE_STUDENT;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import tatracker.model.group.exceptions.DuplicateGroupException;
import tatracker.model.group.exceptions.GroupNotFoundException;

public class UniqueGroupListTest {

    private final UniqueGroupList uniqueGroupList = new UniqueGroupList();

    @Test
    public void contains_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroupList.contains(null));
    }

    @Test
    public void contains_groupNotInList_returnsFalse() {
        assertFalse(uniqueGroupList.contains(NO_STUDENTS));
    }

    @Test
    public void contains_groupInList_returnsTrue() {
        uniqueGroupList.add(MANY_STUDENTS);
        assertTrue(uniqueGroupList.contains(MANY_STUDENTS));
    }

    @Test
    public void contains_groupWithSameIdentityFieldsInList_returnsTrue() {
        uniqueGroupList.add(MANY_STUDENTS);
        assertTrue(uniqueGroupList.contains(MANY_STUDENTS_COPY));
    }

    @Test
    public void add_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroupList.add(null));
    }

    @Test
    public void add_duplicateGroup_throwsDuplicateGroupException() {
        uniqueGroupList.add(NO_STUDENTS);
        assertThrows(DuplicateGroupException.class, () ->
                uniqueGroupList.add(NO_STUDENTS));
    }

    @Test
    public void setGroup_nullTargetGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueGroupList.setGroup(null, MANY_STUDENTS));
    }

    @Test
    public void setGroup_nullEditedGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueGroupList.setGroup(MANY_STUDENTS, null));
    }

    @Test
    public void setGroup_targetGroupNotInList_throwsGroupNotFoundException() {
        assertThrows(GroupNotFoundException.class, () ->
                uniqueGroupList.setGroup(ONE_STUDENT, ONE_STUDENT));
    }

    @Test
    public void setGroup_editedGroupIsSameGroup_success() {
        uniqueGroupList.add(MANY_STUDENTS);
        uniqueGroupList.setGroup(MANY_STUDENTS, MANY_STUDENTS);
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        expectedUniqueGroupList.add(MANY_STUDENTS);
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setGroup_editedGroupHasSameIdentity_success() {
        uniqueGroupList.add(MANY_STUDENTS);
        uniqueGroupList.setGroup(MANY_STUDENTS, MANY_STUDENTS_COPY);
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        expectedUniqueGroupList.add(MANY_STUDENTS_COPY);
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setGroup_editedGroupHasDifferentIdentity_success() {
        uniqueGroupList.add(MANY_STUDENTS);
        uniqueGroupList.setGroup(MANY_STUDENTS, ONE_STUDENT);
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        expectedUniqueGroupList.add(ONE_STUDENT);
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void remove_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroupList.remove(null));
    }

    @Test
    public void remove_groupDoesNotExist_throwsGroupNotFoundException() {
        assertThrows(GroupNotFoundException.class, () -> uniqueGroupList.remove(NO_STUDENTS));
    }

    @Test
    public void remove_existingGroup_removesGroup() {
        uniqueGroupList.add(NO_STUDENTS);
        uniqueGroupList.remove(NO_STUDENTS);
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setGroups_nullUniqueGroupList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroupList.setGroups((UniqueGroupList) null));
    }

    @Test
    public void setGroups_uniqueGroupList_replacesOwnListWithProvidedUniqueGroupList() {
        uniqueGroupList.add(NO_STUDENTS);
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        expectedUniqueGroupList.add(MANY_STUDENTS);
        uniqueGroupList.setGroups(expectedUniqueGroupList);
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setGroups_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueGroupList.setGroups((List<Group>) null));
    }

    @Test
    public void setGroups_list_replacesOwnListWithProvidedList() {
        uniqueGroupList.add(NO_STUDENTS);
        List<Group> groupList = Collections.singletonList(MANY_STUDENTS);
        uniqueGroupList.setGroups(groupList);
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        expectedUniqueGroupList.add(MANY_STUDENTS);
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setGroups_listWithDuplicateGroups_throwsDuplicateGroupException() {
        List<Group> listWithDuplicateGroups = Arrays.asList(NO_STUDENTS, NO_STUDENTS);
        assertThrows(DuplicateGroupException.class, () ->
                uniqueGroupList.setGroups(listWithDuplicateGroups));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                uniqueGroupList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void size() {
        uniqueGroupList.add(NO_STUDENTS);
        uniqueGroupList.add(MANY_STUDENTS);
        uniqueGroupList.add(ONE_STUDENT);
        assertEquals(3, uniqueGroupList.size());
    }

    @Test
    public void get() {
        uniqueGroupList.add(NO_STUDENTS);
        uniqueGroupList.add(MANY_STUDENTS);
        uniqueGroupList.add(ONE_STUDENT);
        assertEquals(uniqueGroupList.get(0), NO_STUDENTS);
        assertEquals(uniqueGroupList.get(1), MANY_STUDENTS);
        assertEquals(uniqueGroupList.get(2), ONE_STUDENT);
    }

    @Test
    public void getUsingGroupId() {
        uniqueGroupList.add(NO_STUDENTS);
        uniqueGroupList.add(MANY_STUDENTS);
        uniqueGroupList.add(ONE_STUDENT);
        assertEquals(uniqueGroupList.get("G04"), NO_STUDENTS);
        assertEquals(uniqueGroupList.get("G05"), MANY_STUDENTS);
        assertEquals(uniqueGroupList.get("G03"), ONE_STUDENT);
    }

}
