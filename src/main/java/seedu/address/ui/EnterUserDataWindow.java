package seedu.address.ui;

import static seedu.address.commons.core.Messages.MESSAGE_EMAIL_ERROR;

import java.io.IOException;
import java.util.logging.Logger;

import javax.mail.MessagingException;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.User;
import seedu.address.model.reminder.ConfirmationEmail;

/**
 * The UI component that is responsible for receiving user' data.
 */
public class EnterUserDataWindow extends UiPart<Stage> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String MESSAGE_MISSING_DATA = "Your details are missing. \nTo use Sharkie, "
            + "please enter your details: ";
    private static final String MESSAGE_INVALID_DETAILS = "The details you keyed in are invalid. \n"
            + "Kindly re-enter your details: \n";
    private static final String MESSAGE_SHOWING_WINDOW = "Showing window to enter user data.";
    private static final String MESSAGE_STORE_DATA_SUCCESS = "User data stored.";
    private static final String MESSAGE_CONFIRMATION_SENT_SUCCESS = "A confirmation email is sent.";
    private static final String MESSAGE_RESENT_PIN = "A confirmation PIN is re-sent to you.";
    private static final String MESSAGE_INVALID_NAME = "Your name should not be empty or start with a whitespace!\n";
    private static final String MESSAGE_INVALID_PHONE = "Your phone number should consist of 3 or more digits!\n";
    private static final String MESSAGE_INVALID_EMAIL = "Your email should be a valid email!\n";
    private static final String MESSAGE_INVALID_PIN = "Invalid PIN! Please try again.";

    private static final Logger logger = LogsCenter.getLogger(EnterUserDataWindow.class);
    private static final String FXML = "EnterUserDataWindow.fxml";

    public final UserDataStorageManager userDataStorageManager;

    @FXML
    protected Label instructionMessage;

    @FXML
    private Label errorMessageName;

    @FXML
    private Label errorMessagePhone;

    @FXML
    private Label errorMessageEmail;

    @FXML
    private Label errorMessagePin;

    @FXML
    private TextField userNameTextField;

    @FXML
    private TextField userPhoneTextField;

    @FXML
    private TextField userEmailTextField;

    @FXML
    private TextField pinTextField;

    @FXML
    private Button submitButton;

    @FXML
    private Button confirmPinButton;

    @FXML
    private Button resendPinButton;

    @FXML
    private HBox pinBox;

    private ConfirmationEmail confirmationEmail;
    private Email initialEmail;

    /**
     * Creates a new EnterUserDataWindow.
     *
     * @param root Stage to use as the root of the EnterUserDataWindow.
     */
    public EnterUserDataWindow(Stage root, UserDataStorageManager userDataStorageManager) {
        super(FXML, root);
        this.userDataStorageManager = userDataStorageManager;
        instructionMessage.setText(MESSAGE_MISSING_DATA);

        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        userNameTextField.textProperty()
                .addListener((unused1, unused2, unused3) -> setStyleToDefault(userNameTextField));
        userPhoneTextField.textProperty()
                .addListener((unused1, unused2, unused3) -> setStyleToDefault(userPhoneTextField));
        userEmailTextField.textProperty()
                .addListener((unused1, unused2, unused3) -> setStyleToDefault(userEmailTextField));
        pinTextField.textProperty()
                .addListener((unused1, unused2, unused3) -> setStyleToDefault(pinTextField));
    }

    /**
     * Creates a new EnterUserDataWindow.
     */
    public EnterUserDataWindow(UserDataStorageManager userDataStorageManager) {
        this(new Stage(), userDataStorageManager);

        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        userNameTextField.textProperty()
                .addListener((unused1, unused2, unused3) -> setStyleToDefault(userNameTextField));
        userPhoneTextField.textProperty()
                .addListener((unused1, unused2, unused3) -> setStyleToDefault(userPhoneTextField));
        userEmailTextField.textProperty()
                .addListener((unused1, unused2, unused3) -> setStyleToDefault(userEmailTextField));
        pinTextField.textProperty()
                .addListener((unused1, unused2, unused3) -> setStyleToDefault(pinTextField));
    }

    /**
     * Shows the window to enter user data.
     *
     * @throws IllegalStateException <ul>
     *                               <li>
     *                               if this method is called on a thread other than the JavaFX Application
     *                               Thread.
     *                               </li>
     *                               <li>
     *                               if this method is called during animation or layout processing.
     *                               </li>
     *                               <li>
     *                               if this method is called on the primary stage.
     *                               </li>
     *                               <li>
     *                               if {@code dialogStage} is already showing.
     *                               </li>
     *                               </ul>
     */
    public void show() {
        logger.fine(MESSAGE_SHOWING_WINDOW);
        getRoot().show();
        getRoot().centerOnScreen();
        revertToInitialStyle();
    }

    /**
     * Returns true if the window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Focuses on the enter user data window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Hides the window to enter user data.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Handles the Submit button pressed event.
     */
    @FXML
    private void handleUserData() throws IOException {
        if (isAllFieldValid() && isEmailModified()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    pinBox.setVisible(true);
                    confirmPinButton.setVisible(true);
                    resendPinButton.setVisible(true);
                    submitButton.setVisible(false);
                }
            }).start();

            // send confirmation email to confirm the email entered is a valid email.
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        confirmationEmail = new ConfirmationEmail(new User(new Name(userNameTextField.getText()),
                                new Phone(userPhoneTextField.getText()),
                                new Email(userEmailTextField.getText())));
                        confirmationEmail.sendConfirmationEmail();
                        logger.info(MESSAGE_CONFIRMATION_SENT_SUCCESS);
                    } catch (MessagingException e) {
                        MainWindow.editResultDisplay(String.format(MESSAGE_EMAIL_ERROR, e.getMessage()));
                        hide();
                    }
                }
            }).start();
        } else if (isAllFieldValid()) {
            userDataStorageManager.storeUserData(userNameTextField.getText(),
                    userPhoneTextField.getText(), userEmailTextField.getText());
            logger.info(MESSAGE_STORE_DATA_SUCCESS);
            hide();
        } else {
            setStyleToIndicateInputFailure();
        }
    }

    /**
     * Handles the confirm PIN button pressed event.
     */
    @FXML
    private void handlePin() throws IOException {
        try {
            if (confirmationEmail.isSamePin(pinTextField.getText())) {
                userDataStorageManager.storeUserData(userNameTextField.getText(),
                        userPhoneTextField.getText(), userEmailTextField.getText());
                logger.info(MESSAGE_STORE_DATA_SUCCESS);
                hide();
            } else {
                setStyleToIndicateInvalidPin();
            }
        } catch (IllegalArgumentException e) {
            setStyleToIndicateInputFailure();
        }
    }

    /**
     * Handles the resend PIN button pressed event.
     */
    @FXML
    private void handleResendPin() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    confirmationEmail = new ConfirmationEmail(new User(new Name(userNameTextField.getText()),
                            new Phone(userPhoneTextField.getText()),
                            new Email(userEmailTextField.getText())));
                    confirmationEmail.sendConfirmationEmail();
                    logger.info(MESSAGE_CONFIRMATION_SENT_SUCCESS);
                } catch (MessagingException e) {
                    MainWindow.editResultDisplay(String.format(MESSAGE_EMAIL_ERROR, e.getMessage()));

                    hide();
                }
            }
        }).start();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                errorMessagePin.setText(MESSAGE_RESENT_PIN);
            }
        });
    }

    /**
     * Returns true if the name, phone and email entered by the user are all valid.
     */
    private boolean isAllFieldValid() {
        return Name.isValidName(userNameTextField.getText())
                && Phone.isValidPhone(userPhoneTextField.getText())
                && Email.isValidEmail(userEmailTextField.getText());
    }

    /**
     * Returns true if the email in userEmailTextField is not the same as the email saved in user data storage.
     */
    private boolean isEmailModified() {
        if (initialEmail != null && initialEmail.toString().equals(userEmailTextField.getText())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Sets the text field style to use the default style.
     */
    private void setStyleToDefault(TextField textField) {
        textField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the text fields style to indicate an invalid name, phone or email.
     */
    public void setStyleToIndicateInputFailure() {
        instructionMessage.setText(MESSAGE_INVALID_DETAILS);
        errorMessageName.setText("");
        errorMessagePhone.setText("");
        errorMessageEmail.setText("");

        ObservableList<String> styleClassName = userNameTextField.getStyleClass();
        ObservableList<String> styleClassPhone = userPhoneTextField.getStyleClass();
        ObservableList<String> styleClassEmail = userEmailTextField.getStyleClass();

        if (!Name.isValidName(userNameTextField.getText())) {
            styleClassName.add(ERROR_STYLE_CLASS);
            errorMessageName.setText(MESSAGE_INVALID_NAME);
            userNameTextField.setText("");
            userNameTextField.setPromptText("Invalid name!");
        }

        if (!Phone.isValidPhone(userPhoneTextField.getText())) {
            styleClassPhone.add(ERROR_STYLE_CLASS);
            errorMessagePhone.setText(MESSAGE_INVALID_PHONE);
            userPhoneTextField.setText("");
            userPhoneTextField.setPromptText("Invalid phone number!");
        }

        if (!Email.isValidEmail(userEmailTextField.getText())) {
            styleClassEmail.add(ERROR_STYLE_CLASS);
            errorMessageEmail.setText(MESSAGE_INVALID_EMAIL);
            userEmailTextField.setText("");
            userEmailTextField.setPromptText("Invalid address!");
        }
    }

    /**
     * Sets the PIN text fields style to indicate an invalid PIN.
     */
    public void setStyleToIndicateInvalidPin() {
        ObservableList<String> styleClassPin = pinTextField.getStyleClass();
        styleClassPin.add(ERROR_STYLE_CLASS);
        errorMessagePin.setText(MESSAGE_INVALID_PIN);
        pinTextField.setText("");
        pinTextField.setPromptText("Invalid PIN!");
    }

    /**
     * Automatically fills in the user details in respective field in enter user data window.
     */
    public void fillInUserDetails(User user) {
        userNameTextField.setText(user.getName().toString());
        userPhoneTextField.setText(user.getPhone().toString());
        userEmailTextField.setText(user.getEmail().toString());
        initialEmail = user.getEmail();
    }

    /**
     * Changes the window back to what it is like when it is first launched.
     */
    private void revertToInitialStyle() {
        errorMessageName.setText("");
        errorMessagePhone.setText("");
        errorMessageEmail.setText("");
        userNameTextField.setText("");
        userPhoneTextField.setText("");
        userEmailTextField.setText("");
        pinTextField.setText("");
        submitButton.setVisible(true);
        pinBox.setVisible(false);
        confirmPinButton.setVisible(false);
        resendPinButton.setVisible(false);
    }

    /**
     * Represents a function that stores user data.
     */
    @FunctionalInterface
    public interface UserDataStorageManager {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.address.logic.Logic#storeUserData(String, String, String)
         */
        void storeUserData(String name, String phone, String email) throws IllegalArgumentException, IOException;
    }

}
