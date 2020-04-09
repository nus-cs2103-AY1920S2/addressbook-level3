package seedu.address.testutil;

import static seedu.address.logic.commands.ImportCommand.OT_ORDER;
import static seedu.address.logic.commands.ImportCommand.OT_RETURN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERY_TIMESTAMP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDERTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RETURN_TIMESTAMP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WAREHOUSE;

//@@author Exeexe93
/**
 * A utility class to help with building order or return order objects.
 */
public class CsvBuilder {

    public static final String DEFAULT_OT_ORDER = PREFIX_ORDERTYPE + OT_ORDER;
    public static final String DEFAULT_OT_RETURN = PREFIX_ORDERTYPE + OT_RETURN;
    public static final String DEFAULT_TID = PREFIX_TID + "A98765431";
    public static final String DEFAULT_NAME = PREFIX_NAME + "Alice Pauline";
    public static final String DEFAULT_PHONE = PREFIX_PHONE + "85355255";
    public static final String DEFAULT_EMAIL = "amy@example.com";
    public static final String DEFAULT_ADDRESS = PREFIX_ADDRESS + "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_TIMESTAMP = PREFIX_DELIVERY_TIMESTAMP + "2020-02-20 1500";
    public static final String DEFAULT_WAREHOUSE = PREFIX_WAREHOUSE + "5 Toh Guan Rd E, #02-30 S608831";
    public static final String DEFAULT_COD = PREFIX_COD + "$3";
    public static final String DEFAULT_COMMENT = PREFIX_COMMENT + "NIL";
    public static final String DEFAULT_TYPE = PREFIX_TYPE + "Plastic";

    private String result;
    private String orderType;
    private String tid;
    private String name;
    private String phone;
    private String address;
    private String warehouse;
    private String timestamp;
    private String cod;
    private String comment;
    private String type;
    private String email;

    /**
     * Generate a empty string for customise.
     */
    public CsvBuilder() {
        orderType = "";
        tid = "";
        name = "";
        phone = "";
        address = "";
        warehouse = "";
        timestamp = "";
        cod = "";
        comment = "";
        type = "";
        email = "";
        result = "";
    }

    /**
     * Constructor of CsvBuilder, which generate default order or return order csv data based on {@code orderType}.
     *
     * @param orderType can either be order or return.
     */
    public CsvBuilder(String orderType) {
        if (orderType.equals(OT_ORDER)) {
            result = DEFAULT_OT_ORDER + DEFAULT_TID + DEFAULT_NAME + DEFAULT_PHONE + DEFAULT_ADDRESS
                    + DEFAULT_TIMESTAMP + DEFAULT_EMAIL + DEFAULT_WAREHOUSE + DEFAULT_COD + DEFAULT_COMMENT
                    + DEFAULT_TYPE;
        } else {
            result = DEFAULT_OT_RETURN + DEFAULT_TID + DEFAULT_NAME + DEFAULT_PHONE + DEFAULT_ADDRESS
                    + DEFAULT_TIMESTAMP + DEFAULT_EMAIL + DEFAULT_WAREHOUSE + DEFAULT_COMMENT + DEFAULT_TYPE;
        }
    }


    /**
     * Sets {@code numOfSpaceBeforePrefix}, {@code numOfSpaceAfterPrefix}, {@code numOfSpaceAfterValue}, prefix and
     * {@code value} of the orderType.
     */
    public CsvBuilder withOrderType(int numOfSpaceBeforePrefix, int numOfSpaceAfterPrefix,
                                    int numOfSpaceAfterValue, String value) {
        orderType += addWhiteSpace(numOfSpaceBeforePrefix) + PREFIX_ORDERTYPE + addWhiteSpace(numOfSpaceAfterPrefix)
                + value + addWhiteSpace(numOfSpaceAfterValue);
        return this;
    }

    /**
     * Sets {@code value} of the orderType without the prefix of the OT.
     */
    public CsvBuilder withOutOrderTypePrefix(String value) {
        orderType += value + ",";
        return this;
    }

    /**
     * Sets {@code numOfSpaceBeforePrefix}, {@code numOfSpaceAfterPrefix}, {@code numOfSpaceAfterValue}, prefix and
     * {@code value} of the tid.
     */
    public CsvBuilder withTid(int numOfSpaceBeforePrefix, int numOfSpaceAfterPrefix,
                              int numOfSpaceAfterValue, String value) {
        tid += addWhiteSpace(numOfSpaceBeforePrefix) + PREFIX_TID + addWhiteSpace(numOfSpaceAfterPrefix)
                + value + addWhiteSpace(numOfSpaceAfterValue) + ",";
        return this;
    }

    /**
     * Sets {@code numOfSpaceBeforePrefix}, {@code numOfSpaceAfterPrefix}, {@code numOfSpaceAfterValue}, prefix and
     * {@code value} of the name.
     */
    public CsvBuilder withName(int numOfSpaceBeforePrefix, int numOfSpaceAfterPrefix,
                               int numOfSpaceAfterValue, String value) {
        name += addWhiteSpace(numOfSpaceBeforePrefix) + PREFIX_NAME + addWhiteSpace(numOfSpaceAfterPrefix)
                + value + addWhiteSpace(numOfSpaceAfterValue) + ",";
        return this;
    }

    /**
     * Sets {@code numOfSpaceBeforePrefix}, {@code numOfSpaceAfterPrefix}, {@code numOfSpaceAfterValue}, prefix and
     * {@code value} of the address.
     */
    public CsvBuilder withAddress(int numOfSpaceBeforePrefix, int numOfSpaceAfterPrefix,
                                  int numOfSpaceAfterValue, String value) {
        address += addWhiteSpace(numOfSpaceBeforePrefix) + PREFIX_ADDRESS + addWhiteSpace(numOfSpaceAfterPrefix)
                + value + addWhiteSpace(numOfSpaceAfterValue) + ",";
        return this;
    }

    /**
     * Sets {@code numOfSpaceBeforePrefix}, {@code numOfSpaceAfterPrefix}, {@code numOfSpaceAfterValue}, prefix and
     * {@code value} of the phone.
     */
    public CsvBuilder withPhone(int numOfSpaceBeforePrefix, int numOfSpaceAfterPrefix,
                                int numOfSpaceAfterValue, String value) {
        phone += addWhiteSpace(numOfSpaceBeforePrefix) + PREFIX_PHONE + addWhiteSpace(numOfSpaceAfterPrefix)
                + value + addWhiteSpace(numOfSpaceAfterValue) + ",";
        return this;
    }

    /**
     * Sets {@code numOfSpaceBeforePrefix}, {@code numOfSpaceAfterPrefix}, {@code numOfSpaceAfterValue}, prefix and
     * {@code value} of the delivery time stamp.
     */
    public CsvBuilder withDeliveryTimeStamp(int numOfSpaceBeforePrefix, int numOfSpaceAfterPrefix,
                                            int numOfSpaceAfterValue, String value) {
        timestamp += addWhiteSpace(numOfSpaceBeforePrefix) + PREFIX_DELIVERY_TIMESTAMP
                + addWhiteSpace(numOfSpaceAfterPrefix) + value + addWhiteSpace(numOfSpaceAfterValue) + ",";
        return this;
    }

    /**
     * Sets {@code numOfSpaceBeforePrefix}, {@code numOfSpaceAfterPrefix}, {@code numOfSpaceAfterValue}, prefix and
     * {@code value} of the return time stamp.
     */
    public CsvBuilder withReturnTimeStamp(int numOfSpaceBeforePrefix, int numOfSpaceAfterPrefix,
                                          int numOfSpaceAfterValue, String value) {
        timestamp += addWhiteSpace(numOfSpaceBeforePrefix) + PREFIX_RETURN_TIMESTAMP
                + addWhiteSpace(numOfSpaceAfterPrefix) + value + addWhiteSpace(numOfSpaceAfterValue) + ",";
        return this;
    }

    /**
     * Sets {@code numOfSpaceBeforePrefix}, {@code numOfSpaceAfterPrefix}, {@code numOfSpaceAfterValue}, prefix and
     * {@code value} of the email.
     */
    public CsvBuilder withEmail(int numOfSpaceBeforePrefix, int numOfSpaceAfterPrefix,
                                int numOfSpaceAfterValue, String value) {
        email += addWhiteSpace(numOfSpaceBeforePrefix) + PREFIX_EMAIL + addWhiteSpace(numOfSpaceAfterPrefix)
                + value + addWhiteSpace(numOfSpaceAfterValue) + ",";
        return this;
    }

    /**
     * Sets {@code numOfSpaceBeforePrefix}, {@code numOfSpaceAfterPrefix}, {@code numOfSpaceAfterValue}, prefix and
     * {@code value} of the warehouse.
     */
    public CsvBuilder withWarehouse(int numOfSpaceBeforePrefix, int numOfSpaceAfterPrefix,
                                    int numOfSpaceAfterValue, String value) {
        warehouse += addWhiteSpace(numOfSpaceBeforePrefix) + PREFIX_WAREHOUSE + addWhiteSpace(numOfSpaceAfterPrefix)
                + value + addWhiteSpace(numOfSpaceAfterValue) + ",";
        return this;
    }

    /**
     * Sets {@code numOfSpaceBeforePrefix}, {@code numOfSpaceAfterPrefix}, {@code numOfSpaceAfterValue}, prefix and
     * {@code value} of the cashOnDelivery.
     */
    public CsvBuilder withCod(int numOfSpaceBeforePrefix, int numOfSpaceAfterPrefix,
                              int numOfSpaceAfterValue, String value) {
        cod += addWhiteSpace(numOfSpaceBeforePrefix) + PREFIX_COD + addWhiteSpace(numOfSpaceAfterPrefix)
                + value + addWhiteSpace(numOfSpaceAfterValue) + ",";
        return this;
    }

    /**
     * Sets {@code numOfSpaceBeforePrefix}, {@code numOfSpaceAfterPrefix}, {@code numOfSpaceAfterValue}, prefix and
     * {@code value} of the comment.
     */
    public CsvBuilder withComment(int numOfSpaceBeforePrefix, int numOfSpaceAfterPrefix,
                                  int numOfSpaceAfterValue, String value) {
        comment += addWhiteSpace(numOfSpaceBeforePrefix) + PREFIX_COMMENT + addWhiteSpace(numOfSpaceAfterPrefix)
                + value + addWhiteSpace(numOfSpaceAfterValue) + ",";
        return this;
    }

    /**
     * Sets {@code numOfSpaceBeforePrefix}, {@code numOfSpaceAfterPrefix}, {@code numOfSpaceAfterValue}, prefix and
     * {@code value} of the type of item.
     */
    public CsvBuilder withTypeOfItem(int numOfSpaceBeforePrefix, int numOfSpaceAfterPrefix,
                                     int numOfSpaceAfterValue, String value) {
        type += addWhiteSpace(numOfSpaceBeforePrefix) + PREFIX_TYPE + addWhiteSpace(numOfSpaceAfterPrefix)
                + value + addWhiteSpace(numOfSpaceAfterValue) + ",";
        return this;
    }

    /**
     * Add the amount of white space to the result string, which depends on {@code num}.
     *
     * @param num number of whitespace to be added.
     */
    private String addWhiteSpace(int num) {
        String whiteSpaces = "";
        for (int i = 0; i < num; i++) {
            whiteSpaces += " ";
        }
        return whiteSpaces;
    }

    /**
     * Construct the whole order or return order sentence with the relevant information in csv format.
     * @return a order in csv format.
     */
    public String build() {
        result = orderType + tid + name + address + phone + email + timestamp + warehouse + cod + comment + type;
        return result;
    }
}
