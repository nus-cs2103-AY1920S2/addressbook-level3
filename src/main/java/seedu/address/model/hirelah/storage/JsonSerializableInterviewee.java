package seedu.address.model.hirelah.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.hirelah.Attribute;
import seedu.address.model.hirelah.Interviewee;
import seedu.address.model.hirelah.IntervieweeList;

/**
 * An Immutable Interviewee that is serializable to JSON format {@link IntervieweeList}.
 */
@JsonRootName(value = "interviewees")
class JsonSerializableInterviewee {
    public static final String MESSAGE_DUPLICATE_INTERVIEWEE = "Interviewee list contains duplicate";
    private final List<JsonAdaptedInterviewee> interviewees = new ArrayList<>();

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     * @param interviewees future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
   @JsonCreator
    public JsonSerializableInterviewee(@JsonProperty("interviewees") List<JsonAdaptedInterviewee> interviewees) {
        this.interviewees.addAll(interviewees);
    }

   /*@JsonCreator
    public JsonSerializableInterviewee(@JsonProperty("interviewees") Map<Attribute,Double> testing) {

    }*/
    public JsonSerializableInterviewee(IntervieweeList source) {
        List<Interviewee> convertedList = source.getObservableList();
        interviewees.addAll(convertedList.stream().map(JsonAdaptedInterviewee::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public IntervieweeList toModelType() throws IllegalValueException {
        IntervieweeList newData = new IntervieweeList();
      for (JsonAdaptedInterviewee jsonAdaptedInterviewee : interviewees) {
          Interviewee interviewee = jsonAdaptedInterviewee.toModelType();
          newData.addInterviewee(interviewee.getFullName()); // temporary only store the name first
      }
        return newData;
    }
}
