package com.notably.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

/**
 *
 */
public class BlockContentEditView extends ViewPart<Region> {

    private static final String sampleText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed eiusmod "
            + "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation "
            + "ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in "
            + "voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non "
            + "proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n" + "\n"
            + "The quick brown fox jumps over the lazy dog. The quick brown fox jumps over the lazy dog. The quick "
            + "brown fox jumps over the lazy dog. The quick brown fox jumps over the lazy dog. The quick brown fox "
            + "jumps over the lazy dog. The quick brown fox jumps over the lazy dog. The quick brown fox jumps over "
            + "the lazy dog. The quick brown fox jumps over the lazy dog.\n" + "\n"
            + "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore "
            + "et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut "
            + "aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse "
            + "cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa "
            + "qui officia deserunt mollit anim id est laborum.";

    private static final String FXML = "BlockContentEditView.fxml";

    @FXML
    private TextArea blockContentTextArea;

    public BlockContentEditView() {
        super(FXML);
        initializeBlockContent();
    }

    private void initializeBlockContent() {
        blockContentTextArea.setText(sampleText);
    }
}
