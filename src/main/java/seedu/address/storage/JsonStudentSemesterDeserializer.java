package seedu.address.storage;

import java.io.IOException;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.time.StudentSemester;

public class JsonStudentSemesterDeserializer extends KeyDeserializer {
    @Override
    public StudentSemester deserializeKey(String key, DeserializationContext ctxt) throws IOException {
        try {
            return new JsonAdaptedStudentSemester(JsonUtil.fromJsonString(key, StudentSemester.class)).toModelType();
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
        return null;
    }
}
