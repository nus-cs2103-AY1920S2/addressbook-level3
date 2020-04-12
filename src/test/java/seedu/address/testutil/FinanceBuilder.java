package seedu.address.testutil;

import seedu.address.model.modelFinance.Finance;
import seedu.address.model.modelObjectTags.*;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * A utility class to help with building Finance objects.
 */
public class FinanceBuilder {

    public static final String DEFAULT_NAME = "AdHoc";
    public static final String DEFAULT_ID = "801";
    public static final String DEFAULT_FINANCETYPE = "m";
    public static final String DEFAULT_DATE = "2020-03-20";
    public static final String DEFAULT_AMOUNT = "102";
    public static final String DEFAULT_COURSEID = "829";
    public static final String DEFAULT_STUDENTID = "33";
    public static final String DEFAULT_TEACHERID = "21";

    private Name name;
    private ID id;
    private FinanceType financeType;
    private Date date;
    private Amount amount;
    private ID courseid;
    private ID studentid;
    private ID teacherid;
    private Set<Tag> tags;

    public FinanceBuilder() {
        name = new Name(DEFAULT_NAME);
        id = new ID(DEFAULT_ID);
        financeType = new FinanceType(DEFAULT_FINANCETYPE);
        date = new Date(DEFAULT_DATE);
        amount = new Amount(DEFAULT_AMOUNT);
        courseid = new ID(DEFAULT_COURSEID);
        studentid = new ID(DEFAULT_STUDENTID);
        teacherid = new ID(DEFAULT_TEACHERID);
        tags = new HashSet<>();
    }

    /**
     * Initializes the FinanceBuilder with the data of {@code financeToCopy}.
     */
    public FinanceBuilder(Finance financeToCopy) {
        name = financeToCopy.getName();
        financeType = financeToCopy.getFinanceType();
        date = financeToCopy.getDate();
        amount = financeToCopy.getAmount();
        courseid = financeToCopy.getCourseID();
        studentid = financeToCopy.getStudentID();
        teacherid = financeToCopy.getStaffID();
        tags = new HashSet<>(financeToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Finance} that we are building.
     */
    public FinanceBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Finance} that we are building.
     */
    public FinanceBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code ID} of the {@code Finance} that we are building.
     */
    public FinanceBuilder withAmount(String amount) {
        this.amount = new Amount(amount);
        return this;
    }

    /**
     * Sets the {@code financeType} of the {@code Finance} that we are building.
     */
    public FinanceBuilder withFinanceType(String financeType) {
        this.financeType = new FinanceType(financeType);
        return this;
    }

    /**
     * Sets the {@code date} of the {@code Finance} that we are building.
     */
    public FinanceBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code courseID} of the {@code Finance} that we are building.
     */
    public FinanceBuilder withCourseID(String courseID) {
        this.courseid = new ID(courseID);
        return this;
    }

    /**
     * Sets the {@code studnetID} of the {@code Finance} that we are building.
     */
    public FinanceBuilder withStudentID(String studentID) {
        this.studentid = new ID(studentID);
        return this;
    }

    /**
     * Sets the {@code teacherID} of the {@code Finance} that we are building.
     */
    public FinanceBuilder withTeacherID(String teacherID) {
        this.teacherid = new ID(teacherID);
        return this;
    }

    public Finance build() {
        return new Finance(name, id, financeType, date, amount, courseid, studentid, teacherid, tags);
    }

}
