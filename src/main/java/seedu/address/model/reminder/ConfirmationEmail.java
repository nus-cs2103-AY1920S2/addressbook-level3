package seedu.address.model.reminder;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import seedu.address.model.person.User;

/**
 * Represents a confirmation email, to confirm that the user email is a valid email.
 */
public class ConfirmationEmail {
    private final User user;
    private MimeMessage message;

    private final int pin;

    private final String emailUsername = "noreply.loansharkie";
    private final String emailPassword = "cs2103t-w12-3";

    /**
     * Constructs a ConfirmationEmail object.
     */
    public ConfirmationEmail(User user) {
        requireAllNonNull(user);
        this.user = user;

        Random random = new Random();
        this.pin = random.nextInt((9999 - 100) + 1) + 10;
    }

    /**
     * Sends an confirmation email to the user.
     * @throws MessagingException if error occurs while sending the email.
     */
    public void sendConfirmationEmail() throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailUsername, emailPassword);
            }
        });

        message = new MimeMessage(session);
        constructMessage();

        Transport.send(message);
    }

    /**
     * Constructs the content of the email.
     * @return A message to be sent.
     * @throws MessagingException if error occurs while sending the email.
     */
    private MimeMessage constructMessage() throws MessagingException {
        String content = "<p>Dear " + user.getName() + ",</p><br><p>Thank you for using&nbsp;<strong>"
                + "Sharkie</strong>!</p><br><p>Your confirmation PIN is&nbsp;<span style=\"text-decoration: "
                + "underline;\"><span style=\"color: #ff0000;\"><strong><span style=\"background-color: #ffff00;\">"
                + pin + "</span></strong></span></span><span>.</p><br><p>Have a good day and enjoy your experience "
                + "at&nbsp;<strong>Sharkie</strong>! :)</p><br><p>Regards,</p><p>Sharkie.</p>";

        message.setFrom(new InternetAddress("noreply.loansharkie@gmail.com"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail().toString()));
        message.setSubject("[Sharkie] Confirmation of signing into Sharkie");
        message.setContent(content, "text/html; charset=utf-8");

        return message;
    }

    public boolean isSamePin(String pin) {
        return this.pin == Integer.parseInt(pin);
    }

    public User getUser() {
        return this.user;
    }

    /**
     * Returns true if both userName and userEmail are the same.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ConfirmationEmail)) {
            return false;
        }

        ConfirmationEmail otherConfirmationEmail = (ConfirmationEmail) other;
        return otherConfirmationEmail.getUser().equals(getUser());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(user);
    }
}
