package seedu.address.model.order;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERY_TIMESTAMP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WAREHOUSE;

import seedu.address.logic.parser.ArgumentMultimap;

/**
 * Checks user input contains any of the {@code Prefix}.
 */
public class KeywordContainsOrderPrefix {

    private ArgumentMultimap argumentMultimap;
    private boolean hasTid;
    private boolean hasName;
    private boolean hasPhone;
    private boolean hasAddress;
    private boolean hasTimeStamp;
    private boolean hasWarehouse;
    private boolean hasCod;
    private boolean hasComment;
    private boolean hasItemType;

    // Empty constructor for OrderContainsKeywordsPredicate constructor.
    public KeywordContainsOrderPrefix() {
    }

    public KeywordContainsOrderPrefix(ArgumentMultimap argumentMultimap) {
        this.argumentMultimap = argumentMultimap;
        checkPrefixExist();
    }

    /**
     * Updates the presence of each prefix accordingly to match the user's input.
     */
    private void checkPrefixExist() {
        hasTid = argumentMultimap.getValue(PREFIX_TID).isPresent();
        hasName = argumentMultimap.getValue(PREFIX_NAME).isPresent();
        hasPhone = argumentMultimap.getValue(PREFIX_PHONE).isPresent();
        hasAddress = argumentMultimap.getValue(PREFIX_ADDRESS).isPresent();
        hasTimeStamp = argumentMultimap.getValue(PREFIX_DELIVERY_TIMESTAMP).isPresent();
        hasWarehouse = argumentMultimap.getValue(PREFIX_WAREHOUSE).isPresent();
        hasCod = argumentMultimap.getValue(PREFIX_COD).isPresent();
        hasComment = argumentMultimap.getValue(PREFIX_COMMENT).isPresent();
        hasItemType = argumentMultimap.getValue(PREFIX_TYPE).isPresent();
    }

    public boolean getHasTid() {
        return hasTid;
    }

    public boolean getHasName() {
        return hasName;
    }

    public boolean getHasPhone() {
        return hasPhone;
    }

    public boolean getHasAddress() {
        return hasAddress;
    }

    public boolean getHasTimeStamp() {
        return hasTimeStamp;
    }

    public boolean getHasWarehouse() {
        return hasWarehouse;
    }

    public boolean getHasCod() {
        return hasCod;
    }

    public boolean getHasComment() {
        return hasComment;
    }

    public boolean getHasItemType() {
        return hasItemType;
    }

    public void setHasTid(boolean hasTid) {
        this.hasTid = hasTid;
    }

    public void setHasName(boolean hasName) {
        this.hasName = hasName;
    }

    public void setHasPhone(boolean hasPhone) {
        this.hasPhone = hasPhone;
    }

    public void setHasAddress(boolean hasAddress) {
        this.hasAddress = hasAddress;
    }

    public void setHasTimeStamp(boolean hasTimeStamp) {
        this.hasTimeStamp = hasTimeStamp;
    }

    public void setHasWarehouse(boolean hasWarehouse) {
        this.hasWarehouse = hasWarehouse;
    }

    public void setHasCod(boolean hasCod) {
        this.hasCod = hasCod;
    }

    public void setHasComment(boolean hasComment) {
        this.hasComment = hasComment;
    }

    public void setHasItemType(boolean hasItemType) {
        this.hasItemType = hasItemType;
    }
}
