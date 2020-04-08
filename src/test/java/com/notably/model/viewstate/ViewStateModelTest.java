package com.notably.model.viewstate;

import static com.notably.testutil.Assert.assertThrows;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ViewStateModelTest {
    private ViewStateModel viewStateModel;

    @BeforeEach
    public void setUpBeforeEach() {
        viewStateModel = new ViewStateModelImpl();
    }

    //============ HelpFlagModelModel =============================================================
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
}
