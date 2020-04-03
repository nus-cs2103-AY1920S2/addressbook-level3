package seedu.address.model.parcel;

import seedu.address.model.parcel.comment.Comment;
import seedu.address.model.parcel.itemtype.TypeOfItem;
import seedu.address.model.parcel.parcelattributes.Address;
import seedu.address.model.parcel.parcelattributes.Email;
import seedu.address.model.parcel.parcelattributes.Name;
import seedu.address.model.parcel.parcelattributes.Phone;
import seedu.address.model.parcel.parcelattributes.TimeStamp;
import seedu.address.model.parcel.parcelattributes.TransactionId;
import seedu.address.model.parcel.parcelattributes.Warehouse;

/**
 * The parcel class is used to abstract both orders and returns in the return book.
 */
public abstract class Parcel {
    public abstract TransactionId getTid();
    public abstract Name getName();
    public abstract Phone getPhone();
    public abstract Email getEmail();
    public abstract Address getAddress();
    public abstract TimeStamp getTimestamp();
    public abstract Warehouse getWarehouse();
    public abstract Comment getComment();
    public abstract TypeOfItem getItemType();
    public abstract boolean isDelivered();
    public abstract boolean isSameParcel(Parcel parcel);

    public abstract void setDeliveryStatus(boolean b);

}
