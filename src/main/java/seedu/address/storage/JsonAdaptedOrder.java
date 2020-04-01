package seedu.address.storage;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.parcel.comment.Comment;
import seedu.address.model.parcel.itemtype.TypeOfItem;
import seedu.address.model.parcel.order.CashOnDelivery;
import seedu.address.model.parcel.order.Order;
import seedu.address.model.parcel.parcelattributes.Address;
import seedu.address.model.parcel.parcelattributes.Email;
import seedu.address.model.parcel.parcelattributes.Name;
import seedu.address.model.parcel.parcelattributes.Phone;
import seedu.address.model.parcel.parcelattributes.TimeStamp;
import seedu.address.model.parcel.parcelattributes.TransactionId;
import seedu.address.model.parcel.parcelattributes.Warehouse;

/**
 * Jackson-friendly version of {@link Order}.
 */
class JsonAdaptedOrder {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Order's %s field is missing!";
    private static final Logger logger = LogsCenter.getLogger(JsonAdaptedOrder.class);

    private final String tid;
    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String timeStamp;
    private final String warehouse;
    private final String cod;
    private final String comment;
    private final String itemType;
    private final boolean deliveryStatus;

    /**
     * Constructs a {@code JsonAdaptedOrder} with the given order details.
     */
    @JsonCreator
    public JsonAdaptedOrder(@JsonProperty("tid") String tid,
                            @JsonProperty("name") String name,
                            @JsonProperty("phone") String phone,
                            @JsonProperty("email") String email,
                            @JsonProperty("address") String address,
                            @JsonProperty("timestamp") String timeStamp,
                            @JsonProperty("warehouse") String warehouse,
                            @JsonProperty("cashOnDelivery") String cod,
                            @JsonProperty("comment") String comment,
                            @JsonProperty("itemType") String itemType,
                            @JsonProperty("deliveryStatus") boolean deliveryStatus) {
        this.tid = tid;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.timeStamp = timeStamp;
        this.warehouse = warehouse;
        this.cod = cod;
        this.comment = comment;
        this.itemType = itemType;
        this.deliveryStatus = deliveryStatus;
    }

    /**
     * Converts a given {@code Order} into this class for Jackson use.
     */
    public JsonAdaptedOrder(Order source) {
        tid = source.getTid().tid;
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        timeStamp = source.getTimestamp().value;
        warehouse = source.getWarehouse().address;
        cod = source.getCash().cashOnDelivery;
        comment = source.getComment().commentMade;
        itemType = source.getItemType().itemType;
        deliveryStatus = source.isDelivered();
    }

    /**
     * Converts this Jackson-friendly adapted order object into the model's {@code Order} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted order.
     */
    public Order toModelType() throws IllegalValueException {
        logger.fine("Converting Json adapted order to order");

        if (tid == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TransactionId.class.getSimpleName()));
        }
        if (!TransactionId.isValidTid(tid)) {
            throw new IllegalValueException(TransactionId.MESSAGE_CONSTRAINTS);
        }
        final TransactionId modelTid = new TransactionId(tid);

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (timeStamp == null) {
            logger.info("Encountered null for timestamp");
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TimeStamp.class.getSimpleName()));
        }
        if (!TimeStamp.isValidTimeStamp(timeStamp)) {
            throw new IllegalValueException(TimeStamp.MESSAGE_CONSTRAINTS);
        }
        final TimeStamp modelTimeStamp = new TimeStamp(timeStamp);

        if (warehouse == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Warehouse.class.getSimpleName()));
        }
        if (!Warehouse.isValidAddress(warehouse)) {
            throw new IllegalValueException(Warehouse.MESSAGE_CONSTRAINTS);
        }
        final Warehouse modelWarehouse = new Warehouse(warehouse);

        if (cod == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    CashOnDelivery.class.getSimpleName()));
        }
        if (!CashOnDelivery.isValidCashValue(cod)) {
            throw new IllegalValueException(CashOnDelivery.MESSAGE_CONSTRAINTS);
        }
        final CashOnDelivery modelCash = new CashOnDelivery(cod);

        final Comment modelComment;
        if (comment == null) {
            logger.fine("No comment for the order.");
            modelComment = new Comment("NIL");
        } else {
            logger.fine("Check whether the comment is valid");
            if (!Comment.isValidComment(comment)) {
                logger.info("Empty comment encountered");
                throw new IllegalValueException(Comment.MESSAGE_CONSTRAINTS);
            }
            modelComment = new Comment(comment);
        }

        final TypeOfItem modelItem;
        if (itemType == null) {
            modelItem = new TypeOfItem("NIL");
        } else {
            if (!TypeOfItem.isValidItemType(itemType)) {
                throw new IllegalValueException(TypeOfItem.MESSAGE_CONSTRAINTS);
            }
            modelItem = new TypeOfItem(itemType);
        }

        Order finalOrder = new Order(modelTid, modelName, modelPhone, modelEmail, modelAddress, modelTimeStamp,
                modelWarehouse, modelCash, modelComment, modelItem);
        finalOrder.setDeliveryStatus(deliveryStatus);
        return finalOrder;
    }

}
