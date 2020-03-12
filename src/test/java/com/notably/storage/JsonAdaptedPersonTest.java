package com.notably.storage;

import org.junit.jupiter.api.Test;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";



    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {

    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {


    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {

    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {

    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {

    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {

    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {

    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {

    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {

    }

}
