package com.notably.model.viewstate;

import static com.notably.testutil.Assert.assertThrows;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ViewStateModelTest {
    private ViewStateModel viewStateModel;

    @BeforeEach
    public void setUpBeforeEach() {
        viewStateModel = new ViewStateModelImpl();
    }

    //============ CommandInputModel ==============================================================
    @Test
    public void inputProperty_void_returnsValidProperty() {
        assertNotNull(viewStateModel.inputProperty());
    }

    @Test
    public void getInput_void_returnsValidString() {
        assertNotNull(viewStateModel.getInput());
    }

    @Test
    public void setInput_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> viewStateModel.setInput(null));
    }

    @Test
    public void setInputAndGetInput() {
        final String input = "This is the new input";
        viewStateModel.setInput(input);
        assertEquals(input, viewStateModel.getInput());
    }

    //============ HelpFlagModel ==================================================================
    @Test
    public void helpOpenProperty_void_returnsValidProperty() {
        assertNotNull(viewStateModel.helpOpenProperty());
    }

    @Test
    public void isHelpOpen_void_returnsValidBoolean() {
        assertNotNull(viewStateModel.isHelpOpen());
    }

    @Test
    public void setHelpOpen_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> viewStateModel.setHelpOpen(null));
    }

    @Test
    public void setHelpOpenAndGetIsHelpOpen() {
        viewStateModel.setHelpOpen(true);
        assertEquals(true, viewStateModel.isHelpOpen());
        viewStateModel.setHelpOpen(false);
        assertEquals(false, viewStateModel.isHelpOpen());
    }

    //============ BlockEditFlagModel =============================================================
    @Test
    public void blockEditableProperty_void_returnsValidProperty() {
        assertNotNull(viewStateModel.blockEditableProperty());
    }

    @Test
    public void isBlockEditable_void_returnsValidBoolean() {
        assertNotNull(viewStateModel.isBlockEditable());
    }

    @Test
    public void setBlockEditable_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> viewStateModel.setBlockEditable(null));
    }

    @Test
    public void setBlockEditableAndGetBlockEditable() {
        viewStateModel.setBlockEditable(true);
        assertEquals(true, viewStateModel.isBlockEditable());
        viewStateModel.setBlockEditable(false);
        assertEquals(false, viewStateModel.isBlockEditable());
    }
}
