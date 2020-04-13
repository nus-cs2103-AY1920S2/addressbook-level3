package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalGroups.SOC;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.person.ActivityList;
import seedu.address.model.person.Name;
import seedu.address.model.person.PlaceList;
import seedu.address.model.person.TimeList;

public class UniqueGroupListTest {

    private final UniqueGroupList uniqueGroupList = new UniqueGroupList();
    private final List<Group> groupList = new ArrayList<>();

    @Test
    public void contains_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroupList.contains(null));
    }

    @Test
    public void contains_nullGroups_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroupList.containsGroups(null));
    }

    @Test
    public void contains_groupInList_returnsTrue() {
        Group group = new Group(new Name("Soc Friends"), new PlaceList(new ArrayList<>()),
                new ActivityList(new ArrayList<>()), new TimeList(new ArrayList<>()));
        uniqueGroupList.addGroup(group);
        assertTrue(uniqueGroupList.contains(group));
    }
    @Test
    public void containsGroups_groupsInList_returnsTrue() {
        uniqueGroupList.addGroup(SOC);
        groupList.add(SOC);
        assertTrue(uniqueGroupList.contains(SOC));
    }

    @Test
    public void remove_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroupList.removeGroup(null));
    }

    @Test
    public void remove_exitingGroup_success() {
        uniqueGroupList.addGroup(SOC);
        uniqueGroupList.removeGroup(SOC);
        UniqueGroupList expectedList = new UniqueGroupList();
        assertEquals(expectedList, uniqueGroupList);
    }

    @Test
    public void add_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroupList.addGroup(null));
    }

    @Test
    public void add_duplicateGroup_throwsDuplicateGroupException() {
        Group group = new Group(new Name("Soc Friends"), new PlaceList(new ArrayList<>()),
                new ActivityList(new ArrayList<>()), new TimeList(new ArrayList<>()));
        uniqueGroupList.addGroup(group);
        assertThrows(DuplicateGroupException.class, () -> uniqueGroupList.addGroup(group));
    }

    @Test
    public void addAll_duplicateGroup_throwsDuplicateGroupsException() {
        Group group = new Group(new Name("Soc Friends"), new PlaceList(new ArrayList<>()),
                new ActivityList(new ArrayList<>()), new TimeList(new ArrayList<>()));
        ArrayList<Group> groups = new ArrayList<>();
        groups.add(group);
        uniqueGroupList.addAll(groups);
        assertThrows(DuplicateGroupException.class, () -> uniqueGroupList.addAll(groups));
    }

    @Test
    public void setGroupNullTargetGroupThrowsNullPointerException() {
        Group group = new Group(new Name("Soc Friends"), new PlaceList(new ArrayList<>()),
                new ActivityList(new ArrayList<>()), new TimeList(new ArrayList<>()));
        assertThrows(NullPointerException.class, () -> uniqueGroupList.setGroup(null, group));
    }

    @Test
    public void setGroupNullEditedGroupThrowsNullPointerException() {
        Group group = new Group(new Name("Soc Friends"), new PlaceList(new ArrayList<>()),
                new ActivityList(new ArrayList<>()), new TimeList(new ArrayList<>()));
        assertThrows(NullPointerException.class, () -> uniqueGroupList.setGroup(group, null));
    }

    @Test
    public void setGroupEditedGroupHasNonUniqueIdentityThrowsDuplicatePersonException() {
        Group group = new Group(new Name("Soc Friends"), new PlaceList(new ArrayList<>()),
                new ActivityList(new ArrayList<>()), new TimeList(new ArrayList<>()));
        uniqueGroupList.addGroup(group);
        Group group1 = new Group(new Name("Cinnamon Friends"), new PlaceList(new ArrayList<>()),
                new ActivityList(new ArrayList<>()), new TimeList(new ArrayList<>()));
        uniqueGroupList.addGroup(group1);
        assertThrows(DuplicateGroupException.class, () -> uniqueGroupList.setGroup(group, group1));
    }

    @Test
    public void setGroupEditedGroupIsSameGroupSuccess() {
        Group group = new Group(new Name("Soc Friends"), new PlaceList(new ArrayList<>()),
                new ActivityList(new ArrayList<>()), new TimeList(new ArrayList<>()));
        uniqueGroupList.addGroup(group);
        uniqueGroupList.setGroup(group, group);
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        expectedUniqueGroupList.addGroup(group);
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setGroups_nullUniqueGroupsList_throwsNullPointerException() {
        assertThrows(
                NullPointerException.class, () -> uniqueGroupList.setGroups((UniqueGroupList) null));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(
                UnsupportedOperationException.class, (

                        ) -> uniqueGroupList.asUnmodifiableObservableList().remove(0));
    }

}
