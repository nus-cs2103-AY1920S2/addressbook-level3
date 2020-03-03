package seedu.address.storage.storageFinance;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.modelFinance.Finance;
import seedu.address.model.person.Amount;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedFinance {

  public static final String MISSING_FIELD_MESSAGE_FORMAT = "Finance's %s field is missing!";

  private final String name;
  private final String amount;
  private final List<JsonFinanceAdaptedTag> tagged = new ArrayList<>();

  /**
   * Constructs a {@code JsonAdaptedPerson} with the given person details.
   */
  @JsonCreator
  public JsonAdaptedFinance(@JsonProperty("name") String name,
      @JsonProperty("amount") String amount,
      @JsonProperty("tagged") List<JsonFinanceAdaptedTag> tagged) {
    this.name = name;
    this.amount = amount;
    if (tagged != null) {
      this.tagged.addAll(tagged);
    }
  }

  /**
   * Converts a given {@code Finance} into this class for Jackson use.
   */
  public JsonAdaptedFinance(Finance source) {
    name = source.getName().fullName;
    amount = source.getAmount().value;
    tagged.addAll(source.getTags().stream()
        .map(JsonFinanceAdaptedTag::new)
        .collect(Collectors.toList()));
  }

  /**
   * Converts this Jackson-friendly adapted finance object into the model's {@code Finance} object.
   *
   * @throws IllegalValueException if there were any data constraints violated in the adapted
   *                               finance.
   */
  public Finance toModelType() throws IllegalValueException {
    final List<Tag> financeTags = new ArrayList<>();
    for (JsonFinanceAdaptedTag tag : tagged) {
      financeTags.add(tag.toModelType());
    }

    if (name == null) {
      throw new IllegalValueException(
          String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
    }
    if (!Name.isValidName(name)) {
      throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
    }
    final Name modelName = new Name(name);

    if (amount == null) {
      throw new IllegalValueException(
          String.format(MISSING_FIELD_MESSAGE_FORMAT, Finance.class.getSimpleName()));
    }
    if (!Amount.isValidAmount(amount)) {
      throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
    }
    final Amount modelAmount = new Amount(amount);

    final Set<Tag> modelTags = new HashSet<>(financeTags);
    return new Finance(modelName, modelAmount, modelTags);
  }

}
