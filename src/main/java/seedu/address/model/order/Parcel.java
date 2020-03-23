package seedu.address.model.order;

/**
 * The parcel class is used to abstract both orders and returns in the return book.
 */
public abstract class Parcel {
    protected abstract TransactionId getTid();
    protected abstract Name getName();
    protected abstract Phone getPhone();
    protected abstract Email getEmail();
    protected abstract Address getAddress();
    protected abstract TimeStamp getTimestamp();
    protected abstract Warehouse getWarehouse();
    protected abstract boolean isDelivered();
    protected abstract boolean isReturn();

    public abstract void setDeliveryStatus(boolean b);

    public abstract void setIsReturn(boolean b);
}
