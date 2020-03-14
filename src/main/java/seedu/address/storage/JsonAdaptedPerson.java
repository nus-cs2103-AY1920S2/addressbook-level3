package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.comment.Comment;
import seedu.address.model.order.Address;
import seedu.address.model.order.CashOnDelivery;
import seedu.address.model.order.Name;
import seedu.address.model.order.Order;
import seedu.address.model.order.Phone;
import seedu.address.model.order.TransactionID;
import seedu.address.model.order.Warehouse;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Order}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Order's %s field is missing!";

    private final String tid;
    private final String name;
    private final String phone;
    private final String address;
    private final String warehouse;
    private final String cod;
    private final String comment;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given order details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("tid") String tid, @JsonProperty("name") String name,
                             @JsonProperty("phone") String phone, @JsonProperty("address") String address,
                             @JsonProperty("warehouse") String warehouse, @JsonProperty("cashOnDelivery") String cod,
                             @JsonProperty("comment") String comment, @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.tid = tid;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.warehouse = warehouse;
        this.cod = cod;
        this.comment = comment;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Order source) {
        tid = source.getTID().tid;
        name = source.getName().fullName;
        phone = source.getPhone().value;
        address = source.getAddress().value;
        warehouse = source.getWarehouse().address;
        cod = source.getCash().cashOnDelivery;
        comment = source.getComment().commentMade;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Order toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (tid == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TransactionID.class.getSimpleName()));
        }
        if (!TransactionID.isValidTID(tid)) {
            throw new IllegalValueException(TransactionID.MESSAGE_CONSTRAINTS);
        }
        final TransactionID modelTID = new TransactionID(tid);

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

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

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
            modelComment = new Comment("NIL");
        } else {
            if (!Comment.isValidComment(comment)) {
                throw new IllegalValueException(Comment.MESSAGE_CONSTRAINTS);
            }
            modelComment = new Comment(comment);
        }

        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Order(modelTID, modelName, modelPhone, modelAddress, modelWarehouse,
                modelCash, modelComment, modelTags);
    }

}
